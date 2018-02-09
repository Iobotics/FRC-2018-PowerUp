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
	
	private TalonSRX _lift;
	
	private int offset;
	
    public void init() {
    	_lift = new TalonSRX(RobotMap.lift);
    	
    	_lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
    	_lift.selectProfileSlot(0, 0);
    	_lift.config_kF(0, kF, TIMEOUT);
    	_lift.config_kP(0, kP, TIMEOUT);
    	_lift.config_kI(0, kI, TIMEOUT);
    	_lift.config_kD(0, kD, TIMEOUT);
    	_lift.config_IntegralZone(0, iZone, TIMEOUT);
    	_lift.configMotionCruiseVelocity(LIFT_VELOCITY, TIMEOUT);
    	_lift.configMotionAcceleration(LIFT_VELOCITY, TIMEOUT);
    	
    	_lift.configContinuousCurrentLimit(11, 0);
    	_lift.configPeakCurrentLimit(12, 0);
    	_lift.configPeakCurrentDuration(100, 0);
    	_lift.enableCurrentLimit(true);
    	
    	_lift.setInverted(true);
    	_lift.setSensorPhase(true);
    	
    	_lift.setSelectedSensorPosition(0, 0, TIMEOUT);
    	
    	_lift.configForwardSoftLimitThreshold(9970, TIMEOUT);
    	_lift.configForwardSoftLimitEnable(true, TIMEOUT);
    	_lift.configReverseSoftLimitThreshold(0, TIMEOUT);
    	_lift.configReverseSoftLimitEnable(true, TIMEOUT);
    }
    
    /**
     * Sets the lift position
     * @param input [-1, 1]
     */
    public void setPosition(double input) {
    	// Center the range on [0, 9970]
    	double pos = Math.round((float) ((9970)/2 * (input + 1)));
    	
    	_lift.set(ControlMode.MotionMagic, pos);
    }
    
    public int getPosition() {
    	return _lift.getSelectedSensorPosition(0);
    }
    
    public void resetOffset() {
    	_lift.setSelectedSensorPosition(0, 0, TIMEOUT);
    }
    
    public int getOffset() {
    	return this.offset;
    }
    
    public double getCurrent() {
		return _lift.getOutputCurrent();
	}
    
    public double getError() {
    	return _lift.getClosedLoopError(0);
    }
    
    public void stop() {
    	_lift.set(ControlMode.PercentOutput, 0.0);
    }

    public void initDefaultCommand() {
    	setDefaultCommand(new OperateLift());
    	//setDefaultCommand(null);
    }
}

