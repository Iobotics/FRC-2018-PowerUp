package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class AutoTurn extends CommandBase implements PIDOutput {
	
	private double _degrees;
	private PIDController _pid;
	
	private final double kP = 0;
	private final double kI = 0;
	private final double kD = 0;
	
	public AutoTurn(double degrees) {
		this(degrees, 0.5, 10);
	}
	
	public AutoTurn(double degrees, double power) {
		this(degrees, power, 10);
	}
	
    public AutoTurn(double degrees, double power, double timeout) {
        requires(drivetrain);
        requires(navSensor);
        
        _degrees = degrees;
        
        if (timeout > 0) {
        	this.setTimeout(timeout);
        }
        
        //Sets up PID variables
        _pid = new PIDController(kP, kI, kD, navSensor, this);
        _pid.setAbsoluteTolerance(1.0);
        _pid.setInputRange(-180.0, 180.0);
        _pid.setOutputRange(-power, power);
        _pid.setContinuous(true);
        this.setTimeout(timeout);
    }

    //Sets the target then start PID
    protected void initialize() {
    	navSensor.zeroGyro();
    	Timer.delay(0.75);
    	_pid.reset();
    	_pid.setSetpoint(_degrees);
    	_pid.enable();
    }

   
    protected void execute() {
    	SmartDashboard.putNumber("Drive setpoint", _pid.getSetpoint());
    	SmartDashboard.putNumber("Drive Turn error", _pid.getError());
    }
 
    //Finishes When On target or Timed Out
    protected boolean isFinished() {
        return _pid.onTarget() || this.isTimedOut();
    }

    protected void end() {
    	_pid.disable();
    	drivetrain.setVelocity(0, 0);
    }

    protected void interrupted() {
    	end();
    }

	@Override
	public void pidWrite(double power) {
		drivetrain.setVelocity(-power, power);
	}
}
