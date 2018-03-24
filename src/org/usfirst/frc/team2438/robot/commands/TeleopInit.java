package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

/**
 *
 */
public class TeleopInit extends CommandBase {

    public TeleopInit() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(lift);
    	requires(intake);
    	requires(ramp);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//CommandBase.ramp.resetServos();
		
    	CommandBase.drivetrain.stop();
    	CommandBase.lift.stop();
    	CommandBase.intake.stop();

    	CommandBase.drivetrain.resetEncoders();
    	CommandBase.lift.resetEncoder();
    	CommandBase.intake.resetEncoder();
    			
    	CommandBase.intake.setArmPosition(Position.home.getArmPosition());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	this.end();
    }
}
