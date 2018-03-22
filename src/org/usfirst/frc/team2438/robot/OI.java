package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.LiftAndArmToPos;
import org.usfirst.frc.team2438.robot.commands.OperateIntake;
import org.usfirst.frc.team2438.robot.commands.OperateIntakeArm;
import org.usfirst.frc.team2438.robot.commands.OperateLift;
import org.usfirst.frc.team2438.robot.commands.ToggleIntakeSolenoid;
import org.usfirst.frc.team2438.robot.commands.ToggleRamp;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.subsystems.Ramp.RampSide;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick _lStick = new Joystick(2);
	private final Joystick _rStick = new Joystick(3);
	
	private final JoystickButton _outtakeButton = new JoystickButton(_lStick, 1);
	private final JoystickButton _intakeButton = new JoystickButton(_rStick, 1);
	
	private final JoystickButton _homeButton = new JoystickButton(_rStick, 2);
	private final JoystickButton _switchButton = new JoystickButton(_rStick, 3);
	private final JoystickButton _scaleButton = new JoystickButton(_rStick, 4);
	
	private final JoystickButton _lowerIntakeButton = new JoystickButton(_lStick, 2);
	private final JoystickButton _raiseIntakeButton = new JoystickButton(_lStick, 3);
	
	private final JoystickButton _raiseLiftButton = new JoystickButton(_rStick, 6);
	private final JoystickButton _lowerLiftButton = new JoystickButton(_rStick, 7);	
	
	private final JoystickButton _solenoidButton = new JoystickButton(_lStick, 5);
	
	private final JoystickButton _raiseLeftRamp = new JoystickButton(_lStick, 8);
	private final JoystickButton _raiseRightRamp = new JoystickButton(_lStick, 9);

	private final JoystickButton _dropLeftRamp = new JoystickButton(_rStick, 8);
	private final JoystickButton _dropRightRamp = new JoystickButton(_rStick, 9);
	
	public OI() {
		_outtakeButton.whileHeld(new OperateIntake(-1));
		_intakeButton.whileHeld(new OperateIntake(1));
		
		_lowerLiftButton.whileHeld(new OperateLift(-0.2));
		_raiseLiftButton.whileHeld(new OperateLift(0.25));
		
		_homeButton.whenPressed(new LiftAndArmToPos(Position.home));
		_switchButton.whenPressed(new LiftAndArmToPos(Position.autoSwitch));
		_scaleButton.whenPressed(new LiftAndArmToPos(Position.autoScale));
		
		_lowerIntakeButton.whileHeld(new OperateIntakeArm(-0.25));
		_raiseIntakeButton.whileHeld(new OperateIntakeArm(0.3));
		
		_solenoidButton.whenPressed(new ToggleIntakeSolenoid());
		
		_raiseLeftRamp.whenPressed(new ToggleRamp(RampSide.left));
		_raiseRightRamp.whenPressed(new ToggleRamp(RampSide.right));
		
		_dropLeftRamp.whenPressed(new ToggleRamp(RampSide.left));
		_dropRightRamp.whenPressed(new ToggleRamp(RampSide.right));
	}
	
	public double getLeftX() {
		return _lStick.getX();
	}
	
	public double getLeftY() {
		return -_lStick.getY();
	}
	
	public double getRightX() {
		return _rStick.getX();
	}
	
	public double getRightY() {
		return -_rStick.getY();
	}
	
	public double getLeftThrottle() {
		return -_lStick.getZ();
	}
	
	public double getRightThrottle() {
		return -_rStick.getZ();
	}
}
