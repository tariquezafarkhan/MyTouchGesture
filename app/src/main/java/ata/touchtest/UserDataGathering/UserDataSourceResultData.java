package ata.touchtest.UserDataGathering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ata.touchtest.DatabaseHelperToRecordResults;

public class UserDataSourceResultData {

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelperToRecordResults dbHelper;
	private String[] allColumns = { DatabaseHelperToRecordResults.COLUMN_ID,
			DatabaseHelperToRecordResults.COLUMN_USERNAME,
			DatabaseHelperToRecordResults.COLUMN_PRESSUREUP,
			DatabaseHelperToRecordResults.COLUMN_PRESSUREDOWN,
			DatabaseHelperToRecordResults.COLUMN_SIZEUP,
			DatabaseHelperToRecordResults.COLUMN_SIZEDOWN,
			DatabaseHelperToRecordResults.COLUMN_TIME,
			DatabaseHelperToRecordResults.COLUMN_ACCERTATION,
			DatabaseHelperToRecordResults.COLUMN_DISTANCE,
			DatabaseHelperToRecordResults.COLUMN_SPEED,
			DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORDOWN,
			DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORUP,
			DatabaseHelperToRecordResults.COLUMN_TOUCHMINORDOWN,
			DatabaseHelperToRecordResults.COLUMN_TOUCHMINORUP };

	private String[] usernameCol = { DatabaseHelperToRecordResults.COLUMN_ID,
			DatabaseHelperToRecordResults.COLUMN_USERNAME };

	public UserDataSourceResultData(Context context) {
		dbHelper = new DatabaseHelperToRecordResults(context);
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
		values.put(DatabaseHelperToRecordResults.COLUMN_USERNAME, username);
		values.put(DatabaseHelperToRecordResults.COLUMN_PRESSUREUP, pressureup);
		values.put(DatabaseHelperToRecordResults.COLUMN_PRESSUREDOWN,
				pressuredown);
		values.put(DatabaseHelperToRecordResults.COLUMN_SIZEUP, sizeup);
		values.put(DatabaseHelperToRecordResults.COLUMN_SIZEDOWN, sizedown);
		values.put(DatabaseHelperToRecordResults.COLUMN_TIME, time);
		values.put(DatabaseHelperToRecordResults.COLUMN_ACCERTATION,
				accertation);
		values.put(DatabaseHelperToRecordResults.COLUMN_DISTANCE, distance);
		values.put(DatabaseHelperToRecordResults.COLUMN_SPEED, speed);
		values.put(DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORDOWN,
				touchmajordown);
		values.put(DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORUP,
				touchmajorup);
		values.put(DatabaseHelperToRecordResults.COLUMN_TOUCHMINORDOWN,
				touchminordown);
		values.put(DatabaseHelperToRecordResults.COLUMN_TOUCHMINORUP,
				touchminorup);

		long insertId = database.insert(
				DatabaseHelperToRecordResults.TABLE_COMMENTS, null, values);
		Cursor cursor = database.query(
				DatabaseHelperToRecordResults.TABLE_COMMENTS, allColumns,
				DatabaseHelperToRecordResults.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		UserData newUserdata = cursorToUserdataAll(cursor);
		cursor.close();
		return newUserdata;
	}

	public void deleteUserdata(UserData userdata) {
		long id = userdata.getId();
		System.out.println("userdata deleted with id: " + id);
		database.delete(DatabaseHelperToRecordResults.TABLE_COMMENTS,
				DatabaseHelperToRecordResults.COLUMN_ID + " = " + id, null);
	}

	public void deleteAllUserdata() {
		database.delete(DatabaseHelperToRecordResults.TABLE_COMMENTS, null,
				null);
	}

	public void deleteUser(String id) {
		database.delete(DatabaseHelperToRecordResults.TABLE_COMMENTS,
				DatabaseHelperToRecordResults.COLUMN_ID + " = " + id, null);
	}

	public List<UserData> getAllUserData() {
		List<UserData> userdatas = new ArrayList<UserData>();

		Cursor cursor = database.query(
				DatabaseHelperToRecordResults.TABLE_COMMENTS, allColumns, null,
				null, null, null, null);

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
			double distance, double speed,
			double touchmajordown, double touchmajorup, double touchminordown,
			double touchminorup) {

		Cursor cursor = database.query(
				DatabaseHelperToRecordResults.TABLE_COMMENTS, usernameCol, "("
						+ DatabaseHelperToRecordResults.COLUMN_PRESSUREUP
						+ " < " + String.valueOf(pressureup + 0.2) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_PRESSUREUP
						+ " >= " + String.valueOf(pressureup - 0.2) + ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_PRESSUREDOWN
						+ " < " + String.valueOf(pressuredown + 0.2) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_PRESSUREDOWN
						+ " >= " + String.valueOf(pressuredown - 0.2)
						+ ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_SIZEUP + " < "
						+ String.valueOf(sizeup + 0.2) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_SIZEUP + " >= "
						+ String.valueOf(sizeup - 0.2) + ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_SIZEDOWN + " < "
						+ String.valueOf(sizedown + 0.2) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_SIZEDOWN
						+ " >= " + String.valueOf(sizedown - 0.2) + ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_TIME + " < "
						+ String.valueOf(time + 2) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_TIME + " >= "
						+ String.valueOf(time - 2) + ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_ACCERTATION
						+ " < " + String.valueOf(accertation + 0.02) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_ACCERTATION
						+ " >= " + String.valueOf(accertation - 0.02)
						+ " ) and ("
						+ DatabaseHelperToRecordResults.COLUMN_DISTANCE + " < "
						+ String.valueOf(distance + 0.02) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_DISTANCE
						+ " >= " + String.valueOf(distance - 0.02) + " ) and ("
						+ DatabaseHelperToRecordResults.COLUMN_SPEED + " < "
						+ String.valueOf(speed + 0.02) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_SPEED + " >= "
						+ String.valueOf(speed - 0.02) + " ) and ("
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORDOWN + " < "
						+ String.valueOf(touchmajordown + 0.02) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORDOWN + " >= "
						+ String.valueOf(touchmajordown - 0.02) + ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORUP + " < "
						+ String.valueOf(touchmajorup + 0.02) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMAJORUP + " >= "
						+ String.valueOf(touchmajorup - 0.02) + ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMINORDOWN + " < "
						+ String.valueOf(touchminordown + 0.02) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMINORDOWN + " >= "
						+ String.valueOf(touchminordown - 0.02) + ") and ("
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMINORUP + " < "
						+ String.valueOf(touchminorup + 0.02) + " and "
						+ DatabaseHelperToRecordResults.COLUMN_TOUCHMINORUP + " >= "
						+ String.valueOf(touchminorup - 0.02) + ")", null, null,
				null, null);
		UserData userdata = new UserData();
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			userdata = cursorToUserdata(cursor);
		}
		// Make sure to close the cursor
		cursor.close();
		return userdata;
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
