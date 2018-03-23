package org.usfirst.frc.team2438.robot.commands.auto;

import org.usfirst.frc.team2438.robot.Robot2018;
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
    	
    	AutoSide _switchSide = Robot2018.getGameData().getCloseSwitch();
    	AutoSide _robotSide = Robot2018.getSide();
    	
    	addParallel(new AutoDriveStraight(156));
    	
    	if(_robotSide == AutoSide.right && _switchSide == AutoSide.right) {
    		addSequential(new AutoTurn(-90));
    	} else if(_robotSide == AutoSide.left && _switchSide == AutoSide.left) {
    		addSequential(new AutoTurn(90));
    	} else {
    		return;
    	}
    	
    	addSequential(new LiftAndArmToPos(Position.autoSwitch));
    	
    	addSequential(new WaitCommand(1));
    	
    	addSequential(new AutoDriveStraight(16));
    	
    	addSequential(new AutoOuttake(0.28));
		addSequential(new WaitCommand(2.0));
		addSequential(new AutoOuttake(0));
    	
    	addSequential(new AutoDriveStraight(-20));
    	
    	addSequential(new LiftAndArmToPos(Position.home));
    }
}
