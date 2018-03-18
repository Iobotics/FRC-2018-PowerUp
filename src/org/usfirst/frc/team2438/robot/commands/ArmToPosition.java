package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class ArmToPosition extends CommandBase {
	
	private static final int ERROR_THRESHOLD = 200;
	
	private Position position;
	private TargetCounter targetCounter;
	
    public ArmToPosition(Position position) {
        // Use requires() here to declare subsystem dependencies
    	requires(intake);
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() { 
    	//intake.setLiftPosition(position);
    	targetCounter = intake.getTargetCounter();
    	
    	intake.setArmPosition(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Arm Error", intake.getLiftError());
    	SmartDashboard.putNumber("Intake current", intake.getLiftCurrent());
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return targetCounter.onTarget(intake.getLiftError(), ERROR_THRESHOLD);
    }

    // Called once after isFinished returns true
    protected void end() {
    	targetCounter.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
