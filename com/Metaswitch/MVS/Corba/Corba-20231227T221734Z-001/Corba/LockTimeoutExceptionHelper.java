package com.Metaswitch.MVS.Corba;


/**
* com/Metaswitch/MVS/Corba/LockTimeoutExceptionHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from SEExceptions.idl
* Thursday, June 18, 2020 8:33:02 PM CDT
*/


/**
 * Thrown if the SE's lock which was previously obtained by invoking the lock
 * method has expired.  SE locks expire/timeout after a fixed period of 30
 * seconds.  Those methods that declare the LockTimeoutException
 * will not throw this exception if the lock method has not been explicitly
 * called by the SEAccessInterface user.
 */
abstract public class LockTimeoutExceptionHelper
{
  private static String  _id = "IDL:LockTimeoutException:1.0";

  public static void insert (org.omg.CORBA.Any a, com.Metaswitch.MVS.Corba.LockTimeoutException that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.Metaswitch.MVS.Corba.LockTimeoutException extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "nlsText",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "wideNlsText",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (com.Metaswitch.MVS.Corba.LockTimeoutExceptionHelper.id (), "LockTimeoutException", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.Metaswitch.MVS.Corba.LockTimeoutException read (org.omg.CORBA.portable.InputStream istream)
  {
    com.Metaswitch.MVS.Corba.LockTimeoutException value = new com.Metaswitch.MVS.Corba.LockTimeoutException ();
    // read and discard the repository ID
    istream.read_string ();
    value.nlsText = istream.read_string ();
    value.wideNlsText = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.Metaswitch.MVS.Corba.LockTimeoutException value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_string (value.nlsText);
    ostream.write_wstring (value.wideNlsText);
  }

}
