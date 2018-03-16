package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	private static final double kF = 0;
	private static final double kP = 1.1;
	private static final double kI = 0.001;
	private static final double kD = 0.1;
	private static final int iZone = 0;
	
	private static final int TALON_TIMEOUT = 20;
	
	private TalonSRX _leftIntake;
	private TalonSRX _rightIntake;
	
	private TalonSRX _intakeArm;
	
	private DoubleSolenoid _solenoid;
	
	private DigitalInput _switch;
	
	private boolean solenoidActivated = true;
	
	public void init() {
		_leftIntake = new TalonSRX(RobotMap.leftIntake);
		_rightIntake = new TalonSRX(RobotMap.rightIntake);
		
		_leftIntake.setInverted(true);
		
		_intakeArm = new TalonSRX(RobotMap.intakeArm);
		
		// Intake lift encoder //
		_intakeArm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
		_intakeArm.selectProfileSlot(0, 0);
		_intakeArm.config_kF(0, kF, TALON_TIMEOUT);
		_intakeArm.config_kP(0, kP, TALON_TIMEOUT);
		_intakeArm.config_kI(0, kI, TALON_TIMEOUT);
		_intakeArm.config_kD(0, kD, TALON_TIMEOUT);
		_intakeArm.config_IntegralZone(0, iZone, TALON_TIMEOUT);
		
		_intakeArm.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		
		this.resetEncoder();
		//this.setLiftPosition(0);
		
		/*_intakeArm.configContinuousCurrentLimit(15, TALON_TIMEOUT);
		_intakeArm.configPeakCurrentLimit(20, TALON_TIMEOUT);
		_intakeArm.configPeakCurrentDuration(1000, TALON_TIMEOUT);
		_intakeArm.enableCurrentLimit(false);*/
		
		//_intakeArm.configNeutralDeadband(0.03, TALON_TIMEOUT);
		
		//this.resetEncoder();
		
		_solenoid = new DoubleSolenoid(0, 1);
		
		_switch = new DigitalInput(0);
	}
	
	public void setPower(double power) {
		_leftIntake.set(ControlMode.PercentOutput, power);
		_rightIntake.set(ControlMode.PercentOutput, power);
	}
	
	public void setVelocity(double velocity) {
		_leftIntake.set(ControlMode.Velocity, velocity);
		_rightIntake.set(ControlMode.Velocity, velocity);
	}
	
	public void setLiftPower(double power) {
		_intakeArm.set(ControlMode.PercentOutput, power);
	}
	
	public void setLiftPosition(double input) {
		int position = Math.round((float) input);
		
		_intakeArm.set(ControlMode.Position, position);
	}
	
	public void setLiftCurrent(double current) {
		_intakeArm.set(ControlMode.Current, current);
	}
	
	public int getLiftPosition() {
		return _intakeArm.getSelectedSensorPosition(0);
	}
	
	public int getLiftError() {
		return _intakeArm.getClosedLoopError(0);
	}
	
	public double getLiftCurrent() {
		return _intakeArm.getOutputCurrent();
	}
	
	public void resetEncoder() {
		this.stop();
		_intakeArm.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
	}
	
	public void toggleSolenoid() {
		if(!solenoidActivated) {
			_solenoid.set(Value.kForward);
		}
		else {
			_solenoid.set(Value.kReverse);
		}
		solenoidActivated = !solenoidActivated;
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_solenoid.set(Value.kOff);
	}
	
	public boolean getLimitSwitch() {
		return _switch.get();
	}
	
	public void initSolenoids() {
		_solenoid.set(Value.kForward);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		_solenoid.set(Value.kOff);
	}
	
	public void stop() {
		_leftIntake.set(ControlMode.PercentOutput, 0);
		_rightIntake.set(ControlMode.PercentOutput, 0);
		
		_intakeArm.set(ControlMode.PercentOutput, 0);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(null);
    	//setDefaultCommand(new OperateIntakeLift());
    }
}

