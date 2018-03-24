package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.util.Constants;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Lift
 */
public class Lift extends Subsystem {
	
	public static enum Position {
		 // TODO - Find encoder values
		home(0, 500),
		autoSwitch(24300, 500),
		autoScale(52500, 1230),
		autoReverse(51500, 0);
		
		private int _liftPosition;
		private int _armPosition;
		
		private Position(int liftPosition, int armPosition) {
			_liftPosition = liftPosition;
			_armPosition = armPosition;
		}
		
		/**
		 * Gets the lift position associated with the enum
		 * @return liftPosition
		 */
		public int getLiftPosition() {
			return _liftPosition;
		}
		
		/**
		 * Gets the arm position associated with the enum
		 * @return armPosition
		 */
		public int getArmPosition() {
			return _armPosition;
		}
	}
	
	// TODO - Tune PID constants
	/* Motion Magic Constants */
	private static final double kF = 0;
	private static final double kP = 0.4;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	// TODO - Change the lift velocity
	public static final int LIFT_VELOCITY = 2000;	  // Native units per 100 ms
	public static final int LIFT_ACCELERATION = 2000; // Native units per 100 ms
	
	public static final int MAX_LIFT_POSITION = 53000; // Max lift position in native units
	
	private static final int ERROR_THRESHOLD = 320;  // Allowable error in native units
	
	private TalonSRX _frontLeftLift;
	private TalonSRX _frontRightLift;
	private TalonSRX _backLeftLift;
	private TalonSRX _backRightLift;
	
	private Position _liftPosition;
	private int _setpoint;
	
	private TargetCounter _targetCounter;
	
	private DigitalInput _limitSwitch;
	
    public void init() {
    	_frontLeftLift = new TalonSRX(RobotMap.frontLeftLift);
    	_frontRightLift = new TalonSRX(RobotMap.frontRightLift);
    	_backLeftLift = new TalonSRX(RobotMap.backLeftLift);
    	_backRightLift = new TalonSRX(RobotMap.backRightLift);
    	
    	// Reverse left side motors
    	_frontLeftLift.setInverted(true);
    	_backLeftLift.setInverted(true);
    	
    	// Reverse the encoder readings
    	_backLeftLift.setSensorPhase(true);
    	
    	/* Set the lift motors to brake mode */
    	_frontLeftLift.setNeutralMode(NeutralMode.Brake);
    	_frontRightLift.setNeutralMode(NeutralMode.Brake);
    	_backLeftLift.setNeutralMode(NeutralMode.Brake);
    	_backRightLift.setNeutralMode(NeutralMode.Brake);
    	
    	// Set the lift motors to follow the front right master motor
    	_frontLeftLift.follow(_backLeftLift);
    	_frontRightLift.follow(_backLeftLift);
    	_backRightLift.follow(_backLeftLift);
    	
    	/* Configure the lift motor encoder */
    	_backLeftLift.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.TALON_TIMEOUT);
    	_backLeftLift.selectProfileSlot(0, 0);
    	_backLeftLift.config_kF(0, kF, Constants.TALON_TIMEOUT);
    	_backLeftLift.config_kP(0, kP, Constants.TALON_TIMEOUT);
    	_backLeftLift.config_kI(0, kI, Constants.TALON_TIMEOUT);
    	_backLeftLift.config_kD(0, kD, Constants.TALON_TIMEOUT);
    	_backLeftLift.config_IntegralZone(0, iZone, Constants.TALON_TIMEOUT);
    	_backLeftLift.configMotionCruiseVelocity(LIFT_VELOCITY, Constants.TALON_TIMEOUT);
    	_backLeftLift.configMotionAcceleration(LIFT_ACCELERATION, Constants.TALON_TIMEOUT);
    	
    	/* Configure a current limit on the front right master motor */
    	_backLeftLift.configContinuousCurrentLimit(15, Constants.TALON_TIMEOUT);
    	_backLeftLift.configPeakCurrentLimit(12, Constants.TALON_TIMEOUT);
    	_backLeftLift.configPeakCurrentDuration(100, Constants.TALON_TIMEOUT);
    	_backLeftLift.enableCurrentLimit(true);
    	
    	// TODO - Enable the forward soft limit
    	/*_backLeftLift.configForwardSoftLimitThreshold(51000, Constants.TALON_TIMEOUT);
    	_backLeftLift.configForwardSoftLimitEnable(true, Constants.TALON_TIMEOUT);*/
    	
    	_limitSwitch = new DigitalInput(RobotMap.liftLimitSwitch);
    	
    	// Initialize the target counter
    	_targetCounter = new TargetCounter(ERROR_THRESHOLD);
    }
    
    public void setPower(double power) { 
    	_backLeftLift.set(ControlMode.PercentOutput, power);
    }
    
    public double getPower() {
    	return _backLeftLift.getMotorOutputPercent();
    }
    
    public void setPosition(Position liftPosition) {
		this.setPosition(liftPosition.getLiftPosition());
		_liftPosition = liftPosition;
	}
    
    public void setPosition(int input) {
    	_backLeftLift.set(ControlMode.MotionMagic, input);
    	_setpoint = input;
    }
    
    public int getLiftEncoderPosition() {
    	return _backLeftLift.getSelectedSensorPosition(0);
    }
    
    public Position getLiftPosition() {
		return _liftPosition;
	}
    
    public void resetEncoder() {
    	_backLeftLift.setSelectedSensorPosition(0, 0, Constants.TALON_TIMEOUT);
    }
    
    public void setCurrent(double current) {    	
    	_backLeftLift.set(ControlMode.Current, current);
    }
    
    public double getCurrent() {
    	return _backLeftLift.getOutputCurrent();
	}
    
    public double getError() {
    	return _setpoint - _backLeftLift.getSelectedSensorPosition(0);
    }
    
    public void stop() {
    	_backLeftLift.set(ControlMode.PercentOutput, 0);
    }
    
    /**
	 * Gets the status of the lift limit switch
	 * @return limitSwitch
	 */
    public boolean getLimitSwitch()
	{
		return _limitSwitch.get();
	}
    
    /**
     * Check if the lift is within a valid output range
     * @param encoderPosition
     * @return isValid
     */
    public boolean withinEncoderRange(int encoderPosition) {
    	return !(this.getLimitSwitch() && this.getLiftEncoderPosition() < encoderPosition);
    }
    
    /**
     * Check if the lift is within a valid output range
     * @param encoderPosition
     * @return isValid
     */
    public boolean withinPowerRange(double power) {
    	return !(this.getLimitSwitch() && this.getPower() < power);
    }
    
    /**
     * Gets the lift TargetCounter
     * @return targetCounter
     */
    public TargetCounter getTargetCounter() {
		return _targetCounter;
	}
    
    public void debug() {
    	SmartDashboard.putNumber("Lift position", this.getLiftEncoderPosition());
    	SmartDashboard.putNumber("Lift error", this.getError());
    	SmartDashboard.putNumber("Lift current", this.getCurrent());
    }

    public void initDefaultCommand() {
    	setDefaultCommand(null);
    }
}

