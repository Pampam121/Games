package WarOfOmens;

import java.awt.Robot;

public class Tournament {

	static Robot robot;
	static MyActions acc;

	public static void main(String[] args) throws Exception {
		robot = new Robot();
		acc = new MyActions(robot);
		robot.delay(2000);
		
		acc.openWoO();
		

	}

}
