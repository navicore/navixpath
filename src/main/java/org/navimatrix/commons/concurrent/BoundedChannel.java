/*
  File: BoundedChannel.java

  Originally written by Doug Lea and released into the public domain.
  This may be used for any purposes whatsoever without acknowledgment.
  Thanks for the assistance and support of Sun Microsystems Labs,
  and everyone contributing, testing, and using this code.

  History:
  Date       Who                What
  11Jun1998  dl               Create public version
*/


package org.navimatrix.commons.concurrent;
//package EDU.oswego.cs.dl.util.concurrent;

public interface BoundedChannel extends Channel {
  /** 
   * Return the maximum number of elements that can be held.
   * @return the capacity of this channel.
   **/
  public int capacity();
}
