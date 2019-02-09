package WarOfOmens;

import java.awt.Robot;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.naming.directory.InvalidAttributeValueException;


public class FarmMoney {

	static String gameLevel = Pictures.journeyman;
	static String character = Pictures.gretta;


	public static void main(String[] args) throws Exception {
		robot = new Robot();
		acc = new MyActions(robot);
		robot.delay(2000);

		while (true) {
			try {
				//play the game until interrupted
				openAndFarm();
			}
			catch (InterceptionException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.out.println("Waiting 10 mins and starting over");
			}
			catch (TimeoutException e) {
				e.printStackTrace();
				System.out.println("Waiting 10 mins and startin over");
			}
			//wait 10 mins and start over
			acc.setNewGame();
		}

	}

	static Robot robot;
	static MyActions acc;
	static int gamesPlayed = 1;


	static void openAndFarm() throws IOException, URISyntaxException, InterceptionException, TimeoutException, InvalidAttributeValueException {
		acc.openWoO();
		acc.getToCharacter(character);
		acc.startSinglePlayerGame(gameLevel);
		System.out.println("Starting game:");
		while (true) {
			acc.playGame();
			System.out.println("Games played " + gamesPlayed + "\r");
			acc.startPlayAgain();
			gamesPlayed++;
		}
	}

}
