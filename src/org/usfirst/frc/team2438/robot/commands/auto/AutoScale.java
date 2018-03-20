package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.LiftAndArmToPos;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScale extends CommandGroup {

    public AutoScale() {
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
    	
    	//addSequential(new AutoInit());
		
    	AutoSide botSide = AutoSide.right;
    	AutoSide switchSide = AutoSide.left;
    	AutoSide scaleSide = AutoSide.right;
    	
    	double extraDistance = 0;
    	
    	addSequential(new AutoInit());
    	
		int botSideCoefficient = -1;
		if (botSide == AutoSide.left) {
			botSideCoefficient = 1;
		}
		
		if (switchSide == null) {
			addSequential(new AutoDriveStraight(168.0));
		}
		else if (switchSide == botSide) {
			extraDistance = 26.7;
			
			addParallel(new LiftAndArmToPos(Position.autoSwitch));
			addSequential(new AutoDriveStraight(168.0));

			addSequential(new AutoTurn(90.0 * botSideCoefficient));

			addSequential(new AutoDriveStraight(extraDistance));
		}
		else if (scaleSide == botSide){			
			addParallel(new LiftAndArmToPos(Position.autoScale));
			
			addSequential(new AutoDriveStraight(258.0));
			
			addSequential(new AutoTurn(30.0 * botSideCoefficient));
		}
		/*else {
			addSequential(new AutoDriveStraight(215.0));

			addSequential(new AutoDriveStraight(166.0));
			
			//addParallel(new LiftToPosition(25000));
			addSequential(new AutoTurn(90.0 * botSideCoefficient));
		}*/

		addSequential(new AutoShoot(1.0));
		addSequential(new LiftAndArmToPos(Position.home));
    }
}
