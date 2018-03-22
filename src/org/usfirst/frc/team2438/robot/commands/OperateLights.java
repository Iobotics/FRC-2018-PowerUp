package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Lift;

/**
 * Operate LEDs
 */
@SuppressWarnings("unused")
public class OperateLights extends CommandBase {

    public OperateLights() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(ledSystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//int numLights = Math.round((float) ((lift.getPosition() / Lift.MAX_LIFT_POSITION) * 10));
    	int numLights = Math.round((float) (((oi.getRightThrottle() + 1) / 2) * 10));
    	ledSystem.enableLights(numLights);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	ledSystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
