package WarOfOmens;

import java.awt.Robot;

public class PlayPlace {

	static Robot robot;
	static TournamentActions acc;
	static int gamesPlayed = 0;
	
	public static void main(String[] args) throws Exception {
		robot = new Robot();
		acc = new TournamentActions(robot);
		robot.delay(2000);

		acc.moveCoord(220, 300);
		while (true) {
			acc.sortTournament();
//			acc.playTournament();
//			acc.startNewTournamentGame();
		}

	}

}
