package org.nowireless.imu;

public interface IMU {
	
	public void registerListener(IMUListener listener);
	public void clearListener();
	
	public void enable();
	public void disable();
	
	public boolean isEnabled();
	
}
