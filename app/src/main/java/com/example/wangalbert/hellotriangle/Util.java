package com.example.wangalbert.hellotriangle;

import android.opengl.GLES20;

/**
 * android
 * <p/>
 * Created by wangalbert on 3/17/16.
 * Copyright (c) 2016 MobiusBobs Inc. All rights reserved.
 */
public class Util {

  /**
   * Compiling OpenGL ES shaders and linking programs is expensive in terms of CPU cycles and
   * processing time, so you should avoid doing this more than once.
   */
  public static int loadShader(int type, String shaderCode){
    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
    int shader = GLES20.glCreateShader(type);

    // add the source code to the shader and compile it
    GLES20.glShaderSource(shader, shaderCode);
    GLES20.glCompileShader(shader);

    return shader;
  }
}
