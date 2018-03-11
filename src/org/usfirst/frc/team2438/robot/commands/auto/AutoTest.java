package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.ArmToPosition;
import org.usfirst.frc.team2438.robot.commands.LiftToPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoTest extends CommandGroup {

    public AutoTest() {
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
    	
    	//addSequential(new AutoDriveStraight(210));
    	//addParallel(new LiftToPosition(22894));
    	//addSequential(new AutoTurn(70, 0.1));
    	//addSequential(new ArmToPosition(578));
    	//addSequential(new AutoShoot());
    	
    	addSequential(new AutoDriveStraight(48));
    	addSequential(new AutoTurn(-90, 0.1));
    	
    	/*addSequential(new AutoDriveStraight(48));
    	addSequential(new AutoTurn(-90, 0.1));
    	addSequential(new AutoDriveStraight(48));
    	addSequential(new AutoTurn(-90, 0.1));
    	addSequential(new AutoDriveStraight(48));
    	addSequential(new AutoTurn(-90, 0.1));
    	addSequential(new AutoDriveStraight(48));
    	addSequential(new AutoTurn(-90, 0.1));*/
    }
}
