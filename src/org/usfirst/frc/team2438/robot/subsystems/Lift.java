package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Lift subsystem
 */
public class Lift extends Subsystem {
	
	public static enum Position {
		home(0, 450),
		autoSwitch(30500, 550),
		autoScale(51500, 1340),
		autoReverse(0, 0); // TODO
		
		private int _liftPosition;
		private int _armPosition;
		
		Position(int liftPosition, int armPosition) {
			_liftPosition = liftPosition;
			_armPosition = armPosition;
		}
		
		public int getLiftPosition() {
			return _liftPosition;
		}
		
		public int getArmPosition() {
			return _armPosition;
		}
	}
	
	private static final double kF = 0;
	private static final double kP = 0.46;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int LIFT_VELOCITY = 6000; // TODO
	private static final int MAX_LIFT_POSITION = 51000; // FIXME
	
	private static final double VOLTS_PER_INCH = 0.009766;
	
	private static final int ERROR_THRESHOLD = 320;
	
	private static final int TALON_TIMEOUT = 20;
	
	private TalonSRX _frontLeftLift;
	private TalonSRX _frontRightLift;
	private TalonSRX _backLeftLift;
	private TalonSRX _backRightLift;
	
	private AnalogInput _rangeSensor;
	
	private Position _liftPosition;
	
	private TargetCounter _targetCounter;
	
    public void init() {
    	_frontLeftLift = new TalonSRX(RobotMap.frontLeftLift);
    	_frontRightLift = new TalonSRX(RobotMap.frontRightLift);
    	_backLeftLift = new TalonSRX(RobotMap.backLeftLift);
    	_backRightLift = new TalonSRX(RobotMap.backRightLift);
    	
    	_frontLeftLift.setInverted(true);
    	_backLeftLift.setInverted(true);
    	
    	_frontRightLift.setSensorPhase(true);
    	
    	_frontLeftLift.setNeutralMode(NeutralMode.Brake);
    	_frontRightLift.setNeutralMode(NeutralMode.Brake);
    	_backLeftLift.setNeutralMode(NeutralMode.Brake);
    	_backRightLift.setNeutralMode(NeutralMode.Brake);
    	
    	_frontLeftLift.follow(_frontRightLift);
    	_backLeftLift.follow(_frontRightLift);
    	_backRightLift.follow(_frontRightLift);
    	
    	_frontRightLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
    	_frontRightLift.selectProfileSlot(0, 0);
    	_frontRightLift.config_kF(0, kF, TALON_TIMEOUT);
    	_frontRightLift.config_kP(0, kP, TALON_TIMEOUT);
    	_frontRightLift.config_kI(0, kI, TALON_TIMEOUT);
    	_frontRightLift.config_kD(0, kD, TALON_TIMEOUT);
    	_frontRightLift.config_IntegralZone(0, iZone, TALON_TIMEOUT);
    	_frontRightLift.configMotionCruiseVelocity(LIFT_VELOCITY, TALON_TIMEOUT);
    	_frontRightLift.configMotionAcceleration(LIFT_VELOCITY, TALON_TIMEOUT);
    	
    	/*_frontRightLift.configForwardSoftLimitThreshold(51000, TALON_TIMEOUT);
    	_frontRightLift.configForwardSoftLimitEnable(true, TALON_TIMEOUT);
    	_frontRightLift.configReverseSoftLimitThreshold(0, TALON_TIMEOUT);
    	_frontRightLift.configReverseSoftLimitEnable(true, TALON_TIMEOUT);*/
    	
    	this.resetEncoder();
    	//this.setPosition(0);
    	
    	_rangeSensor = new AnalogInput(0);
    	
    	_targetCounter = new TargetCounter(ERROR_THRESHOLD);
    }
    
    public void setPower(double power) { 
    	_frontRightLift.set(ControlMode.PercentOutput, power);
    }
    
    public void setPosition(double input) {    	
    	_frontRightLift.set(ControlMode.MotionMagic, input);
    }
    
    public int getPosition() {
    	return _frontRightLift.getSelectedSensorPosition(0);
    }
    
    public void resetEncoder() {
    	_frontRightLift.setSelectedSensorPosition(0, 0, TALON_TIMEOUT);
    }
    
    public void setCurrent(double current) {
    	_frontLeftLift.set(ControlMode.Current, current);
    	_backLeftLift.set(ControlMode.Current, -current);
    	
    	_frontRightLift.set(ControlMode.Current, current);
    	_backRightLift.set(ControlMode.Current, -current);
    }
    
    public double getCurrent() {
    	return _frontRightLift.getOutputCurrent();
	}
    
    public double getError() {
    	return _frontRightLift.getClosedLoopError(0);
    }
    
    /**
     * Gets lift height from the range sensor in inches
     * @return height
     */
    public double getHeight() {
    	double height = _rangeSensor.getAverageVoltage() / VOLTS_PER_INCH;
    	
    	return height;
    }
    
    public ControlMode getMode() {
    	return _frontRightLift.getControlMode();
    }
    
    public void stop() {
    	_frontRightLift.set(ControlMode.PercentOutput, 0);
    }

    public void initDefaultCommand() {
    	//setDefaultCommand(new OperateLift());
    	setDefaultCommand(null);
    }

	public Position getLiftPosition() {
		return _liftPosition;
	}

	public void setPosition(Position liftPosition) {
		this.setPosition(liftPosition.getLiftPosition());
		_liftPosition = liftPosition;
	}
	
	public TargetCounter getTargetCounter() {
		return _targetCounter;
	}
	
	/*public void cyclePositionUp() {
		switch(liftPosition) {
			case home:
				this.setPosition(Position.autoSwitch.getLiftPosition());
				liftPosition = Position.autoScale;
				break;
			case autoSwitch:
				this.setPosition(Position.autoScale.getLiftPosition());
				liftPosition = Position.autoScale;
				break;
			default:
				this.setPosition(Position.home.getPosition());
				liftPosition = Position.home;
		}
	}
	
	public void cyclePositionDown() {
		switch(liftPosition) {
			case autoScale:
				this.setPosition(Position.autoSwitch.getPosition());
				liftPosition = Position.autoSwitch;
				break;				
			case autoSwitch:
				this.setPosition(Position.home.getPosition());
				liftPosition = Position.home;
			default:
				this.setPosition(Position.home.getPosition());
				liftPosition = Position.home;
		}
	}*/
}

