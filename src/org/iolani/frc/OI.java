package org.iolani.frc;

import org.iolani.frc.commands.LiftMoveUp;
import org.iolani.frc.subsystems.Lift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	public final Joystick _lStick = new Joystick(1);
	public final Joystick _rStick = new Joystick(2);
	
	public final Button _liftUpButton = new JoystickButton(_lStick, 1); 
	public final Button _liftDownButton = new JoystickButton(_lStick, 2); 
	
	
	public OI(){
		_liftUpButton.whenPressed(new LiftMoveUp());
	}
	
	public Joystick getLeftStick(){
		return _lStick;
	}
	
	public Joystick getRightStick(){
		return _rStick;
	}
	
	public Button getLiftUpBotton(){
		return _liftUpButton;
	}
	
	public Button getLiftDownButton(){
		return _liftDownButton;
	}
	
}
