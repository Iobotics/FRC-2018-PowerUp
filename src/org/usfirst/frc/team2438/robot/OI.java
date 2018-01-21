package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.CycleDriveMode;
import org.usfirst.frc.team2438.robot.commands.LowerLift;
import org.usfirst.frc.team2438.robot.commands.RaiseLift;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick _lStick = new Joystick(2);
	private final Joystick _rStick = new Joystick(3);
	
	private JoystickButton _cycleDrive = new JoystickButton(_lStick, 3);
	
	private JoystickButton _raiseLift = new JoystickButton(_rStick, 3);
	private JoystickButton _lowerLift = new JoystickButton(_rStick, 4);
	
	public OI() {
		_cycleDrive.whenPressed(new CycleDriveMode());
		
		_raiseLift.whileHeld(new RaiseLift());
		_lowerLift.whileHeld(new LowerLift());
	}
	
	public double getLeftX() {
		return -_lStick.getX();
	}
	
	public double getLeftY() {
		return _lStick.getY();
	}
	
	public double getRightX() {
		return -_rStick.getX();
	}
	
	public double getRightY() {
		return _rStick.getY();
	}
}
