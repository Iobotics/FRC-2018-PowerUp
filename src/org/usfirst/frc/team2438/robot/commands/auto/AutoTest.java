package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.commands.ResetEncoders;
import org.usfirst.frc.team2438.robot.commands.intake.ArmToPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 * Reverse into the switch and lower the intake arm
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
    		
    	addSequential(new ResetEncoders());
    	
    	addSequential(new ArmToPosition(-1400));
    	addSequential(new WaitCommand(2));
    	
    	addSequential(new AutoDriveStraight(100));
    }
}
