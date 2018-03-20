package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Ramp.RampPosition;
import org.usfirst.frc.team2438.robot.subsystems.Ramp.RampSide;

/**
 *
 */
public class DropRamp extends CommandBase {

	private RampSide side;
	
    public DropRamp(RampSide side) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(ramp);
    	
    	this.side = side;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(side == RampSide.left) {
    		ramp.toggleLeftRampPosition();
    	} else if(side == RampSide.right) {
    		ramp.toggleRightRampPosition();
    	}
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
