package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.CycleDriveMode;
import org.usfirst.frc.team2438.robot.commands.OperateDriveToPos;
import org.usfirst.frc.team2438.robot.commands.ToggleLeds;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick _lStick = new Joystick(2);
	private final Joystick _rStick = new Joystick(3);
	
	private final JoystickButton _cycleDrive = new JoystickButton(_lStick, 3);
	private final JoystickButton _driveToPos = new JoystickButton(_rStick, 2);
	private final JoystickButton _toggleLights = new JoystickButton(_lStick, 2);
	
	public OI() {
		_cycleDrive.whenPressed(new CycleDriveMode());
		_driveToPos.whenPressed(new OperateDriveToPos());
		_toggleLights.whenPressed(new ToggleLeds());
	}
	
	/**
	 * Get left stick X
	 * @return x
	 */
	public double getLeftX() {
		return _lStick.getX();
	}
	
	/**
	 * Get left stick Y
	 * @return y
	 */
	public double getLeftY() {
		return -_lStick.getY();
	}
	
	/**
	 * Get right stick X
	 * @return x
	 */
	public double getRightX() {
		return _rStick.getX();
	}
	
	/**
	 * Get right stick Y
	 * @return y
	 */
	public double getRightY() {
		return -_rStick.getY();
	}
	
	/**
	 * Get left stick throttle
	 * @return throttle
	 */
	public double getLeftThrottle() {
		return -_lStick.getZ();
	}
	
	/**
	 * Get right stick throttle
	 * @return throttle
	 */
	public double getRightThrottle() {
		return -_rStick.getZ();
	}
}
