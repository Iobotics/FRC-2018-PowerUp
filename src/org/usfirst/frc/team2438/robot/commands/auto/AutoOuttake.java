package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

/**
 * Auto outtake
 */
public class AutoOuttake extends CommandBase {
	private double _timeout;

    public AutoOuttake(double timeout) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(intake);
        
        _timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.setTimeout(_timeout);
    	
    	intake.setPower(-0.4);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { }
    
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
    protected void end() {
    	intake.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.err.println("AutoOuttake interrupted!");
    	this.end();
    }
}
