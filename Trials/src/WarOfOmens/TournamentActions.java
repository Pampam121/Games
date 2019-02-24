package WarOfOmens;

import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TournamentActions extends GameActions {

	public TournamentActions(Robot rob) {
		super(rob);
	}

	public void startTournament() throws InterceptionException, TimeoutException {
		waitAndClickPicture(Pictures.tournament);
	}

	public void playTournament() throws InterceptionException, TimeoutException {

		for (int turn = 0; turn < 200; turn++) {

			if (waitForPictures(Pictures.victory, Pictures.defeat, Pictures.levelUp,
					Pictures.endTurnTournament) == Pictures.endTurnTournament) {
				clickPicture(Pictures.endTurnTournament);
				continue;
			} else {
				return;
			}

		}
		throw new TimeoutException("Game lasted longer than 200 turns!");

	}

	public boolean sortTournament() throws InterceptionException, TimeoutException {
		List<String> tickets = new ArrayList<String>();
//		tickets.add(Pictures._0Ticket);
		tickets.add(Pictures._1Ticket);
//		tickets.add(Pictures._2Ticket);
//		tickets.add(Pictures._3Ticket);
		tickets.add(Pictures._4Ticket);
		tickets.add(Pictures._5Ticket);
		tickets.add(Pictures._6Ticket);

		List<String> tourneyPacks = new ArrayList<String>();
		tourneyPacks.add(Pictures.heroPack);
		tourneyPacks.add(Pictures._4Pack);
		tourneyPacks.add(Pictures._5Pack);
		tourneyPacks.add(Pictures._6Pack);

		List<String> goldPacks = new ArrayList<String>();
		goldPacks.add(Pictures.oakPack);
		goldPacks.add(Pictures.silverPack);
//		goldPacks.add(Pictures.goldPack);

		List<String> tournamentPictures = new ArrayList<String>();
		tournamentPictures.add(Pictures.continueButton);
		tournamentPictures.addAll(goldPacks);
		tournamentPictures.addAll(tourneyPacks);
		tournamentPictures.add(Pictures.enterTournament);
		tournamentPictures.add(Pictures.ready);
		tournamentPictures.add(Pictures.levelUp);
		tournamentPictures.add(Pictures.concede);
		tournamentPictures.add(Pictures.endTurnTournament);

		String whereWeAre = waitForPictures(tournamentPictures.toArray(new String[tournamentPictures.size()]));

		// if it's the main page check the number of tickets, and if too few, exit, else
		// enter
		if (whereWeAre == Pictures.enterTournament) {
//			throw new UserInterceptionException();
			int origtimeout = timeOut;
			try {
				timeOut = 900;
				waitForPictures(0.9, tickets.toArray(new String[tickets.size()]));
				robot.mouseWheel(-1);
				wait(1000);
				clickPicture(Pictures.newsX);
				wait(1000);
				robot.mouseWheel(1);
				wait(1000);
				return false;
			} catch (TimeoutException e) {
				clickPicture(whereWeAre);
				wait(2000);
			} finally {
				timeOut = origtimeout;
			}

		}
		// if it's the deck-making place
		else if (tourneyPacks.contains(whereWeAre)) {
			Point packpp = findImageLocation(Pictures.heroPack, 0.9);
			if (packpp != null) {
				clickCoord(packpp);
				wait(5000);
				clickCoord(packpp.x + 50, packpp.y);
			}
			packpp = findImageLocation(Pictures._4Pack, 0.9);
			if (packpp != null) {
				clickCoord(packpp);
				wait(5000);
				clickCoord(packpp.x - 200, packpp.y);
			}

			packpp = findImageLocation(Pictures._5Pack, 0.9);
			if (packpp != null) {
				clickCoord(packpp);
				wait(5000);
				clickCoord(packpp.x - 300, packpp.y - 100);
			}

			packpp = findImageLocation(Pictures._6Pack, 0.9);
			if (packpp != null) {
				clickCoord(packpp);
				wait(5000);
				clickCoord(packpp.x - 450, packpp.y - 100);
			}
		}
		// if it's the pack-opening page at the end
		else if (goldPacks.contains(whereWeAre)) {
			openPack(whereWeAre);
		}
		// otherwise just click whatever comes up
		else {
			clickPicture(whereWeAre);
		}
		return true;
	}

	private void openPack(String pack) throws InterceptionException, TimeoutException {
		Point pp = findImageLocation(pack);
		clickCoord(pp);
		wait(5000);
		clickCoord(pp.x, pp.y - 30);
		waitAndClickPicture(Pictures.continueButton);
		waitAndClickPicture(Pictures.playAgainTournament);

	}

}
