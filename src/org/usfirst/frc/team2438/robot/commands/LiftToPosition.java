package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Operate the lift
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
    	
    	lift.setPosition(_encoderPosition);
    	Timer.delay(0.1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {    	
    	SmartDashboard.putNumber("Lift Error", lift.getError());
    	SmartDashboard.putNumber("Lift Current", lift.getCurrent());
    	SmartDashboard.putNumber("Lift Counter", _targetCounter.getCount());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return _targetCounter.onTarget(lift.getError()) || (lift.getLimitSwitch() && lift.getPosition() < _encoderPosition);
    }

    // Called once after isFinished returns true
    protected void end() {
    	_targetCounter.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
