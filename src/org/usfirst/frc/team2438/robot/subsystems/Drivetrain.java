package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateTankDrive;
import org.usfirst.frc.team2438.robot.util.Constants;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain
 */
public class Drivetrain extends Subsystem {
	
	public static enum ProfileSlot {
		MotionMagic,
		Velocity
	}
	
	private static final double WHEEL_DIAMETER = 6; // inches
	private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	
	private static final double COUNTS_PER_EDGE = 4;
	private static final double GEAR_RATIO = 6; // 6:1 gear ratio
	private static final double PULSES_PER_ROTATION = 128;
	private static final double COUNTS_PER_ROTATION = PULSES_PER_ROTATION * COUNTS_PER_EDGE * GEAR_RATIO;
	
	public static final double UNITS_PER_INCH = COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;
	
	private static final double MAX_SPEED = 3800; // Native units per 100 ms
	
	/* Motion Magic constants */
	private static final double kF = 0;
	private static final double kP = 0.5;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	/* Velocity Control Constants */
	private static final double v_kF = 0.267;
	private static final double v_kP = 0.51;
	private static final double v_kI = 0.007;
	private static final double v_kD = 5.1;
	private static final int v_iZone = 0;
	
	// FIXME - Determine velocity
	private static final int DRIVE_VELOCITY = 1228;		// Native units per 100 ms
	private static final int DRIVE_ACCELERATION = 1228; // Native units per 100 ms
	
	private static final int ERROR_THRESHOLD = 200; // Allowable error in native units
    
	private TalonSRX _frontLeft;
    private TalonSRX _frontRight;
    private TalonSRX _backLeft;
    private TalonSRX _backRight;
    
    private TargetCounter _targetCounter;
	
	public void init() {
		_frontLeft = new TalonSRX(RobotMap.frontLeftMotor);
		_frontRight = new TalonSRX(RobotMap.frontRightMotor);
		_backLeft = new TalonSRX(RobotMap.backLeftMotor);
		_backRight = new TalonSRX(RobotMap.backRightMotor);
		
		// Reverse left side motors
		_frontLeft.setInverted(true);
		_backLeft.setInverted(true);
		
		// Set the front motors to follow the back
		_frontLeft.follow(_backLeft);
		_frontRight.follow(_backRight);
		
		/* Motion Magic */
		_backLeft.setSensorPhase(true);
		_backLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.TALON_TIMEOUT);
		_backLeft.config_kF(0, kF, Constants.TALON_TIMEOUT);
		_backLeft.config_kP(0, kP, Constants.TALON_TIMEOUT);
		_backLeft.config_kI(0, kI, Constants.TALON_TIMEOUT);
		_backLeft.config_kD(0, kD, Constants.TALON_TIMEOUT);
		_backLeft.config_IntegralZone(0, iZone, Constants.TALON_TIMEOUT);
		_backLeft.configMotionCruiseVelocity(DRIVE_VELOCITY, Constants.TALON_TIMEOUT);
		_backLeft.configMotionAcceleration(DRIVE_ACCELERATION, Constants.TALON_TIMEOUT);
		
		_backRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.TALON_TIMEOUT);
		_backRight.config_kF(0, kF, Constants.TALON_TIMEOUT);
		_backRight.config_kP(0, kP, Constants.TALON_TIMEOUT);
		_backRight.config_kI(0, kI, Constants.TALON_TIMEOUT);
		_backRight.config_kD(0, kD, Constants.TALON_TIMEOUT);
		_backRight.config_IntegralZone(0, iZone, Constants.TALON_TIMEOUT);
		_backRight.configMotionCruiseVelocity(DRIVE_VELOCITY, Constants.TALON_TIMEOUT);
		_backRight.configMotionAcceleration(DRIVE_ACCELERATION, Constants.TALON_TIMEOUT);
		
		/* Velocity Control */
		_backLeft.configNominalOutputForward(0, Constants.TALON_TIMEOUT);
		_backLeft.configNominalOutputReverse(0, Constants.TALON_TIMEOUT);
		_backLeft.configPeakOutputForward(1, Constants.TALON_TIMEOUT);
		_backLeft.configPeakOutputReverse(-1, Constants.TALON_TIMEOUT);
		
		_backLeft.config_kF(1, v_kF, Constants.TALON_TIMEOUT);
		_backLeft.config_kP(1, v_kP, Constants.TALON_TIMEOUT);
		_backLeft.config_kI(1, v_kI, Constants.TALON_TIMEOUT);
		_backLeft.config_kD(1, v_kD, Constants.TALON_TIMEOUT);
		_backLeft.config_IntegralZone(1, v_iZone, Constants.TALON_TIMEOUT);
		
		_backRight.configNominalOutputForward(0, Constants.TALON_TIMEOUT);
		_backRight.configNominalOutputReverse(0, Constants.TALON_TIMEOUT);
		_backRight.configPeakOutputForward(1, Constants.TALON_TIMEOUT);
		_backRight.configPeakOutputReverse(-1, Constants.TALON_TIMEOUT);
		
		_backRight.config_kF(1, v_kF, Constants.TALON_TIMEOUT);
		_backRight.config_kP(1, v_kP, Constants.TALON_TIMEOUT);
		_backRight.config_kI(1, v_kI, Constants.TALON_TIMEOUT);
		_backRight.config_kD(1, v_kD, Constants.TALON_TIMEOUT);
		_backRight.config_IntegralZone(1, v_iZone, Constants.TALON_TIMEOUT);
		
		// Initialize the target counter
		_targetCounter = new TargetCounter(ERROR_THRESHOLD);
    	
		// Set the default profile slot to Motion Magic
    	this.setProfileSlot(ProfileSlot.MotionMagic);
    	
    	this.resetEncoders();
	}
	
	/**
	 * Drives in tank mode
	 * @param left
	 * @param right
	 */
	public void setTank(double left, double right) {
		_frontLeft.set(ControlMode.PercentOutput, left);
		_backLeft.set(ControlMode.PercentOutput, left);
		
		_frontRight.set(ControlMode.PercentOutput, right);
		_backRight.set(ControlMode.PercentOutput, right);
	}
	
	/**
	 * Drives in arcade mode
	 * @param x
	 * @param y
	 */
	public void setArcade(double x, double y) {
		_frontLeft.set(ControlMode.PercentOutput,   x + y);
		_frontRight.set(ControlMode.PercentOutput, -x + y);
		
		_backLeft.set(ControlMode.PercentOutput,    x + y);
		_backRight.set(ControlMode.PercentOutput,  -x + y);
	}

	/**
	 * Selects the drivetrain profile slot
	 * @param profile
	 */
	public void setProfileSlot(ProfileSlot profile){
		if(profile == ProfileSlot.MotionMagic) {
			_frontLeft.selectProfileSlot(0, 0);
			_frontRight.selectProfileSlot(0, 0);
			_backLeft.selectProfileSlot(0, 0);
			_backRight.selectProfileSlot(0, 0);
		}
		else if (profile == ProfileSlot.Velocity) {
			_frontLeft.selectProfileSlot(1, 0);
			_frontRight.selectProfileSlot(1, 0);
			_backLeft.selectProfileSlot(1, 0);
			_backRight.selectProfileSlot(1, 0);
		}
	}
	
	/**
	 * Sets the motors to move a given distance using Motion Magic
	 * @param distance
	 */
	public void setTargetDistance(double distance) {
		// Change the profile slot to Motion Magic
		setProfileSlot(ProfileSlot.MotionMagic);
		
		// Move the motors
		_backLeft.set(ControlMode.MotionMagic, distance);
		_backRight.set(ControlMode.MotionMagic, distance);
	}
	
	/**
	 * Sets the motors to move at a give velocity using PID
	 * @param distance
	 */
	public void setVelocity(double rightVelocity, double leftVelocity) {
		// Change the profile slot to Velocity PID
		setProfileSlot(ProfileSlot.Velocity);
		
		// FIXME - Remove max speed constant
		_backLeft.set(ControlMode.Velocity, leftVelocity * MAX_SPEED);
		_backRight.set(ControlMode.Velocity, rightVelocity * MAX_SPEED);
	}
	
	public double getLeftVelocity() {
		return _backLeft.getSelectedSensorVelocity(0);
	}
	
	public double getRightVelocity() {
		return _backRight.getSelectedSensorVelocity(0);
	}
	
	public double getLeftEncoder() {
		return _frontLeft.getSelectedSensorPosition(0);
	}
	
	public double getRightEncoder() {
		return _frontRight.getSelectedSensorPosition(0);
	}
	
	public void resetEncoders() {		
		_backLeft.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		_backRight.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
	}

	/**
	 * Gets the output current of the back left motor
	 * @return current
	 */
    public double getCurrent() {
		return _backLeft.getOutputCurrent();
	}
    
    /**
     * Gets the closed loop error of the back left motor
     * @return error
     */
    public double getError() {
    	return _backLeft.getClosedLoopError(0);
    }
    
    /**
     * Gets the drivetrain TargetCounter
     * @return targetCounter
     */
    public TargetCounter getTargetCounter() {
    	return _targetCounter;
    }
	
	public void initDefaultCommand() {
		setDefaultCommand(new OperateTankDrive()); 
    	//setDefaultCommand(null); 
    }
}

