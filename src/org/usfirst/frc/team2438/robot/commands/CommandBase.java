package org.usfirst.frc.team2438.robot.commands;

import org.usfirst.frc.team2438.robot.OI;
import org.usfirst.frc.team2438.robot.subsystems.Camera;
import org.usfirst.frc.team2438.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2438.robot.subsystems.Intake;
import org.usfirst.frc.team2438.robot.subsystems.Lift;
import org.usfirst.frc.team2438.robot.subsystems.NavigationSensor;
import org.usfirst.frc.team2438.robot.subsystems.Ramp;
import org.usfirst.frc.team2438.robot.subsystems.LEDSystem;


import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command {

	public static OI oi;
    // Create a single static instance of all of your subsystems
	public static final NavigationSensor navsensor = new NavigationSensor();
	public static final Drivetrain drivetrain 	   = new Drivetrain();
	public static final LEDSystem ledSystem = new LEDSystem();
	public static final Intake intake = new Intake();
	public static final Camera camera = new Camera();
	public static final Lift lift = new Lift();
	public static final Ramp ramp = new Ramp();
	
	public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        navsensor.init();
        drivetrain.init();
        //ledSystem.init();
        intake.init();
        camera.init();
        lift.init();
        //ramp.init();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
