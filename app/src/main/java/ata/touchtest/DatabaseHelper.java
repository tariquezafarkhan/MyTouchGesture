package ata.touchtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
	  public static final String TABLE_COMMENTS = "GestureDate";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_USERNAME = "username";
	  public static final String COLUMN_PRESSUREUP = "pressureup";
	  public static final String COLUMN_PRESSUREDOWN = "pressuredown";
	  public static final String COLUMN_SIZEUP = "sizeup";
	  public static final String COLUMN_SIZEDOWN = "sizedown";
	  public static final String COLUMN_TIME = "time";
	  public static final String COLUMN_ACCERTATION = "accertation";
	  public static final String COLUMN_DISTANCE = "distance";
	  public static final String COLUMN_SPEED = "speed";
	  public static final String COLUMN_TOUCHMAJORDOWN = "touchmajordown";
	  public static final String COLUMN_TOUCHMAJORUP = "touchmajorup";
	  public static final String COLUMN_TOUCHMINORDOWN = "touchminordown";
	  public static final String COLUMN_TOUCHMINORUP = "touchminorup";
	  
	  public static final String DATABASE_NAME = "GestureData.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_COMMENTS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_USERNAME
	      + " text not null, " + COLUMN_PRESSUREUP
	      + " float null, " + COLUMN_PRESSUREDOWN
	      + " float null, " + COLUMN_SIZEUP
	      + " float null, " + COLUMN_SIZEDOWN
	      + " float null, " + COLUMN_TIME
	      + " float null, " + COLUMN_ACCERTATION
	      + " float null, " + COLUMN_DISTANCE
	      + " float null, " + COLUMN_SPEED
	      + " float null, " + COLUMN_TOUCHMAJORDOWN
	      + " float null, " + COLUMN_TOUCHMAJORUP
	      + " float null, " + COLUMN_TOUCHMINORDOWN
	      + " float null, " + COLUMN_TOUCHMINORUP
	      + " float null);";

	  public DatabaseHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(DatabaseHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
	    onCreate(db);
	  }

	} 