/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ata.touchtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import ata.touchtest.UserDataGathering.UserDataSource;

@SuppressLint("NewApi")
public class CreateGestureActivity extends Activity {
	public static String Name = "";
	public static int cnt = 0;
	private static final float LENGTH_THRESHOLD = 120.0f;

	private Gesture mGesture;
	private View mDoneButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.create_gesture);

		mDoneButton = findViewById(R.id.done);
		cnt = 0;
		GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
		overlay.addOnGestureListener(new GesturesProcessor());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (mGesture != null) {
			outState.putParcelable("gesture", mGesture);
		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		mGesture = savedInstanceState.getParcelable("gesture");
		if (mGesture != null) {
			final GestureOverlayView overlay = (GestureOverlayView) findViewById(R.id.gestures_overlay);
			overlay.post(new Runnable() {
				public void run() {
					overlay.setGesture(mGesture);
				}
			});

			mDoneButton.setEnabled(true);
		}
	}

	@SuppressWarnings({ "UnusedDeclaration" })
	public void addGesture(View v) {
		if (mGesture != null) {
			final TextView input = (TextView) findViewById(R.id.gesture_name);
			final CharSequence name = input.getText();
			if (name.length() == 0) {
				input.setError(getString(R.string.error_missing_name));
				return;
			}

			final GestureLibrary store = GestureBuilderActivity.getStore();
			store.addGesture(name.toString(), mGesture);
			Name = name.toString();
			store.save();

			setResult(RESULT_OK);

			final String path = new File(
					Environment.getExternalStorageDirectory(), "gestures")
					.getAbsolutePath();
			Toast.makeText(this, getString(R.string.save_success, path),
					Toast.LENGTH_LONG).show();
		} else {
			setResult(RESULT_CANCELED);
		}

		finish();

	}

	@SuppressWarnings({ "UnusedDeclaration" })
	public void cancelGesture(View v) {
		setResult(RESULT_CANCELED);
		finish();
		cnt = 0;
	}

	private class GesturesProcessor implements
			GestureOverlayView.OnGestureListener {

		private boolean isshowed = false;

		private Paint paint = new Paint();
		private Path path = new Path();
		double pressureup, pressuredown, sizeup, sizedown, time, accertation,
				distance, speed, touchminordown, touchminorup, touchmajordown,
				touchmajorup;
		private UserDataSource datasource;
		long time1 = 0, time2 = 0;
		double x1 = 0, x2 = 0, y1 = 0, y2 = 0;

		public void onGestureStarted(GestureOverlayView overlay,
				MotionEvent event) {
			mDoneButton.setEnabled(false);
			
			mGesture = null;

			// CreateGestureActivity.cnt=0;
			paint.setAntiAlias(true);
			paint.setStrokeWidth(6f);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);

			datasource = new UserDataSource(getBaseContext());
			datasource.open();

		}

		public void onGesture(GestureOverlayView overlay, MotionEvent event) {

		}

		@SuppressWarnings("deprecation")
		public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {

			time1 = time2;
			x1 = x2;
			y1 = y2;
			mGesture = overlay.getGesture();
			if (mGesture.getLength() < LENGTH_THRESHOLD) {
				overlay.clear(false);
			}

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Toast.makeText(getBaseContext(), "ACTION_DOWN",
						Toast.LENGTH_SHORT).show();
				pressuredown = event.getPressure();
				touchmajordown = event.getTouchMajor();
				touchminordown = event.getToolMinor();
				sizedown = event.getSize();
				time1 = event.getDownTime();
				x1 = event.getX();
				y1 = event.getY();
				
				break;
			case MotionEvent.ACTION_UP:

				CreateGestureActivity.cnt++;
				pressureup = event.getPressure();
				touchmajorup = event.getTouchMajor();
				touchminorup = event.getToolMinor();
				sizeup = event.getSize();
				time2 = event.getEventTime();
				time = Math.abs(time2 - time1);
				x2 = event.getX();
				y2 = event.getY();
				distance = Distance(x1, y1, x2, y2);
				speed = Speed(distance, Math.abs(time2 - time1));
				accertation = Accertation(distance, Math.abs(time2 - time1));
				TextView input = (TextView) findViewById(R.id.gesture_name);
				CharSequence Name = input.getText();
				
//				 Toast.makeText(getBaseContext(),Name.toString()+" - "+pressureup+" - "+
//				 pressuredown+" - "+sizeup+" - "+sizedown+" - "+time+" - "+accertation+" - "+distance+" - "+speed
//				 +" - "+touchmajordown+" - "+touchmajorup+" - "+touchminordown+" - "+touchminorup,
//				 Toast.LENGTH_SHORT).show();
				
				 insert(Name.toString(), pressureup, pressuredown, sizeup,
						sizedown, time, accertation, distance, speed,
						touchmajordown, touchmajorup, touchminordown,
						touchminorup);

				break;
			default:
				CreateGestureActivity.cnt = 7;
				break;
			}

			if (cnt >= 6) {
				mDoneButton.setEnabled(true);
				// Toast.makeText(getBaseContext(),"Since now you can save the data by pressing Done button",
				// Toast.LENGTH_SHORT).show();
				// get your custom_toast.xml ayout
				LayoutInflater inflater = getLayoutInflater();

				View layout = inflater.inflate(R.layout.custom_toast,
						(ViewGroup) findViewById(R.id.custom_toast_layout_id));

				// set a dummy image
				ImageView image = (ImageView) layout.findViewById(R.id.image);
				image.setImageResource(R.drawable.gradient_bg);

				// set a message
				TextView text = (TextView) layout.findViewById(R.id.text);
				text.setText("Since now you can save the data by pressing Done button");

				// Toast...
				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();
			}

		}

		public void onGestureCancelled(GestureOverlayView overlay,
				MotionEvent event) {
		}

		protected void insert(String username, double pressureup,
				double pressuredown, double sizeup, double sizedown,
				double time, double accertation, double distance, double speed,
				double touchmajordown, double touchmajorup,
				double touchminordown, double touchminorup) {

			datasource.createUserdata(username, pressureup, pressuredown,
					sizeup, sizedown, time, accertation, distance, speed, touchmajordown, touchmajorup, touchminordown, touchminorup);
		}

		double Distance(double x1, double y1, double x2, double y2) {
			double deltaX = (x2 - x1);
			double deltaY = (y2 - y1);
			return (double) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		}

		double Speed(double d, double time) {
			if (time == 0)
				return 0;
			else
				return d / time;
		}

		double Accertation(double d, double time) {
			if (time == 0)
				return 0;
			else
				return d / (time * time);
		}
	}

}
