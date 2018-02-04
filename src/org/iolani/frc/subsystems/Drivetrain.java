package org.iolani.frc.subsystems;

import org.iolani.frc.RobotMap;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drivetrain extends Subsystem {
	private TalonSRX _frontLeft; 
	private TalonSRX _backLeft;
	private TalonSRX _frontRight;
	private TalonSRX _backRight;
	
	private static final double WHEEL_DIAMETER_INCHES  = 8.0;
	private static final double WHEEL_INCHES_PER_REV   = Math.PI * WHEEL_DIAMETER_INCHES;
	private static final int ENCODER_TICKS_PER_REV = 140;
	private static final double ENCODER_INCHES_PER_REV = ENCODER_TICKS_PER_REV / WHEEL_INCHES_PER_REV;
	
	double frontLeftOffSet;
	double frontRightOffSet;
	double backLeftOffSet;
	double backRightOffSet;
	
	public void init()
	{
		
		
		_frontLeft = new TalonSRX(RobotMap._frontLeft);
		_frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		_frontLeft.configMotionCruiseVelocity(3, 0);
		_frontLeft.configMotionAcceleration(1, 0);
		_frontLeft.selectProfileSlot(0, 0);
		_frontLeft.config_kF(0, 0, 0);
		_frontLeft.config_kP(0, 0, 0);
		_frontLeft.config_kI(0, 0, 0);
		_frontLeft.config_kD(0, 0, 0);
		_frontLeft.configPeakOutputForward(.5, 0);
		_frontLeft.configPeakOutputReverse(-.5, 0);
		
		_backLeft = new TalonSRX(RobotMap._backLeft);
		_backLeft.configMotionAcceleration(1, 0);
		_backLeft.selectProfileSlot(0, 0);
		_backLeft.config_kF(0, 0, 0);
		_backLeft.config_kP(0, 0, 0);
		_backLeft.config_kI(0, 0, 0);
		_backLeft.config_kD(0, 0, 0);
		_backLeft.configPeakOutputForward(.5, 0);
		_backLeft.configPeakOutputReverse(-.5, 0);
		
		_frontRight = new TalonSRX(RobotMap._frontRight);
		_frontRight.setInverted(true);
		_frontRight.configMotionAcceleration(1, 0);
		_frontRight.selectProfileSlot(0, 0);
		_frontRight.config_kF(0, 0, 0);
		_frontRight.config_kP(0, 0, 0);
		_frontRight.config_kI(0, 0, 0);
		_frontRight.config_kD(0, 0, 0);
		_frontRight.configPeakOutputForward(.5, 0);
		_frontRight.configPeakOutputReverse(-.5, 0);
	
    	_backRight = new TalonSRX(RobotMap._frontRight);
    	_backRight.setInverted(true);
    	_backRight.configMotionAcceleration(1, 0);
    	_backRight.selectProfileSlot(0, 0);
    	_backRight.config_kF(0, 0, 0);
    	_backRight.config_kP(0, 0, 0);
    	_backRight.config_kI(0, 0, 0);
    	_backRight.config_kD(0, 0, 0);
    	_backRight.configPeakOutputForward(.5, 0);
    	_backRight.configPeakOutputReverse(-.5, 0);
    	
		frontLeftOffSet = _frontLeft.getSensorCollection().getPulseWidthPosition();
		frontRightOffSet = _frontRight.getSensorCollection().getPulseWidthPosition();
	}
	
	public void initDefaultCommand()
	{
		
	}
	
	public void moveMotor()
	{
		
	}
	
	public double getLeft() {
		return _frontLeft.getSensorCollection().getPulseWidthPosition() - frontLeftOffSet;
	}
	
	public double getRight()
	{
		return _frontRight.getSensorCollection().getPulseWidthPosition() - frontRightOffSet;
	}
	
	public void debug() {
		SmartDashboard.putNumber("Rotations", getLeft() / ENCODER_TICKS_PER_REV);
	}
}