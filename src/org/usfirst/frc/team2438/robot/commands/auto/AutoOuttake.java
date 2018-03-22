package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

/**
 * Auto outtake
 */
public class AutoOuttake extends CommandBase {
	
	private double _power;

    public AutoOuttake(double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(intake);
        
        _power = -power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// TODO - Test if needed
    	intake.setPower(_power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { }
    
	protected boolean isFinished() {
		return true;
	}
	
    protected void end() { }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	intake.setPower(0);
    }
}
