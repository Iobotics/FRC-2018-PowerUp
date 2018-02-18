package org.usfirst.frc.team2438.robot.commands;

/**
 *
 */
public class RotateWheel extends CommandBase {

    public RotateWheel() {
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.rotateWheel(20);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
