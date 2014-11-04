package org.nowireless.imu;

public abstract class IMUAbstract implements IMU {

	private boolean enabled = false;
	
	private IMUListener listener = null;
	
	@Override public boolean isEnabled() { return enabled; }
	@Override public void enable() { this.enabled = true; }
	@Override public void disable() { this.enabled = false; }
	
	@Override
	public void registerListener(IMUListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void clearListener() {
		this.listener = null;
	}
	
	protected IMUListener getListener() {
		return this.listener;
	}
	
}
