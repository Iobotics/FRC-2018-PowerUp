package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

/**
 *
 */
public class OperateIntake extends CommandBase {
	
	private double power;
    public OperateIntake(double power) {
        // Use requires() here to declare subsystem dependencies
        requires(intake);
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!intake.getLimitSwitch() || intake.getLimitSwitch() && power < 0) {
    		if(power > 0) {
    			intake.setPosition(0);
    		}
    		if(lift.getLiftPosition() == Position.autoSwitch) {
    			intake.setPower(power * 0.28);
    		} else {
    			intake.setPower(power);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.setPosition(Position.home.getArmPosition());
    	intake.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
