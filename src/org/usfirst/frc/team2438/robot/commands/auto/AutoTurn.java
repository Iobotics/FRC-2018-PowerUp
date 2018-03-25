package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class AutoTurn extends CommandBase implements PIDOutput {
	
	private static final double GYRO_TOLERANCE = 1.0;
	
	private double _degrees;
	private PIDController _pid;
	private TargetCounter _targetCounter;
	
	private final double kP = 0.0044;
	private final double kI = 0.05;
	private final double kD = 0;
	
	public AutoTurn(double degrees) {
		this(degrees, 0.7, -1);
	}
	
	public AutoTurn(double degrees, double power) {
		this(degrees, power, -1);
	}
	
    public AutoTurn(double degrees, double power, double timeout) {
        requires(drivetrain);
        requires(navsensor);
        
        _degrees = degrees;
        
        if (timeout > 0) {
        	this.setTimeout(timeout);
        }
        
        //Sets up PID variables
        _pid = new PIDController(kP, kI, kD, navsensor, this);
        _pid.setAbsoluteTolerance(GYRO_TOLERANCE);
        _pid.setInputRange(-180.0, 180.0);
        _pid.setOutputRange(-power, power);
        _pid.setContinuous(true);
    }

    //Sets the target then start PID
    protected void initialize() {
    	navsensor.zeroGyro();
    	Timer.delay(0.5);
    	drivetrain.resetEncoders();
    	_pid.reset();
    	_pid.setSetpoint(_degrees);
    	_pid.enable();
    	
    	_targetCounter = drivetrain.getTargetCounter();
    	_targetCounter.reset();
    }
   
    protected void execute() {
    	SmartDashboard.putNumber("Heading", navsensor.getGyro());
    	SmartDashboard.putNumber("Drive setpoint", _pid.getSetpoint());
    	SmartDashboard.putNumber("Drive Turn error", _pid.getError());
    	
    	SmartDashboard.putNumber("Drivetrain position", drivetrain.getLeftEncoder());
    	SmartDashboard.putNumber("Drivetrain counter", drivetrain.getTargetCounter().getCount());
    }
 
    //Finishes When On target or Timed Out
    protected boolean isFinished() {
        return _targetCounter.onTarget(_pid.getError(), 1.5) || this.isTimedOut();
    }

    protected void end() {
    	_pid.disable();
    	drivetrain.setTank(0, 0);
    }

    protected void interrupted() {
    	System.err.println("AutoTurn interrupted!");
    	end();
    }

	@Override
	public void pidWrite(double power) {
		drivetrain.setVelocity(-power, power);
	}
}
