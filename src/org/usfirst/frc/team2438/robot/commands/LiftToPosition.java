package org.usfirst.frc.team2438.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class LiftToPosition extends CommandBase {
	
	private int position;
	
	private static final double THRESHOLD = 0;
	
	public LiftToPosition(int position) {
        this(position, -1);
    }
	
    public LiftToPosition(int position, double timeout) {
        // Use requires() here to declare subsystem dependencies
    	requires(lift);
    	
    	if(timeout > 0) {
    		this.setTimeout(timeout);
    	}
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lift.setPosition(position);
    	Timer.delay(0.5);
    	System.out.println("Im working");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//lift.setPosition(position);
    	
    	SmartDashboard.putNumber("Lift current", lift.getCurrent(1));
    }
    
    private boolean onTarget() {
    	// FIXME
    	return (lift.getError() < THRESHOLD);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
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
