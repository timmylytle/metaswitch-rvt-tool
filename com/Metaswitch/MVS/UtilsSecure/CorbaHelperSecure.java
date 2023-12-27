/**
 * Title: CorbaHelperSecure
 *
 * Description: A utility class providing methods to start an Orbacus ORB in
 * secure mode.
 *
 * Copyright: Copyright (c) 2004
 *
 * Company: Metaswitch Networks
 *
 * @author Richard Underwood, Thomas Price and Alex Wilber, MetaView Team
 *
 * @version 1.0
 */
package com.Metaswitch.MVS.UtilsSecure;

import org.omg.CORBA.*;
import org.omg.PortableServer.POAManagerPackage.*;

import com.Metaswitch.MVS.Corba.*;
import com.Metaswitch.MVS.Utils.*;

public class CorbaHelperSecure extends CorbaHelper
{
  //---------------------------------------------------------------------------
  // The filenames of the security certificates which will be used by default,
  // if no certificate and key filenames are specified by the user.  There is
  // no CA key filename because the CA key is never used.
  //---------------------------------------------------------------------------
  private static final String CLIENT_CERT_FILENAME = "EMSClient.der";
  private static final String CA_CERT_FILENAME = "EMSCA.der";
  private static final String CLIENT_KEY_FILENAME = "EMSClient.key";

  //---------------------------------------------------------------------------
  // There are two different possible ways of logging into an EMS, both of
  // which are supported by this utility class:
  //
  // -  A secure user login.  The user will need to provide a username and
  //    password.  CorbaHelperSecure will use the default client certificates,
  //    since the security in this case comes from the username and password
  //    rather than the certificates.
  // -  A secure application login.  The user needs to supply secure
  //    certificates.  No username and password are required.
  //
  // The overloaded startORB method below can be used to make either of these
  // connection types - the comments with each method declaration indicate what
  // type of connection will be made.
  //
  // To perform an insecure user login, the startORB method in the CorbaHelper
  // superclass should be called.
  //
  // Note that insecure application logins are not possible.
  //---------------------------------------------------------------------------

  /**
   * Start the ORB in preparation for a user login, either secure or insecure.
   * Calls the main startOrb method below, setting the 'secure' parameter as
   * appropriate, and setting 'isUserLogin' to true.  All other parameters on
   * the main method are irrelevant.
   *
   * @param secure      Indicates whether to prepare for a secure user login
   *                    (if set to true), or an insecure login.
   */
  public static void startORB(boolean secure)
  {
    startORB(secure, true, null, null, null);
  }

  /**
   * Start the ORB in preparation for an application login.  The fact that
   * three parameters are passed indicates that an application login is
   * desired.  Calls the main startORB method below, setting 'secure' to true
   * and 'isUserLogin' to false, and passing the certificate and key filenames.
   *
   * @param caCertFilename
   *                    CA certificate filename.
   * @param clientCertFilename
   *                    Client certificate filename.
   * @param clientKeyFilename
   *                    Client key filename.
   */
  public static void startORB(String caCertFilename,
                              String clientCertFilename,
                              String clientKeyFilename)
  {
    startORB(true,
             false,
             caCertFilename,
             clientCertFilename,
             clientKeyFilename);
  }

  /**
   * Start the ORB.  No CORBA request will work until an ORB is started.
   *
   * @param secure      Indicates whether a secure connection should be made,
   *                    using the Orbacus ORB, or an insecure connection using
   *                    the Sun ORB.
   * @param isUserLogin
   *                    Indicates that a user login rather than an application
   *                    login will be performed. If false, the certificate and
   *                    key filenames will be used - if true they are ignored.
   * @param caCertFilename
   *                    The filename of the Certificate Authority (CA)
   *                    certificate
   * @param clientCertFilename
   *                    The filename of the client certificate
   * @param clientKeyFilename
   *                    The filename of the client key file
   */
  public static void startORB(boolean secure,
                              boolean isUserLogin,
                              String caCertFilename,
                              String clientCertFilename,
                              String clientKeyFilename)
  {
    //-------------------------------------------------------------------------
    // Set static variables using the supplied parameters, to make them
    // available to all other methods.
    //-------------------------------------------------------------------------
    CorbaHelper.sSecure = secure;
    CorbaHelper.sIsUserLogin = isUserLogin;

    synchronized (CorbaHelper.sOrbLock)
    {
      if (!CorbaHelper.sOrbRunning)
      {
        //---------------------------------------------------------------------
        // Use different initialising code depending on whether we are in
        // secure or insecure mode.
        //---------------------------------------------------------------------
        try
        {
          if (CorbaHelper.sSecure)
          {
            //-----------------------------------------------------------------
            // The process of setting up a secure ORB involves a number of
            // stages:
            //
            // -  Initialise in secure mode, using the Orbacus ORB.  See
            //    Orbacus documentation for details, but the initialisation has
            //    five phases:
            //   -  Call ORB.init() with properties specifying various
            //      behaviour options including the required communication
            //      protocols.  We use the bidirectional FSSL inter-ORB
            //      protocol.
            //   -  Resolve the FSSL context manager, which will be given the
            //      security certificates to use for authentication.
            //   -  Load the security certificate files, and create an FSSL
            //      context that uses them.  Set this as the default context.
            //   -  Create and activate the POA manager.
            //   -  Resolve the ORB policy manager and set up the protocol
            //      policies to use bidirectional FSSL.
            // -  When this is complete, the ORB will be ready to use to
            //    resolve remote interfaces, and it will communicate securely
            //    with the remote host.
            //-----------------------------------------------------------------

            //-----------------------------------------------------------------
            // Initialise in secure mode, using the Orbacus ORB.
            // First set up the ORB properties - see Orbacus documentation for
            // details.
            //-----------------------------------------------------------------
            java.util.Properties props = System.getProperties();

            props.put("org.omg.CORBA.ORBClass", "com.ooc.CORBA.ORB");
            props.put("org.omg.CORBA.ORBSingletonClass",
                      "com.ooc.CORBA.ORBSingleton");

            props.put("ooc.orb.conc_model", "threaded");
            props.put("ooc.orb.oa.conc_model", "thread_per_request");

            String protocolString = "fssliop, bidir --protocol fssliop";
            props.put("ooc.orb.policy.protocol", "bidir_fssliop");
            props.put("ooc.orb.oa.endpoint", "bidir_fssliop --callback");

            props.put("ooc.oci.client", protocolString);
            props.put("ooc.oci.server", protocolString);
            props.put("ooc.orb.oa.conc_model", "thread_pool");
            props.put("ooc.orb.oa.thread_pool", "10");
            props.put("ooc.orb.conc_model", "threaded");

            //-----------------------------------------------------------------
            // Initialise the ORB.
            //-----------------------------------------------------------------
            CorbaHelper.sOrb = ORB.init(new String[]{}, props);

            //-----------------------------------------------------------------
            // Create the POA Manager object.
            //-----------------------------------------------------------------
            org.omg.PortableServer.POAManager poaManager = null;

            //-----------------------------------------------------------------
            // Resolve the FSSL Context Manager.
            //-----------------------------------------------------------------
            com.ooc.FSSL.Manager fSSLManager = null;
            try
            {
              fSSLManager = com.ooc.FSSL.ManagerHelper.narrow(
                  CorbaHelper.sOrb.resolve_initial_references(
                                                        "FSSLContextManager"));
            }
            catch (org.omg.CORBA.ORBPackage.InvalidName e)
            {
              //---------------------------------------------------------------
              // Invalid Context Manager name error.
              //---------------------------------------------------------------
              throw new IllegalStateException("FSSL Context Manager name is"
                                                                 + " invalid");
            }

            //-----------------------------------------------------------------
            // If we are performing a user login, use the default certificate
            // filenames.  The CA key filename is not set as it is never
            // actually used.
            //-----------------------------------------------------------------
            if (CorbaHelper.sIsUserLogin)
            {
              caCertFilename = CA_CERT_FILENAME;
              clientCertFilename = CLIENT_CERT_FILENAME;
              clientKeyFilename = CLIENT_KEY_FILENAME;
            }

            //-----------------------------------------------------------------
            // Load the client certificate.
            //-----------------------------------------------------------------
            com.ooc.FSSL.Certificate clientCert = null;

            try
            {
              clientCert = fSSLManager.create_certificate(
                              com.ooc.FSSL.FSSL.load_file(clientCertFilename));
            }
            catch (BAD_PARAM e)
            {
              //---------------------------------------------------------------
              // Couldn't find the client certificate file.
              //---------------------------------------------------------------
              throw new IllegalStateException(
                                "Error while opening client certificate file '"
                                + clientCertFilename + "'\n"
                                + " - check that the filename is valid.");
            }

            //-----------------------------------------------------------------
            // Load the CA certificate.
            //-----------------------------------------------------------------
            com.ooc.FSSL.Certificate caCert = null;

            try
            {
              caCert = fSSLManager.create_certificate(
                                  com.ooc.FSSL.FSSL.load_file(caCertFilename));
            }
            catch (BAD_PARAM e)
            {
              //---------------------------------------------------------------
              // Couldn't find the CA certificate file.
              //---------------------------------------------------------------
              throw new IllegalStateException(
                                    "Error while opening CA certificate file '"
                                    + caCertFilename + "'\n"
                                    + " - check that the filename is valid.");
            }

            //-----------------------------------------------------------------
            // Create the certificate chain.  The secure Orbacus ORB requires
            // at least two certificates in this chain in order to communicate
            // with the server.
            //-----------------------------------------------------------------
            com.ooc.FSSL.Certificate[] chain = new com.ooc.FSSL.Certificate[2];
            chain[0] = clientCert;
            chain[1] = caCert;

            int id = 0;

            //-----------------------------------------------------------------
            // A hard-coded, easily guessable pass-phrase ("EmsEms") is used,
            // because a pass-phrase is required by the FSSL package but does
            // not add any security.
            //-----------------------------------------------------------------
            try
            {
              id = fSSLManager.create_context(
                     chain,
                     com.ooc.FSSL.FSSL.load_file(clientKeyFilename),
                     com.ooc.FSSL.FSSL.string_to_PassPhrase("EmsEms"),
                     new AlwaysTrust(),
                     com.ooc.FSSL.FSSL.get_RSA_ciphers());
            }
            catch (com.ooc.FSSL.ManagerPackage.BadCertificate e)
            {
              //---------------------------------------------------------------
              // Bad certificate error.
              //---------------------------------------------------------------
              throw new IllegalStateException(
                                  "One of the security certificates provided, "
                                  + clientCertFilename + " or "
                                  + caCertFilename + ", is invalid");
            }
            catch (com.ooc.FSSL.ManagerPackage.BadKey e)
            {
              //---------------------------------------------------------------
              // Bad key error.
              //---------------------------------------------------------------
              throw new IllegalStateException("The key provided ("
                                         + clientKeyFilename + ") is invalid");
            }
            catch (com.ooc.FSSL.ManagerPackage.BadCipher e)
            {
              //---------------------------------------------------------------
              // Bad cipher error.
              //---------------------------------------------------------------
              throw new IllegalStateException("The cipher used is invalid");
            }
            catch (org.omg.CORBA.BAD_PARAM e)
            {
              //---------------------------------------------------------------
              // This error is thrown if any of the filenames given are
              // incorrect.  However, if either of the certificate filenames
              // was incorrect an exception would have been thrown earlier.
              // Reaching this exception therefore indicates that the key
              // filename is incorrect.
              //---------------------------------------------------------------
              throw new IllegalStateException("The security key filename is "
                                            + "invalid: " + clientKeyFilename);
            }

            //-----------------------------------------------------------------
            // Set this as the default context for all object references.
            //-----------------------------------------------------------------
            try
            {
              fSSLManager.set_context(id);
            }
            catch (com.ooc.FSSL.ManagerPackage.NoContext e)
            {
              //---------------------------------------------------------------
              // Unexpected, as we just filled in the context above.
              //---------------------------------------------------------------
              throw new IllegalStateException("No context");
            }

            //-----------------------------------------------------------------
            // Create the POA Manager.
            //-----------------------------------------------------------------
            poaManager = com.ooc.FSSL.FSSL.create_poa_manager("RootPOAManager",
                                                              CorbaHelper.sOrb,
                                                              fSSLManager,
                                                              id,
                                                              props);

            try
            {
              //---------------------------------------------------------------
              // Activate the Root POA manager.
              //---------------------------------------------------------------
              poaManager.activate();
            }
            catch (org.omg.PortableServer.POAManagerPackage.AdapterInactive e)
            {
              //---------------------------------------------------------------
              // Error - adapter inactive.
              //---------------------------------------------------------------
              throw new IllegalStateException("Adapter inactive");
            }

            //-----------------------------------------------------------------
            // Set up ORB policies and Policy Manager.
            //-----------------------------------------------------------------
            Policy[] policies = new Policy[1];
            PolicyManager policyManager = PolicyManagerHelper.narrow(
              CorbaHelper.sOrb.resolve_initial_references("ORBPolicyManager"));
            policies = new org.omg.CORBA.Policy[1];
            policies[0] = new com.ooc.OB.ProtocolPolicy_impl(
                                     new String[]{"bidir_fssliop", "fssliop"});
            policyManager.add_policy_overrides(policies);
          }
          else
          {
            //-----------------------------------------------------------------
            // Insecure mode.
            //-----------------------------------------------------------------
            if (!isUserLogin)
            {
              //---------------------------------------------------------------
              // User is attempting to perform an application login in insecure
              // mode - this won't work, so throw an exception.
              //---------------------------------------------------------------
              throw new IllegalStateException(
                "Error - an application login cannot be performed in insecure "
                  + "mode. Either:\n"
                  + " - Perform a user login, supplying a username and "
                  + "password, or\n"
                  + " - Login in secure mode");
            }

            //-----------------------------------------------------------------
            // Call the insecure method in the superclass, CorbaHelper.
            //-----------------------------------------------------------------
            CorbaHelper.startORB();
          }
        }
        catch (Exception e)
        {
          //-------------------------------------------------------------------
          // Any exceptions not caught elsewhere in the code above are caught
          // here.  If it is an IllegalStateException then we should throw it
          // again - if not, it is an unexpected error, so we should print the
          // stack trace.
          //-------------------------------------------------------------------
          if (e instanceof IllegalStateException)
          {
            throw (IllegalStateException)e;
          }
          else
          {
            e.printStackTrace();
            throw new IllegalStateException("ORB Initialization failed");
          }
        }
      }
    }
  }

  /**
   * This class is used to determine whether or not a certificate can be
   * trusted.  It always indicates that a certificate can be trusted, i.e.
   * server authentication is not required.
   */
  public static class AlwaysTrust extends org.omg.CORBA.LocalObject
    implements com.ooc.FSSL.TrustDecider
  {
    /**
     * This method is the one that is accessed to verify that a certificate can
     * be trusted.  It automatically indicates that it can.
     *
     * @returns           True, always.
     *
     * @param chain       The certificate chain. Ignored.
     *
     */
    public boolean is_trusted(com.ooc.FSSL.Certificate[] chain)
    {
      //-----------------------------------------------------------------------
      // Always returns true.
      //-----------------------------------------------------------------------
      return true;
    }
  }
}