package WarOfOmens;

import java.awt.Desktop;
import java.awt.Point;
import java.awt.Robot;
import java.net.URI;


public class MyActions extends BasicActions {

	private Point coin;
	private Point shop1;
	private Point shop2;
	private Point shop3;
	private Point shop4;


	public MyActions(Robot rob) {
		super(rob);
	}


	public void openWoO() throws Exception {
		//open WarOfOmens in Chrome
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI("https://www.kongregate.com/games/FifthColumnGames/war-of-omens"));
		}
		System.out.println("Game opened");
		checkUserIntervention();

		// scroll down
		waitAndClickPicture("WoOinKong");
		robot.mouseWheel(4);
		System.out.println("Scrolled down");
		checkUserIntervention();

		getToMainScreen();

	}


	void getToMainScreen() throws Exception {
		final String javaPermissonNeed = "JavaDownload";
		final String newsPopUp = "";
		final String questPopUp = "";
		final String weekendPopUp = "";
		final String homeScreen = "LeftArrow2";

		String loadedScreenType = waitForPictures(javaPermissonNeed, newsPopUp, questPopUp, weekendPopUp, homeScreen);

		switch (loadedScreenType) {
			case javaPermissonNeed:
				clickPicture(javaPermissonNeed);
				wait(2000);
				clickPicture("JavaEngedely");
				wait(1000);
				System.out.println("Permission given to Java");
				getToMainScreen();

			case questPopUp:
				clickPicture("Collect");
				wait(2000);
				clickPicture(questPopUp);
				wait(1000);
				System.out.println("Daily collected");
				getToMainScreen();

			case homeScreen:
				break;

			default:
				clickPicture(loadedScreenType);
				wait(1000);
				System.out.println(loadedScreenType + " closed");
				getToMainScreen();
		}
	}


	public void getToCharacter(String characterName) throws Exception {
		int counter = 0;
		System.out.print("Looking for " + characterName);
		while (findImageLocation(characterName) == null) {
			waitAndClickPicture("LeftArrow");
			wait(3000);
			counter++;
			System.out.print(counter + " ");
			if (counter > 32) {
				throw new Exception(characterName + " not found!");
			}
		}
		System.out.println("Found in " + counter + " tries!");

		calculateShopsAndCoin(characterName);

	}


	void calculateShopsAndCoin(String characterName) throws Exception {
		Point theo = findImageLocation(characterName); //(2900, 890)
		Point arr = findImageLocation("LeftArrow2"); //(3090, 720)
		Point sp = findImageLocation("SinglePlayer2"); //(2450, 380)

		coin = calcCoord(theo, arr, sp, "Coins"); //(2700, 800)
		shop1 = calcCoord(theo, arr, sp, "Shop1"); //(2150, 500)
		shop2 = calcCoord(theo, arr, sp, "Shop2"); //(2150, 650)
		shop3 = calcCoord(theo, arr, sp, "Shop3"); //(2150, 800)
		shop4 = calcCoord(theo, arr, sp, "Shop4"); //(2150, 950)
	}


	private Point calcCoord(Point theo, Point arr, Point sp, String place) throws Exception {
		double px = 800. / 1170.;
		double qx = 990. / 1170.;
		double rx = 350. / 1170.;
		double sx;

		double py = 740. / 880.;
		double qy = 570. / 880.;
		double ry = 230. / 880.;
		double sy;

		switch (place) {
			case "Coins":
				sx = 600. / 1170.;
				sy = 650. / 880.;
				break;

			case "Shop1":
				sx = 50. / 1170.;
				sy = 350. / 880.;
				break;

			case "Shop2":
				sx = 50. / 1170.;
				sy = 500. / 880.;
				break;

			case "Shop3":
				sx = 50. / 1170.;
				sy = 650. / 880.;
				break;

			case "Shop4":
				sx = 50. / 1170.;
				sy = 800. / 880.;
				break;

			default:
				throw new Exception("Invalid argument in Coordinate Calculation!");

		}

		double nx = (theo.getX() - arr.getX()) * (sx - rx) / (px - qx) + sp.getX();
		double ny = (theo.getY() - arr.getY()) * (sy - ry) / (py - qy) + sp.getY();

		return new Point((int) nx, (int) ny);

	}


	public void startSinglePlayerGame(String gameLevel) throws Exception {
		waitAndClickPicture("SinglePlayer");
		waitAndClickPicture(gameLevel);
		waitAndClickPicture("ClickToContinue");

	}


	void clickCoin() throws Exception {
		clickCoord(coin);
	}


	void clickAllShop() throws Exception {

		clickCoord(shop1);
		wait(500);

		clickCoord(shop2);
		wait(500);

		clickCoord(shop3);
		wait(500);

		clickCoord(shop4);
	}


	public void playGame() throws Exception {

		Point endTurn = findImageLocation(waitForPictures("EndTurn", "EndTurn2"));
		clickCoin();

		while (checkGameEnd()) {

			clickCoin();
			wait(500);
			clickCoin();
			wait(500);
			clickCoin();
			wait(1000);

			clickAllShop();

			wait(1000);
			clickCoord(endTurn);

			wait(6000);

		}

	}


	boolean checkGameEnd() throws Exception {
		if (findImageLocation("PlayAgain") != null) {
			return false;
		}
		if (findImageLocation("PlayAgain2") != null) {
			return false;
		}
		if (findImageLocation("LevelUp") != null) {
			return false;
		}
		return true;

	}


	public void startPlayAgain() throws Exception {
		String levelUp = "LevelUp";
		if (waitForPictures("PlayAgain", "PlayAgain2", levelUp) == levelUp) {
			clickPicture(levelUp);
			System.out.println("Level up!");
		}
		waitAndClickPicture("PlayAgain");
		waitAndClickPicture("ClickToContinue");
	}

}
