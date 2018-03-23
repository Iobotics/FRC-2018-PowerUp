package org.usfirst.frc.team2438.robot.commands.auto;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public enum AutoSide implements Sendable {
	left,
	right,
	unknown;
	
	private String _name = "";
	private String _subsystem = "Ungrouped";

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public String getSubsystem() {
		return _subsystem;
	}

	@Override
	public void setSubsystem(String subsystem) {
		_subsystem = subsystem;
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("AutoSide");
	}
}
