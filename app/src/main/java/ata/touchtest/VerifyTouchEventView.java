package ata.touchtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ata.touchtest.UserDataGathering.UserData;
import ata.touchtest.UserDataGathering.UserDataSource;
import ata.touchtest.UserDataGathering.UserDataSourceResultData;

public class VerifyTouchEventView extends View {
	 
	  UserIdentityActivity uia;  
	  private Paint paint = new Paint();
	  private Path path = new Path();
	  private UserDataSource datasource;
	  private UserDataSourceResultData DSResult;
	  double pressureup,pressuredown,sizeup,sizedown, touchmajordown, touchmajorup, touchminordown, touchminorup;
	  long time1=0,time2=0;
	  double x1=0,x2=0, y1=0,y2=0;

	  public VerifyTouchEventView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    
	    paint.setAntiAlias(true);
	    paint.setStrokeWidth(6f);
	    paint.setColor(Color.BLUE);
	    paint.setStyle(Paint.Style.STROKE);
	    paint.setStrokeJoin(Paint.Join.ROUND);
	    
	    datasource = new UserDataSource(getContext());
	    datasource.open();
	    
	    DSResult = new UserDataSourceResultData(getContext());
	    DSResult.open();
	    
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
	      //Toast.makeText(getContext()," Down pressure is:"+ String.valueOf(event.getPressure())+ " :\n Down size is:"+ String.valueOf(event.getSize()), Toast.LENGTH_SHORT).show();
	      pressuredown=event.getPressure();
	      touchmajordown = event.getTouchMajor();
		  touchminordown = event.getToolMinor();
	      sizedown=event.getSize();
	      time1=event.getDownTime();
	      x1=event.getX();
	      y1=event.getY();
	      return true;
	    case MotionEvent.ACTION_MOVE:
	      path.lineTo(eventX, eventY);
	      break;
	    case MotionEvent.ACTION_UP:
	       //Toast.makeText(getContext()," Up pressure is:"+ String.valueOf(event.getPressure())+ " :\n Up size is:"+ String.valueOf(event.getSize()), Toast.LENGTH_SHORT).show();
	       pressureup=event.getPressure();
	       touchmajorup = event.getTouchMajor();
			touchminorup = event.getToolMinor();
	       sizeup=event.getSize();
	       time2=event.getEventTime();
	       x2=event.getX();
	       y2=event.getY();

	       double distance = Distance(x1, y1, x2, y2);
	       double speed=Speed(distance,Math.abs(time2-time1));
	       double accertation=Accertation(distance,Math.abs(time2-time1));
	       //Toast.makeText(getContext(), String.valueOf(pressureup)+" - "+String.valueOf(pressuredown)+" - "+String.valueOf(sizeup)+" - "+String.valueOf(sizedown)+" - "+String.valueOf(Math.abs(time2-time1))+" - "+String.valueOf(accertation), Toast.LENGTH_SHORT).show();
	       DSResult.createUserdata(String.valueOf("Ali"), pressureup, pressuredown, sizeup, sizedown, Math.abs(time2-time1), accertation, distance, speed, touchmajordown, touchmajorup, touchminordown, touchminorup);

	       List<UserData> udata = new ArrayList<UserData>();
	       udata=datasource.getData();
	       //ud = datasource.getUserData(double.valueOf(pressureup), double.valueOf(pressuredown), double.valueOf(sizeup), double.valueOf(sizedown), double.valueOf(Math.abs(time2-time1)),double.valueOf(accertation));
	       
	       for (UserData ud : udata) {
	    	   
	    	   Toast.makeText(getContext()," user is: "+ ud.getUsername() +" - "+ud.getTime(), Toast.LENGTH_SHORT).show();
			
	    	 /*  if(ud.getUsername()!=null)
		    	   Toast.makeText(getContext()," user is: "+ ud.getUsername(), Toast.LENGTH_SHORT).show();
		       else
		    	   Toast.makeText(getContext()," user is not verified", Toast.LENGTH_SHORT).show();*/
	       }
	       
	      break;
	    default:
	      return false;
	    }
	    invalidate();
	    return true;
	  }
	  
	  
	  double Distance(double x1,double y1,double x2,double y2 )
	  {
	      double deltaX = (x2-x1);
	      double deltaY = (y2-y1);
	      return (double) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	  }
	  
	  double Accertation( double d, double time)
	  {
		  if(time==0)
			  return 0;
		  else
			  return d/(time*time);
	  }
	  
	  double Speed( double d, double time)
	  {
		  if(time==0)
			  return 0;
		  else
			  return d/time;
	  }

	} 