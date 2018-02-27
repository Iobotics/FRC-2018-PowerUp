package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class AutoTurn extends CommandBase implements PIDOutput {

	private double _degrees;
	private PIDController _pid;
	private double _onTargetTime;
	
	private final double kP = 0.001;
	private final double kI = 0;
	private final double kD = 0;
	
    public AutoTurn(double degrees, double power, double timeout) {
        requires(drivetrain);
        requires(navSensor);
        
        _degrees = degrees;
        
        if (timeout > 0) {
        	this.setTimeout(timeout);
        }
        
        _pid = new PIDController(kP, kI, kD, navSensor, this);
        _pid.setAbsoluteTolerance(1);
        _pid.setInputRange(-180.0, 180.0);
        _pid.setOutputRange(-power, power);
        _pid.setContinuous(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	_pid.reset();
    	_pid.setSetpoint(_degrees);
    	_pid.enable();
    	_onTargetTime = Double.MAX_VALUE;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//SmartDashboard.putNumber("Drive Turn error", _pid.getContinuousError());
    }
 
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return _pid.onTarget() || this.isTimedOut();
    
    	/*
        if(_pid.onTarget() & !_onTarget) {
        	_onTarget = true;
        	_onTargetTime = this.timeSinceInitialized() + 1.0;
        } else if(!_pid.onTarget()){
        	_onTarget = false;
        }
        */
    }

    // Called once after isFinished returns true
    protected void end() {
    	_pid.disable();
    	drivetrain.setTank(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	_pid.disable();
    	drivetrain.setTank(0, 0);
    }

	@Override
	public void pidWrite(double leftPower) {
		drivetrain.setTank(leftPower, -leftPower);
	}
}
