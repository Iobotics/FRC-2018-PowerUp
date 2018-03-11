package org.usfirst.frc.team2438.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
    	
    	/*addSequential(new AutoDriveStraight(0.0));
    	
    	double angle = 90.0;
    	if(side == right) {
    		angle *= -1;
    	}
    	
    	addSequential(new AutoTurn(angle));
    	
    	addSequential(new AutoDriveStraight(0.0));
    	
    	angle *= -1;
    	addSequential(new AutoTurn(angle));
    	
    	addParallel(new RaiseLift());
    	addSequential(new AutoDriveStraight(0.0));
    	
    	if(side != null) {
    		addSequential(new OuttakeBox());
    	} else {
    		this.end();
    	}*/
    }
}
