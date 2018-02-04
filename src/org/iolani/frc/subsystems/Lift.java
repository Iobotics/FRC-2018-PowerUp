package org.iolani.frc.subsystems;

import org.iolani.frc.RobotMap;
import org.iolani.frc.commands.LiftMoveUp;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lift extends Subsystem {

	private TalonSRX _lift;
	
	double liftOffSet;

	private static final double WHEEL_DIAMETER_INCHES  = 1.5;
	private static final double WHEEL_INCHES_PER_REV   = Math.PI * WHEEL_DIAMETER_INCHES;
	private static final int ENCODER_TICKS_PER_REV = 140;
	private static final double ENCODER_INCHES_PER_REV = (4 * ENCODER_TICKS_PER_REV) / WHEEL_INCHES_PER_REV;

	public void init() {
		_lift = new TalonSRX(RobotMap._liftTalon);
		_lift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		_lift.configMotionCruiseVelocity(180, 0);
		_lift.configMotionAcceleration(1, 0);
		_lift.selectProfileSlot(0, 0);
		_lift.config_kF(0, 0, 0);
		_lift.config_kP(0, 0, 0);
		_lift.config_kI(0, 0, 0);
		_lift.config_kD(0, 0, 0);
		_lift.configPeakOutputForward(1, 0);
		_lift.configPeakOutputReverse(-1, 0);

		liftOffSet = _lift.getSensorCollection().getPulseWidthPosition();
	}

	public void initDefaultCommand()
	{
		this.setDefaultCommand(new LiftMoveUp());
	}
	
	public void moveMotorRotations()
	{
		_lift.set(ControlMode.MotionMagic, 2.25 * ENCODER_TICKS_PER_REV);
	}
	
	public void moveMotorEncoder()
	{
		_lift.set(ControlMode.MotionMagic, 12 * ENCODER_INCHES_PER_REV);
	}
	
	public void moveMotorSpeed(double percent)
	{
		_lift.set(ControlMode.PercentOutput, percent);
	}
	
	public double getLeft() {
		return _lift.getSensorCollection().getPulseWidthPosition() - liftOffSet;
	}
	
	public void debug() {
		SmartDashboard.putNumber("Rotations", getLeft() / ENCODER_TICKS_PER_REV);
		SmartDashboard.putNumber("Encoder Ticks", getLeft());
		SmartDashboard.putNumber("Voltage", _lift.getBusVoltage());
	}
}
