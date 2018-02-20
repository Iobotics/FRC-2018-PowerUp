package org.usfirst.frc.team2438.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class OperateIntakeLift extends CommandBase {
	
    public OperateIntakeLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	double current = (oi.getRightThrottle() * 5) + 0.5;
    	
    	intake.setLiftCurrent(current);
    	//intake.setLiftPower(oi.getLeftY());
    	
    	SmartDashboard.putNumber("Intake position", intake.getLiftPosition());
    	SmartDashboard.putNumber("Intake error", intake.getLiftError());
    	SmartDashboard.putNumber("Intake current", intake.getLiftCurrent());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setLiftPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
