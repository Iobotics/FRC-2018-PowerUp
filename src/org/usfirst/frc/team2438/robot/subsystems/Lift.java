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
	
	private static final double kF = 0;
	private static final double kP = 3.0;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int LIFT_VELOCITY = 750;
	public static final int MAX_LIFT_POSITION = 9970;
	
	private TalonSRX _leftLift1;
	private TalonSRX _leftLift2;
	private TalonSRX _rightLift1;
	private TalonSRX _rightLift2;
	
	private int offset;
	
    public void init() {
    	_leftLift1 = new TalonSRX(RobotMap.leftLift1);
    	_leftLift2 = new TalonSRX(RobotMap.leftLift2);
    	_rightLift1 = new TalonSRX(RobotMap.rightLift1);
    	_rightLift2 = new TalonSRX(RobotMap.rightLift2);
    	
    	_leftLift1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
    	_leftLift1.selectProfileSlot(0, 0);
    	_leftLift1.config_kF(0, kF, TIMEOUT);
    	_leftLift1.config_kP(0, kP, TIMEOUT);
    	_leftLift1.config_kI(0, kI, TIMEOUT);
    	_leftLift1.config_kD(0, kD, TIMEOUT);
    	_leftLift1.config_IntegralZone(0, iZone, TIMEOUT);
    	_leftLift1.configMotionCruiseVelocity(LIFT_VELOCITY, TIMEOUT);
    	_leftLift1.configMotionAcceleration(LIFT_VELOCITY, TIMEOUT);
    	
    	_leftLift1.configContinuousCurrentLimit(11, 0);
    	_leftLift1.configPeakCurrentLimit(12, 0);
    	_leftLift1.configPeakCurrentDuration(100, 0);
    	_leftLift1.enableCurrentLimit(true);
    	
    	_leftLift1.setInverted(true);
    	_leftLift1.setSensorPhase(true);
    	
    	_leftLift1.setSelectedSensorPosition(0, 0, TIMEOUT);
    	
    	_leftLift1.configForwardSoftLimitThreshold(MAX_LIFT_POSITION, TIMEOUT);
    	_leftLift1.configForwardSoftLimitEnable(true, TIMEOUT);
    	_leftLift1.configReverseSoftLimitThreshold(0, TIMEOUT);
    	_leftLift1.configReverseSoftLimitEnable(true, TIMEOUT);
    	
    	_leftLift2.follow(_leftLift1);
    	_leftLift2.setInverted(true);
    	
    	_rightLift1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
    	_rightLift1.selectProfileSlot(0, 0);
    	_rightLift1.config_kF(0, kF, TIMEOUT);
    	_rightLift1.config_kP(0, kP, TIMEOUT);
    	_rightLift1.config_kI(0, kI, TIMEOUT);
    	_rightLift1.config_kD(0, kD, TIMEOUT);
    	_rightLift1.config_IntegralZone(0, iZone, TIMEOUT);
    	_rightLift1.configMotionCruiseVelocity(LIFT_VELOCITY, TIMEOUT);
    	_rightLift1.configMotionAcceleration(LIFT_VELOCITY, TIMEOUT);
    	
    	_rightLift1.configContinuousCurrentLimit(11, 0);
    	_rightLift1.configPeakCurrentLimit(12, 0);
    	_rightLift1.configPeakCurrentDuration(100, 0);
    	_rightLift1.enableCurrentLimit(true);
    	
    	_rightLift1.setSelectedSensorPosition(0, 0, TIMEOUT);
    	
    	_rightLift1.configForwardSoftLimitThreshold(MAX_LIFT_POSITION, TIMEOUT);
    	_rightLift1.configForwardSoftLimitEnable(true, TIMEOUT);
    	_rightLift1.configReverseSoftLimitThreshold(0, TIMEOUT);
    	_rightLift1.configReverseSoftLimitEnable(true, TIMEOUT);
    	
    	_rightLift2.follow(_rightLift1);
    }
    
    /**
     * Sets the lift position
     * @param input [-1, 1]
     */
    public void setPosition(double input) {
    	
    	double pos = Math.round((float) ((MAX_LIFT_POSITION)/2 * (input + 1)));
    	
    	_leftLift1.set(ControlMode.MotionMagic, pos);
    	_rightLift1.set(ControlMode.MotionMagic, pos);
    }
    
    public int getPosition() {
    	return _leftLift1.getSelectedSensorPosition(0);
    }
    
    public void resetOffset() {
    	_leftLift1.setSelectedSensorPosition(0, 0, TIMEOUT);
    	_rightLift1.setSelectedSensorPosition(0, 0, TIMEOUT);
    }
    
    public int getOffset() {
    	return this.offset;
    }
    
    public double getCurrent() {
		return _leftLift1.getOutputCurrent();
	}
    
    public double getError() {
    	return _leftLift1.getClosedLoopError(0);
    }
    
    public void stop() {
    	_leftLift1.set(ControlMode.PercentOutput, 0.0);
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new OperateLift());
    	//setDefaultCommand(null);
    }
}

