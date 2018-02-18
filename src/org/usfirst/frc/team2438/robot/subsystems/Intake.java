package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateIntakeLift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final double kF = 0.0;
	private final double kP = 0.5;
	private final double kI = 0.0;
	private final double kD = 0.0;
	private final int iZone = 0;
	
	private final int TIMEOUT = 20;
	
	private TalonSRX _leftIntake;
	private TalonSRX _rightIntake;
	
	private TalonSRX _intakeLift;
	
	private DoubleSolenoid _solenoid;
	
	private boolean solenoidActivated = false;
	
	public void init() {
		_leftIntake = new TalonSRX(RobotMap.leftIntake);
		_rightIntake = new TalonSRX(RobotMap.rightIntake);
		
		_leftIntake.setInverted(true);
		
		_intakeLift = new TalonSRX(RobotMap.intakeLift);
		_intakeLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
		_intakeLift.selectProfileSlot(0, 0);
		_intakeLift.config_kF(0, kF, TIMEOUT);
		_intakeLift.config_kP(0, kP, TIMEOUT);
		_intakeLift.config_kI(0, kI, TIMEOUT);
		_intakeLift.config_kD(0, kD, TIMEOUT);
		_intakeLift.config_IntegralZone(0, iZone, TIMEOUT);
		
		_intakeLift.configContinuousCurrentLimit(15, TIMEOUT);
		_intakeLift.configPeakCurrentLimit(20, TIMEOUT);
		_intakeLift.configPeakCurrentDuration(1000, TIMEOUT);
		_intakeLift.enableCurrentLimit(false);
		
		this.resetEncoder();
		
		_solenoid = new DoubleSolenoid(0, 1);
	}
	
	public void setPower(double power) {
		_leftIntake.set(ControlMode.PercentOutput, power);
		_rightIntake.set(ControlMode.PercentOutput, power);
	}
	
	public void setVelocity(double velocity) {
		_leftIntake.set(ControlMode.Velocity, velocity);
		_leftIntake.set(ControlMode.Velocity, velocity);
	}
	
	public void setLiftPower(double power) {
		_intakeLift.set(ControlMode.PercentOutput, power);
	}
	
	public void setLiftPosition(double input) {
		// FIXME - Find equation to map input
		int position = Math.round((float) input);
		
		_intakeLift.set(ControlMode.Position, position);
	}
	
	public void resetEncoder() {
		_intakeLift.setSelectedSensorPosition(0, 0, TIMEOUT);
		_intakeLift.set(ControlMode.Position, 0);
	}
	
	public void setSolenoid() {
		if(!solenoidActivated) {
			_solenoid.set(Value.kForward);
		}
		else if(solenoidActivated) {
			_solenoid.set(Value.kReverse);
		}
		solenoidActivated = !solenoidActivated;
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_solenoid.set(Value.kOff);
	}
	
    public void initDefaultCommand() {
    	//setDefaultCommand(null);
    	setDefaultCommand(new OperateIntakeLift());
    }
}

