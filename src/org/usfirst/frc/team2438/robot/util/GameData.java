package org.usfirst.frc.team2438.robot.util;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team2438.robot.commands.auto.AutoSide;

public class GameData {
	
	private String _rawData = "SSS";
	private List<AutoSide> _sides;
	
	public GameData(String data) {
		if(data != null && data.length() == 3) {
			_rawData = data;
		}
		
		_sides = new ArrayList<AutoSide>();
		
		this.convertString();
	}
		
	private void convertString() {
		for(char side : _rawData.toCharArray()) {	
			if(side == 'L') {
				_sides.add(AutoSide.left);
			}
			else if(side == 'R') {
				_sides.add(AutoSide.right);
			}
			else {
				_sides.add(AutoSide.unknown);
			}
		}
	}
	
	public String getRawData() {
		return _rawData;
	}
	
	public List<AutoSide> getSides() {
		return _sides;
	}
	
	public AutoSide getCloseSwitch() {
		return _sides.get(0);
	}
	
	public AutoSide getScale() {
		return _sides.get(1);
	}
	
	public AutoSide getFarSwitch() {
		return _sides.get(1);
	}
}
