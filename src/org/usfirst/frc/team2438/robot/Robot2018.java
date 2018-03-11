package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.commands.auto.AutoTest;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot2018 extends IterativeRobot {
	Compressor 			   _compressor;
	PowerDistributionPanel _pdp;
	Preferences            _prefs;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		_pdp = new PowerDistributionPanel();
    	_pdp.clearStickyFaults();
    	
    	_compressor = new Compressor();
    	_compressor.clearAllPCMStickyFaults();
    	_compressor.start();
    	
    	_prefs = Preferences.getInstance();
		
		CommandBase.init();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
    	SmartDashboard.putData(Scheduler.getInstance());
    }
    
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
    	// pick auto command via program number //
    	//(new AutoInitRobot()).start();
		(new AutoTest()).start();
    }

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Left Velocity", CommandBase.drivetrain.getLeftVelocity());
		SmartDashboard.putNumber("Right Velocity", CommandBase.drivetrain.getRightVelocity());
		SmartDashboard.putNumber("Heading", CommandBase.navSensor.getGyro());
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().run();
		
		CommandBase.drivetrain.resetEncoders();
		CommandBase.lift.resetEncoder();
		CommandBase.intake.resetEncoder();
		CommandBase.intake.initSolenoids();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Lift Position", CommandBase.lift.getPosition());
		SmartDashboard.putNumber("Distance sensor", CommandBase.lift.getHeight());
		SmartDashboard.putNumber("Intake arm position", CommandBase.intake.getLiftPosition());
		SmartDashboard.putNumber("Intake Arm Current", CommandBase.intake.getLiftCurrent());
		SmartDashboard.putBoolean("Intake Limit Switch", CommandBase.intake.getLimitSwitch());
	}
}
