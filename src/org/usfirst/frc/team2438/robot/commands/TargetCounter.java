package org.usfirst.frc.team2438.robot.commands;

public class TargetCounter {
	
	private static final int COUNTER_THRESHOLD = 25;
	
	private int targetCounter = 0;
	
	public boolean onTarget(double error, int errorThreshold) {
    	if(Math.abs(error) < errorThreshold) {
    		targetCounter++;
    	} else {
    		targetCounter = 0;
    	}
    	
    	return (targetCounter > COUNTER_THRESHOLD);
    }
	
	public void reset() {
		targetCounter = 0;
	}
}
