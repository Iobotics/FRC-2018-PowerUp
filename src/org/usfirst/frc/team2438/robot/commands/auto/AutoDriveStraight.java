package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoDriveStraight extends CommandBase {
	
	private static final double THRESHOLD = 1.0;
	
	private double _error = 5.0;
	
	private final double  _distance;
	
	public AutoDriveStraight(double inches) {
    	this(inches, -1);
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
    	drivetrain.resetEncoders();
    	drivetrain.setTargetDistance(_distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("DriveStraight Setpoint", _distance);
    	SmartDashboard.putNumber("DriveStraight Error", drivetrain.getError());
    	
    	_error = drivetrain.getError();
    }
    
    private boolean onTarget() {
    	return (drivetrain.getError() < THRESHOLD);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putBoolean("Done driving", this.onTarget() || this.isTimedOut());
        return this.onTarget() || this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.setTank(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
