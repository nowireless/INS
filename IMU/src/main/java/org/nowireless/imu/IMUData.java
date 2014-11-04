package org.nowireless.imu;

public interface IMUData {
	
	public double getX();
	public double getY();
	public double getZ();
	
	public double getAngle();
	
	public int getTimeStamp();
}
