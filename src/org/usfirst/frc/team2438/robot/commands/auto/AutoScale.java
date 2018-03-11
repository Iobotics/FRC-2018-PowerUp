package org.usfirst.frc.team2438.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScale extends CommandGroup {
	
	public static enum Side {
		left,
		right
	}

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
    	
		/*int botSideCoefficient = 1;
		if (botSide == Side.left) {
			botSideCoefficient = -1;
		} else {
			botSideCoefficient = 1;
		}

		if (switchSide == botSide) {
			addSequential(new AutoDriveStraight(168.0));

			addParallel(new RaiseLift());
			addSequential(new AutoTurn(90.0 * botSideConstant));

			addSequential(new AutoDriveStraight(26.7));
		} else if (switchSide == null) {
			addSequential(new AutoDriveStraight(168.0));
		} else {
			addSequential(new AutoDriveStraight(324.0));

			addParallel(new RaiseLift());
			addSequential(new AutoTurn(90.0 * botSideConstant));

			addSequential(new AutoDriveStraight(10.7));*/

			// addSequential(new RaiseIntakeLift()); //practice bot only, not programming
			// bot
		}

		/*if (switchSide != null) {
			addSequential(new Outtake());
		}*/
}
