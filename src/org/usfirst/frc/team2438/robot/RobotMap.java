package org.usfirst.frc.team2438.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Talons //
	public static final int frontLeftMotor  = 1;
	public static final int frontRightMotor = 2;
	public static final int backLeftMotor   = 3;
	public static final int backRightMotor  = 4;
	
	public static final int frontLeftLift  = 5;
	public static final int frontRightLift = 6;
	public static final int backLeftLift   = 7;
	public static final int backRightLift  = 8;
	
	public static final int leftIntake  = 9;
	public static final int	rightIntake = 10;
	
	public static final int intakeArm = 11;
	
	// DIO //
	public static final int intakeLimitSwitch = 0;
	public static final int liftLimitSwitch   = 1;
	
	// Pneumatics //
	public static final int intakeForwardChannel = 0;
	public static final int intakeReverseChannel = 1;
	
	// Analog //
	public static final int distanceSensor = 0;
	
	// PWM //
	public static final int leftServo  = 0;
	public static final int rightServo = 1;	
}
