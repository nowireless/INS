package org.nowireless.imu;

public interface IMUData {
	
	/**
	 * Gets the X Acceleration
	 * @return The X Accel
	 */
	public double getX();
	
	/**
	 * Gets the Y Acceleration
	 * @return The Y Accel
	 */
	public double getY();
	
	/**
	 * Gets the Z Acceleration
	 * @return The Y Accel
	 */
	public double getZ();
	
	/**
	 * Gets the current angle
	 * @return The angle
	 */
	public double getAngle();
	
	/**
	 * The Time stamp in milliseconds
	 * @return The timestamp
	 */
	public long getTimeStamp();
}
