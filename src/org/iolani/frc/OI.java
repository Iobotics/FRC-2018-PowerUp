package org.iolani.frc;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

import org.iolani.frc.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick _lStick = new Joystick(1);
	private final Joystick _rStick = new Joystick(2);
	
	public OI() {
		
	}
}
