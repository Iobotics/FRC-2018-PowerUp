package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

/**
 *
 */
public class Intake extends Subsystem {
	
	private static final double kF = 0;
	private static final double kP = 1.1;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int MM_VEL = 1500;
	private static final int MM_ACC = 2000;
	
	private static final int ERROR_THRESHOLD = 200;
	
	private static final int TALON_TIMEOUT = 20;
	
	private TalonSRX _leftIntake;
	private TalonSRX _rightIntake;
	
	private TalonSRX _intakeArm;
	
	private DoubleSolenoid _solenoid;
	
	private DigitalInput _switch;
	private Potentiometer _potentiometer;
	
	private boolean _solenoidActivated = true;
	
	private TargetCounter _targetCounter;
	
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
		_intakeArm.configMotionCruiseVelocity(MM_VEL, TALON_TIMEOUT);
		_intakeArm.configMotionAcceleration(MM_ACC, TALON_TIMEOUT);
		
		_intakeArm.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
		
		this.resetEncoder();
		//this.setLiftPosition(0);
		
		/*_intakeArm.configContinuousCurrentLimit(15, TALON_TIMEOUT);
		_intakeArm.configPeakCurrentLimit(20, TALON_TIMEOUT);
		_intakeArm.configPeakCurrentDuration(1000, TALON_TIMEOUT);
		_intakeArm.enableCurrentLimit(false);*/
		
		//this.resetEncoder();
		
		_solenoid = new DoubleSolenoid(0, 1);
		
		_switch = new DigitalInput(0);
		_potentiometer = new AnalogPotentiometer(1, 360, 0);
		
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
	
	public void setPosition(int position) {
		_intakeArm.set(ControlMode.MotionMagic, position);
	}
	
	public void setArmCurrent(double current) {
		_intakeArm.set(ControlMode.Current, current);
	}
	
	public int getPosition() {
		return _intakeArm.getSelectedSensorPosition(0);
	}
	
	public int getArmError() {
		return _intakeArm.getClosedLoopError(0);
	}
	
	public double getArmCurrent() {
		return _intakeArm.getOutputCurrent();
	}
	
	public void resetEncoder() {
		this.stop();
		_intakeArm.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
	}
	
	public void toggleSolenoid() {
		if(!_solenoidActivated) {
			_solenoid.set(Value.kForward);
		}
		else {
			_solenoid.set(Value.kReverse);
		}
		_solenoidActivated = !_solenoidActivated;
		
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

	public void setArmPosition(Position position) {
		this.setPosition(position.getArmPosition());
	}
	
	public TargetCounter getTargetCounter() {
		return _targetCounter;
	}
	
	public double getPotentiometer() {
		return _potentiometer.get();
	}
	
	/*public void cyclePositionUp() {
		switch(_armPosition) {
			case home:
				this.setLiftPosition(ArmPosition.autoSwitch.getPosition());
				_armPosition = ArmPosition.autoSwitch;
				break;
			case autoSwitch:
				this.setLiftPosition(ArmPosition.autoScale.getPosition());
				_armPosition = ArmPosition.autoScale;
				break;
			case autoScale:
				this.setLiftPosition(Position.autoReverse.getPosition());
				armPosition = Position.autoReverse;
				break;
			default:
				this.setLiftPosition(ArmPosition.home.getPosition());
				_armPosition = ArmPosition.home;
		}
	}
	
	public void cyclePositionDown() {
		switch(_armPosition) {
			case autoReverse:
				this.setLiftPosition(Position.autoScale.getPosition());
				armPosition = Position.autoScale;
				break;
			case autoScale:
				this.setLiftPosition(ArmPosition.autoSwitch.getPosition());
				_armPosition = ArmPosition.autoSwitch;
				break;				
			case autoSwitch:
				this.setLiftPosition(ArmPosition.home.getPosition());
				_armPosition = ArmPosition.home;
				break;		
			default:
				this.setLiftPosition(ArmPosition.home.getPosition());
				_armPosition = ArmPosition.home;
		}
	}*/
}