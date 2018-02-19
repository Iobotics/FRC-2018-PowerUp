package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateLift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lift subsystem
 */
public class Lift extends Subsystem {
	
	public enum LiftPosition {
		TOP,
		MIDDLE,
		BOTTOM
	}

	private static final int TIMEOUT = 10;
	
	private static final double kF = 0.075;
	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int LIFT_VELOCITY = 2200;
	public static final int MAX_LIFT_POSITION = 53000;
	
	private TalonSRX _leftLift1;
	private TalonSRX _leftLift2;
	private TalonSRX _rightLift1;
	private TalonSRX _rightLift2;
	
    public void init() {
    	_leftLift1 = new TalonSRX(RobotMap.leftLift1);
    	_leftLift2 = new TalonSRX(RobotMap.leftLift2);
    	_rightLift1 = new TalonSRX(RobotMap.rightLift1);
    	_rightLift2 = new TalonSRX(RobotMap.rightLift2);
    	
    	_leftLift2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
    	_leftLift2.selectProfileSlot(0, 0);
    	_leftLift2.config_kF(0, kF, TIMEOUT);
    	_leftLift2.config_kP(0, kP, TIMEOUT);
    	_leftLift2.config_kI(0, kI, TIMEOUT);
    	_leftLift2.config_kD(0, kD, TIMEOUT);
    	_leftLift2.config_IntegralZone(0, iZone, TIMEOUT);
    	_leftLift2.configMotionCruiseVelocity(LIFT_VELOCITY, TIMEOUT);
    	_leftLift2.configMotionAcceleration(LIFT_VELOCITY, TIMEOUT);
    	
    	_leftLift2.configContinuousCurrentLimit(22, TIMEOUT);
    	_leftLift2.configPeakCurrentLimit(24, TIMEOUT);
    	_leftLift2.configPeakCurrentDuration(2500, TIMEOUT);
    	_leftLift2.enableCurrentLimit(true);
    	
    	_leftLift2.setInverted(true);
    	_leftLift2.setSensorPhase(true);
    	
    	_leftLift2.configForwardSoftLimitThreshold(MAX_LIFT_POSITION, TIMEOUT);
    	_leftLift2.configForwardSoftLimitEnable(true, TIMEOUT);
    	_leftLift2.configReverseSoftLimitThreshold(0, TIMEOUT);
    	_leftLift2.configReverseSoftLimitEnable(true, TIMEOUT);
    	
    	_leftLift1.follow(_leftLift2);
    	_leftLift1.setInverted(true);
    	
    	
    	_rightLift1.follow(_leftLift2);
    	_rightLift2.follow(_leftLift2);
    	
    	//_rightLift2.follow(_rightLift1);
    	
    	this.resetEncoder();
    }
    
    public void setPower(double power) {
    	_leftLift2.set(ControlMode.PercentOutput, power);
    }
    
    /**
     * Sets the lift position
     * @param input [-1, 1]
     */
    public void setPosition(double input) {
    	
    	double pos = Math.round((float) ((MAX_LIFT_POSITION)/2 * (input + 1)));
    	
    	_leftLift2.set(ControlMode.MotionMagic, pos);
    }
    
    public int getPosition() {
    	return _leftLift2.getSelectedSensorPosition(0);
    }
    
    public void resetEncoder() {
    	_leftLift2.setSelectedSensorPosition(0, 0, TIMEOUT);
    	_leftLift2.set(ControlMode.MotionMagic, 0);
    }
    
    public void setCurrent(double current) {
    	_leftLift2.set(ControlMode.Current, current);
    }
    
    public double getCurrent() {
		return _leftLift2.getOutputCurrent();
	}
    
    public double getError() {
    	return _leftLift2.getClosedLoopError(0);
    }
    
    public void stop() {
    	_rightLift1.set(ControlMode.PercentOutput, 0);
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new OperateLift());
    	//setDefaultCommand(null);
    }
}

