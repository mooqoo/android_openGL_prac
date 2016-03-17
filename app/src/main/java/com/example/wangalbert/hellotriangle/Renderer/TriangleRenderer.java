package com.example.wangalbert.hellotriangle.Renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

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
    Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

    // Set the camera position (View matrix)
    Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    // Calculate the projection and view transformation
    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

    mTriangle.draw(mMVPMatrix);
  }
}
