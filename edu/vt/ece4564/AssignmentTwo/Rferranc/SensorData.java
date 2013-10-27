package edu.vt.ece4564.AssignmentTwo.Rferranc;

/*
 * Container for sensor data it holds a point in time and the
 * data at that point.
 */
public class SensorData {
	private long time;
	private int humidity;
	private int temp;
	private int light;
	private String measuredFor;
	
	public SensorData(String loc) {
		measuredFor = loc;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public String getMeasuredFor() {
		return measuredFor;
	}

	public void setMeasuredFor(String measuredFor) {
		this.measuredFor = measuredFor;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getLight() {
		return light;
	}

	public void setLight(int light) {
		this.light = light;
	}
	
}
