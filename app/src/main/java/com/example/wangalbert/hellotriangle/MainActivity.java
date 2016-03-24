package com.example.wangalbert.hellotriangle;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.wangalbert.hellotriangle.Renderer.SampleRenderer;
import com.example.wangalbert.hellotriangle.Renderer.SquareRenderer;
import com.example.wangalbert.hellotriangle.Renderer.TriangleRenderer;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = "HelloGL";

  /** Hold a reference to our GLSurfaceView */
  private GLSurfaceView mGLSurfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mGLSurfaceView = new GLSurfaceView(this);
    //mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    // Check if the system supports OpenGL ES 2.0.
    final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
    final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

    if (supportsEs2)
    {
      Log.d(TAG, "System supports OpenGL ES 2.0 ;)");

      // Request an OpenGL ES 2.0 compatible context.
      mGLSurfaceView.setEGLContextClientVersion(2);

      // Set the renderer to our demo renderer, defined below.
      mGLSurfaceView.setRenderer(new TriangleRenderer());
    }
    else
    {
      Log.e(TAG, "System does not supports OpenGL ES 2.0 :(");
      // This is where you could create an OpenGL ES 1.x compatible
      // renderer if you wanted to support both ES 1 and ES 2.
      return;
    }

    setContentView(mGLSurfaceView);
    //setContentView(R.layout.activity_main);
  }

  @Override
  protected void onResume()
  {
    // The activity must call the GL surface view's onResume() on activity onResume().
    super.onResume();
    mGLSurfaceView.onResume();
  }

  @Override
  protected void onPause()
  {
    // The activity must call the GL surface view's onPause() on activity onPause().
    super.onPause();
    mGLSurfaceView.onPause();
  }

}
