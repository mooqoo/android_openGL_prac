package com.example.wangalbert.hellotriangle.Renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

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

  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

    mTriangle.draw();
  }
}
