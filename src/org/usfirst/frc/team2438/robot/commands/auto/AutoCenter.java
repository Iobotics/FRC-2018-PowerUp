package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.Robot2018;
import org.usfirst.frc.team2438.robot.commands.LiftAndArmToPos;
import org.usfirst.frc.team2438.robot.commands.ResetEncoders;
import org.usfirst.frc.team2438.robot.commands.intake.ArmToPosition;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class AutoCenter extends CommandGroup {

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
    	
    	AutoSide switchSide = Robot2018.getGameData().getCloseSwitch();
    	
    	int autoSide = 1;
    	
    	if(switchSide == AutoSide.left) {
    		autoSide = -1;
    	}
    	
    	addSequential(new ResetEncoders());
    	
    	addSequential(new ArmToPosition(-1400));
    	addSequential(new WaitCommand(2));
    	
    	addSequential(new ResetEncoders());
    	
    	addSequential(new LiftAndArmToPos(Position.autoSwitch));
    	
    	addSequential(new AutoDriveStraight(39));
    	
    	addSequential(new AutoTurn(90 * autoSide));
    	addSequential(new WaitCommand(0.5));
    	
    	addSequential(new AutoDriveStraight(55));
    	addSequential(new WaitCommand(0.5));
    	
    	addSequential(new AutoTurn(-90 * autoSide));
    	addSequential(new WaitCommand(0.5));
    	
    	addSequential(new AutoDriveStraight(74));
    	
    	addSequential(new AutoOuttake(2));
    	
    	addSequential(new AutoDriveStraight(-14));
    	
    	addSequential(new LiftAndArmToPos(Position.home));
    }
}
