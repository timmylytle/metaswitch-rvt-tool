import org.omg.CORBA.*;

//import java.io.ObjectInputStream.GetField;

import com.Metaswitch.MVS.Corba.*;
import com.Metaswitch.MVS.Utils.*;

import java.io.File; 
import java.io.PrintWriter;
//import java.io.IOException;


public class rvt_run 
{
    private String mEMSAddress = null;
    private String mUserName = null;
    private String mPassword = null;
    private int mGrpNum = 0;
    private ClientSessionInterface mClientSession = null;
    private SEAccessInterface mTopLevelSEA = null;
    private SEAccessInterface rvtgrpSEA = null;
    private SEAccessInterface rvttestSEA = null;
    //private SEAccessInterface testSEA = null;
    private SettingsUserInterface tempSettings = null;
    private static final int A_RUN_ALL_TESTS = 0x30820d12;

    public static void main(String[] args) 
    {

        if (args.length < 4)
        {
          System.err.println("Usage: MetaViewServerIPAddress/HostName "
                             + "Username Password "
                             + "Test Group #");
          System.exit(1);
        }
    
        String emsAddress = args[0];
        String userName = args[1];
        String password = args[2];
        String grpnum = args[3];

        int igrpnum = Integer.parseInt(grpnum);

        CorbaHelper.startORB();

        TraceHelper.trace("Started ORB");

        rvt_run verify = new rvt_run(emsAddress, userName, password, igrpnum);
        verify.execute();
   
        CorbaHelper.stopORB();

        TraceHelper.trace("Stopped ORB");
        //TraceHelper.trace("Closing File");

        System.exit(0);
    }

   public rvt_run(String emsAddress, String userName, String password, int igrpnum)
    {
        mEMSAddress = emsAddress;
        mUserName = userName;
        mPassword = password;
        mGrpNum = igrpnum;
    }   
    public synchronized void execute() 
    {
        try
        { 
          //File file = new File("rvt_run_output_grp_" + mGrpNum + ".csv");
          //PrintWriter pw = new PrintWriter(file);
          //pw.println("grpname,testname,configset,exp_result,result,media_channel,call_type,uda1,uda2,uda3,uda4,uda5,uda6,uda20");

            mClientSession = CorbaHelper.login(mEMSAddress, mUserName, mPassword);
            mClientSession.setNLSLocale("en", "US");
            mTopLevelSEA = mClientSession.createSEAccess();
            TraceHelper.trace("Logged in");


            rvtgrpSEA = mTopLevelSEA.findElementWithIntField(omlapi.O_ROUTE_VERIFICATION_TEST_GROUP,omlapi.F_GROUP_INDEX,mGrpNum); 

            if (rvtgrpSEA == null)
            {
              TraceHelper.trace("Failed to find RVTGroup");
            }
            else
            {
              tempSettings =rvtgrpSEA.getSnapshot_Settings();
              String rvtgrpname = tempSettings.getFieldAsStringByName(omlapi.F_TEST_NAME, new BooleanHolder());
              int rvtgrpindex = tempSettings.getFieldAsIntByName(omlapi.F_GROUP_INDEX, new BooleanHolder()); 

              TraceHelper.trace("Connected to RVTGroup "+rvtgrpname+" INDEX: "+rvtgrpindex);
              
              rvttestSEA = rvtgrpSEA.findElement(omlapi.O_SUBSCRIBER_ROUTE_VERIFICATION_TEST);
              
              while (rvttestSEA != null)
              {
                DualString currTest = null;
                currTest = rvtgrpSEA.getSnapshot_Element();
                //RunAllTests(omlapi.A_RUN_ALL_TESTS, rvtgrpSEA, mClientSession);
                TraceHelper.trace("Running All test for Test Group");
                SEAccessInterface runtestSEA = null;
                
                String tempactionSEA = null;
                tempactionSEA = rvtgrpSEA.doAction(omlapi.A_RUN_ALL_TESTS);
                if (tempactionSEA != null)
                {
                  runtestSEA = mClientSession.createSEAccess();
                  runtestSEA.attachTo(tempactionSEA);
      
                  runtestSEA.doAction(A_RUN_ALL_TESTS);
                
                }
                else
                {
                  TraceHelper.trace("Failed to activate Tests");}




                //currTest = rvttestSEA.getSnapshot_Element();
    
                //rvtgrpSEA = mTopLevelSEA.findNextElement(omlapi.O_ROUTE_VERIFICATION_TEST_GROUP,currTest.internal);
                
                
                
                //DualString currTest = null;
                //currTest = rvttestSEA.getSnapshot_Element();
                //tempSettings = rvttestSEA.getSnapshot_Settings();

                //String testname = tempSettings.getFieldAsStringByName(omlapi.F_TEST_NAME, new BooleanHolder());
                //String configset = tempSettings.getFieldAsStringByName(omlapi.F_TRUNK_ROUTING_CONFIG_SET_3, new BooleanHolder());
                //String expresult = tempSettings.getFieldAsStringByName(omlapi.F_EXPECTED_RESULT___EXPECTED_RESULT, new BooleanHolder());
                //String result = tempSettings.getFieldAsStringByName(omlapi.F_TEST_RESULT, new BooleanHolder());
                //String mediachan = tempSettings.getFieldAsStringByName(omlapi.F_ROUTING___MEDIA_CHANNEL, new BooleanHolder());
                //String nvcalltype = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___CALL_TYPE, new BooleanHolder());
                //String uda1 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_1, new BooleanHolder());
                //String uda2 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_2, new BooleanHolder());
                //String uda3 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_3, new BooleanHolder());
                //String uda4 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_4, new BooleanHolder());
                //String uda5 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_5, new BooleanHolder());
                //String uda6 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_6, new BooleanHolder());
                //String uda20 = tempSettings.getFieldAsStringByName(omlapi.F_NUMBER_VALIDATION___USER_DEFINED_ATTRIBUTE_20, new BooleanHolder());

                //rvtgrpname = rvtgrpname.replaceAll(",", "");

                //pw.println(rvtgrpname + "," 
                //          + testname + "," 
                //          + configset + "," 
                //          + expresult + ","
                //          + result + "," 
                //          + mediachan + ","
                //          + nvcalltype + "," 
                //          + uda1 + "," 
                //          + uda2 + "," 
                //          + uda3 + ","
                //          + uda4 + ","
                //          + uda5 +","
                //          + uda6 + ","
                //          + uda20); 

                //rvttestSEA = rvtgrpSEA.findNextElement(omlapi.O_SUBSCRIBER_ROUTE_VERIFICATION_TEST,currTest.internal);

              }
            }
          //pw.close();  
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
          //System.err.println("Unexpected exception: " + e.toString());
          //e.printStackTrace();
          //System.err.println(e.getMessage());
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
            mClientSession.logout();
            mClientSession = null;
            TraceHelper.trace("Logged out");
          }
        }

    }

}
        



