package org.iolani.frc.commands;

import org.iolani.frc.OI;
import org.iolani.frc.subsystems.Drivetrain;
import org.iolani.frc.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    public static final Drivetrain drivetrain = new Drivetrain();
    public static final Lift lift = new Lift();

    // Called just before this Command runs the first time
    protected void initialize() {
    	oi = new OI();
    	drivetrain.init();
    	lift.init();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
