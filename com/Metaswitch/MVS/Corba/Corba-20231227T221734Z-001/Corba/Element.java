package com.Metaswitch.MVS.Corba;


/**
* com/Metaswitch/MVS/Corba/Element.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from SEAccessInterface.idl
* Thursday, June 18, 2020 8:33:02 PM CDT
*/

public final class Element implements org.omg.CORBA.portable.IDLEntity
{
  public String elementName = null;
  public String displayName = null;

  public Element ()
  {
  } // ctor

  public Element (String _elementName, String _displayName)
  {
    elementName = _elementName;
    displayName = _displayName;
  } // ctor

} // class Element
