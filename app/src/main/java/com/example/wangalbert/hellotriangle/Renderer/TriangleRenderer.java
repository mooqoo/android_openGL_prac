package com.example.wangalbert.hellotriangle.Renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.example.wangalbert.hellotriangle.Shape.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * android
 * <p/>
 * Created by wangalbert on 3/17/16.
 * Copyright (c) 2016 MobiusBobs Inc. All rights reserved.
 */
public class TriangleRenderer implements GLSurfaceView.Renderer {
  public static final String TAG = "Renderer";

  // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
  private final float[] mMVPMatrix = new float[16];
  private final float[] mProjectionMatrix = new float[16];
  private final float[] mViewMatrix = new float[16];
  private float[] mRotationMatrix = new float[16];

  // shape
  private Triangle mTriangle;

  // Constructor
  public TriangleRenderer() {

  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    // Set the background clear color to gray.
    GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

    mTriangle = new Triangle();
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES20.glViewport(0, 0, width, height);

    float ratio = (float) width / height;

    // this projection matrix is applied to object coordinates
    // in the onDrawFrame() method

    // this is orthographic projection
    Matrix.orthoM(mProjectionMatrix, 0, -1, 1, -1, 1, -1, 1);
    // this is perspective projection
    //Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    float[] scratch = new float[16];

    GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

    // Create a rotation transformation for the triangle
    //long time = SystemClock.uptimeMillis() % 4000L;
    //float angle = 0.090f * ((int) time);
    //Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

    // Set the camera position (View matrix)
    // set the camer position at (0,0,-3),
    // looking at (0,0,0)
    // with up vector (0,1,0)
    Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 1.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    // Calculate the projection and view transformation
    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

    // Combine the rotation matrix with the projection and camera view
    // Note that the mMVPMatrix factor *must be first* in order
    // for the matrix multiplication product to be correct.
    //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

    mTriangle.draw(mMVPMatrix);
  }
}
