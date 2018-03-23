package org.usfirst.frc.team2438.robot;

import org.usfirst.frc.team2438.robot.commands.CalibrateNavigationSensor;
import org.usfirst.frc.team2438.robot.commands.CommandBase;
import org.usfirst.frc.team2438.robot.commands.auto.AutoSide;
import org.usfirst.frc.team2438.robot.commands.auto.AutoTest;
import org.usfirst.frc.team2438.robot.subsystems.Lift.Position;
import org.usfirst.frc.team2438.robot.util.GameData;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	
	private static GameData _gameData;
	
	String _rawData;
	SendableChooser<AutoSide> _chooser;
	
	Command _autoCommand;
	
	Compressor _compressor;
	PowerDistributionPanel _pdp;
	Preferences _prefs;
	
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
		
		_chooser = new SendableChooser<AutoSide>();
		_chooser.addDefault("Unknown", AutoSide.unknown);
		_chooser.addObject("Left", AutoSide.left);
		_chooser.addObject("Right", AutoSide.right);
		
		SmartDashboard.putData("Auto Side", _chooser);
		
		CommandBase.init();
		
		(new CalibrateNavigationSensor()).start();
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
		// Pick auto side via a SendableChooser //
		//_side = _chooser.getSelected();
		_rawData = "LLR";
		_side = AutoSide.left;
		
		_gameData = new GameData(_rawData);
				
		_autoCommand = new AutoTest();
		_autoCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putString("Auto Side Data", _chooser.getSelected().toString());
		SmartDashboard.putString("Auto Data", _rawData);
	}

	@Override
	public void teleopInit() {
		
		if (_autoCommand != null) {
			_autoCommand.cancel();
		}
		
		//CommandBase.ramp.resetServos();
		
		CommandBase.drivetrain.stop();
		CommandBase.lift.stop();
		CommandBase.intake.stop();

		CommandBase.drivetrain.resetEncoders();
		CommandBase.lift.resetEncoder();
		CommandBase.intake.resetEncoder();
		
		CommandBase.intake.setArmPosition(Position.home.getArmPosition());
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// TODO - Cut down on unnecessary telemetry
		//SmartDashboard.putString("Intake Limit Switch", (CommandBase.intake.getLimitSwitch() ? "Detected" : "Not found"));
		//SmartDashboard.putString("Lift Limit Switch", (CommandBase.lift.getLimitSwitch() ? "Detected" : "Not found"));
		
		this.debug();
	}
	
	public static AutoSide getSide() {
		return _side;
	}
	
	public static GameData getGameData() {
		return _gameData;
	}
	
	private void debug() {
		//CommandBase.navSensor.debug();
		/*CommandBase.drivetrain.debug();
        //CommandBase.ledSystem.debug();
		CommandBase.intake.debug();
		CommandBase.camera.debug();
		CommandBase.lift.debug();
		CommandBase.ramp.debug();*/
	}
}
