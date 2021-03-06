package org.usfirst.frc.team2438.robot.commands.lift;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Move the lift to the given position
 */
public class LiftToPosition extends CommandBase {
	
	private int _encoderPosition;
	private TargetCounter _targetCounter;
	
    public LiftToPosition(Position position) {
    	this(position.getLiftPosition());
    }
    
    public LiftToPosition(int encoderPosition) {
    	requires(lift);
    	
    	_encoderPosition = encoderPosition;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	_targetCounter = lift.getTargetCounter();
    	_targetCounter.reset();
    	
    	System.out.println("Set lift target to: " + _encoderPosition);
    	lift.setPosition(_encoderPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {    	
    	SmartDashboard.putNumber("Lift Error", lift.getError());
    	SmartDashboard.putNumber("Lift Current", lift.getCurrent());
    	SmartDashboard.putNumber("Lift Counter", _targetCounter.getCount());
    	
    	lift.setPosition(_encoderPosition);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return _targetCounter.onTarget(lift.getError()) || lift.withinEncoderRange(_encoderPosition);
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Lift finished at: " + lift.getLiftEncoderPosition());
    	System.out.println();
    	_targetCounter.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.err.println("LiftToPosition was interrupted!");
    	end();
    }
}
