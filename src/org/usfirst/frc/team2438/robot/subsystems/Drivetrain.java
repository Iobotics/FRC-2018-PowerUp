package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateTankDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
	
	
	private static final double WHEEL_DIAMETER 		= 6; 	 	// inches
	private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	
	private static final double COUNTS_PER_EDGE = 4;
	private static final double GEAR_RATIO = 6; // 6:1 gear ratio
	private static final double COUNTS_PER_ROTATION = 128 * COUNTS_PER_EDGE * GEAR_RATIO;  // ppr * 4 counts per edge * gear ratio
	
	public static final double UNITS_PER_INCH 		= COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;
	
	private static final double MAX_SPEED = 3800;
	//Velocity Control Constants
	private double vkF = 0.267;
	private double vkP = 0.51;
	private double vkI = 0.007;
	private double vkD = 5.1;
	private int viZone = 0;
	    
	//Motion Magic constants
	private static final double kF = 0;
	private static final double kP = 0.5;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	// FIXME - Determine velocity
	private static final int DRIVE_VELOCITY = 1228;
	private static final int DRIVE_ACCELERATION = 1228;
	
	private static final int TALON_TIMEOUT = 20; // milliseconds
    
	private TalonSRX _frontLeft;
    private TalonSRX _frontRight;
    private TalonSRX _backLeft;
    private TalonSRX _backRight;
	
	public void init() {
		_frontLeft = new TalonSRX(RobotMap.frontLeftMotor);
		_backLeft = new TalonSRX(RobotMap.backLeftMotor);
		
		_frontRight = new TalonSRX(RobotMap.frontRightMotor);
		_backRight = new TalonSRX(RobotMap.backRightMotor);
		
		_frontLeft.setInverted(true);
		_backLeft.setInverted(true);
		
		// Left encoder //
		
		//Motion Magic
		_backLeft.setSensorPhase(true);
		_backLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
		_backLeft.config_kF(0, kF, TALON_TIMEOUT);
		_backLeft.config_kP(0, kP, TALON_TIMEOUT);
		_backLeft.config_kI(0, kI, TALON_TIMEOUT);
		_backLeft.config_kD(0, kD, TALON_TIMEOUT);
		_backLeft.config_IntegralZone(0, iZone, TALON_TIMEOUT);
		_backLeft.configMotionCruiseVelocity(DRIVE_VELOCITY, TALON_TIMEOUT);
		_backLeft.configMotionAcceleration(DRIVE_ACCELERATION, TALON_TIMEOUT);
		
		//Velocity Control
		_backLeft.configNominalOutputForward(0, TALON_TIMEOUT);
		_backLeft.configNominalOutputReverse(0, TALON_TIMEOUT);
		_backLeft.configPeakOutputForward(1, TALON_TIMEOUT);
		_backLeft.configPeakOutputReverse(-1, TALON_TIMEOUT);
		
		_backLeft.config_kF(1, vkF, TALON_TIMEOUT);
		_backLeft.config_kP(1, vkP, TALON_TIMEOUT);
		_backLeft.config_kI(1, vkI, TALON_TIMEOUT);
		_backLeft.config_kD(1, vkD, TALON_TIMEOUT);
		_backLeft.config_IntegralZone(1, viZone, TALON_TIMEOUT);
    	
    	// Right encoder //
		
		//Motion Magic
		_backRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
		_backRight.config_kF(0, kF, TALON_TIMEOUT);
		_backRight.config_kP(0, kP, TALON_TIMEOUT);
		_backRight.config_kI(0, kI, TALON_TIMEOUT);
		_backRight.config_kD(0, kD, TALON_TIMEOUT);
		_backRight.config_IntegralZone(0, iZone, TALON_TIMEOUT);
		_backRight.configMotionCruiseVelocity(DRIVE_VELOCITY, TALON_TIMEOUT);
		_backRight.configMotionAcceleration(DRIVE_ACCELERATION, TALON_TIMEOUT);
		
		//Velocity Control
		_backRight.configNominalOutputForward(0, TALON_TIMEOUT);
		_backRight.configNominalOutputReverse(0, TALON_TIMEOUT);
		_backRight.configPeakOutputForward(1, TALON_TIMEOUT);
		_backRight.configPeakOutputReverse(-1, TALON_TIMEOUT);
		
		_backRight.config_kF(1, vkF, TALON_TIMEOUT);
		_backRight.config_kP(1, vkP, TALON_TIMEOUT);
		_backRight.config_kI(1, vkI, TALON_TIMEOUT);
		_backRight.config_kD(1, vkD, TALON_TIMEOUT);
		_backRight.config_IntegralZone(1, viZone, TALON_TIMEOUT);
    	
    	this.setProfileSlot(ProfileSlot.MotionMagic);
    	
    	this.resetEncoders();
	}
	
	/**
	 * Sets tank mode
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
	 * Sets arcade mode
	 * @param x
	 * @param y
	 */
	public void setArcade(double x, double y) {
		_frontLeft.set(ControlMode.PercentOutput,   x + y);
		_frontRight.set(ControlMode.PercentOutput, -x + y);
		
		_backLeft.set(ControlMode.PercentOutput,    x + y);
		_backRight.set(ControlMode.PercentOutput,  -x + y);
	}

	public void setProfileSlot(ProfileSlot slot){
		if(slot == ProfileSlot.MotionMagic) {
			_frontLeft.selectProfileSlot(0, 0);
			_frontRight.selectProfileSlot(0, 0);
			_backLeft.selectProfileSlot(0, 0);
			_backRight.selectProfileSlot(0, 0);
		}
		else if (slot == ProfileSlot.Velocity) {
			_frontLeft.selectProfileSlot(1, 0);
			_frontRight.selectProfileSlot(1, 0);
			_backLeft.selectProfileSlot(1, 0);
			_backRight.selectProfileSlot(1, 0);
		}
	}
	
	public void setTargetDistance(double distance) {
		_frontLeft.follow(_backLeft);
		_frontRight.follow(_backRight);
		
		setProfileSlot(ProfileSlot.MotionMagic);
		
		_backLeft.set(ControlMode.MotionMagic, distance);
		_backRight.set(ControlMode.MotionMagic, distance);
	}
	
	public void setVelocity(double rightVelocity, double leftVelocity) {
		_frontLeft.follow(_backLeft);
		_frontRight.follow(_backRight);
		
		setProfileSlot(ProfileSlot.Velocity);
		
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
		_backLeft.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		_backRight.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		
		_backLeft.set(ControlMode.MotionMagic, 0);
		_backRight.set(ControlMode.MotionMagic, 0);
	}

    public double getCurrent() {
		return _frontLeft.getOutputCurrent();
	}
    
    public double getError() {
    	return _backLeft.getClosedLoopError(0);
    }
    
    public int getPosition() {
    	return _frontLeft.getSelectedSensorPosition(0);
    }
    
	public void setLeftEncoderPosition(int encoderUnits) {
		_frontLeft.setSelectedSensorPosition(encoderUnits, 0, TALON_TIMEOUT);
	}
	
	public void setRightEncoderPosition(int encoderUnits) {
		_frontRight.setSelectedSensorPosition(encoderUnits, 0, TALON_TIMEOUT);
	}
	
	public void initDefaultCommand() {
        setDefaultCommand(new OperateTankDrive()); 
    	//setDefaultCommand(null); 
    }
}

