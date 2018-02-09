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
    
	private TalonSRX _leftFront;
    private TalonSRX _rightFront;
    private TalonSRX _leftBack;
    private TalonSRX _rightBack;
    
    private DriveMode _dmode;
    
    private ControlMode _cmode;
	
	public void init() {
		_leftFront = new TalonSRX(RobotMap.leftFrontMotor);		
		_leftBack = new TalonSRX(RobotMap.leftBackMotor);
		_rightFront = new TalonSRX(RobotMap.rightFrontMotor);
		_rightBack = new TalonSRX(RobotMap.rightBackMotor);
		
		_leftFront.setInverted(true);
		_leftBack.setInverted(true);
		
		_leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT);
		_leftFront.setSelectedSensorPosition(0, 0, TIMEOUT);
		
		_dmode = DriveMode.Tank;
		_cmode = ControlMode.PercentOutput;
	}
	
	/**
	 * Sets tank mode
	 * @param left
	 * @param right
	 */
	public void setTank(double left, double right) {
		_leftFront.set(_cmode, left);
		_leftBack.set(_cmode, left);
		_rightFront.set(_cmode, right);
		_rightBack.set(_cmode, right);
	}
	
	/**
	 * Sets arcade mode
	 * @param x
	 * @param y
	 */
	public void setArcade(double x, double y) {
		_leftFront.set(_cmode,   x + y);
		_leftBack.set(_cmode,    x + y);
		_rightFront.set(_cmode, -x + y);
		_rightBack.set(_cmode,  -x + y);
		
	}
	
	/**
	 * Sets mecanum mode
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void setMecanum(double x, double y, double rotation) {
		_leftFront.set(_cmode,   x + y + rotation);
		_leftBack.set(_cmode,   -x + y + rotation);
		_rightFront.set(_cmode, -x + y - rotation);
		_rightBack.set(_cmode,   x + y - rotation);
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
        setDefaultCommand(new OperateTankDrive());
    }

	public double getLeftEncoder() {
		return _leftFront.getSelectedSensorPosition(0);
	}

	public void setLeftEncoderDistance(int distance) {
		_leftFront.setSelectedSensorPosition(distance, 0, TIMEOUT);
	}
}

