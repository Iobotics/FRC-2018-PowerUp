package org.usfirst.frc.team2438.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class ArmToPosition extends CommandBase {
	
	private int position;
	
	public ArmToPosition(int position) {
        this(position, 1.75);
    }
	
    public ArmToPosition(int position, double timeout) {
        // Use requires() here to declare subsystem dependencies
    	requires(intake);
    	
    	if(timeout > 0) {
    		this.setTimeout(timeout);
    	}
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() { 
    	intake.setLiftPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Intake current", intake.getLiftCurrent());
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
