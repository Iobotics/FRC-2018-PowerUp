package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LiftAndArmToPos extends CommandGroup {
	
    public LiftAndArmToPos(Position liftPos) {
    	
        addSequential(new ArmToPosition(Position.autoSwitch));
        
        addSequential(new LiftToPosition(liftPos));
        
        addSequential(new ArmToPosition(liftPos));
    }
}
