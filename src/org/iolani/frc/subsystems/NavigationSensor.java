package org.iolani.frc.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
//import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * AHRS
 */
public class NavigationSensor extends Subsystem implements PIDSource {
    
    private AHRS _ahrs;
	
	public void init() {
		try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            _ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
	}
	
	public boolean isCalibrating() {
		return _ahrs.isCalibrating();
	}
	
	public void zeroGyro() {
		_ahrs.zeroYaw();
	}
	
	public double getGyro() {
		return _ahrs.getYaw();
	}
	
	public double pidGet() {
		return this.getGyro();
	}
	
	public void debug() {
		SmartDashboard.putNumber("imu-yaw", _ahrs.getYaw());
		SmartDashboard.putBoolean("imu-moving", _ahrs.isMoving());
		SmartDashboard.putBoolean("imu-connected", _ahrs.isConnected());
		SmartDashboard.putBoolean("imu-calibrating", _ahrs.isCalibrating());
		SmartDashboard.putData("imu", _ahrs);
	}
	
    public void initDefaultCommand() { }
    
    public PIDSourceType getPIDSourceType() {
    	return _ahrs.getPIDSourceType();
    }

    public void setPIDSourceType(PIDSourceType type) {
    	_ahrs.setPIDSourceType(type);
    }
}