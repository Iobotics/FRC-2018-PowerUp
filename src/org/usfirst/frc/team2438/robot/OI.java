package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.ArmToPosition;
import org.usfirst.frc.team2438.robot.commands.LiftToPosition;
import org.usfirst.frc.team2438.robot.commands.OperateIntake;
import org.usfirst.frc.team2438.robot.commands.OperateIntakeArm;
import org.usfirst.frc.team2438.robot.commands.OperateLift;
import org.usfirst.frc.team2438.robot.commands.ResetEncoders;
import org.usfirst.frc.team2438.robot.commands.ToggleIntakeSolenoid;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick _lStick = new Joystick(2);
	private final Joystick _rStick = new Joystick(3);
	
	private final JoystickButton _intakeButton = new JoystickButton(_rStick, 1);
	private final JoystickButton _outtakeButton = new JoystickButton(_lStick, 1);
	
	private final JoystickButton _lowerLiftButton = new JoystickButton(_rStick, 6);
	private final JoystickButton _raiseLiftButton = new JoystickButton(_rStick, 7);
	private final JoystickButton _setLiftButton = new JoystickButton(_rStick, 8);
	
	private final JoystickButton _homeLiftButton = new JoystickButton(_rStick, 2);
	private final JoystickButton _switchLiftButton = new JoystickButton(_rStick, 3);
	private final JoystickButton _scaleLiftButton = new JoystickButton(_rStick, 4);
	
	private final JoystickButton _lowerIntakeButton = new JoystickButton(_lStick, 2);
	private final JoystickButton _shootIntakeButton = new JoystickButton(_lStick, 3);
	
	private final JoystickButton _raiseRightRamp = new JoystickButton(_lStick, 5);
	private final JoystickButton _raiseLeftRamp = new JoystickButton(_lStick, 4);

	private final JoystickButton _dropRamps = new JoystickButton(_lStick, 9);
	
	private final JoystickButton _resetEncoders = new JoystickButton(_rStick, 11);
	
	// TODO - Repurpose for proper servo activation
	private final JoystickButton _activateServo = new JoystickButton(_lStick, 10);
	private final JoystickButton _deactivateServo = new JoystickButton(_rStick, 7);
	
	private final JoystickButton _solenoidButton = new JoystickButton(_rStick, 10);
	
	public OI() {
		_intakeButton.whileHeld(new OperateIntake(1));
		_outtakeButton.whileHeld(new OperateIntake(-1));
		
		_lowerLiftButton.whileHeld(new OperateLift(-0.1));
		_raiseLiftButton.whileHeld(new OperateLift(0.15));
		_setLiftButton.whenPressed(new LiftToPosition(25000));
		
		_homeLiftButton.whenPressed(new LiftToPosition(1));
		_switchLiftButton.whenPressed(new LiftToPosition(30500));
		_scaleLiftButton.whenPressed(new LiftToPosition(51500));
		
		_lowerIntakeButton.whileHeld(new OperateIntakeArm(-0.3));
		_shootIntakeButton.whenPressed(new ArmToPosition(1000));
		
		//_raiseLeftRamp.whenPressed(new OperateRamp(RampSide.left));
		//_raiseRightRamp.whenPressed(new OperateRamp(RampSide.right));
		
		//_dropRamps.whenPressed(new DropRamp());
		
		//_activateServo.whenPressed(new ActivateServo(RampPosition.ramp));
		//_deactivateServo.whenPressed(new ActivateServo(RampPosition.up));
		
		_resetEncoders.whenPressed(new ResetEncoders());
		
		_solenoidButton.whenPressed(new ToggleIntakeSolenoid());
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
