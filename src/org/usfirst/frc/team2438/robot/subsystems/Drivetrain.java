package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateTankDrive;
import org.usfirst.frc.team2438.robot.subsystems.Drivetrain.ProfileSlot;

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
	
	private static final double COUNTS_PER_ROTATION = 256 * 4;  // ppr * 4
	private static final double WHEEL_DIAMETER 		= 6; 	 	// inches
	private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	public static final double UNITS_PER_INCH 		= COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;
	private static final double MAX_SPEED = 0;
	
	//Velocity Control Constants
	private double vkF = 0;
	private double vkP = 0;
	private double vkI = 0;
	private double vkD = 0;
	private int viZone = 0;
	    
	//Motion Magic constants
	private static final double kF = 0;
	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	// FIXME - Determine velocity
	private static final int DRIVE_VELOCITY = 750;
	private static final int DRIVE_ACCELERATION = 750;
	
	private static final int TALON_TIMEOUT = 20; // milliseconds
    
	private TalonSRX _leftFront;
    private TalonSRX _rightFront;
    private TalonSRX _leftBack;
    private TalonSRX _rightBack;
	
	public void init() {
		_leftFront = new TalonSRX(RobotMap.leftFrontMotor);
		_leftBack = new TalonSRX(RobotMap.leftBackMotor);
		
		_rightFront = new TalonSRX(RobotMap.rightFrontMotor);
		_rightBack = new TalonSRX(RobotMap.rightBackMotor);
		
		_leftFront.setInverted(true);
		_leftBack.setInverted(true);
		
		// Left encoder //
		
		//Velocity Control
		_leftFront.configNominalOutputForward(0, TALON_TIMEOUT);
		_leftFront.configNominalOutputReverse(0, TALON_TIMEOUT);
		_leftFront.configPeakOutputForward(1, TALON_TIMEOUT);
		_leftFront.configPeakOutputReverse(-1, TALON_TIMEOUT);
		
		_leftFront.config_kF(1, vkF, TALON_TIMEOUT);
		_leftFront.config_kP(1, vkP, TALON_TIMEOUT);
		_leftFront.config_kI(1, vkI, TALON_TIMEOUT);
		_leftFront.config_kD(1, vkD, TALON_TIMEOUT);
		_leftFront.config_IntegralZone(1, viZone, TALON_TIMEOUT);
		
		//Motion Magic
		_leftFront.setSensorPhase(true);
		_leftFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
		_leftFront.config_kF(0, kF, TALON_TIMEOUT);
		_leftFront.config_kP(0, kP, TALON_TIMEOUT);
		_leftFront.config_kI(0, kI, TALON_TIMEOUT);
		_leftFront.config_kD(0, kD, TALON_TIMEOUT);
		_leftFront.config_IntegralZone(0, iZone, TALON_TIMEOUT);
		_leftFront.configMotionCruiseVelocity(DRIVE_VELOCITY, TALON_TIMEOUT);
		_leftFront.configMotionAcceleration(DRIVE_ACCELERATION, TALON_TIMEOUT);
    	
    	// Right encoder //
		
		//Velocity Control
		_rightFront.configNominalOutputForward(0, TALON_TIMEOUT);
		_rightFront.configNominalOutputReverse(0, TALON_TIMEOUT);
		_rightFront.configPeakOutputForward(1, TALON_TIMEOUT);
		_rightFront.configPeakOutputReverse(-1, TALON_TIMEOUT);
		
		_rightFront.config_kF(1, vkF, TALON_TIMEOUT);
		_rightFront.config_kP(1, vkP, TALON_TIMEOUT);
		_rightFront.config_kI(1, vkI, TALON_TIMEOUT);
		_rightFront.config_kD(1, vkD, TALON_TIMEOUT);
		_rightFront.config_IntegralZone(1, viZone, TALON_TIMEOUT);
		
		//Motion Magic
		_rightFront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TALON_TIMEOUT);
    	_rightFront.config_kF(0, kF, TALON_TIMEOUT);
    	_rightFront.config_kP(0, kP, TALON_TIMEOUT);
    	_rightFront.config_kI(0, kI, TALON_TIMEOUT);
    	_rightFront.config_kD(0, kD, TALON_TIMEOUT);
    	_rightFront.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	_rightFront.configMotionCruiseVelocity(DRIVE_VELOCITY, TALON_TIMEOUT);
    	_rightFront.configMotionAcceleration(DRIVE_ACCELERATION, TALON_TIMEOUT);
    	
    	this.setProfileSlot(ProfileSlot.MotionMagic);
    	
    	this.resetEncoders();
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
		_rightFront.set(ControlMode.PercentOutput, -x + y);
		
		_leftBack.set(ControlMode.PercentOutput,    x + y);
		_rightBack.set(ControlMode.PercentOutput,  -x + y);
	}

	public void setProfileSlot(ProfileSlot slot){
		if(slot == ProfileSlot.MotionMagic) {
			_leftBack.selectProfileSlot(0,0);
			_rightBack.selectProfileSlot(0, 0);
		}
		else if (slot == ProfileSlot.Velocity) {
			_leftBack.selectProfileSlot(0,0);
			_rightBack.selectProfileSlot(0, 0);
		}
	}
	
	public void setVelocity(double rightVelocity, double leftVelocity) {
		_leftBack.follow(_leftFront);
		_rightBack.follow(_rightFront);
		setProfileSlot(ProfileSlot.Velocity);
		_leftFront.set(ControlMode.Velocity, leftVelocity * MAX_SPEED);
		_rightFront.set(ControlMode.Velocity, rightVelocity * MAX_SPEED);
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
    
	public void setLeftEncoderPosition(int encoderUnits) {
		_leftFront.setSelectedSensorPosition(encoderUnits, 0, TALON_TIMEOUT);
	}
	
	public void setRightEncoderPosition(int encoderUnits) {
		_rightFront.setSelectedSensorPosition(encoderUnits, 0, TALON_TIMEOUT);
	}
	
	public void initDefaultCommand() {
        setDefaultCommand(new OperateTankDrive()); 
    	//setDefaultCommand(null); 
    }
}

