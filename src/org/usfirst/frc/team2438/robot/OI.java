package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.DropRamp;
import org.usfirst.frc.team2438.robot.commands.LiftAndArmToPos;
import org.usfirst.frc.team2438.robot.commands.LockRamp;
import org.usfirst.frc.team2438.robot.commands.OperateIntake;
import org.usfirst.frc.team2438.robot.commands.OperateIntakeArm;
import org.usfirst.frc.team2438.robot.commands.OperateLift;
import org.usfirst.frc.team2438.robot.commands.OperateRamp;
import org.usfirst.frc.team2438.robot.commands.ToggleIntakeSolenoid;
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
	
	private final JoystickButton _intakeButton = new JoystickButton(_rStick, 1);
	private final JoystickButton _outtakeButton = new JoystickButton(_lStick, 1);
	
	private final JoystickButton _raiseLiftButton = new JoystickButton(_rStick, 6);
	private final JoystickButton _lowerLiftButton = new JoystickButton(_rStick, 7);
	
	private final JoystickButton _homeButton = new JoystickButton(_rStick, 2);
	private final JoystickButton _switchButton = new JoystickButton(_rStick, 3);
	private final JoystickButton _scaleButton = new JoystickButton(_rStick, 4);
	
	private final JoystickButton _lowerIntakeButton = new JoystickButton(_lStick, 2);
	private final JoystickButton _raiseIntakeButton = new JoystickButton(_lStick, 3);
	
	private final JoystickButton _raiseRightRamp = new JoystickButton(_lStick, 8);
	private final JoystickButton _raiseLeftRamp = new JoystickButton(_lStick, 9);

	private final JoystickButton _dropLeftRamp = new JoystickButton(_lStick, 10);
	private final JoystickButton _dropRightRamp = new JoystickButton(_lStick, 11);
	
	// TODO - Repurpose for proper servo activation
	//private final JoystickButton _activateServo = new JoystickButton(_lStick, 10);
	//private final JoystickButton _deactivateServo = new JoystickButton(_rStick, 7);
	
	private final JoystickButton _solenoidButton = new JoystickButton(_lStick, 5);
	
	public OI() {
		_intakeButton.whileHeld(new OperateIntake(1));
		_outtakeButton.whileHeld(new OperateIntake(-1));
		
		_lowerLiftButton.whileHeld(new OperateLift(-0.1));
		_raiseLiftButton.whileHeld(new OperateLift(0.15));
		
		_homeButton.whenPressed(new LiftAndArmToPos(Position.home));
		_switchButton.whenPressed(new LiftAndArmToPos(Position.autoSwitch));
		_scaleButton.whenPressed(new LiftAndArmToPos(Position.autoScale));
		
		_lowerIntakeButton.whileHeld(new OperateIntakeArm(-0.3));
		_raiseIntakeButton.whileHeld(new OperateIntakeArm(0.3));
		
		_raiseLeftRamp.whenPressed(new OperateRamp(RampSide.left));
		_raiseRightRamp.whenPressed(new OperateRamp(RampSide.right));
		
		_dropLeftRamp.whenPressed(new DropRamp(RampSide.left));
		_dropRightRamp.whenPressed(new DropRamp(RampSide.right));
		
		//_activateServo.whenPressed(new ActivateServo(RampPosition.ramp));
		//_deactivateServo.whenPressed(new ActivateServo(RampPosition.up));
		
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
