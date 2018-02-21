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
		down,
		ramp
	}
	
	// Boolean that tracks if the solenoids are activated
	private boolean leftSolenoidActivated = false;
	private boolean rightSolenoidActivated = false;
	
	// Solenoid objects
	private DoubleSolenoid _rightRamp;
	private DoubleSolenoid _leftRamp;
	
	private Servo _rightServo;
	private Servo _leftServo;
	
	public void init() {
		//right solenoid uses slots 4 and 5 on the PCM
		_rightRamp = new DoubleSolenoid(4,5);
		//left solenoid uses slots 2 and 3 on the PCM
		_leftRamp = new DoubleSolenoid(2,3);
		
		_rightServo = new Servo(0);
		_leftServo = new Servo(1);
		
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
			Thread.sleep(500);
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
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_rightRamp.set(Value.kOff);
	}
	
	public void raiseRamps() {
		toggleLeftRamp();
		toggleRightRamp();
	}
	
	public void setRampPosition(RampPosition position) {
		switch(position) {
			case up:
				_leftServo.set(0);
				_rightServo.set(0);
				break;
			case down:
				_leftServo.set(0.5);
				_rightServo.set(0.5);
				break;
			case ramp:
				_leftServo.set(1.0);
				_rightServo.set(1.0);
				break;
		}
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(null);
    }
}

