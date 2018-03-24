package org.usfirst.frc.team2438.robot.commands.lift;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class OperateLift extends CommandBase {
	
	private double power;
	
    public OperateLift(double power) {
        // Use requires() here to declare subsystem dependencies
    	requires(lift);
    	
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() { }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!(lift.getLimitSwitch() && power < 0)) {
    		lift.setPower(power);
    	}
    	
    	SmartDashboard.putNumber("Lift Position", CommandBase.lift.getLiftEncoderPosition());
    	
    	SmartDashboard.putNumber("Lift Error", lift.getError());
    	SmartDashboard.putNumber("Lift Current", lift.getCurrent());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (lift.getLimitSwitch() && power < 0);
    }

    // Called once after isFinished returns true
    protected void end() {
    	lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
