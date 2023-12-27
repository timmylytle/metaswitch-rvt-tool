package com.Metaswitch.MVS.Corba;

/**
* com/Metaswitch/MVS/Corba/ClientSessionManagerInterfaceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ClientSessionManagerInterface.idl
* Thursday, June 18, 2020 8:33:04 PM CDT
*/


/**
 * The ClientSessionManagerInterface is the initial interface used by all
 * client applications to establish communications with the EMS server.
 */
public final class ClientSessionManagerInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public com.Metaswitch.MVS.Corba.ClientSessionManagerInterface value = null;

  public ClientSessionManagerInterfaceHolder ()
  {
  }

  public ClientSessionManagerInterfaceHolder (com.Metaswitch.MVS.Corba.ClientSessionManagerInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.Metaswitch.MVS.Corba.ClientSessionManagerInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.Metaswitch.MVS.Corba.ClientSessionManagerInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.Metaswitch.MVS.Corba.ClientSessionManagerInterfaceHelper.type ();
  }

}
