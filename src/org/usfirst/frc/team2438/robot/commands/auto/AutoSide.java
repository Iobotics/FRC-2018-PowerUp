package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.ArmToPosition;
import org.usfirst.frc.team2438.robot.commands.LiftToPosition;
import org.usfirst.frc.team2438.robot.commands.OperateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoSide extends CommandGroup {
	
	private static enum Side {
		left,
		right
	}

    public AutoSide() {
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
    	addSequential(new AutoInit());
		
    	Side botSide = Side.right;
    	Side switchSide = Side.right;
    	Side scaleSide = Side.left;
    	
		int botSideCoefficient = 1;
		if (botSide == Side.left) {
			botSideCoefficient = -1;
		}
		
		if (switchSide == botSide) {
			addSequential(new AutoDriveStraight(168.0));

			//addParallel(new LiftToPosition(25000));
			addSequential(new AutoTurn(90.0 * botSideCoefficient));

			addSequential(new AutoDriveStraight(26.7));
		} else if (switchSide == null) {
			addSequential(new AutoDriveStraight(168.0));
		} else if (scaleSide == botSide){
			addSequential(new AutoDriveStraight(324.0));

			//addParallel(new LiftToPosition(48000));
			addSequential(new AutoTurn(90.0 * botSideCoefficient));

			addSequential(new AutoDriveStraight(10.7));

			//addSequential(new ArmToPosition(1000)); //practice bot only, not programming
			// bot
		}
		else{

			addSequential(new AutoDriveStraight(215.0));

			addSequential(new AutoDriveStraight(166.0));
			
			//addParallel(new LiftToPosition(25000));
			addSequential(new AutoTurn(90.0 * botSideCoefficient));

		}

		if (switchSide != null) {
			//addSequential(new OperateIntake(-1.0));
		}
    }
}
