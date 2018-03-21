package org.usfirst.frc.team2438.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.commands.auto.AutoInit;
import org.usfirst.frc.team2438.robot.commands.auto.AutoSide;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.util.GameData;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
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
	
	private static AutoSide _side = AutoSide.left;
	
	public static GameData _gameData;
	
	String _rawData;
	
	Compressor _compressor;
	PowerDistributionPanel _pdp;
	Preferences _prefs;
	UsbCamera _camera;
	
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
			_camera = CameraServer.getInstance().startAutomaticCapture();
			_camera.setVideoMode(PixelFormat.kYUYV, 320, 240, 10);
			
            CvSink cvSink = CameraServer.getInstance().getVideo(_camera);
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
		
		_rawData = DriverStation.getInstance().getGameSpecificMessage();
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
		_gameData = new GameData(_rawData);
		
		(new AutoInit()).start();
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
		
		CommandBase.ramp.resetServos();

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
		SmartDashboard.putString("Intake Limit Switch", (CommandBase.intake.getLimitSwitch() ? "Detected" : "Not found"));
		SmartDashboard.putString("Lift Limit Switch", (CommandBase.lift.getLimitSwitch() ? "Detected" : "Not found"));
		SmartDashboard.putNumber("Heading", CommandBase.navSensor.getGyro());
	}
	
	public static AutoSide getSide() {
		return _side;
	}
	
	public static GameData getGameData() {
		return _gameData;
	}
}
