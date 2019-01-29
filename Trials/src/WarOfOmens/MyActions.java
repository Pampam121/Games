package WarOfOmens;

import java.awt.Desktop;
import java.awt.Robot;
import java.net.URI;

public class MyActions extends BasicActions {

	public MyActions(Robot rob) {
		super(rob);
	}


	public void openWoO() throws Exception {
		//open WarOfOmens in Chrome
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI("https://www.kongregate.com/games/FifthColumnGames/war-of-omens"));
		}
		checkUserIntervention();

		// scroll down
		clickPicture("WoOinKong");
		robot.mouseWheel(3);
		checkUserIntervention();

	}


	public void getToTheodox() throws Exception {
		int counter = 0;
		while (findImageLocation("Theodox") == null) {
			clickPicture("LeftArrow");
			wait(3000);
			counter ++;
			if (counter > 24){
				throw new Exception("Theodox not found!");
			}
		}

	}


	public void startApprenticeGame() throws Exception {
		clickPicture("SinglePlayer");
		wait(3000);
		clickPicture("Apprentice");
		waitForPictures("ClickToContinue");
		clickPicture("ClickToContinue");

	}


	void clickCoin() throws Exception {
		int x = 0;
		int y = 0;
		//TODO
		clickCoord(x, y);
	}


	void clickAllShop() throws Exception {
		int x = 0;
		int y = 0;
		//TODO
		clickCoord(x, y);
		wait(500);
		//TODO
		clickCoord(x, y);
		wait(500);
		//TODO
		clickCoord(x, y);
		wait(500);
		//TODO
		clickCoord(x, y);
	}


	public void playGame() throws Exception {

		waitForPictures("EndTurn");
		clickCoin();

		while (true) {

			clickCoin();
			wait(500);
			clickCoin();
			wait(500);
			clickCoin();
			wait(1000);

			clickAllShop();
			wait(3000);

			try {
				clickPicture("EndTurn");
			}
			catch (Exception e) {
				break;
			}
			wait(5000);
			waitForPictures("EndTurn");

		}

		waitForGameEnd();

	}


	void waitForGameEnd() throws Exception {
		String levelUp = "";
		waitForPictures("PlayAgain", levelUp);
		if (findImageLocation(levelUp) != null) {
			clickPicture(levelUp);
		}
		waitForPictures("PlayAgain");
	}


	public void clickPlayAgain() throws Exception {
		clickPicture("PlayAgain");
		waitForPictures("ClickToContinue");
		clickPicture("ClickToContinue");
	}

}
