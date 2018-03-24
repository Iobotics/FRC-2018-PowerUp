package org.usfirst.frc.team2438.robot.commands.intake;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
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
    	// If the intake doesn't have a box in it, lower the intake arm
    	if(!intake.getLimitSwitch() && power > 0) {
    		intake.setArmPosition(0);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(intake.canIntake(power)) {
    		
    		if(lift.getLiftPosition() == Position.autoSwitch) {
    			power *= 0.28;
    		}
    		
    		intake.setPower(power);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	// Only set arm position if intaking
    	if(power > 0) {
    		intake.setArmPosition(Position.home.getArmPosition());
    	}
    	
    	intake.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
