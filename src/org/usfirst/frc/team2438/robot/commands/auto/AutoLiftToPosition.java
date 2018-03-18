package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class AutoLiftToPosition extends CommandBase {
	
	private Position position;
	
	public AutoLiftToPosition(Position position) {
        this(position, 2.5);
    }
	
    public AutoLiftToPosition(Position position, double timeout) {
        // Use requires() here to declare subsystem dependencies
    	requires(lift);
    	
    	if(timeout > 0) {
    		this.setTimeout(timeout);
    	}
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//lift.setPosition(position);
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
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	//lift.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	lift.stop();
    	end();
    }
}
