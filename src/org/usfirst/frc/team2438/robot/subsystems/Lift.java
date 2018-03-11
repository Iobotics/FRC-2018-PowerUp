package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lift subsystem
 */
public class Lift extends Subsystem {
	
	private static final double kF = 0.075;
	private static final double kP = 0.05;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int LIFT_VELOCITY = 2200; // TODO
	private static final int MAX_LIFT_POSITION = 53000; // FIXME
	
	private static final double VOLTS_PER_INCH = 0.009766;
	
	private static final int TALON_TIMEOUT = 20;
	
	private TalonSRX _frontLeftLift;
	private TalonSRX _frontRightLift;
	private TalonSRX _backLeftLift;
	private TalonSRX _backRightLift;
	
	private AnalogInput _rangeSensor;
	
    public void init() {
    	_frontLeftLift = new TalonSRX(RobotMap.frontLeftLift);
    	_frontRightLift = new TalonSRX(RobotMap.frontRightLift);
    	_backLeftLift = new TalonSRX(RobotMap.backLeftLift);
    	_backRightLift = new TalonSRX(RobotMap.backRightLift);
    	
    	_frontLeftLift.setInverted(true);
    	_backLeftLift.setInverted(true);
    	
    	_frontRightLift.setSensorPhase(true);
    	
    	_frontLeftLift.setNeutralMode(NeutralMode.Brake);
    	_frontRightLift.setNeutralMode(NeutralMode.Brake);
    	_backLeftLift.setNeutralMode(NeutralMode.Brake);
    	_backRightLift.setNeutralMode(NeutralMode.Brake);
    	

    	_frontLeftLift.follow(_frontRightLift);
    	_backLeftLift.follow(_frontRightLift);
    	_backRightLift.follow(_frontRightLift);
    	
    	_frontRightLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
    	_frontRightLift.selectProfileSlot(0, 0);
    	_frontRightLift.config_kF(0, kF, TALON_TIMEOUT);
    	_frontRightLift.config_kP(0, kP, TALON_TIMEOUT);
    	_frontRightLift.config_kI(0, kI, TALON_TIMEOUT);
    	_frontRightLift.config_kD(0, kD, TALON_TIMEOUT);
    	_frontRightLift.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	_frontRightLift.configMotionCruiseVelocity(LIFT_VELOCITY, TALON_TIMEOUT);
    	_frontRightLift.configMotionAcceleration(LIFT_VELOCITY, TALON_TIMEOUT);
    	
    	this.resetEncoder();
    	
    	_rangeSensor = new AnalogInput(0);
    }
    
    public void setPower(double power) {
    	//_frontLeftLift.set(ControlMode.PercentOutput, power);
    	//_backLeftLift.set(ControlMode.PercentOutput, power);
    	
    	_frontRightLift.set(ControlMode.PercentOutput, power);
    	//_backRightLift.set(ControlMode.PercentOutput, power);
    }
    
    /**
     * Sets the lift position
     * @param input [-1, 1]
     */
    public void setPosition(double input) {    	
    	_frontRightLift.set(ControlMode.MotionMagic, input);
    }
    
    public int getPosition() {
    	return _frontRightLift.getSelectedSensorPosition(0);
    }
    
    public void resetEncoder() {
    	_frontRightLift.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
    	_frontRightLift.set(ControlMode.MotionMagic, 0);
    }
    
    public void setCurrent(double current) {
    	_frontLeftLift.set(ControlMode.Current, current);
    	_backLeftLift.set(ControlMode.Current, -current);
    	
    	_frontRightLift.set(ControlMode.Current, current);
    	_backRightLift.set(ControlMode.Current, -current);
    }
    
    public double getCurrent(int num) {
    	double current = 0;
    	
    	switch(num) {
    		case 1:
    			current = _frontLeftLift.getOutputCurrent();
    		case 2:
    			current = _frontRightLift.getOutputCurrent();
    		case 3:
    			current = _backLeftLift.getOutputCurrent();
    		case 4:
    			current = _backRightLift.getOutputCurrent();
    	}
		return current;
	}
    
    public double getError() {
    	return _frontRightLift.getClosedLoopError(0);
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
    	_frontRightLift.set(ControlMode.PercentOutput, 0);
    }

    public void initDefaultCommand() {
    	//setDefaultCommand(new OperateLift());
    	setDefaultCommand(null);
    }
}

