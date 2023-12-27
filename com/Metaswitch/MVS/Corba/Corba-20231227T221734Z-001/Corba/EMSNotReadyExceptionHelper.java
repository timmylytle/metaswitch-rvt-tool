package com.Metaswitch.MVS.Corba;


/**
* com/Metaswitch/MVS/Corba/EMSNotReadyExceptionHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from SEExceptions.idl
* Thursday, June 18, 2020 8:33:03 PM CDT
*/


/**
 * Thrown if the EMS is not yet able to perform a requested
 * operation because it is still performing some internal
 * processing.  This can happen both at start of day, and following
 * any changes to its managed network, such as MetaSwitch
 * connections going unavailable or coming back.
 */
abstract public class EMSNotReadyExceptionHelper
{
  private static String  _id = "IDL:EMSNotReadyException:1.0";

  public static void insert (org.omg.CORBA.Any a, com.Metaswitch.MVS.Corba.EMSNotReadyException that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.Metaswitch.MVS.Corba.EMSNotReadyException extract (org.omg.CORBA.Any a)
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
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (com.Metaswitch.MVS.Corba.EMSNotReadyExceptionHelper.id (), "EMSNotReadyException", _members0);
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

  public static com.Metaswitch.MVS.Corba.EMSNotReadyException read (org.omg.CORBA.portable.InputStream istream)
  {
    com.Metaswitch.MVS.Corba.EMSNotReadyException value = new com.Metaswitch.MVS.Corba.EMSNotReadyException ();
    // read and discard the repository ID
    istream.read_string ();
    value.nlsText = istream.read_string ();
    value.wideNlsText = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.Metaswitch.MVS.Corba.EMSNotReadyException value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_string (value.nlsText);
    ostream.write_wstring (value.wideNlsText);
  }

}
