package org.usfirst.frc.team2438.robot.commands.intake;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.util.TargetCounter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Move the intake arm to a given position
 */
public class ArmToPosition extends CommandBase {
	
	private int _encoderPosition;
	private TargetCounter _targetCounter;
	
    public ArmToPosition(Position position) {
    	this(position.getArmPosition(), -1);
    }
    
    public ArmToPosition(Position position, double timeout) {
		this(position.getArmPosition(), timeout);
	}
    
    public ArmToPosition(int encoderPosition) {
		this(encoderPosition, -1);
	}
    
    public ArmToPosition(int encoderPosition, double timeout) {
    	requires(intake);
		
		_encoderPosition = encoderPosition;
		
		if(timeout > 0) {
			this.setTimeout(timeout);
		}
    }

    // Called just before this Command runs the first time
    protected void initialize() { 
    	_targetCounter = intake.getTargetCounter();
    	_targetCounter.reset();
    	
    	System.out.println("Set intake arm target to: " + _encoderPosition);
    	intake.setArmPosition(_encoderPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Arm Error", intake.getArmError());
    	SmartDashboard.putNumber("Arm Current", intake.getArmCurrent());
    	SmartDashboard.putNumber("Arm Counter", _targetCounter.getCount());
    	
    	intake.setArmPosition(_encoderPosition);
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return _targetCounter.onTarget(intake.getArmError()) || this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Intake arm finished at: " + intake.getArmEncoderPosition());
    	System.out.println();
    	_targetCounter.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.err.println("ArmToPosition was interrupted!");
    	end();
    }
}
