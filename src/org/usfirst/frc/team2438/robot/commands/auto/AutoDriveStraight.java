package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoDriveStraight extends CommandBase {
	
	private TargetCounter _targetCounter;
	
	private final double  _encoderPosition;
	
	public AutoDriveStraight(double inches) {
    	this(inches, 10);
    }
	
    public AutoDriveStraight(double inches, double timeout) {
    	requires(drivetrain);
    	
    	_encoderPosition = inches * Drivetrain.UNITS_PER_INCH;
    	
    	if(timeout > 0) {
    		this.setTimeout(timeout);
    	}
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	_targetCounter = drivetrain.getTargetCounter();
    	_targetCounter.reset();
    	
    	drivetrain.resetEncoders();
    	
    	drivetrain.setTargetDistance(_encoderPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Drivetrain position", drivetrain.getLeftEncoder());
    	SmartDashboard.putNumber("Drivetrain error", drivetrain.getError(_encoderPosition));
    	SmartDashboard.putNumber("Drivetrain counter", drivetrain.getTargetCounter().getCount());
    	
    	drivetrain.setTargetDistance(_encoderPosition);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return _targetCounter.onTarget(drivetrain.getError(_encoderPosition)) || this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setTank(0, 0);
    	_targetCounter.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.err.println("AutoDriveStraight interrupted!");
    	this.end();
    }
}
