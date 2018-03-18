package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class LiftToPosition extends CommandBase {
	
	private static final int ERROR_THRESHOLD = 250;
	
	private Position position;
	private TargetCounter targetCounter;
	
    public LiftToPosition(Position position) {
        // Use requires() here to declare subsystem dependencies
    	requires(lift);
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//lift.setPosition(position);

    	targetCounter = lift.getTargetCounter();
    	
    	lift.setPosition(position);
    	Timer.delay(0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//lift.setPosition(position);
    	
    	SmartDashboard.putNumber("Lift current", lift.getCurrent(1));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return targetCounter.onTarget(lift.getError(), ERROR_THRESHOLD);
    }

    // Called once after isFinished returns true
    protected void end() {
    	//lift.stop();
    	targetCounter.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	lift.stop();
    	end();
    }
}
