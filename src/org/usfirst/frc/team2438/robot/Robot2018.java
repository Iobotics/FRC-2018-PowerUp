package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.commands.auto.AutoTurn;
import org.usfirst.frc.team2438.robot.commands.auto.DriveForwardMotionMagic;

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
		CommandBase.drivetrain.resetEncoders();
		int autonum = _prefs.getInt("auto-program-number", 0);
    	SmartDashboard.putNumber("auto-num", autonum);
    	// pick auto command via program number //
    	//(new AutoDriveStraight(5)).start();
    	(new AutoTurn(90,0.5,5)).start();
    }

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		//SmartDashboard.putNumber("Drive Error Left", CommandBase.drivetrain.getLeftError());	
		//SmartDashboard.putNumber("Drive Error Right", CommandBase.drivetrain.getRightError());
		SmartDashboard.putNumber("Heading", CommandBase.navSensor.getGyro());

	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().run();
		
		CommandBase.drivetrain.resetEncoders();
		CommandBase.lift.resetEncoder();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putString("Drive Mode", CommandBase.drivetrain.getDriveMode().toString());
		
		SmartDashboard.putNumber("Left Drive Encoder Position", CommandBase.drivetrain.getLeftEncoder());
		SmartDashboard.putNumber("Right Drive Encoder Position", CommandBase.drivetrain.getRightEncoder());
		
		SmartDashboard.putNumber("Drive Error", CommandBase.drivetrain.getLeftError());
	}
}
