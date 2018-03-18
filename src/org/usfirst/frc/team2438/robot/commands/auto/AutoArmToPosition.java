package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class AutoArmToPosition extends CommandBase {
	
	private Position position;
	
	public AutoArmToPosition(Position position) {
        this(position, 1.75);
    }
	
    public AutoArmToPosition(Position position, double timeout) {
        // Use requires() here to declare subsystem dependencies
    	requires(intake);
    	
    	if(timeout > 0) {
    		this.setTimeout(timeout);
    	}
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() { 
    	//intake.setLiftPosition(position);
    	intake.setArmPosition(position);
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
