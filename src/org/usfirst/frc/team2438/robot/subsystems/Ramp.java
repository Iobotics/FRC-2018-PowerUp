package org.usfirst.frc.team2438.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Ramp extends Subsystem {
	
	public static enum RampSide {
		left,
		right
	}
	
	public static enum RampPosition {
		up,
		ramp
	}
	
	private static final int ACTIVATION_TIME = 1000;
	
	// Track if the solenoids are activated
	private boolean leftSolenoidActivated = false;
	private boolean rightSolenoidActivated = false;
	
	private DoubleSolenoid _leftRamp;
	private DoubleSolenoid _rightRamp;
	
	private Servo _leftServo;
	private Servo _rightServo;
	
	private RampPosition _leftServoPosition;
	private RampPosition _rightServoPosition;
	
	public void init() {
		//left solenoid uses slots 2 and 3 on the PCM
		_leftRamp = new DoubleSolenoid(2,3);
		//right solenoid uses slots 4 and 5 on the PCM
		_rightRamp = new DoubleSolenoid(4,5);
		
		_leftServo = new Servo(0);
		_rightServo = new Servo(1);
		
		_leftServoPosition = RampPosition.up;
		_rightServoPosition = RampPosition.up;
	}
	
	public void toggleLeftRamp() {
		if(!leftSolenoidActivated) {
			_leftRamp.set(Value.kForward);
		}
		else {
			_leftRamp.set(Value.kReverse);
		}
		leftSolenoidActivated = !leftSolenoidActivated;
		
		try {
			Thread.sleep(ACTIVATION_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_leftRamp.set(Value.kOff);
	}

	public void toggleRightRamp() {
		// Turns solenoid on if it is off
		if(!rightSolenoidActivated) {
			_rightRamp.set(Value.kForward);
		} else {
			_rightRamp.set(Value.kReverse);
		}
		rightSolenoidActivated = !rightSolenoidActivated;
		
		try {
			Thread.sleep(ACTIVATION_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_rightRamp.set(Value.kOff);
	}
	
	public void raiseRamps() {
		toggleLeftRamp();
		toggleRightRamp();
	}
	
	public void dropLeftRamp() {
		_leftServo.set(0.5);
	}
	
	public void dropRightRamp() {
		_rightServo.set(0.75);
	}
	
	public void toggleLeftRampPosition() {
		_leftServo.set(1.0);
		_leftServoPosition = RampPosition.ramp;
	}
	
	public void toggleRightRampPosition() {
		_rightServo.set(0);
		_rightServoPosition = RampPosition.ramp;
	}
	
	public void resetServos() {
		_leftServo.set(0);
		_rightServo.set(1.0);
		
		_leftServoPosition = RampPosition.up;
		_rightServoPosition = RampPosition.up;
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(null);
    }
}

