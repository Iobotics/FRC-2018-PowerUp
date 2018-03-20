package org.usfirst.frc.team2438.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class OperateIntakeArm extends CommandBase {
	
	private double power;
	
    public OperateIntakeArm(double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(intake);
    	
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setArmPower(power);
    	
    	SmartDashboard.putNumber("Intake Arm Error", intake.getArmError());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (lift.getPosition() >= 51500);
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setPosition(intake.getPosition() + 50);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
