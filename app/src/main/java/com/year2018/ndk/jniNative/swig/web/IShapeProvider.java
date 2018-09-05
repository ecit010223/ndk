/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.year2018.ndk.jniNative.swig.web;

public class IShapeProvider {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected IShapeProvider(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(IShapeProvider obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ShapeJNI.delete_IShapeProvider(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void openFile(SWIGTYPE_p_std__string file_name) {
    ShapeJNI.IShapeProvider_openFile(swigCPtr, this, SWIGTYPE_p_std__string.getCPtr(file_name));
  }

  public void extent(SWIGTYPE_p_int x, SWIGTYPE_p_int y, SWIGTYPE_p_int with, SWIGTYPE_p_int height) {
    ShapeJNI.IShapeProvider_extent(swigCPtr, this, SWIGTYPE_p_int.getCPtr(x), SWIGTYPE_p_int.getCPtr(y), SWIGTYPE_p_int.getCPtr(with), SWIGTYPE_p_int.getCPtr(height));
  }

  public SWIGTYPE_p_std__string toJavaString() {
    return new SWIGTYPE_p_std__string(ShapeJNI.IShapeProvider_toJavaString(swigCPtr, this), true);
  }

}
