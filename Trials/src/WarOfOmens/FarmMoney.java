package WarOfOmens;

import java.awt.Robot;
import java.awt.event.KeyEvent;


public class FarmMoney {

	static Robot robot;
	static MyActions acc;
	static int gamesPlayed = 1;


	public static void main(String[] args) throws Exception {
		robot = new Robot();
		acc = new MyActions(robot);
		robot.delay(2000);

		while (true) {
			try {
				//play the game until intercepted
				openAndFarm();
			}
			catch (UserInterceptionException e) {
				e.printStackTrace();
			}
			//wait 10 mins and start over

			robot.delay(10000);
			//close Chrome:
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SHIFT);
			robot.keyPress(KeyEvent.VK_Q);
			robot.delay(200);
			robot.keyRelease(KeyEvent.VK_Q);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_SHIFT);

		}

	}


	static void openAndFarm() throws Exception {
		acc.openWoO();
		acc.getToCharacter("Theodox2");
		acc.startSinglePlayerGame("Apprentice");
		System.out.println("Starting game:");
		while (true) {
			acc.playGame();
			System.out.println("Games played " + gamesPlayed + "\r");
			acc.startPlayAgain();
			gamesPlayed++;
		}
	}

}
