package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.ArmToPosition;
import org.usfirst.frc.team2438.robot.commands.LiftToPosition;
import org.usfirst.frc.team2438.robot.commands.ResetEncoders;

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
    	
    	System.out.println("Resetting encoders...");
    	addSequential(new ResetEncoders());
    	System.out.println("Done!");
    	addSequential(new ArmToPosition(0));
    	addSequential(new LiftToPosition(5000));
    	//addSequential(new AutoToBottom());
    	//addSequential(new ArmToPosition(-1280));
    }
}
