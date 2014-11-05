package org.nowireless.ins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nowireless.common.Initializable;
import org.nowireless.common.LoggerProvidor;
import org.nowireless.common.Resetable;
import org.nowireless.imu.IMUData;
import org.nowireless.imu.IMUListener;
import org.nowireless.imu.ImmutableIMUData;
import org.nowireless.miscmath.ImmutableVector;
import org.nowireless.miscmath.RunningVector;
import org.nowireless.miscmath.Vector;
import org.nowireless.miscmath.VectorIntegration;
import org.nowireless.miscmath.VectorRotation;
import org.nowireless.miscmath.annotations.Radian;

public class INS implements IMUListener, Initializable, LoggerProvidor, Resetable {

	private final Logger log = LogManager.getLogger();
	@Override public Logger log() { return this.log; }

	private final VectorIntegration accelIntegration = new VectorIntegration();
	private final VectorIntegration velcoityIntegration = new VectorIntegration();
	
	private final Vector currentPosition = new RunningVector();
	
	private final Object imuDataLock = new Object();
	private IMUData currentIMUData = new ImmutableIMUData(0, 0, 0, 0, 0);
	
	private long lastTs = 0;
	
	private boolean firstRun = true;
	
	public Vector getCurrentPostion() {
		Vector ret = null;
		synchronized (currentPosition) {
			ret = ImmutableVector.valueOf(currentPosition);
		}
		return ret;
	}
	
	public Vector getCurrentVelocitys() {
		Vector ret;
		synchronized (accelIntegration) {
			ret = accelIntegration.getIntegral();
		}
		return ret;
	}
	
	public Vector getCurrentAccelerations() {
		Vector ret;
		synchronized (imuDataLock) {
			ret = IMUHelper.imuDataToVector(currentIMUData);
		}
		return ret;
	}
	
	@Radian
	public double getCurrentAngle() {
		double ret;
		synchronized (imuDataLock) {
			ret = currentIMUData.getAngle();
		}
		return ret;
	}
	
	@Override
	public void init() {
		this.rest();
	}

	@Override
	public void deinit() {
	}
	
	@Override
	public void rest() {
		this.accelIntegration.reset();
		this.velcoityIntegration.reset();
		this.currentPosition.reset();
		this.currentIMUData = new ImmutableIMUData(0, 0, 0, 0, 0);
		this.lastTs = 0;
		this.firstRun = true;
	}
	
	@Override
	public void onNewData(IMUData data) {
		if(!firstRun) {
			
			synchronized (imuDataLock) {
				this.currentIMUData = data;
			}
			
			long delta = data.getTimeStamp() - this.lastTs;
			Vector robotFrameAccelerations = IMUHelper.imuDataToVector(data);
			Vector fieldFrameAccelerations = VectorRotation.rotateCounterClockWise(robotFrameAccelerations, data.getAngle());
			
			Vector velocitys;
			synchronized (accelIntegration) {
				accelIntegration.integrate(fieldFrameAccelerations, delta);
				velocitys = accelIntegration.getIntegral();
			}
			
			Vector position;
			synchronized (velcoityIntegration) {
				velcoityIntegration.integrate(velocitys, delta);
				position = velcoityIntegration.getIntegral();
			}
			
			synchronized (currentPosition) {
				currentPosition.add(position);
			}
			
		} else {
			this.firstRun = false;
			this.lastTs = System.currentTimeMillis();
		}
	}

}
