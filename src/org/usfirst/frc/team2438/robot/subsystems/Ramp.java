package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.util.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Ramp
 */
public class Ramp extends Subsystem {
	
	public static enum RampSide {
		left,
		right
	}
	
	// Track if the solenoids are activated
	private boolean leftSolenoidActivated = false;
	private boolean rightSolenoidActivated = false;
	
	private DoubleSolenoid _leftRamp;
	private DoubleSolenoid _rightRamp;
	
	private Servo _leftServo;
	private Servo _rightServo;
	
	public void init() {
		
		/* TODO - Use RobotMap */
		// Left solenoid uses slots 2 and 3 on the PCM
		_leftRamp = new DoubleSolenoid(2, 3);
		// Right solenoid uses slots 4 and 5 on the PCM
		_rightRamp = new DoubleSolenoid(4, 5);
		
		_leftServo = new Servo(RobotMap.leftServo);
		_rightServo = new Servo(RobotMap.rightServo);
	}
	
	public void toggleLeftRamp() {
		// Reverses the solenoid position
		if(!leftSolenoidActivated) {
			_leftRamp.set(Value.kForward);
		}
		else {
			_leftRamp.set(Value.kReverse);
		}
		leftSolenoidActivated = !leftSolenoidActivated;
		
		// Wait for the solenoid to activate
		try {
			Thread.sleep(Constants.SOLENOID_PERIOD);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Turn the solenoid off
		_leftRamp.set(Value.kOff);
	}

	public void toggleRightRamp() {
		// Reverses the solenoid position
		if(!rightSolenoidActivated) {
			_rightRamp.set(Value.kForward);
		} else {
			_rightRamp.set(Value.kReverse);
		}
		rightSolenoidActivated = !rightSolenoidActivated;
		
		// Wait for the solenoid to activate
		try {
			Thread.sleep(Constants.SOLENOID_PERIOD);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Turn the solenoid off
		_rightRamp.set(Value.kOff);
	}
	
	/**
	 * Toggle both ramps
	 */
	public void toggleRamps() {
		toggleLeftRamp();
		toggleRightRamp();
	}
	
	/**
	 * Release the left ramp
	 */
	public void dropLeftRamp() {
		_leftServo.set(0.5);
	}
	
	/**
	 * Release the right ramp
	 */
	public void dropRightRamp() {
		_rightServo.set(0.75);
	}
	
	/**
	 * Reset both servos to their original position
	 */
	public void resetServos() {
		_leftServo.set(0);
		_rightServo.set(1.0);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(null);
    }
}

