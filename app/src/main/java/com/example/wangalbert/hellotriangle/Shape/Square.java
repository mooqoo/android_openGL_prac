package com.example.wangalbert.hellotriangle.Shape;

import android.opengl.GLES20;

import com.example.wangalbert.hellotriangle.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * android
 * <p/>
 * Created by wangalbert on 3/17/16.
 * Copyright (c) 2016 MobiusBobs Inc. All rights reserved.
 */
public class Square {
  private FloatBuffer vertexBuffer;
  private ShortBuffer drawListBuffer;

  // number of coordinates per vertex in this array
  static final int COORDS_PER_VERTEX = 3;
  static float squareCoords[] = {
    -1.0f,  1.0f, 0.0f,   // top left
    -1.0f, -1.0f, 0.0f,   // bottom left
    1.0f, -1.0f, 0.0f,   // bottom right
    1.0f,  1.0f, 0.0f }; // top right

  private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

  float color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };

  // GLSL (OpenGL Shading Language)
  private final String vertexShaderCode =
    // This matrix member variable provides a hook to manipulate
    // the coordinates of the objects that use this vertex shader
    "uniform mat4 uMVPMatrix;" +

      "attribute vec4 vPosition;" +
      "void main() {" +
      "  gl_Position = uMVPMatrix * vPosition;" +
      "}";

  private final String fragmentShaderCode =
    "precision mediump float;" +
      "uniform vec4 vColor;" +
      "void main() {" +
      "  gl_FragColor = vColor;" +
      "}";


  // Use to access and set the view transformation
  private int mMVPMatrixHandle;

  private final int mProgram;

  // --- draw variable ---
  private int mPositionHandle;
  private int mColorHandle;
  private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
  private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

  public Square() {
    // initialize vertex byte buffer for shape coordinates
    ByteBuffer bb = ByteBuffer.allocateDirect(
      // (# of coordinate values * 4 bytes per float)
      squareCoords.length * 4);
    bb.order(ByteOrder.nativeOrder());
    vertexBuffer = bb.asFloatBuffer();
    vertexBuffer.put(squareCoords);
    vertexBuffer.position(0);

    // initialize byte buffer for the draw list
    ByteBuffer dlb = ByteBuffer.allocateDirect(
      // (# of coordinate values * 2 bytes per short)
      drawOrder.length * 2);
    dlb.order(ByteOrder.nativeOrder());
    drawListBuffer = dlb.asShortBuffer();
    drawListBuffer.put(drawOrder);
    drawListBuffer.position(0);

    int vertexShader = Util.loadShader(GLES20.GL_VERTEX_SHADER,
      vertexShaderCode);
    int fragmentShader = Util.loadShader(GLES20.GL_FRAGMENT_SHADER,
      fragmentShaderCode);

    // create empty OpenGL ES Program
    mProgram = GLES20.glCreateProgram();

    // add the vertex shader to program
    GLES20.glAttachShader(mProgram, vertexShader);

    // add the fragment shader to program
    GLES20.glAttachShader(mProgram, fragmentShader);

    // creates OpenGL ES program executables
    GLES20.glLinkProgram(mProgram);
  }

  public void draw(float[] mvpMatrix) {
    // Add program to OpenGL ES environment
    GLES20.glUseProgram(mProgram);

    // get handle to vertex shader's vPosition member
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    // Enable a handle to the triangle vertices
    GLES20.glEnableVertexAttribArray(mPositionHandle);

    // Prepare the triangle coordinate data
    GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
      GLES20.GL_FLOAT, false,
      vertexStride, vertexBuffer);
    // get handle to fragment shader's vColor member
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

    // Set color for drawing the triangle
    GLES20.glUniform4fv(mColorHandle, 1, color, 0);
    // get handle to shape's transformation matrix
    mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

    // Pass the projection and view transformation to the shader
    GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

    // Draw the triangle
    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

    // Disable vertex array
    GLES20.glDisableVertexAttribArray(mPositionHandle);
  }
}
