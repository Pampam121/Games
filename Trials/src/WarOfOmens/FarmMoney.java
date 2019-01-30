package WarOfOmens;

import java.awt.Robot;

public class FarmMoney {

	public static void main(String[] args) throws Exception {
		Robot robot = new Robot();
		MyActions acc = new MyActions(robot);

		//		acc.openWoO();
		//		acc.getToTheodox();
		acc.calculateShopsAndCoin();
		acc.startApprenticeGame();
		while (true) {
			acc.playGame();
			acc.clickPlayAgain();
		}

	}

}
