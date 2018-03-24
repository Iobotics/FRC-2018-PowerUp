package org.usfirst.frc.team2438.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * LED System
 */
public class LEDSystem extends Subsystem {
	
	public static final int I2C_ADDRESS = 0;	  // TODO - Choose an I2C address
	public static final int REGISTER_ADDRESS = 0; // TODO - Choose a register address

    private I2C _leds;
    
    public void init() {
    	// Initialize the I2C communication port
    	_leds = new I2C(Port.kOnboard, I2C_ADDRESS);
    }
    
    /**
     * Enables a given number of LEDs
     * @param numLights
     */
    public void enableLights(int numLights) {
    	_leds.write(REGISTER_ADDRESS, numLights);
    }
    
    /**
     * Enables teleop lighting mode
     */
    public void enableTeleop() {
    	// Convert the letter 'A' to an int and send it over I2C
    	_leds.write(I2C_ADDRESS, (new Integer('A').intValue()));
    }
    
    /**
     * Stops the LED system
     */
    public void stop() {
    	_leds.write(I2C_ADDRESS, 0);
    	_leds.free();
    }

    public void initDefaultCommand() {
        //setDefaultCommand(new OperateLights());
    	setDefaultCommand(null);
    }
}

