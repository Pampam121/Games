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
			counter++;
			if (counter > 24) {
				throw new Exception("Theodox not found!");
			}
		}

		calculateShopsAndCoin();

	}


	public void calculateShopsAndCoin() throws Exception {
		Point theo = findImageLocation("Theodox"); //(2900, 890)
		Point arr = findImageLocation("LeftArrow"); //(3090, 720)
		Point sp = findImageLocation("SinglePlayer"); //(2450, 380)

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


	public void startApprenticeGame() throws Exception {
		clickPicture("SinglePlayer");
		wait(3000);
		clickPicture("Apprentice");
		waitForPictures("ClickToContinue");
		clickPicture("ClickToContinue");

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
		String levelUp = "PlayAgain";
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
