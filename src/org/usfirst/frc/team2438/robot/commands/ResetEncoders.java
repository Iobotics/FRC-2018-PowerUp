package org.usfirst.frc.team2438.robot.commands;

/**
 * Reset all subsystem encoders
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
    	drivetrain.stop();
    	lift.stop();
    	intake.stop();
    	
    	drivetrain.resetEncoders();
		lift.resetEncoder();
		intake.resetEncoder();
		
		lift.setPosition(0);
		intake.setArmPosition(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() { }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
