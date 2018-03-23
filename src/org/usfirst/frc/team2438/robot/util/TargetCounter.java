package org.usfirst.frc.team2438.robot.util;

/**
 * Counts the number of times the motor controller is within the error threshold.
 * @author Iobotics
 */
public class TargetCounter {
	
	private static final int COUNTER_THRESHOLD = 5; // Number of successful counts
	private final double _errorThreshold;
	
	private int _targetCounter = 0;
	
	public TargetCounter(int errorThreshold) {
		if(errorThreshold <= 0) {
			throw new IllegalArgumentException("The error threshold is too low!");
		}
		
		_errorThreshold = errorThreshold;
	}
	
	public boolean onTarget(double error) {    	
    	return onTarget(error, _errorThreshold);
    }
	
	/**
	 * Checks if the error has settled within the threshold
	 * @param error
	 * @param errorThreshold
	 * @return status
	 */
	public boolean onTarget(double error, double errorThreshold) {
    	if(Math.abs(error) < errorThreshold) {
    		_targetCounter++;
    	} else {
    		_targetCounter = 0;
    	}
    	
    	return (_targetCounter > COUNTER_THRESHOLD);
    }
	
	/**
	 * Get the current number of successful counts
	 * @return _targetCounter
	 */
	public int getCount() {
		return _targetCounter;
	}
	
	/**
	 * Resets the target counter
	 */
	public void reset() {
		_targetCounter = 0;
	}
}
