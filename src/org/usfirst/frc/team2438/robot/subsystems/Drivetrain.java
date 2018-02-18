package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateTankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain
 */
public class Drivetrain extends Subsystem {
	
	public static enum DriveMode {
		Tank,
		Arcade,
		Mecanum
	}
	
	public static final double COUNTS_PER_ROTATION = 537.6;
	private static final double WHEEL_DIAMETER = 4;
	private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	public static final double UNITS_PER_INCH = COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;

    private static final int TALON_TIMEOUT = 20;
    
	private TalonSRX _leftFront;
    private TalonSRX _rightFront;
    private TalonSRX _leftBack;
    private TalonSRX _rightBack;
    
    private double kF = 0;
    private double kP = 6.0;
    private double kI = 0;
    private double kD = 0;
    private int iZone = 0;
    private final int DEFAULT_VELOCITY = 1000;
    
    private DriveMode _dmode;
	
	public void init() {
		_leftFront = new TalonSRX(RobotMap.leftFrontMotor);		
		_leftBack = new TalonSRX(RobotMap.leftBackMotor);
		_rightFront = new TalonSRX(RobotMap.rightFrontMotor);
		_rightBack = new TalonSRX(RobotMap.rightBackMotor);
		
		_leftFront.setInverted(true);
		_leftBack.setInverted(true);
		_rightBack.setSensorPhase(true);
		
		_leftFront.follow(_leftBack);
		_rightFront.follow(_rightBack);
		
		_dmode = DriveMode.Tank;
		
		// Left side encoder //
		_leftBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
		_leftBack.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		_leftBack.set(ControlMode.MotionMagic, 0);
		
		_leftBack.selectProfileSlot(0, 0);
		_leftBack.config_kF(0, kF, TALON_TIMEOUT);
		_leftBack.config_kP(0, kP, TALON_TIMEOUT);
		_leftBack.config_kI(0, kI, TALON_TIMEOUT);
		_leftBack.config_kD(0, kD, TALON_TIMEOUT);
		_leftBack.config_IntegralZone(0, iZone, TALON_TIMEOUT);
		_leftBack.configMotionCruiseVelocity(DEFAULT_VELOCITY, TALON_TIMEOUT);
		_leftBack.configMotionAcceleration(DEFAULT_VELOCITY, TALON_TIMEOUT);
		
		// Right side encoder //
		_rightBack.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
		_rightBack.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		_rightBack.set(ControlMode.MotionMagic, 0);
		
		_rightBack.selectProfileSlot(0, 0);
		_rightBack.config_kF(0, kF, TALON_TIMEOUT);
		_rightBack.config_kP(0, kP, TALON_TIMEOUT);
		_rightBack.config_kI(0, kI, TALON_TIMEOUT);
		_rightBack.config_kD(0, kD, TALON_TIMEOUT);
		_rightBack.config_IntegralZone(0, iZone, TALON_TIMEOUT);
		_rightBack.configMotionCruiseVelocity(DEFAULT_VELOCITY, TALON_TIMEOUT);
		_rightBack.configMotionAcceleration(DEFAULT_VELOCITY, TALON_TIMEOUT);
	}
	
	/**
	 * Sets tank mode
	 * @param left
	 * @param right
	 */
	public void setTank(double left, double right) {
		_leftFront.set(ControlMode.PercentOutput, left);
		_leftBack.set(ControlMode.PercentOutput, left);
		_rightFront.set(ControlMode.PercentOutput, right);
		_rightBack.set(ControlMode.PercentOutput, right);
	}
	
	/**
	 * Sets arcade mode
	 * @param x
	 * @param y
	 */
	public void setArcade(double x, double y) {
		_leftFront.set(ControlMode.PercentOutput,   x + y);
		_leftBack.set(ControlMode.PercentOutput,    x + y);
		_rightFront.set(ControlMode.PercentOutput, -x + y);
		_rightBack.set(ControlMode.PercentOutput,  -x + y);
		
	}
	
	/**
	 * Sets mecanum mode
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void setMecanum(double x, double y, double rotation) {
		_leftFront.set(ControlMode.PercentOutput,   x + y + rotation);
		_leftBack.set(ControlMode.PercentOutput,   -x + y + rotation);
		_rightFront.set(ControlMode.PercentOutput, -x + y - rotation);
		_rightBack.set(ControlMode.PercentOutput,   x + y - rotation);
	}
	
	/**
	 * Get the current drive mode
	 * @return _dmode
	 */
	public DriveMode getDriveMode() {
		return _dmode;
	}
	
	public void setDriveMode(DriveMode mode) {
		_dmode = mode;
	}
	
	/**
	 * Cycle through drive modes
	 */
	public void cycleDriveMode() {
		switch(_dmode) {
			case Tank:
				_dmode = DriveMode.Arcade;
				break;
			case Arcade:
				_dmode = DriveMode.Mecanum;
				break;
			case Mecanum:
				_dmode = DriveMode.Tank;
				break;
			default:
				_dmode = DriveMode.Tank;
		}
	}
	
	public void rotateWheel(double rotations) {
		_leftBack.set(ControlMode.MotionMagic, Math.round((float) 537.6 * rotations));
	}
	
	public void driveToPosition(double inches) {
		this.driveToPosition(inches, DEFAULT_VELOCITY);
	}
	
	public void driveToPosition(double inches, double velocity) {
		int cruiseVelocity = Math.round((float) (UNITS_PER_INCH * velocity));
		
		_leftBack.configMotionCruiseVelocity(cruiseVelocity, TALON_TIMEOUT);
		_leftBack.configMotionAcceleration(cruiseVelocity, TALON_TIMEOUT);
		
		int position = Math.round((float) (UNITS_PER_INCH * inches));
		
		_leftBack.set(ControlMode.MotionMagic, position);
		_rightBack.set(ControlMode.MotionMagic, position);
	}

    public void resetEncoders() {
    	_leftBack.setSelectedSensorPosition(0,0, TALON_TIMEOUT);
    	_leftBack.set(ControlMode.MotionMagic, 0);
    	
    	_rightBack.setSelectedSensorPosition(0,0, TALON_TIMEOUT);
    	_rightBack.set(ControlMode.MotionMagic, 0);
    }
    
	public double getLeftEncoder() {
		return _leftBack.getSelectedSensorPosition(0);
	}
	
	public double getRightEncoder() {
		return _rightBack.getSelectedSensorPosition(0);
	}
	
	public void setLeftEncoderPosition(int position) {
		_leftFront.setSelectedSensorPosition(position, 0, TALON_TIMEOUT);
	}
	
	public void setRightEncoderPosition(int position) {
		_rightFront.setSelectedSensorPosition(position, 0, TALON_TIMEOUT);
	}
	
	public double getLeftError() {
		return _leftBack.getClosedLoopError(0);
	}
	
	public double getRightError() {
		return _rightBack.getClosedLoopError(0);
	}
	
	public void initDefaultCommand() {
		//setDefaultCommand(null);
        setDefaultCommand(new OperateTankDrive());
    }
}

