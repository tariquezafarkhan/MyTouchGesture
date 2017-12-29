package ata.touchtest.UserDataGathering;

public class UserData {
	private long _id;
	private String _username;
	private double _pressureup;
	private double _pressuredown;
	private double _sizeup;
	private double _sizedown;
	private double _time;
	private double _accertation;
	private double _distance;
	private double _speed;
	private double _touchmajordown;
	private double _touchmajorup;
	private double _touchminordown;
	private double _touchminorup;

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		this._id = id;
	}

	public String getUsername() {
		return _username;
	}

	public void setUsername(String username) {
		this._username = username;
	}

	public double getPressureup() {
		return _pressureup;
	}

	public void setPressureup(double pressureup) {
		this._pressureup = pressureup;
	}

	public double getPressuredown() {
		return _pressuredown;
	}

	public void setPressuredown(double pressuredown) {
		this._pressuredown = pressuredown;
	}

	public double getSizeup() {
		return _sizeup;
	}

	public void setSizeup(double sizeup) {
		this._sizeup = sizeup;
	}

	public double getSizedown() {
		return _sizedown;
	}

	public void setSizedown(double sizedown) {
		this._sizedown = sizedown;
	}

	public double getTime() {
		return _time;
	}

	public void setTime(double time) {
		this._time = time;
	}

	public double getAccertation() {
		return _accertation;
	}

	public void setAccertation(double accertation) {
		this._accertation = accertation;
	}

	public double getDistance() {
		return _distance;
	}

	public void setDistance(double distance) {
		this._distance = distance;
	}

	public double getSpeed() {
		return _speed;
	}

	public void setSpeed(double speed) {
		this._speed = speed;
	}
	
	public double getTouchMajorDown() {
		return _touchmajordown;
	}

	public void setTouchMajorDown(double touchmajordown) {
		this._touchmajordown = touchmajordown;
	}
	
	public double getTouchMajorUp() {
		return _touchmajorup;
	}

	public void setTouchMajorUp(double touchmajorup) {
		this._touchmajorup = touchmajorup;
	}
	
	public double getTouchMinorDown() {
		return _touchminordown;
	}

	public void setTouchMinorDown(double touchminordown) {
		this._touchminordown = touchminordown;
	}
	
	public double getTouchMinorUp() {
		return _touchminorup;
	}

	public void setTouchMinorUp(double touchminorup) {
		this._touchminorup = touchminorup;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return _id + " - " + _username;
	}
}