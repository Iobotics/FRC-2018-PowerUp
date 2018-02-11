package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.utils.DriveMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate tank drive
 */
public class OperateMotionMagicDrive extends CommandBase {

    public OperateMotionMagicDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(oi.getPositionButton()) {
    		drivetrain.setMotionMagic(oi.getRightY()*120);
    	}

    	SmartDashboard.putNumber("Drive Position", drivetrain.getPosition());
    	SmartDashboard.putNumber("Drive Error", drivetrain.getError());
    	
    	SmartDashboard.putNumber("Drive current", drivetrain.getCurrent());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.getDriveMode() != DriveMode.MotionMagic;
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
