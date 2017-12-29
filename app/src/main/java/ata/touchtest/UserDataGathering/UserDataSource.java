package ata.touchtest.UserDataGathering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ata.touchtest.DatabaseHelper;

public class UserDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns = { DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_PRESSUREUP,
			DatabaseHelper.COLUMN_PRESSUREDOWN, DatabaseHelper.COLUMN_SIZEUP,
			DatabaseHelper.COLUMN_SIZEDOWN, DatabaseHelper.COLUMN_TIME,
			DatabaseHelper.COLUMN_ACCERTATION, DatabaseHelper.COLUMN_DISTANCE,
			DatabaseHelper.COLUMN_SPEED, DatabaseHelper.COLUMN_TOUCHMAJORDOWN,
			DatabaseHelper.COLUMN_TOUCHMAJORUP, DatabaseHelper.COLUMN_TOUCHMINORDOWN,
			DatabaseHelper.COLUMN_TOUCHMINORUP };

	private String[] usernameCol = { DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_USERNAME };

	public UserDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public UserData createUserdata(String username, double pressureup,
			double pressuredown, double sizeup, double sizedown, double time,
			double accertation, double distance, double speed,
			double touchmajordown, double touchmajorup, double touchminordown,
			double touchminorup) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_USERNAME, username);
		values.put(DatabaseHelper.COLUMN_PRESSUREUP, pressureup);
		values.put(DatabaseHelper.COLUMN_PRESSUREDOWN, pressuredown);
		values.put(DatabaseHelper.COLUMN_SIZEUP, sizeup);
		values.put(DatabaseHelper.COLUMN_SIZEDOWN, sizedown);
		values.put(DatabaseHelper.COLUMN_TIME, time);
		values.put(DatabaseHelper.COLUMN_ACCERTATION, accertation);
		values.put(DatabaseHelper.COLUMN_DISTANCE, distance);
		values.put(DatabaseHelper.COLUMN_SPEED, speed);
		values.put(DatabaseHelper.COLUMN_TOUCHMAJORDOWN, touchmajordown);
		values.put(DatabaseHelper.COLUMN_TOUCHMAJORUP, touchmajorup);
		values.put(DatabaseHelper.COLUMN_TOUCHMINORDOWN, touchminordown);
		values.put(DatabaseHelper.COLUMN_TOUCHMINORUP, touchminorup);

		long insertId = database.insert(DatabaseHelper.TABLE_COMMENTS, null,
				values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		UserData newUserdata = cursorToUserdataAll(cursor);
		cursor.close();
		return newUserdata;
	}

	public void deleteUserdata(UserData userdata) {
		long id = userdata.getId();
		System.out.println("userdata deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_COMMENTS, DatabaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public void deleteAllUserdata() {
		database.delete(DatabaseHelper.TABLE_COMMENTS, null, null);
	}

	public void deleteUser(String id) {
		database.delete(DatabaseHelper.TABLE_COMMENTS, DatabaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<UserData> getAllUserData() {
		List<UserData> userdatas = new ArrayList<UserData>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			UserData userdata = cursorToUserdataAll(cursor);
			userdatas.add(userdata);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return userdatas;
	}

	public UserData getUserData(double pressureup, double pressuredown,
			double sizeup, double sizedown, double time, double accertation,
			double distance, double speed, double touchmajordown, double touchmajorup, double touchminordown,
			double touchminorup ) {

		Cursor cursor = database.query(
				DatabaseHelper.TABLE_COMMENTS,
				usernameCol,
				"(" + DatabaseHelper.COLUMN_PRESSUREUP + " < "
						+ String.valueOf(pressureup + 0.2) + " and "
						+ DatabaseHelper.COLUMN_PRESSUREUP + " >= "
						+ String.valueOf(pressureup - 0.2) + ") and ("
						+ DatabaseHelper.COLUMN_PRESSUREDOWN + " < "
						+ String.valueOf(pressuredown + 0.2) + " and "
						+ DatabaseHelper.COLUMN_PRESSUREDOWN + " >= "
						+ String.valueOf(pressuredown - 0.2) + ") and ("
						+ DatabaseHelper.COLUMN_SIZEUP + " < "
						+ String.valueOf(sizeup + 0.2) + " and "
						+ DatabaseHelper.COLUMN_SIZEUP + " >= "
						+ String.valueOf(sizeup - 0.2) + ") and ("
						+ DatabaseHelper.COLUMN_SIZEDOWN + " < "
						+ String.valueOf(sizedown + 0.2) + " and "
						+ DatabaseHelper.COLUMN_SIZEDOWN + " >= "
						+ String.valueOf(sizedown - 0.2) + ") and ("
						+ DatabaseHelper.COLUMN_TIME + " < "
						+ String.valueOf(time + 2) + " and "
						+ DatabaseHelper.COLUMN_TIME + " >= "
						+ String.valueOf(time - 2) + ") and ("
						+ DatabaseHelper.COLUMN_ACCERTATION + " < "
						+ String.valueOf(accertation + 0.02) + " and "
						+ DatabaseHelper.COLUMN_ACCERTATION + " >= "
						+ String.valueOf(accertation - 0.02) + ") and ("
						+ DatabaseHelper.COLUMN_DISTANCE + " < "
						+ String.valueOf(distance + 0.02) + " and "
						+ DatabaseHelper.COLUMN_DISTANCE + " >= "
						+ String.valueOf(distance - 0.02) + ") and ("
						+ DatabaseHelper.COLUMN_SPEED + " < "
						+ String.valueOf(speed + 0.02) + " and "
						+ DatabaseHelper.COLUMN_SPEED + " >= "
						+ String.valueOf(speed - 0.02) + ") and ("
						+ DatabaseHelper.COLUMN_TOUCHMAJORDOWN + " < "
						+ String.valueOf(touchmajordown + 0.02) + " and "
						+ DatabaseHelper.COLUMN_TOUCHMAJORDOWN + " >= "
						+ String.valueOf(touchmajordown - 0.02) + ") and ("
						+ DatabaseHelper.COLUMN_TOUCHMAJORUP + " < "
						+ String.valueOf(touchmajorup + 0.02) + " and "
						+ DatabaseHelper.COLUMN_TOUCHMAJORUP + " >= "
						+ String.valueOf(touchmajorup - 0.02) + ") and ("
						+ DatabaseHelper.COLUMN_TOUCHMINORDOWN + " < "
						+ String.valueOf(touchminordown + 0.02) + " and "
						+ DatabaseHelper.COLUMN_TOUCHMINORDOWN + " >= "
						+ String.valueOf(touchminordown - 0.02) + ") and ("
						+ DatabaseHelper.COLUMN_TOUCHMINORUP + " < "
						+ String.valueOf(touchminorup + 0.02) + " and "
						+ DatabaseHelper.COLUMN_TOUCHMINORUP + " >= "
						+ String.valueOf(touchminorup - 0.02) + ")", null, null, null,
				null);

		UserData userdata = new UserData();
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			userdata = cursorToUserdata(cursor);
		}
		// Make sure to close the cursor
		cursor.close();
		return userdata;
	}

	public List<UserData> getData() {

		Cursor cursor = database.query(DatabaseHelper.TABLE_COMMENTS,
				usernameCol, null, null, null, null, null);

		List<UserData> ud = new ArrayList<UserData>();
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			ud.add(cursorToUserdata(cursor));
		}
		// Make sure to close the cursor
		cursor.close();
		return ud;
	}

	private UserData cursorToUserdata(Cursor cursor) {
		UserData userdata = new UserData();
		userdata.setId(cursor.getLong(0));
		userdata.setUsername(cursor.getString(1));
		return userdata;
	}

	private UserData cursorToUserdataAll(Cursor cursor) {
		UserData userdata = new UserData();
		userdata.setId(cursor.getLong(0));
		userdata.setUsername(cursor.getString(1) + " : \n "
				+ cursor.getString(2) + " / " + cursor.getString(3) + " / "
				+ cursor.getString(4) + " / " + cursor.getString(5) + " / "
				+ cursor.getString(6) + " / " + cursor.getString(7));
		return userdata;
	}
}
