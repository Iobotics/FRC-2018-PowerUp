package org.usfirst.frc.team2438.robot.util;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team2438.robot.commands.auto.AutoSide;

/**
 * Store and process the field data
 * @author Iobotics
 */
public class GameData {
	
	private String _rawData = "SSS";
	private List<AutoSide> _plateSides;
	
	public GameData(String data) {
		// Ignore input if invalid
		if(data != null && data.length() == 3) {
			_rawData = data;
		}
		
		_plateSides = new ArrayList<AutoSide>();
		
		this.processData();
	}
	
	/**
	 * Processes the data string and stores the data in a List
	 */
	private void processData() {
		for(char side : _rawData.toCharArray()) {	
			if(side == 'L') {
				_plateSides.add(AutoSide.left);
			}
			else if(side == 'R') {
				_plateSides.add(AutoSide.right);
			}
			else {
				_plateSides.add(AutoSide.unknown);
			}
		}
	}
	
	/**
	 * Gets the raw data string
	 * @return rawData
	 */
	public String getRawData() {
		return _rawData;
	}
	
	/**
	 * Gets the List of plate sides
	 * @return sides
	 */
	public List<AutoSide> getPlateSides() {
		return _plateSides;
	}
	
	/**
	 * Gets the side of the switch closest to the driver station
	 * @return side
	 */
	public AutoSide getCloseSwitch() {
		return _plateSides.get(0);
	}
	
	/**
	 * Gets the side of the scale
	 * @return side
	 */
	public AutoSide getScale() {
		return _plateSides.get(1);
	}
	
	/**
	 * Gets the side of the switch farthest from the driver station
	 * @return side
	 */
	public AutoSide getFarSwitch() {
		return _plateSides.get(2);
	}
}
