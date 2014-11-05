package org.nowireless.ins;

import org.nowireless.imu.IMUData;
import org.nowireless.miscmath.ImmutableVector;
import org.nowireless.miscmath.Vector;

public class IMUHelper {
	
	public static Vector imuDataToVector(IMUData data) {
		return new ImmutableVector(data.getX(), data.getY());
	}
}
