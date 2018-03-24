package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.util.Constants;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Intake
 */
public class Intake extends Subsystem {
	
	/* Motion Magic Constants */
	private static final double kF = 0;
	private static final double kP = 1.1;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	// TODO - Slightly raise the velocity
	private static final int INTAKE_VELOCITY = 1500;	 // Native units per 100 ms
	private static final int INTAKE_ACCELERATION = 2000; // Native units per 100 ms
	
	private static final int ERROR_THRESHOLD = 160; // Allowable error in native units
	
	private static final int ENCODER_OFFSET = 60; // Encoder offset in native units
	
	private TalonSRX _leftIntake;
	private TalonSRX _rightIntake;
	
	private TalonSRX _intakeArm;
	
	private DoubleSolenoid _intakeSolenoid;
	
	private DigitalInput _limitSwitch;
	
	private TargetCounter _targetCounter;
	
	private boolean _solenoidActivated;
	
	public void init() {
		_leftIntake = new TalonSRX(RobotMap.leftIntake);
		_rightIntake = new TalonSRX(RobotMap.rightIntake);
		
		// Invert the left intake motor
		_leftIntake.setInverted(true);
		
		_intakeArm = new TalonSRX(RobotMap.intakeArm);
		
		/* Configure the intake arm encoder */
		_intakeArm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.TALON_TIMEOUT);
		_intakeArm.selectProfileSlot(0, 0);
		_intakeArm.config_kF(0, kF, Constants.TALON_TIMEOUT);
		_intakeArm.config_kP(0, kP, Constants.TALON_TIMEOUT);
		_intakeArm.config_kI(0, kI, Constants.TALON_TIMEOUT);
		_intakeArm.config_kD(0, kD, Constants.TALON_TIMEOUT);
		_intakeArm.config_IntegralZone(0, iZone, Constants.TALON_TIMEOUT);
		_intakeArm.configMotionCruiseVelocity(INTAKE_VELOCITY, Constants.TALON_TIMEOUT);
		_intakeArm.configMotionAcceleration(INTAKE_ACCELERATION, Constants.TALON_TIMEOUT);
		
		_intakeArm.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
		//_intakeArm.set(ControlMode.MotionMagic, 0); TODO
		
		/* 
		 * Set a current limit of 5 amps.
		 * If it is greater than 5 amps for 100 ms, current drops to 3 amps.
		 */
		_intakeArm.configContinuousCurrentLimit(5, Constants.TALON_TIMEOUT);
		_intakeArm.configPeakCurrentLimit(3, Constants.TALON_TIMEOUT);
		_intakeArm.configPeakCurrentDuration(100, Constants.TALON_TIMEOUT);
		_intakeArm.enableCurrentLimit(true);
		
		// Initalize the intake solenoid
		_intakeSolenoid = new DoubleSolenoid(RobotMap.intakeForwardChannel, RobotMap.intakeReverseChannel);
		_solenoidActivated = true;
		
		_limitSwitch = new DigitalInput(RobotMap.intakeLimitSwitch);
		
		// Initialize the target counter
		_targetCounter = new TargetCounter(ERROR_THRESHOLD);
	}
	
	public void setPower(double power) {
		_leftIntake.set(ControlMode.PercentOutput, power);
		_rightIntake.set(ControlMode.PercentOutput, power);
	}
	
	public void setVelocity(double velocity) {
		_leftIntake.set(ControlMode.Velocity, velocity);
		_rightIntake.set(ControlMode.Velocity, velocity);
	}
	
	public void setArmPower(double power) {
		_intakeArm.set(ControlMode.PercentOutput, power);
	}
	
	public double getArmPower() {
		return _intakeArm.getMotorOutputPercent();
	}
	
	public void setArmPosition(Position position) {
		this.setArmPosition(position.getArmPosition());
	}
	
	public void setArmPosition(int position) {
		_intakeArm.set(ControlMode.MotionMagic, position + ENCODER_OFFSET);
	}
	
	public void setArmCurrent(double current) {
		_intakeArm.set(ControlMode.Current, current);
	}
	
	/**
	 * Gets the intake arm position in native units
	 * @return position
	 */
	public int getArmEncoderPosition() {
		return _intakeArm.getSelectedSensorPosition(0);
	}
	
	/**
	 * Gets the closed loop error of the intake arm in native units
	 * @return error
	 */
	public int getArmError() {
		return _intakeArm.getClosedLoopError(0);
	}
	
	public double getArmCurrent() {
		return _intakeArm.getOutputCurrent();
	}
	
	/**
	 * Checks if the intake arm is above the horizontal
	 * @return isAboveHorizontal
	 */
	// FIXME
	public boolean aboveHorizontal() {
		return (this.getArmEncoderPosition() > 480);
	}
	
	/**
	 * Resets the intake arm encoder
	 */
	public void resetEncoder() {
		_intakeArm.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
	}
	
	/**
	 * Toggle the intake solenoid
	 */
	public void toggleSolenoid() {
		if(!_solenoidActivated) {
			_intakeSolenoid.set(Value.kForward);
		}
		else {
			_intakeSolenoid.set(Value.kReverse);
		}
		_solenoidActivated = !_solenoidActivated;
		
		try {
			Thread.sleep(Constants.SOLENOID_PERIOD);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_intakeSolenoid.set(Value.kOff);
	}
	
	/**
	 * Gets the status of the intake limit switch
	 * @return limitSwitch
	 */
	public boolean getLimitSwitch() {
		return _limitSwitch.get();
	}
	
	/**
	 * Manually set the intake solenoid to a position
	 * @param solenoidPosition
	 */
	public void setSolenoid(Value solenoidPosition) {
		// Activate the solenoid
		_intakeSolenoid.set(solenoidPosition);
		
		// Wait for the solenoid to activate
		try {
			Thread.sleep(Constants.SOLENOID_PERIOD);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Turn the solenoid off
		_intakeSolenoid.set(Value.kOff);
	}
	
	public boolean canIntake(double power) {
		return (!this.getLimitSwitch() || (this.getLimitSwitch() && (power < 0)));
	}
	
	public void stop() {
		_leftIntake.set(ControlMode.PercentOutput, 0);
		_rightIntake.set(ControlMode.PercentOutput, 0);
		
		_intakeArm.set(ControlMode.PercentOutput, 0);
	}
	
	/**
     * Gets the intake TargetCounter
     * @return targetCounter
     */
	public TargetCounter getTargetCounter() {
		return _targetCounter;
	}
	
	public void debug() {
		SmartDashboard.putNumber("Intake arm position", _intakeArm.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Intake arm error", _intakeArm.getClosedLoopError(0));
		SmartDashboard.putNumber("Intake arm current", _intakeArm.getOutputCurrent());
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(null);
    }
}