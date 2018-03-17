package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.LiftToPosition;
import org.usfirst.frc.team2438.robot.commands.OperateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCenter extends CommandGroup {

	public static enum Side {
		left,
		right
	}
	
    public AutoCenter() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	addSequential(new AutoDriveStraight(0.0));
    	
		Side side = Side.right;
		double distance = 0;
    	double angle = 90.0;
    	if(side == Side.right) {
    		angle *= -1;
    		distance = 29;
    	}
    	else {
    		distance = 130;
    	}

    	
    	addSequential(new AutoTurn(angle));
    	
    	addSequential(new AutoDriveStraight(distance));
    	
    	angle *= -1;
    	addSequential(new AutoTurn(angle));
    	
    	addParallel(new LiftToPosition(25000));
    	addSequential(new AutoDriveStraight(20.0));
    	
    	if(side != null) {
    		addSequential(new OperateIntake(-1.0));
    	} else {
    		this.end();
    	}
    }
}
