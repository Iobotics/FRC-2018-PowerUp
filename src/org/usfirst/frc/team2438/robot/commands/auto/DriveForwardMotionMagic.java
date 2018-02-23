package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;


/**
 *
 */
public class DriveForwardMotionMagic extends CommandBase {

    public DriveForwardMotionMagic() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);\
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.driveToPosition(36);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setTank(0, 0);
    	drivetrain.resetEncoders();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
