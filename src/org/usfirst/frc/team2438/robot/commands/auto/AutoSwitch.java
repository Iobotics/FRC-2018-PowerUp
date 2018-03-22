package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.LiftAndArmToPos;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoSwitch extends CommandGroup {

    public AutoSwitch() {
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
    	
    	addParallel(new AutoDriveStraight(156));
    	
    	addSequential(new LiftAndArmToPos(Position.autoSwitch));
    	
    	addSequential(new AutoTurn(-90));
    	
    	addSequential(new AutoDriveStraight(16));
    	
    	addSequential(new AutoOuttake(0.28));
		addSequential(new WaitCommand(2.0));
		addSequential(new AutoOuttake(0));
    	
    	addSequential(new AutoDriveStraight(-20));
    	
    	addSequential(new LiftAndArmToPos(Position.home));
    }
}
