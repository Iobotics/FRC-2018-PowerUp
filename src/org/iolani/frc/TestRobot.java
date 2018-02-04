package org.iolani.frc;

import org.iolani.frc.commands.CommandBase;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestRobot extends IterativeRobot {
	PowerDistributionPanel _pdp;
	Preferences _prefs;
	
	public void robotInit(){
		_pdp = new PowerDistributionPanel();
    	
    	_pdp.clearStickyFaults();
    	
    	_prefs = Preferences.getInstance();
	}
	
	public void disabledInit() {
    	SmartDashboard.putData(Scheduler.getInstance());
    }
    
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		int autonum = _prefs.getInt("auto-program-number", 0);
    	SmartDashboard.putNumber("auto-num", autonum);
    	
    	this.debugStuff();
	}

    private void debugStuff() {
		// TODO Auto-generated method stub
		
	}

	public void autonomousInit() {
    	int autonum = _prefs.getInt("auto-program-number", 0);
    	SmartDashboard.putNumber("auto-num", autonum);
    }
 }
