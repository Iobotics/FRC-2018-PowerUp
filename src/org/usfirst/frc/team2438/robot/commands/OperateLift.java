package org.usfirst.frc.team2438.robot.commands;

/**
 * Operate the lift
 */
public class OperateLift extends CommandBase {

    public OperateLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lift.setPower(50);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	lift.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
