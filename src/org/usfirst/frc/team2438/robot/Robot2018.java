package org.usfirst.frc.team2438.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.commands.ResetEncoders;
import org.usfirst.frc.team2438.robot.commands.auto.AutoScale;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
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
	Compressor _compressor;
	PowerDistributionPanel _pdp;
	Preferences _prefs;
	UsbCamera _cameraOne;
	UsbCamera _cameraTwo;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		_pdp = new PowerDistributionPanel();
		_pdp.clearStickyFaults();

		_compressor = new Compressor();
		_compressor.clearAllPCMStickyFaults();
		_compressor.start();

		_prefs = Preferences.getInstance();
		
		new Thread(() -> {
			_cameraOne = CameraServer.getInstance().startAutomaticCapture();
			_cameraTwo = CameraServer.getInstance().startAutomaticCapture();
			_cameraOne.setVideoMode(PixelFormat.kYUYV, 320, 240, 10);
			_cameraTwo.setVideoMode(PixelFormat.kYUYV, 320, 240, 10);
            
            CvSink cvSink = CameraServer.getInstance().getVideo(_cameraTwo);
            CvSource outputStream = CameraServer.getInstance().putVideo("Flip", 640, 480);
            
            Mat source = new Mat();
            Mat output = new Mat();
            
            while(!Thread.interrupted()) {
                cvSink.grabFrame(source);
                Core.flip(source, output, 0);
                outputStream.putFrame(output);
            }
        }).start();
		
		CommandBase.init();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	public void disabledInit() {
		SmartDashboard.putData(Scheduler.getInstance());
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// pick auto command via program number //
		(new AutoScale()).start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Heading", CommandBase.navSensor.getGyro());
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().run();

		CommandBase.intake.initSolenoids();

		CommandBase.drivetrain.resetEncoders();
		CommandBase.lift.resetEncoder();
		CommandBase.intake.resetEncoder();
		
		CommandBase.intake.setPosition(Position.home.getArmPosition());
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// TODO - Cut down on unnecessary telemetry
		SmartDashboard.putNumber("Potentiometer", CommandBase.intake.getPotentiometer());
		SmartDashboard.putNumber("Lift Position", CommandBase.lift.getPosition());
		SmartDashboard.putNumber("Lift Error", CommandBase.lift.getError());
		SmartDashboard.putNumber("Intake arm position", CommandBase.intake.getPosition());
		SmartDashboard.putNumber("Intake Arm Current", CommandBase.intake.getArmCurrent());
		SmartDashboard.putNumber("Heading", CommandBase.navSensor.getGyro());
	}
}
