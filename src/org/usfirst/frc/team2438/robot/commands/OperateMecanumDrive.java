package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Drivetrain;

/**
 * Operate mecanum drive
 */
public class OperateMecanumDrive extends CommandBase {

    public OperateMecanumDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.setMecanum(oi.getRightX(), oi.getRightY(), oi.getLeftX());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.getDriveMode() != Drivetrain.DriveMode.Mecanum;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setMecanum(0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
