import org.omg.CORBA.*;
import com.Metaswitch.MVS.Corba.*;
import com.Metaswitch.MVS.Utils.*;
import java.io.File; 
import java.io.PrintWriter;



public class rvt_all_verify
{
    private String mEMSAddress = null;
    private String mUserName = null;
    private String mPassword = null;
    //private int mGrpNum = 0;
    private ClientSessionInterface mClientSession = null;
    private SEAccessInterface mTopLevelSEA = null;
    private SEAccessInterface rvtgrpSEA = null;
    private SEAccessInterface rvttestSEA = null;
    private SettingsUserInterface grptempSettings = null;
    private SettingsUserInterface tempSettings = null;

    public static void main(String[] args) 
    {

        if (args.length < 3)
        {
          System.err.println("Usage: MetaViewServerIPAddress/HostName "
                             + "Username Password "
                             + "Test Group #");
          System.exit(1);
        }
    
        String emsAddress = args[0];
        String userName = args[1];
        String password = args[2];
        //String grpnum = args[3];

        //int igrpnum = Integer.parseInt(grpnum);

        CorbaHelper.startORB();

        TraceHelper.trace("Started ORB");

        rvt_all_verify verify = new rvt_all_verify(emsAddress, userName, password);
        verify.execute();
   
        CorbaHelper.stopORB();

        TraceHelper.trace("Stopped ORB");
        TraceHelper.trace("Closing File");

        System.exit(0);
    }

   public rvt_all_verify(String emsAddress, String userName, String password)
    {
        mEMSAddress = emsAddress;
        mUserName = userName;
        mPassword = password;
        //mGrpNum = igrpnum;
    }   
    public synchronized void execute() 
    {
        try
        { 
          File file = new File("rvt_all_verify_output.csv");
          PrintWriter pw = new PrintWriter(file);
          pw.println("grpname," +
                     "testindex," +
                     "testname," +
                     "calling_num,"+
                     "called_num,"+
                     "configset,exp_result,"+
                     "result,"+
                     "exp_media_channel_1,"+
                     "exp_media_channel_2,"+
                     "exp_media_channel_3,"+
                     "exp_media_channel_4,"+
                     "exp_media_channel_5,"+
                     "exp_media_channel_6,"+
                     "exp_media_channel_7,"+
                     "exp_media_channel_8,"+
                     "exp_media_channel_8,"+
                     "exp_media_channel_10,"+
                     "media_channel,"+
                     "call_type,"+
                     "modified_called_num,"+
                     "uda1,"+
                     "uda2,"+
                     "uda3,"+
                     "uda4,"+
                     "uda5,"+
                     "uda6,"+
                     "uda20");

            mClientSession = CorbaHelper.login(mEMSAddress, mUserName, mPassword);
            mClientSession.setNLSLocale("en", "US");
            
            mTopLevelSEA = mClientSession.createSEAccess();
            TraceHelper.trace("Logged in");
            CorbaHelper.startPolling(mClientSession);

            rvtgrpSEA = mTopLevelSEA.findElement(omlapi.O_ROUTE_VERIFICATION_TEST_GROUP); 

            //if (rvtgrpSEA == null)
            //{
            //  TraceHelper.trace("Failed to find RVTGroup");
            //}
            //else
            //{

              while (rvtgrpSEA != null)
              {
                grptempSettings =rvtgrpSEA.getSnapshot_Settings();
                String rvtgrpname = grptempSettings.getFieldAsStringByName(omlapi.F_TEST_NAME, new BooleanHolder());
                int rvtgrpindex = grptempSettings.getFieldAsIntByName(omlapi.F_GROUP_INDEX, new BooleanHolder()); 
                TraceHelper.trace("Connected to RVTGroup "+rvtgrpname+" INDEX: "+rvtgrpindex);
                
                DualString currGrp = null;              
                currGrp = rvtgrpSEA.getSnapshot_Element();
                rvttestSEA = rvtgrpSEA.findElement(omlapi.O_SUBSCRIBER_ROUTE_VERIFICATION_TEST);
              

                while (rvttestSEA != null)
                {
                DualString currTest = null;
                currTest = rvttestSEA.getSnapshot_Element();
                tempSettings = rvttestSEA.getSnapshot_Settings();

                String testindex = tempSettings.getFieldAsStringByName(omlapi.F_TEST_INDEX, new BooleanHolder());
                String testname = tempSettings.getFieldAsStringByName(omlapi.F_TEST_NAME, new BooleanHolder());
                String callingnum = tempSettings.getFieldAsStringByName(omlapi.F_SUBSCRIBER_DIRECTORY_NUMBER_3, new BooleanHolder());
                String dialednum = tempSettings.getFieldAsStringByName(omlapi.F_DIALED_NUMBER, new BooleanHolder());
                String configset = tempSettings.getFieldAsStringByName(omlapi.F_TRUNK_ROUTING_CONFIG_SET_3, new BooleanHolder());
                String expresult = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___EXPECTED_RESULT, new BooleanHolder());
                String result = tempSettings.getFieldAsStringByName(omlapi.F_TEST_RESULT, new BooleanHolder());
                String expMC1 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_1, new BooleanHolder());
                String expMC2 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_2, new BooleanHolder());
                String expMC3 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_3, new BooleanHolder());
                String expMC4 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_4, new BooleanHolder());
                String expMC5 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_5, new BooleanHolder());
                String expMC6 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_6, new BooleanHolder());
                String expMC7 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_7, new BooleanHolder());
                String expMC8 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_8, new BooleanHolder());
                String expMC9 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_9, new BooleanHolder());
                String expMC10 = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___MEDIA_CHANNEL_10, new BooleanHolder());
                String mediachan = tempSettings.getFieldAsStringByName(omlapi.F_ROUTING___MEDIA_CHANNEL, new BooleanHolder());
                String nvcalltype = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___CALL_TYPE, new BooleanHolder());
                String modifiednum = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___MODIFIED_CALLED_NUMBER, new BooleanHolder());
                String uda1 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_1, new BooleanHolder());
                String uda2 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_2, new BooleanHolder());
                String uda3 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_3, new BooleanHolder());
                String uda4 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_4, new BooleanHolder());
                String uda5 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_5, new BooleanHolder());
                String uda6 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_6, new BooleanHolder());
                String uda20 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_20, new BooleanHolder());

                rvtgrpname = rvtgrpname.replaceAll(",", "");

                pw.println(rvtgrpname + "," 
                          + testindex + ","
                          + testname + "," 
                          + callingnum + ","
                          + dialednum + ","
                          + configset + "," 
                          + expresult + ","
                          + result + "," 
                          + expMC1 + ","
                          + expMC2 + ","
                          + expMC3 + ","
                          + expMC4 + ","
                          + expMC5 + ","
                          + expMC6 + ","
                          + expMC7 + ","
                          + expMC8 + ","
                          + expMC9 + ","
                          + expMC10 + ","
                          + mediachan + ","
                          + nvcalltype + "," 
                          + modifiednum + ","
                          + uda1 + "," 
                          + uda2 + "," 
                          + uda3 + ","
                          + uda4 + ","
                          + uda5 +","
                          + uda6 + ","
                          + uda20); 

                rvttestSEA = rvtgrpSEA.findNextElement(omlapi.O_SUBSCRIBER_ROUTE_VERIFICATION_TEST,currTest.internal);

              }
              
              rvtgrpSEA = mTopLevelSEA.findNextElement(omlapi.O_ROUTE_VERIFICATION_TEST_GROUP, currGrp.internal);

            //  }
            }

          pw.close();  
        }
        catch (ElementUnavailableException e)
        {
          //-----------------------------------------------------------------------
          // This exception will be thrown if an object we are working with has
          // become unavailable.  If this occurs we will just exit.
          //-----------------------------------------------------------------------
          TraceHelper.trace("Caught ElementUnavailableException - exiting");
        }
        catch (org.omg.CORBA.COMM_FAILURE e)
        {
          System.err.println("Unable to login to the MetaView Server.");
          System.err.println("This may be because it is not running, or it is "
                             + "running in secure mode.");
        }
        catch (org.omg.CORBA.TRANSIENT e)
        {
          System.err.println("Transient communication failure with the MetaView "
                             + "Server.\nThis may be because it is not running, "
                             + "or it is running in secure mode,\nor there are "
                             + "problems with the network.");
        }
        catch (LoginFailedException e)
        {
          System.err.println("Unable to login to the MetaView Server.");
          System.err.println("Make sure that:\n"
                             + " - your MetaView Server is running in insecure mode\n"
                             + " - your username and password are correct.");
        }
        catch (IllegalStateException e)
        {
          e.printStackTrace();
          System.err.println(e.getMessage());
          System.exit(1);
        }
        catch (org.omg.CORBA.UserException e)
        {
          System.out.println("Corba exception caught: " + e);
          e.printStackTrace();
        }
        catch (Exception e)
        {
          System.err.println("Unexpected exception: " + e.toString());
          e.printStackTrace();
          System.err.println(e.getMessage());
          //System.exit(1);
        }
        finally
        {
          //-----------------------------------------------------------------------
          // Destroy the SEAccess we created above, if we got that far.
          //-----------------------------------------------------------------------
          if (rvtgrpSEA != null)
          {
            rvtgrpSEA.destroy();
            rvtgrpSEA = null;
          }
    
          //-----------------------------------------------------------------------
          // Destroy the SEAccess we created above, if we got that far.
          //-----------------------------------------------------------------------
          if (mTopLevelSEA != null)
          {
            mTopLevelSEA.destroy();
            mTopLevelSEA = null;
          }
    
          //-----------------------------------------------------------------------
          // Log out of the server, if we managed to log in above.
          //-----------------------------------------------------------------------
          if (mClientSession != null)
          {
            CorbaHelper.stopPolling();
            mClientSession.logout();
            mClientSession = null;
            TraceHelper.trace("Logged out");
          }
        }

    }

}