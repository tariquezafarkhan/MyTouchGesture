package ata.touchtest;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class GestureTest extends Activity implements OnGesturePerformedListener {
	  private GestureLibrary gestureLib;
	  private boolean valid=true;
	  
	  /** Called when the activity is first created. */

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	
	      super.onCreate(savedInstanceState);
	      GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
	      View inflate = getLayoutInflater().inflate(R.layout.main, null);
	      gestureOverlayView.addView(inflate);
	      gestureOverlayView.addOnGesturePerformedListener(this);
	      File sd = Environment.getExternalStorageDirectory();
	      File source = new File(sd, "gestures");
	     
	      gestureLib = GestureLibraries.fromFile(source);
	      if (!gestureLib.load()) {
	        finish();
	      }
	      setContentView(gestureOverlayView);
	    }

	    @Override
	    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
	      ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
	      valid=true;
	      for (Prediction prediction : predictions) {
	        if (prediction.score > 1.0) {
	        	if(valid)
	          Toast.makeText(this,"User is: "+ prediction.name, Toast.LENGTH_SHORT)
	              .show();
	        	valid=false;
	        }
	      }
	    }
	  } 
