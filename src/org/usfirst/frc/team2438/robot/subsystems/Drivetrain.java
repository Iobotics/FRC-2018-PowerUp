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
		Arcade
	}
	
	public static final double COUNTS_PER_ROTATION = 4096;  // cpr
	private static final double WHEEL_DIAMETER = 4; 		// inches
	private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	public static final double UNITS_PER_INCH = COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;

    private static final int TALON_TIMEOUT = 20; // milliseconds

	private static final double kF = 0;
	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int DRIVE_VELOCITY = 750;
	private static final int DRIVE_ACCELERATION = 750;
    
	private TalonSRX _leftFront;
    private TalonSRX _rightFront;
    //private TalonSRX _leftMiddle;
    //private TalonSRX _rightMiddle;
    private TalonSRX _leftBack;
    private TalonSRX _rightBack;
    
    private DriveMode _dmode;
	
	public void init() {
		_leftFront = new TalonSRX(RobotMap.leftFrontMotor);
		//_leftMiddle = new TalonSRX(RobotMap.leftMiddleMotor);
		_leftBack = new TalonSRX(RobotMap.leftBackMotor);
		
		_rightFront = new TalonSRX(RobotMap.rightFrontMotor);
		//_rightMiddle = new TalonSRX(RobotMap.rightMiddleMotor);
		_rightBack = new TalonSRX(RobotMap.rightBackMotor);
		
		_leftFront.setInverted(true);
		//_leftMiddle.setInverted(true);
		_leftBack.setInverted(true);
		
		//_leftMiddle.follow(_leftFront);
		//_leftBack.follow(_leftFront);
		
		//_rightMiddle.follow(_rightFront);
		//_rightBack.follow(_rightFront);
		
		// Left encoder //
		_leftFront.setSensorPhase(true);
		_leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
		_leftFront.selectProfileSlot(0, 0);
		_leftFront.config_kF(0, kF, TALON_TIMEOUT);
		_leftFront.config_kP(0, kP, TALON_TIMEOUT);
		_leftFront.config_kI(0, kI, TALON_TIMEOUT);
		_leftFront.config_kD(0, kD, TALON_TIMEOUT);
		_leftFront.config_IntegralZone(0, iZone, TALON_TIMEOUT);
		_leftFront.configMotionCruiseVelocity(DRIVE_VELOCITY, TALON_TIMEOUT);
		_leftFront.configMotionAcceleration(DRIVE_ACCELERATION, TALON_TIMEOUT);
    	
    	// Right encoder //
		_rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
		_rightFront.selectProfileSlot(0, 0);
    	_rightFront.config_kF(0, kF, TALON_TIMEOUT);
    	_rightFront.config_kP(0, kP, TALON_TIMEOUT);
    	_rightFront.config_kI(0, kI, TALON_TIMEOUT);
    	_rightFront.config_kD(0, kD, TALON_TIMEOUT);
    	_rightFront.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	_rightFront.configMotionCruiseVelocity(DRIVE_VELOCITY, TALON_TIMEOUT);
    	_rightFront.configMotionAcceleration(DRIVE_ACCELERATION, TALON_TIMEOUT);
    	
    	this.resetEncoders();
	}
	
	/**
	 * Sets tank mode
	 * @param left
	 * @param right
	 */
	public void setTank(double left, double right) {
		_leftFront.set(ControlMode.PercentOutput, left);
		//_leftMiddle.set(ControlMode.PercentOutput, left);
		_leftBack.set(ControlMode.PercentOutput, left);
		
		_rightFront.set(ControlMode.PercentOutput, right);
		//_rightMiddle.set(ControlMode.PercentOutput, right);
		_rightBack.set(ControlMode.PercentOutput, right);
	}
	
	/**
	 * Sets arcade mode
	 * @param x
	 * @param y
	 */
	public void setArcade(double x, double y) {
		_leftFront.set(ControlMode.PercentOutput,   x + y);
		_rightFront.set(ControlMode.PercentOutput, -x + y);
		
		//_leftMiddle.set(ControlMode.PercentOutput,    x + y);
		//_rightMiddle.set(ControlMode.PercentOutput,  -x + y);
		
		_leftBack.set(ControlMode.PercentOutput,    x + y);
		_rightBack.set(ControlMode.PercentOutput,  -x + y);
	}
	
	
	public void driveStraight(double inches) {
		this.driveStraight(inches, DRIVE_VELOCITY, DRIVE_ACCELERATION);
	}
	
	public void driveStraight(double inches, int velocity, int acceleration) {
		int position = Math.round((float) (UNITS_PER_INCH * inches));
		
		//_leftMiddle.configMotionCruiseVelocity(velocity, TALON_TIMEOUT);
		//_leftMiddle.configMotionAcceleration(acceleration, TALON_TIMEOUT);
		//_leftMiddle.configMotionCruiseVelocity(velocity, TALON_TIMEOUT);
		//_leftMiddle.configMotionAcceleration(acceleration, TALON_TIMEOUT);
		
		//_leftMiddle.set(ControlMode.MotionMagic, position);
		//_rightMiddle.set(ControlMode.MotionMagic, position);
	}
	
	/**
	 * Cycle through drive modes
	 */
	public void cycleDriveMode() {
		if(_dmode == DriveMode.Tank) {
			_dmode = DriveMode.Arcade;
		} else {
			_dmode = DriveMode.Tank;
		}
	}
	
	/**
	 * Get the current drive mode
	 * @return _dmode
	 */
	public DriveMode getDriveMode() {
		return _dmode;
	}

	public double getLeftEncoder() {
		return _leftFront.getSelectedSensorPosition(0);
	}
	
	public double getRightEncoder() {
		return _rightFront.getSelectedSensorPosition(0);
	}
	
	public void resetEncoders() {
		_leftFront.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		_rightFront.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		
		_leftFront.set(ControlMode.MotionMagic, 0);
		_rightFront.set(ControlMode.MotionMagic, 0);
	}

    public double getCurrent() {
		return _leftFront.getOutputCurrent();
	}
    
    public double getError() {
    	return _leftFront.getClosedLoopError(0);
    }
    
    public int getPosition() {
    	return _leftFront.getSelectedSensorPosition(0);
    }
    
	public void setLeftEncoderDistance(int distance) {
		_leftFront.setSelectedSensorPosition(distance, 0, TALON_TIMEOUT);
	}
	
	public void initDefaultCommand() {
        setDefaultCommand(new OperateTankDrive()); 
    	//setDefaultCommand(null); 
    }
}

