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
		
		if (waitForPictures("LeftArrow", "LeftArrow2", "JavaDownload") == "JavaDownload") {
			clickPicture("JavaDownload");
			wait(2000);
			clickPicture("JavaEngedely");
			waitForPictures("LeftArrow", "LeftArrow2");
		}

	}


	public void getToTheodox() throws Exception {
		int counter = 0;
		System.out.print("Looking for Theodox... ");
		while (findImageLocation("Theodox") == null && findImageLocation("Theodox2") == null) {
			waitAndClickPicture("LeftArrow");
			wait(3000);
			counter++;
			System.out.print(counter+" ");
			if (counter > 32) {
				throw new Exception("Theodox not found!");
			}
		}
		System.out.println("Found!");

		calculateShopsAndCoin();

	}


	public void calculateShopsAndCoin() throws Exception {
		Point theo = findImageLocation("Theodox2"); //(2900, 890)
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


	public void startApprenticeGame() throws Exception {
		waitAndClickPicture("SinglePlayer");
		waitAndClickPicture("Apprentice");
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

		waitForPictures("EndTurn", "EndTurn2");
		clickCoin();

		while (checkGameEnd()) {

			clickCoin();
			wait(500);
			clickCoin();
			wait(500);
			clickCoin();
			wait(1000);

			clickAllShop();

			wait(3000);
			waitForPictures("EndTurn", "EndTurn2");

		}

	}


	boolean checkGameEnd() throws Exception {
		if(findImageLocation("PlayAgain") == null) {
			return false;
		}
		if(findImageLocation("PlayAgain2") == null) {
			return false;
		}
		return true;
		
	}


	public void startPlayAgain() throws Exception {
		String levelUp = "PlayAgain";
		if(waitForPictures("PlayAgain","PlayAgain2", levelUp) == levelUp){
			clickPicture(levelUp);
		}
		waitAndClickPicture("PlayAgain");
		waitAndClickPicture("ClickToContinue");
	}

}
