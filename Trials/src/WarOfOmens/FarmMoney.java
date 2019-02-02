package WarOfOmens;

import java.awt.Robot;

public class FarmMoney {

	public static void main(String[] args) throws Exception {
		Robot robot = new Robot();
		MyActions acc = new MyActions(robot);
		robot.delay(2000);

		acc.openWoO();
		acc.getToTheodox();
		acc.calculateShopsAndCoin();
		acc.startApprenticeGame();
		System.out.println("Starting game:");
		int counter = 1;
		while (true) {
			acc.playGame();
			System.out.print("Games played... " + counter + "\r");
			acc.startPlayAgain();
			counter++;
		}

	}

}
