package ata.touchtest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import ata.touchtest.UserDataGathering.UserDataSource;

public class SingleTouchEventView extends View {

	// private SimpleDBAdapter mDbHelper;

	UserIdentityActivity uia;
	private boolean isshowed = false;
	private static int cnt = 0;
	private Paint paint = new Paint();
	private Path path = new Path();
	double pressureup, pressuredown, sizeup, sizedown, time, accertation,
			distance, speed, touchmajordown, touchmajorup, touchminordown,
			touchminorup;
	private UserDataSource datasource;
	long time1 = 0, time2 = 0;
	double x1 = 0, x2 = 0, y1 = 0, y2 = 0;

	public SingleTouchEventView(Context context, AttributeSet attrs) {
		super(context, attrs);

		cnt = 0;
		paint.setAntiAlias(true);
		paint.setStrokeWidth(6f);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);

		datasource = new UserDataSource(getContext());
		datasource.open();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawPath(path, paint);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float eventX = event.getX();
		float eventY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(eventX, eventY);
			// Toast.makeText(getContext(),uia.curname+
			// " :\n Down pressure is:"+ String.valueOf(event.getPressure())+
			// " :\n Down size is:"+ String.valueOf(event.getSize())+
			// " :\n Down time is:"+ String.valueOf(event.getEventTime()),
			// Toast.LENGTH_SHORT).show();
			pressuredown = event.getPressure();
			touchmajordown = event.getTouchMajor();
			touchminordown = event.getToolMinor();
			sizedown = event.getSize();
			time1 = event.getDownTime();
			x1 = event.getX();
			y1 = event.getY();

			cnt++;
			return true;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(eventX, eventY);
			break;
		case MotionEvent.ACTION_UP:

			// Toast.makeText(getContext(), ma.curname+" - "+ pressureup+" - "+
			// pressuredown+" - "+ sizeup+" - "+ sizedown, Toast.LENGTH_LONG);
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
			insert(uia.curname, pressureup, pressuredown, sizeup, sizedown,
					time, accertation, distance, speed, touchmajordown, touchmajorup, touchminordown, touchminorup);
			// insert("Ala", event.getPressure());
			// Toast.makeText(getContext(),uia.curname+ " :\n Up pressure is:"+
			// String.valueOf(event.getPressure())+ " :\n Up size is:"+
			// String.valueOf(event.getSize()+ " :\n Time is:"+
			// String.valueOf(time)+ " :\n Accertation is:"+
			// String.valueOf(accertation)), Toast.LENGTH_SHORT).show();
			// insert(ma.curname, pressureup, pressuredown, sizeup, sizedown);
			break;
		default:
			return false;
		}

		// Schedules a repaint.
		invalidate();

		if (cnt >= 6 && !isshowed) {
			isshowed = true;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					getContext());
			// set title
			alertDialogBuilder.setTitle("Gathering data");
			// set dialog message
			alertDialogBuilder
					.setMessage("Click save to record data!")
					.setCancelable(false)
					.setPositiveButton("Save",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// insert(uia.curname, pressureup/6,
									// pressuredown/6, sizeup/6,
									// sizedown/6,time/6,accertation);
									Toast.makeText(getContext(),
											"Successfully Saved",
											Toast.LENGTH_SHORT).show();

									cnt = 0;
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									cnt = 0;
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
		return true;
	}

	protected void insert(String username, double pressureup,
			double pressuredown, double sizeup, double sizedown, double time,
			double accertation, double distance, double speed, double touchmajordown, double touchmajorup, double touchminordown, double touchminorup) {

		datasource.createUserdata(username, pressureup, pressuredown, sizeup,
				sizedown, time, accertation, distance, speed, touchmajordown, touchmajorup, touchminordown, touchminorup);
		/*
		 * Log.v("log_tag", "username" +username); Log.v("log_tag", "pressureup"
		 * +pressureup); Log.v("log_tag", "pressuredown" +pressuredown);
		 * Log.v("log_tag", "sizeup" +sizeup); Log.v("log_tag", "sizedown"
		 * +sizedown);
		 * 
		 * mDbHelper.insertdata(username, (double)((pressureup==0.0) ? 1.2 :
		 * pressureup), (double)((pressuredown==0.0) ? 1.2 : pressuredown),
		 * (double)((sizeup==0.0)? 1.2 :sizeup),(double)((sizedown==0.0) ? 1.2
		 * :sizedown));
		 */
	}

	double Distance(double x1, double y1, double x2, double y2) {
		double deltaX = (x2 - x1);
		double deltaY = (y2 - y1);
		return (double) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	double Accertation(double d, double time) {
		if (time == 0)
			return 0;
		else
			return d / (time * time);
	}

	double Speed(double d, double time) {
		if (time == 0)
			return 0;
		else
			return d / time;
	}

}