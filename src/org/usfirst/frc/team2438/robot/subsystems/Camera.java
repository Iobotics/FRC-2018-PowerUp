package org.usfirst.frc.team2438.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Camera
 */
public class Camera extends Subsystem {

	UsbCamera _camera;
	
	public void init() {
		new Thread(() -> {
			_camera = CameraServer.getInstance().startAutomaticCapture();
			_camera.setVideoMode(PixelFormat.kYUYV, 320, 240, 10);
        }).start();
	}

    public void initDefaultCommand() {
    	setDefaultCommand(null);
    }
}

