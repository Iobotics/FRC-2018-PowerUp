package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutoShoot extends CommandBase {
	
	private double _power;

    public AutoShoot(double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(intake);
        
        _power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Timer.delay(1);
    	intake.setPower(-_power);
    	Timer.delay(2);
    	intake.setPower(0);
    	Timer.delay(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
    
	protected boolean isFinished() {
		return true;
	}

    // Called once after timeout
    protected void end() {
    	intake.setPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
