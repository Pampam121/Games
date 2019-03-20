package WarOfOmens;

import java.awt.Robot;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.naming.directory.InvalidAttributeValueException;

import WarOfOmens.InterceptionException.Interruption;

public class FarmMoney {

	static String gameLevel = Pictures.grandmaster;
	static String character = Pictures.theodox;

	public static void main(String[] args) throws Exception {
		robot = new Robot();
		acc = new TournamentActions(robot);
		robot.delay(2000);
//		tournament = false;

		while (true) {
			try {
				// play the game until interrupted
				openAndFarm();
			} catch (InterceptionException e) {
				printError(e);
				if (e.interruption == Interruption.USER) {
					return;
				}

			} catch (TimeoutException e) {
				printError(e);
			}
			// wait 10 mins and start over
			System.out.println("Waiting 10 mins and starting over");
			tournament = false;
			acc.setNewGame();
		}

	}

	static Robot robot;
	static TournamentActions acc;
	static int gamesPlayed = 0;
	static boolean tournament = true;

	static void openAndFarm() throws IOException, URISyntaxException, InterceptionException, TimeoutException,
			InvalidAttributeValueException {
		acc.openWoO();
		if (tournament) {
			acc.startTournament();
			while (tournament) {
				tournament = acc.sortTournament();
			}
		}

		acc.getToCharacter(character);
		acc.startSinglePlayerGame(gameLevel);
		System.out.println("Starting game:");
		while (true) {
			acc.playGame();
			gamesPlayed++;
			System.out.println("Games played " + gamesPlayed + "\r");
			acc.startPlayAgain();

		}
	}

	static void printError(Exception e) {
		System.out.println("Games played: " + gamesPlayed);
		System.out.println("Games won: " + acc.victory);
		System.out.println("Games lost: " + acc.defeat);
		System.out.println("Leveled up " + acc.levelUp + " times.");
		System.out.println("");
		e.printStackTrace();
	}

}
