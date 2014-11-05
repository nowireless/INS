package org.nowireless.imu;

public class ImmutableIMUData implements IMUData {
	private final double x;
	private final double y;
	private final double z;
	private final double angle;
	private final long timestamp;
	
	
	public ImmutableIMUData(double x, double y, double z, double angle, long ts) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.angle = angle;
		this.timestamp = ts;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public long getTimeStamp() {
		return timestamp;
	}
}
