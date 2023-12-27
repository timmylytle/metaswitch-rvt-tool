package com.Metaswitch.MVS.Corba;


/**
* com/Metaswitch/MVS/Corba/ClientSessionManagerInterfaceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ClientSessionManagerInterface.idl
* Thursday, June 18, 2020 8:33:04 PM CDT
*/


/**
 * The ClientSessionManagerInterface is the initial interface used by all
 * client applications to establish communications with the EMS server.
 */
public interface ClientSessionManagerInterfaceOperations 
{

  /**
     * Initiates a user session on the server.
     *
     * This entry point is used by applications wishing to perform operations
     * using a particular EMS account.
     *
     * The account details (username and password) must match those in an EMS
     * User Object.
     *
     * The supportedVersions parameter should contain a sequence of integers,
     * each identifying a particular EMS Object version.  The number of versions
     * in the sequence is dependent on the supportVersionsFormat parameter (see
     * below).  Each version identifier is formatted as follows:
     *
     * -  the top 8 bits represent the major version,
     * -  the next 8 bits represent the minor version,
     * -  the next 8 bits represent the compatibility level,
     * -  the bottom 8 bits are reserved for future use.
     *
     * For example, version V2.1.10 is specified as 0x02010a00.
     *
     * The supportedVersionsFormat is used to indicate how the supportedVersions
     * sequence should be interpreted.  The number of entries in the sequence and
   * the meaning of each of the entries in the sequence varies according to the
     * value of this parameter.  The parameter can take one of four possible
     * values:
     *
     * -  SUPPORT_EXACT_SINGLE_VERSION,
     * -  SUPPORT_EXACT_RANGE_OF_VERSIONS,
     * -  SUPPORT_FWD_COMPATIBLE_SINGLE_VERSION,
     * -  SUPPORT_FWD_COMPATIBLE_RANGE_OF_VERSIONS.
     *
     * Only applications that are written to strictly follow all of the forward
     * compatibility guidelines in the Integration and Customization manual
     * should use either of the SUPPORT_FWD_COMPATIBLE values.  In this case the
     * EMS will only ever return objects that a correctly written forward
     * compatible application will be compatible with.  Please refer to the
     * Integration and Customization manual for full details on the intended use
     * of this support.
     *
     * -  If SUPPORT_EXACT_RANGE_OF_VERSIONS is specified then the
     *    supportedVersions sequence contains two integers, a lowest and highest
     *    EMS Object version, used to specify the range of EMS Object versions
     *    that the application supports.  In this case the EMS will typically
     *    return objects with versions within the specified range, and will
     *    usually not allow the management of objects whose version falls outside
     *    of this range.  However, the following object types are always handled
     *    as though forward compatibility is supported (see version
     *    format specifier SUPPORT_FWD_COMPATIBLE_RANGE_OF_VERSIONS):
     *
     *      -  CFS / UMG / IS / MVD Connection (BOONetworkElementConnSE)
     *      -  Element Management System (BOOEmsSE)
     *      -  Element Management System Statistics (BOOEmsStatsContainerSE)
     *      -  'Summary Period' Element Management System Statistics (BOOEmsStatsSE)
     *      -  Log Collection Status (BOOLogCollectorSE)
     *      -  Email Notification (BOOEmailNotificationSE)
     *      -  Security Certificates (BOONMSCertificateContainerSE)
     *      -  Security Certificate (BOONMSCertificateSE)
     *      -  Users (BOOEmsUsersSE)
     *      -  EMS Users (BOOEmsUserContainerSE)
     *      -  EMS User (BOOEmsUserSE)
     *      -  Craft Users (BOOEmsCraftUserContainerSE)
     *      -  Craft User (BOOEmsCraftUserSE).
     *
     *    Objects of the above type will be returned regardless of the supported
     *    range of versions.  Their version will be equal to the EMS version.
     *
     * -  If SUPPORT_EXACT_SINGLE_VERSION is specified then the supportedVersions
     *    sequence contains a single integer to specify the only EMS Object
     *    version that the application supports.  The behaviour is identical to
     *    SUPPORT_EXACT_RANGE_OF_VERSIONS where the same version is used for both
     *    the lowest and highest supported versions.
     *
     * -  If SUPPORT_FWD_COMPATIBLE_RANGE_OF_VERSIONS is specified then the
     *    supportedVersions sequence contains two integers, a lowest and highest
     *    EMS Object version, used to specify the range of forward compatible EMS
     *    Object versions that the application supports.  In this case the EMS
     *    will return objects with versions within the specified range and above.
     *    However, the EMS will not allow management of an object if the version
     *    of the object is not forward compatible with the highest of the
     *    specified range of versions.
     *
     * -  If SUPPORT_FWD_COMPATIBLE_SINGLE_VERSION is specified then the
     *    supportedVersions sequence contains a single integer to specify the
     *    only forward compatible version.  The behaviour is identical to
     *    SUPPORT_FWD_COMPATIBLE_RANGE_OF_VERSIONS where the same version is used
     *    for both the lowest and highest forward compatible versions.
     *
     * @returns           A ClientSessionInterface that provides the application
     *                    with access to EMS Objects.
     *
     * @param userName    The user name of the EMS account.
     * @param password    The password of the EMS account.
     * @param supportedVersionsFormat
     *                    The format in which the supportVersions information is
     *                    encoded. See the comment above for more details.
     * @param supportedVersions
     *                    Sequence of integers specifying the EMS Object versions
     *                    that the application wishes to use. See the comment
     *                    above for more details.
     *
     * @exception LoginFailedException
     *                    Thrown if the supplied account details do not match the
     *                    details of any EMS User Object.
     * @exception VersionNotSupportedException
     *                    Thrown if the versions supported by the application
     *                    fall entirely outside the scope of the manageable EMS
     *                    Object versions.
     * @exception VersionFormatException
     *                    Thrown when the version information is incorrectly
     *                    formatted. This may occur when supportedVersionsFormat
     *                    is an unrecognized format, when the supportedVersions
     *                    sequence does not contain recognized EMS Object
     *                    Versions, or when the supportedVersions sequence is
     *                    invalid for the specified supportedVersionsFormat
     *                    value.
     */
  com.Metaswitch.MVS.Corba.ClientSessionInterface userLogin (String userName, String password, int supportedVersionsFormat, int[] supportedVersions) throws com.Metaswitch.MVS.Corba.LoginFailedException, com.Metaswitch.MVS.Corba.VersionNotSupportedException, com.Metaswitch.MVS.Corba.VersionFormatException;

  /**
     * Enhanced version of userLogin that supports the Session Controller
     * (Perimeta) by passing its version alongside the EMS version.
     *
     * @returns           A ClientSessionInterface that provides the application
     *                    with access to EMS Objects.
     *
     * @param userName    The user name of the EMS account.
     * @param password    The password of the EMS account.
     * @param supportedVersionsFormat
     *                    The format in which the supportVersions information is
     *                    encoded. See the comment above for more details.
     * @param supportedVersions
     *                    Sequence of integers specifying the EMS Object versions
     *                    that the application wishes to use. See the comment
     *                    above for more details.
     * @param scSupportedVersionsFormat
     *                    The format in which the scSupportVersions information
     *                    is encoded. See the comment above for more details.
     * @param scSupportedVersions
     *                    Sequence of integers specifying the Session Controller
     *                    (Perimeta) Object versions that the application wishes
     *                    to use. See the comment above for more details.
     *
     * @exception LoginFailedException
     *                    Thrown if the supplied account details do not match the
     *                    details of any EMS User Object.
     * @exception VersionNotSupportedException
     *                    Thrown if the versions supported by the application
     *                    fall entirely outside the scope of the manageable EMS
     *                    Object versions.
     * @exception VersionFormatException
     *                    Thrown when the version information is incorrectly
     *                    formatted. This may occur when supportedVersionsFormat
     *                    is an unrecognized format, when the supportedVersions
     *                    sequence does not contain recognized EMS or Session 
     *                    Controller (Perimeta) Object Versions, or when the
     *                    supportedVersions sequence is invalid for the specified
     *                    supportedVersionsFormat value.
     */
  com.Metaswitch.MVS.Corba.ClientSessionInterface userLogin_SessionController (String userName, String password, int supportedVersionsFormat, int[] supportedVersions, int scSupportedVersionsFormat, int[] scSupportedVersions) throws com.Metaswitch.MVS.Corba.LoginFailedException, com.Metaswitch.MVS.Corba.VersionNotSupportedException, com.Metaswitch.MVS.Corba.VersionFormatException;

  /**
     * Identical to userLogin except that a new password is supplied to which
     * the user's password is set if login is successful.  This userLogin
     * must be called to login users whose passwords have expired.
     *
     * @returns           A ClientSessionInterface that provides the application
     *                    with access to EMS Objects.
     *
     * @param userName    The user name of the EMS account.
     * @param password    The current password of the EMS account.
     * @param newPassword The new password for the EMS account.
     * @param supportedVersionsFormat
     *                    The format in which the supportVersions information is
     *                    encoded. See the comment above for more details.
     * @param supportedVersions
     *                    Sequence of integers specifying the EMS Object versions
     *                    that the application wishes to use. See the comment
     *                    above for more details.
     *
     * @exception LoginFailedException
     *                    Thrown if the supplied account details do not match the
     *                    details of any EMS User Object.
     * @exception VersionNotSupportedException
     *                    Thrown if the versions supported by the application
     *                    fall entirely outside the scope of the manageable EMS
     *                    Object versions.
     * @exception VersionFormatException
     *                    Thrown when the version information is incorrectly
     *                    formatted. This may occur when supportedVersionsFormat
     *                    is an unrecognized format, when the supportedVersions
     *                    sequence does not contain recognized EMS Object
     *                    Versions, or when the supportedVersions sequence is
     *                    invalid for the specified supportedVersionsFormat
     *                    value.
     */
  com.Metaswitch.MVS.Corba.ClientSessionInterface userLoginWithNewPassword (String userName, String password, String newPassword, int supportedVersionsFormat, int[] supportedVersions) throws com.Metaswitch.MVS.Corba.LoginFailedException, com.Metaswitch.MVS.Corba.VersionNotSupportedException, com.Metaswitch.MVS.Corba.VersionFormatException;

  /**
     * Identical to userLogin_SessionController except that a new password is
     * supplied to which the user's password is set if login is successful. This method must
     * be called to login users whose passwords have expired.
     *
     * @returns           A ClientSessionInterface that provides the application
     *                    with access to EMS/Session Controller (Perimeta)
     *                    Objects.
     *
     * @param userName    The user name of the EMS account.
     * @param password    The current password of the EMS account.
     * @param newPassword The new password for the EMS account.
     * @param supportedVersionsFormat
     *                    The format in which the supportVersions information is
     *                    encoded. See the comment above for more details.
     * @param supportedVersions
     *                    Sequence of integers specifying the EMS Object versions
     *                    that the application wishes to use. See the comment
     *                    above for more details.
     * @param scSupportedVersionsFormat
     *                    The format in which the scSupportVersions information
     *                    is encoded. See the comment above for more details.
     * @param scSupportedVersions
     *                    Sequence of integers specifying the Session Controller
     *                    (Perimeta) Object versions that the application wishes
     *                    to use. See the comment above for more details.
     *
     * @exception LoginFailedException
     *                    Thrown if the supplied account details do not match the
     *                    details of any EMS User Object.
     * @exception VersionNotSupportedException
     *                    Thrown if the versions supported by the application
     *                    fall entirely outside the scope of the manageable EMS
     *                    Object versions.
     * @exception VersionFormatException
     *                    Thrown when the version information is incorrectly
     *                    formatted. This may occur when supportedVersionsFormat
     *                    is an unrecognized format, when the supportedVersions
     *                    sequence does not contain recognized EMS or Session 
     *                    Controller (Perimeta) Object Versions, or when the
     *                    supportedVersions sequence is invalid for the specified
     *                    supportedVersionsFormat value.
     */
  com.Metaswitch.MVS.Corba.ClientSessionInterface userLoginWithNewPassword_SessionController (String userName, String password, String newPassword, int supportedVersionsFormat, int[] supportedVersions, int scSupportedVersionsFormat, int[] scSupportedVersions) throws com.Metaswitch.MVS.Corba.LoginFailedException, com.Metaswitch.MVS.Corba.VersionNotSupportedException, com.Metaswitch.MVS.Corba.VersionFormatException;

  /**
     * Called by a client application to initiate a session on the server.
     *
     * The application's authenticity must have been established prior to this
     * invocation.
     *
     * This login does not require an EMS User account.
     *
     * The lowestSupportedVersion and highestSupportedVersion fields are version
     * identifiers as discussed in the CORBA integration chapter of the
     * Integration and Customization manual.
     *
     * @param userType  The userType to associate with this session.
     *                  For more information on User types see the
     *                  "MetaSwitch Class 5 Softswitch: Operations Manual".
     *                  User type constants are defined above.
     * @param supportedVersionsFormat  The format in which the supportedVersions
     *                  information is encoded.  See the comment for userLogin.
     * @param supportedVersions  Array of integers specifying the EMS Object
     *                  versions that this application wishes to use.  See the
     *                  comment for userLogin.
     *
     * @return          ClientSessionInterface that provides the application with
     *                  access to EMS objects.
     *
     * @throws LoginFailedException  Thrown if the userType is unknown.
     */
  com.Metaswitch.MVS.Corba.ClientSessionInterface applicationLogin (int userType, int supportedVersionsFormat, int[] supportedVersions) throws com.Metaswitch.MVS.Corba.LoginFailedException, com.Metaswitch.MVS.Corba.VersionNotSupportedException, com.Metaswitch.MVS.Corba.VersionFormatException;

  /**
     * Enhanced version of applicationLogin that supports the Session Controller
     * (Perimeta) by passing its version alongside the EMS version.
     *
     * @param userType  The userType to associate with this session.
     *                  For more information on User types see the
     *                  "MetaSwitch Class 5 Softswitch: Operations Manual".
     *                  User type constants are defined above.
     * @param supportedVersionsFormat
     *                    The format in which the supportVersions information is
     *                    encoded. See the comment above for more details.
     * @param supportedVersions
     *                    Sequence of integers specifying the EMS Object versions
     *                    that the application wishes to use. See the comment
     *                    above for more details.
     * @param scSupportedVersionsFormat
     *                    The format in which the scSupportVersions information
     *                    is encoded. See the comment above for more details.
     * @param scSupportedVersions
     *                    Sequence of integers specifying the Session Controller
     *                    (Perimeta) Object versions that the application wishes
     *                    to use. See the comment above for more details.
     *
     * @return          ClientSessionInterface that provides the application with
     *                  access to EMS/Session Controller (Perimeta) objects.
     *
     * @throws LoginFailedException  Thrown if the userType is unknown.
     */
  com.Metaswitch.MVS.Corba.ClientSessionInterface applicationLogin_SessionController (int userType, int supportedVersionsFormat, int[] supportedVersions, int scSupportedVersionsFormat, int[] scSupportedVersions) throws com.Metaswitch.MVS.Corba.LoginFailedException, com.Metaswitch.MVS.Corba.VersionNotSupportedException, com.Metaswitch.MVS.Corba.VersionFormatException;

  /**
     * Obtains a list of the EMS Server properties.
     *
     * EMS Server properties include:
     *
     * dcl.vpems.server.versioninfo - this is a displayable string that indicates
     * the version of code used by the EMS Server.  This is a version identifier
     * of the form described in the userLogin method, but in dotted decimal
     * format: "[major version].[minor version].[compatability level]".
     *
     * dcl.vpems.server.helpmanualversioninfo - this string is only interpretable
     * by the EMS Client.
     *
     * @return  PropertiesList containing key and value pairs of the
     *          form "key=value".
     */
  String[] getServerProperties ();

  /**
     * Provides access to the NLSSupportInterface that allows translation
     * from National Language Support (NLS) enabled text into human-readable
     * displayable text.
     *
     * @return  A reference to an NLSSupportInterface.
     */
  com.Metaswitch.MVS.Corba.NLSSupportInterface getNLSSupport ();
} // interface ClientSessionManagerInterfaceOperations
