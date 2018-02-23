package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;


/**
 *
 */
public class AutoTurn extends CommandBase implements PIDOutput, PIDSource {

	private double _degrees;
	private PIDController _pid;
	
	private final double kP = 0;
	private final double kI = 0;
	private final double kD = 0;
	
    public AutoTurn(double degrees, double power, double timeout) {
        requires(drivetrain);
        requires(navSensor);
        
        _degrees = degrees;
        
        if (timeout > 0) {
        	this.setTimeout(timeout);
        }
        
        _pid = new PIDController(kP, kI, kD, this, this);
        _pid.setAbsoluteTolerance(1);
        _pid.setOutputRange(-power, power);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	_pid.setSetpoint(_degrees);
    	_pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return _pid.onTarget() || this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		return navSensor.getGyro();
	}

	@Override
	public void pidWrite(double leftPower) {
		drivetrain.setTank(leftPower, -leftPower);
	}
}
