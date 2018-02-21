package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lift subsystem
 */
public class Lift extends Subsystem {
	
	private static final double kF = 0.075;
	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int LIFT_VELOCITY = 2200; // TODO
	private static final int MAX_LIFT_POSITION = 53000; // FIXME
	
	private static final double VOLTS_PER_INCH = 0.009766;
	
	private static final int TALON_TIMEOUT = 20;
	
	private TalonSRX _leftLift1;
	private TalonSRX _leftLift2;
	private TalonSRX _rightLift1;
	private TalonSRX _rightLift2;
	
	private AnalogInput _rangeSensor;
	
    public void init() {
    	_leftLift1 = new TalonSRX(RobotMap.leftLift1);
    	_leftLift2 = new TalonSRX(RobotMap.leftLift2);
    	_rightLift1 = new TalonSRX(RobotMap.rightLift1);
    	_rightLift2 = new TalonSRX(RobotMap.rightLift2);
    	
    	_leftLift2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
    	_leftLift2.selectProfileSlot(0, 0);
    	_leftLift2.config_kF(0, kF, TALON_TIMEOUT);
    	_leftLift2.config_kP(0, kP, TALON_TIMEOUT);
    	_leftLift2.config_kI(0, kI, TALON_TIMEOUT);
    	_leftLift2.config_kD(0, kD, TALON_TIMEOUT);
    	_leftLift2.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	_leftLift2.configMotionCruiseVelocity(LIFT_VELOCITY, TALON_TIMEOUT);
    	_leftLift2.configMotionAcceleration(LIFT_VELOCITY, TALON_TIMEOUT);
    	
    	//_leftLift2.configContinuousCurrentLimit(22, TALON_TIMEOUT);
    	//_leftLift2.configPeakCurrentLimit(24, TALON_TIMEOUT);
    	//_leftLift2.configPeakCurrentDuration(2500, TALON_TIMEOUT);
    	//_leftLift2.enableCurrentLimit(true);
    	
    	_leftLift2.setInverted(true);
    	_leftLift2.setSensorPhase(true);
    	
    	_leftLift1.selectProfileSlot(0, 0);
    	_leftLift1.config_kF(0, kF, TALON_TIMEOUT);
    	_leftLift1.config_kP(0, kP, TALON_TIMEOUT);
    	_leftLift1.config_kI(0, kI, TALON_TIMEOUT);
    	_leftLift1.config_kD(0, kD, TALON_TIMEOUT);
    	_leftLift1.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	
    	_leftLift1.setInverted(true);
    	
    	_rightLift2.selectProfileSlot(0, 0);
    	_rightLift2.config_kF(0, kF, TALON_TIMEOUT);
    	_rightLift2.config_kP(0, kP, TALON_TIMEOUT);
    	_rightLift2.config_kI(0, kI, TALON_TIMEOUT);
    	_rightLift2.config_kD(0, kD, TALON_TIMEOUT);
    	_rightLift2.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	
    	_rightLift1.selectProfileSlot(0, 0);
    	_rightLift1.config_kF(0, kF, TALON_TIMEOUT);
    	_rightLift1.config_kP(0, kP, TALON_TIMEOUT);
    	_rightLift1.config_kI(0, kI, TALON_TIMEOUT);
    	_rightLift1.config_kD(0, kD, TALON_TIMEOUT);
    	_rightLift1.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	
    	this.resetEncoder();
    	
    	_rangeSensor = new AnalogInput(0);
    }
    
    public void setPower(double power) {
    	_leftLift1.set(ControlMode.Current, power);
    	_rightLift1.set(ControlMode.Current, -power);
    	
    	_leftLift2.set(ControlMode.Current, power);
    	_rightLift2.set(ControlMode.Current, -power);
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
    	_leftLift2.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
    	_leftLift2.set(ControlMode.MotionMagic, 0);
    }
    
    public void setCurrent(double current) {
    	_leftLift1.set(ControlMode.Current, current);
    	_rightLift1.set(ControlMode.Current, -current);
    	
    	_leftLift2.set(ControlMode.Current, current);
    	_rightLift2.set(ControlMode.Current, -current);
    }
    
    public double getCurrent(int num) {
    	double current = 0;
    	
    	switch(num) {
    		case 1:
    			current = _leftLift1.getOutputCurrent();
    		case 2:
    			current = _leftLift2.getOutputCurrent();
    		case 3:
    			current = _rightLift1.getOutputCurrent();
    		case 4:
    			current = _rightLift2.getOutputCurrent();
    	}
		return current;
	}
    
    public double getError() {
    	return _leftLift2.getClosedLoopError(0);
    }
    
    /**
     * Gets distance in inches
     * @return distance
     */
    public double getHeight() {
    	double height = _rangeSensor.getAverageVoltage() / VOLTS_PER_INCH;
    	
    	return height;
    }
    
    public void stop() {
    	_leftLift1.set(ControlMode.PercentOutput, 0);
    	_rightLift1.set(ControlMode.PercentOutput, 0);
    	_leftLift2.set(ControlMode.PercentOutput, 0);
    	_rightLift2.set(ControlMode.PercentOutput, 0);
    }

    public void initDefaultCommand() {
    	//setDefaultCommand(new OperateLift());
    	setDefaultCommand(null);
    }
}

