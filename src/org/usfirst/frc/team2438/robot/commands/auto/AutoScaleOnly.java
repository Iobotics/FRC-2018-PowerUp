package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.ArmToPosition;
import org.usfirst.frc.team2438.robot.commands.LiftToPosition;
import org.usfirst.frc.team2438.robot.commands.OperateIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScaleOnly extends CommandGroup {

    public AutoScaleOnly() {
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
		
    	AutoSide botSide = AutoSide.left;
    	AutoSide scaleSide = AutoSide.left;
    	
		int botSideCoefficient = 1;
		if (botSide == AutoSide.left) {
			botSideCoefficient = -1;
		}

		if (scaleSide == botSide){
			addSequential(new AutoDriveStraight(324.0));

			//addParallel(new LiftToPosition(48000));
			addSequential(new AutoTurn(90.0 * botSideCoefficient));

			addSequential(new AutoDriveStraight(10.7));

			//addSequential(new ArmToPosition(1000)); //practice bot only, not programming bot
		}
		else{

			addSequential(new AutoDriveStraight(215.0));

			addSequential(new AutoDriveStraight(166.0));
			
			//addParallel(new LiftToPosition(48000));
			addSequential(new AutoTurn(-90.0 * botSideCoefficient));

			//addSequential(new ArmToPosition(1000)); //practice bot only, not programming bot
		}

	addSequential(new OperateIntake(-1.0));
    }
}
