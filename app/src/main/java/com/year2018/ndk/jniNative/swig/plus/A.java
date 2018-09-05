/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.year2018.ndk.jniNative.swig.plus;

public class A {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected A(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(A obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        PlusJNI.delete_A(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public A() {
    this(PlusJNI.new_A__SWIG_0(), true);
  }

  public A(int value) {
    this(PlusJNI.new_A__SWIG_1(value), true);
  }

  public void print() {
    PlusJNI.A_print(swigCPtr, this);
  }

  public void setValue(int value) {
    PlusJNI.A_value_set(swigCPtr, this, value);
  }

  public int getValue() {
    return PlusJNI.A_value_get(swigCPtr, this);
  }

}