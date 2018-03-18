package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.ArmToPosition;
import org.usfirst.frc.team2438.robot.commands.LiftAndArmToPos;
import org.usfirst.frc.team2438.robot.commands.LiftToPosition;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

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
    	
    	addParallel(new AutoDriveStraight(156));
    	
    	//addSequential(new ArmToPosition(465));
    	//addSequential(new LiftToPosition(19820));
    	
    	addSequential(new LiftAndArmToPos(Position.autoSwitch));
    	
    	addSequential(new AutoTurn(-90));
    	
    	addSequential(new AutoShoot());
    	
    	addSequential(new AutoDriveStraight(-12));
    	
    	//addSequential(new LiftToPosition(0));
    	//addSequential(new ArmToPosition(0));
    	
    	addSequential(new LiftAndArmToPos(Position.home));
    	
    	/*addParallel(new LiftToPosition(51000, 3));
    	addParallel(new ArmToPosition(1000, 3));
    	addSequential(new AutoTurn (90));
    	addSequential(new AutoShoot());
    	addSequential(new LiftToPosition(0, 3));
    	addSequential(new ArmToPosition(0, 3));*/
    }
}
