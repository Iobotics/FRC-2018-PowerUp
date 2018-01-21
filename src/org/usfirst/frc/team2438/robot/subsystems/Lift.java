package org.usfirst.frc.team2438.robot.subsystems;

import org.usfirst.frc.team2438.robot.RobotMap;
import org.usfirst.frc.team2438.robot.commands.OperateLift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

    private TalonSRX _lift;
    
    public void init() {
    	_lift = new TalonSRX(RobotMap.lift);
    }
	
	public void setPower(double power) {
		_lift.set(ControlMode.PercentOutput, power);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new OperateLift());
    }
}

