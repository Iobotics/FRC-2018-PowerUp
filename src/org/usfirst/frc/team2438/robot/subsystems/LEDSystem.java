package org.usfirst.frc.team2438.robot.subsystems;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * LED System
 */
// FIXME - Fix communication
public class LEDSystem extends Subsystem {

    private SerialPort _leds;
    private ByteBuffer _buffer;
    
    public void init() {
    	// Initialize the serial communication port
    	_leds = new SerialPort(9600, Port.kUSB);
    	_leds.reset();
    	
    	// Initialize a ByteBuffer for streaming data
    	_buffer = ByteBuffer.allocate(4);
    }
    
    /**
     * Enables a given number of LEDs
     * @param numLights
     */
    public void enableLights(int numLights) {
    	// Converts an int to a byte array and sends it over serial
    	_leds.write(_buffer.putInt(numLights).array(), _buffer.capacity());
    }
    
    /**
     * Enables teleop lighting mode
     */
    public void enableTeleop() {
    	_leds.writeString("A");
    }
    
    /**
     * Turns off the LED system
     */
    public void stop() {
    	_leds.writeString("0");
    	_leds.reset();
    }

    public void initDefaultCommand() {
        //setDefaultCommand(new OperateLights());
    	setDefaultCommand(null);
    }
}

