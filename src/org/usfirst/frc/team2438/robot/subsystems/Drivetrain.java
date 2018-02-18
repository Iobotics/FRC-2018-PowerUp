package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateTankDrive;
import org.usfirst.frc.team2438.robot.utils.DriveMode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain
 */
public class Drivetrain extends Subsystem {
	
	private static final double WHEEL_DIAMETER = 4;
	private static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	public static final double COUNTS_PER_ROTATION = 4096;
	public static final double UNITS_PER_INCH = COUNTS_PER_ROTATION / WHEEL_CIRCUMFERENCE;

    private static final int TIMEOUT = 20;

	private static final double kF = 0;
	private static final double kP = 0;
	private static final double kI = 0;
	private static final double kD = 0;
	private static final int iZone = 0;
	
	private static final int DRIVE_VELOCITY = 750;
	public static final int DRIVE_ACCELERATION = 112;
    
	private TalonSRX _leftFront;
    private TalonSRX _rightFront;
    private TalonSRX _leftMiddle;
    private TalonSRX _rightMiddle;
    //private TalonSRX _leftBack;
    //private TalonSRX _rightBack;
    
    private DriveMode _dmode;
    
    private ControlMode _cmode;
	
	public void init() {
		_leftFront = new TalonSRX(RobotMap.leftFrontMotor);
		_leftMiddle = new TalonSRX(RobotMap.leftBackMotor);
		_rightFront = new TalonSRX(RobotMap.rightFrontMotor);
		_rightMiddle = new TalonSRX(RobotMap.rightBackMotor);

    	_leftMiddle.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
    	_leftMiddle.selectProfileSlot(0, 0);
    	_leftMiddle.config_kF(0, kF, TIMEOUT);
    	_leftMiddle.config_kP(0, kP, TIMEOUT);
    	_leftMiddle.config_kI(0, kI, TIMEOUT);
    	_leftMiddle.config_kD(0, kD, TIMEOUT);
    	_leftMiddle.config_IntegralZone(0, iZone, TIMEOUT);
    	_leftMiddle.configMotionCruiseVelocity(DRIVE_VELOCITY, TIMEOUT);
    	_leftMiddle.configMotionAcceleration(DRIVE_ACCELERATION, TIMEOUT);
    	
    	_rightMiddle.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
    	_rightMiddle.selectProfileSlot(0, 0);
    	_rightMiddle.config_kF(0, kF, TIMEOUT);
    	_rightMiddle.config_kP(0, kP, TIMEOUT);
    	_rightMiddle.config_kI(0, kI, TIMEOUT);
    	_rightMiddle.config_kD(0, kD, TIMEOUT);
    	_rightMiddle.config_IntegralZone(0, iZone, TIMEOUT);
    	_rightMiddle.configMotionCruiseVelocity(DRIVE_VELOCITY, TIMEOUT);
    	_rightMiddle.configMotionAcceleration(DRIVE_ACCELERATION, TIMEOUT);
    	
		_leftFront.setInverted(true);
		_leftMiddle.setInverted(true);
		
		_leftFront.setSelectedSensorPosition(0, 0, TIMEOUT);
		
		_dmode = DriveMode.MotionMagic;
		_cmode = ControlMode.PercentOutput;
	}
	
	/**
	 * Sets tank mode
	 * @param left
	 * @param right
	 */
	public void setTank(double left, double right) {
		_leftFront.set(_cmode, left);
		_leftMiddle.set(_cmode, left);
		_rightFront.set(_cmode, right);
		_rightMiddle.set(_cmode, right);
	}
	
	/**
	 * Sets arcade mode
	 * @param x
	 * @param y
	 */
	public void setArcade(double x, double y) {
		_leftFront.set(_cmode,   x + y);
		_leftMiddle.set(_cmode,    x + y);
		_rightFront.set(_cmode, -x + y);
		_rightMiddle.set(_cmode,  -x + y);
		
	}
	
	/**
	 * Sets mecanum mode
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void setMecanum(double x, double y, double rotation) {
		_leftFront.set(_cmode,   x + y + rotation);
		_leftMiddle.set(_cmode,   -x + y + rotation);
		_rightFront.set(_cmode, -x + y - rotation);
		_rightMiddle.set(_cmode,   x + y - rotation);
	}
	
	public void setMotionMagic(double pos) {
		_leftMiddle.set(ControlMode.MotionMagic, pos * UNITS_PER_INCH);
		_rightMiddle.set(ControlMode.MotionMagic, pos * UNITS_PER_INCH);
		_leftFront.follow(_leftMiddle);
		_rightFront.follow(_rightMiddle);
	}
	
	/**
	 * Cycle through drive modes
	 */
	public void cycleDriveMode() {
		switch(_dmode) {
			case Tank:
				_dmode = DriveMode.Arcade;
				break;
			case Arcade:
				_dmode = DriveMode.Mecanum;
				break;
			case Mecanum:
				_dmode = DriveMode.Tank;
				break;
			case MotionMagic:
				_dmode = DriveMode.MotionMagic;
			default:
				_dmode = DriveMode.Tank;
		}
	}
	
	/**
	 * Get the current drive mode
	 * @return _dmode
	 */
	public DriveMode getDriveMode() {
		return _dmode;
	}

    public void initDefaultCommand() {
        //setDefaultCommand(new OperateTankDrive()); 
    	setDefaultCommand(null); 
    }

	public double getLeftEncoder() {
		return _leftFront.getSelectedSensorPosition(0);
	}

    public double getCurrent() {
		return _leftMiddle.getOutputCurrent();
	}
    
    public double getError() {
    	return _leftMiddle.getClosedLoopError(0);
    }
    
    public int getPosition() {
    	return _leftMiddle.getSelectedSensorPosition(0);
    }
    
	public void setLeftEncoderDistance(int distance) {
		_leftFront.setSelectedSensorPosition(distance, 0, TIMEOUT);
	}
}

