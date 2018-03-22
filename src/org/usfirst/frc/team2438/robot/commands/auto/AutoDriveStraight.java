package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutoDriveStraight extends CommandBase {
	
	private TargetCounter _targetCounter;
	
	private final double  _distance;
	
	public AutoDriveStraight(double inches) {
    	this(inches, 10);
    }
	
    public AutoDriveStraight(double inches, double timeout) {
    	requires(drivetrain);
    	
    	_distance = inches * Drivetrain.UNITS_PER_INCH;
    	
    	if(timeout > 0) {
    		this.setTimeout(timeout);
    	}
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.setTargetDistance(_distance);
    	Timer.delay(0.5); // TODO - Test if needed
    	
    	_targetCounter = drivetrain.getTargetCounter();
    	_targetCounter.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() { }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {        
        return _targetCounter.onTarget(drivetrain.getError()) || this.isTimedOut();
    
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setTank(0, 0);
    	_targetCounter.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
