package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

/**
 * Drive for a given amount of time
 */
public class AutoTimedDrive extends CommandBase {
	
	private double _timeout;

    public AutoTimedDrive(double timeout) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        
        if(timeout < 0) {
        	throw new IllegalArgumentException("Timeout is too small!");
        }
        
        _timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setTimeout(_timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.setTank(-0.5, -0.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.err.println("AutoTimedDrive interrupted!");
    	end();
    }
}
