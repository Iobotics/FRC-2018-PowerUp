package org.usfirst.frc.team2438.robot.commands;

/**
 *
 */
public class ResetEncoders extends CommandBase {

    public ResetEncoders() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(lift);
    	requires(intake);
    	
    	this.setInterruptible(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetEncoders();
		lift.resetEncoder();
		intake.resetEncoder();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
