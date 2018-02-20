package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
 */
public class OperateLift extends CommandBase {
	
	private double input;
	
    public OperateLift(double input) {
        // Use requires() here to declare subsystem dependencies
    	requires(lift);
    	
    	this.input = input;
    }

    // Called just before this Command runs the first time
    protected void initialize() { }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double current = (input * 5);
    	
    	lift.setCurrent(current);
    	
    	//SmartDashboard.putNumber("Lift position", lift.getPosition());
    	SmartDashboard.putNumber("Lift setpoint", current);
    	SmartDashboard.putNumber("Lift error", lift.getError());
    	//SmartDashboard.putNumber("Lift current", lift.getCurrent());
    	
    	SmartDashboard.putNumber("Lift output #1", lift.getCurrent(1));
    	SmartDashboard.putNumber("Lift output #2", lift.getCurrent(2));
    	SmartDashboard.putNumber("Lift output #3", lift.getCurrent(3));
    	SmartDashboard.putNumber("Lift output #4", lift.getCurrent(4));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
