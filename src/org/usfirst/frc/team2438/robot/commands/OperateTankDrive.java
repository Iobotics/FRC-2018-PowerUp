package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Drivetrain;

/**
 * Operate tank drive
 */
public class OperateTankDrive extends CommandBase {

    public OperateTankDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.setTank(oi.getLeftY(), oi.getRightY());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.getDriveMode() != Drivetrain.DriveMode.Tank;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setTank(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
