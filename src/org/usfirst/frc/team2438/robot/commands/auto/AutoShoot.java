package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutoShoot extends CommandBase {

    public AutoShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intake.setPower(-0.28);
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
