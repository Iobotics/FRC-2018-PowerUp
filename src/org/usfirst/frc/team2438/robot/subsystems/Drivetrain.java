package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateTankDrive;
import org.usfirst.frc.team2438.robot.utils.DriveMode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain
 */
public class Drivetrain extends Subsystem {

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
}

