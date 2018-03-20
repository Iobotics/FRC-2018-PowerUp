package org.usfirst.frc.team2438.robot.util;

public class TargetCounter {
	
	private static final int COUNTER_THRESHOLD = 5;
	
	private int _targetCounter = 0;
	private final double _errorThreshold;
	
	public TargetCounter(int errorThreshold) {
		if(errorThreshold <= 0) {
			throw new IllegalArgumentException("The error threshold is too low!");
		}
		
		_errorThreshold = errorThreshold;
	}
	
	public boolean onTarget(double error) {    	
    	return onTarget(error, _errorThreshold);
    }
	
	public boolean onTarget(double error, double errorThreshold) {
    	if(Math.abs(error) < errorThreshold) {
    		_targetCounter++;
    	} else {
    		_targetCounter = 0;
    	}
    	
    	return (_targetCounter > COUNTER_THRESHOLD);
    }
	
	public int getCount() {
		return _targetCounter;
	}
	
	public void reset() {
		_targetCounter = 0;
	}
}
