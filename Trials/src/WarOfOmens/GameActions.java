package WarOfOmens;

import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeoutException;

import javax.naming.directory.InvalidAttributeValueException;

public class GameActions extends BasicActions {

	private Point coin;
	private Point shop1;
	private Point shop2;
	private Point shop3;
	private Point shop4;

	private int coinTimes;

	public int levelUp = 0;
	public int victory = 0;
	public int defeat = 0;

	public GameActions(Robot rob) {
		super(rob);
	}

	/**
	 * Open WarOfOmens in Chrome, gives Java Permission and closes pop-ups in game
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterceptionException
	 * @throws TimeoutException
	 * @throws InterruptedException 
	 * 
	 */
	public void openWoO() throws IOException, URISyntaxException, InterceptionException, TimeoutException, InterruptedException {
		// open WarOfOmens in Chrome
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI("https://www.kongregate.com/games/FifthColumnGames/war-of-omens"));
		}
		System.out.println("Game opened");
		checkUserIntervention();

		// scroll down
		waitAndClickPicture(Pictures.woOinKong);
		robot.mouseWheel(4);
		System.out.println("Scrolled down");
		checkUserIntervention();

		waitForPictures(Pictures.gameLoaded);
		getToMainScreen();
		gameLoaded = true;

	}

	/**
	 * Closes Pop-ups
	 * 
	 * @throws InterceptionException
	 * @throws TimeoutException
	 * @throws InterruptedException 
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	void getToMainScreen() throws InterceptionException, TimeoutException, InterruptedException, IOException, URISyntaxException {

		String loadedScreenType = waitForPictures(0.9, Pictures.javaPermissonNeed, Pictures.newsPopUp,
				Pictures.questPopUp, Pictures.weekendPopUp, Pictures.homeScreen, Pictures.signIn);

		if (findImageLocation(Pictures.tooHigh) != null) {
			robot.mouseWheel(1);
			wait(1000);
		}

		switch (loadedScreenType) {
		// if we need to sign in, sign in and reload
		case Pictures.signIn:
			waitAndClickPicture(Pictures.signIn);
			wait(2000);
			setNewGame();
			openWoO();
			return;

			// in case permission needed, give it
		case Pictures.javaPermissonNeed:
			clickPicture(Pictures.javaPermissonNeed);
			wait(2000);
			clickPicture(Pictures.javaEngedely);
			wait(10000);
			System.out.println("Permission given to Java");
			getToMainScreen();
			break;

		// in case Collecting is possible, do so
		case Pictures.questPopUp:
			clickPicture(Pictures.collect);
			wait(1000);
			robot.mouseWheel(-1);
			wait(1000);
			clickPicture(Pictures.newsX);
			wait(1000);
			robot.mouseWheel(1);
			wait(1000);
			System.out.println("Daily collected");
			getToMainScreen();
			break;

		// close weekend pop up
		case Pictures.weekendPopUp:
			clickPicture(Pictures.weekendPopUp);
			wait(1000);
			System.out.println("WeekendPopUp closed");
			getToMainScreen();
			break;

		// this is the only way to exit the loop
		case Pictures.homeScreen:
			return;

		// close whatever we find
		default:
			robot.mouseWheel(-1);
			wait(1000);
			clickPicture(Pictures.questX);
			wait(1000);
			robot.mouseWheel(1);
			wait(1000);
			System.out.println(loadedScreenType + " closed");
			getToMainScreen();
			break;
		}
	}

	/**
	 * Navigates on main screen to given character's page
	 * 
	 * @param characterName
	 * @throws InterceptionException
	 * @throws TimeoutException               in case character could not be found
	 *                                        in 32 tries
	 * @throws InvalidAttributeValueException in case coins and shop coordinates
	 *                                        could not be calculated
	 */
	public void getToCharacter(String characterName)
			throws InterceptionException, TimeoutException, InvalidAttributeValueException {
		// for only trying 2*all character = 32
		int counter = 0;
		System.out.print("Looking for " + characterName + " ");
		while (findImageLocation(characterName) == null) {
			waitAndClickPicture(Pictures.leftArrow);
			// move away mouse so arrow does not change to green:
			moveCoord(0, 0);
			// wait for new character page to load
			wait(3000);
			counter++;
			System.out.print(counter + " ");
			if (counter > 32) {
				throw new TimeoutException(characterName + " not found!");
			}
		}
		System.out.println("Found in " + counter + " tries!");

		calculateShopsAndCoin(characterName);

	}

	/**
	 * Calculates shop and coin coordinates, because they cannot be done throw
	 * picture recognition.
	 * 
	 * @param characterName Needs 3 point of reference to find correct resolution:
	 *                      one is the character name, one is the location of left
	 *                      arrow, one is the location of single Player
	 * @throws InvalidAttributeValueException
	 */
	void calculateShopsAndCoin(String characterName) throws InvalidAttributeValueException {
		Point theo = findImageLocation(characterName); // (2900, 890)
		Point arr = findImageLocation(Pictures.leftArrow); // (3090, 720)
		Point sp = findImageLocation(Pictures.singlePlayer); // (2450, 380)

		coin = calcCoord(theo, arr, sp, "Coins"); // (2700, 800)
		shop1 = calcCoord(theo, arr, sp, "Shop1"); // (2150, 500)
		shop2 = calcCoord(theo, arr, sp, "Shop2"); // (2150, 650)
		shop3 = calcCoord(theo, arr, sp, "Shop3"); // (2150, 800)
		shop4 = calcCoord(theo, arr, sp, "Shop4"); // (2150, 950)

		coinTimes = Pictures.coinNumber(characterName);
	}

	private Point calcCoord(Point theo, Point arr, Point sp, String place) throws InvalidAttributeValueException {
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
			sy = 670. / 850.;
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
			throw new InvalidAttributeValueException("Invalid argument in Coordinate Calculation!");

		}

		double nx = (theo.getX() - arr.getX()) * (sx - rx) / (px - qx) + sp.getX();
		double ny = (theo.getY() - arr.getY()) * (sy - ry) / (py - qy) + sp.getY();

		return new Point((int) nx, (int) ny);

	}

	/**
	 * Navigates and starts a new game of given level
	 * 
	 * @param gameLevel
	 * @throws InterceptionException
	 * @throws TimeoutException      in case of more than 10 mins of waiting for any
	 *                               picture to load
	 */
	public void startSinglePlayerGame(String gameLevel) throws InterceptionException, TimeoutException {
		waitAndClickPicture(Pictures.singlePlayer);
		waitAndClickPicture(gameLevel);
		waitAndClickPicture(Pictures.clickToContinue);

	}

	private void clickCoin() throws InterceptionException {
		clickCoord(coin);
	}

	private void clickAllShop() throws InterceptionException {

		clickCoord(shop1);

		clickCoord(shop2);

		clickCoord(shop3);

		clickCoord(shop4);
	}

	/**
	 * Plays one game, and checks when game ended, or interruption occurred
	 * 
	 * @throws InterceptionException
	 * @throws TimeoutException      if game does not load in 10 mins, or a single
	 *                               game lasts longer than 30 mins
	 */
	public void playGame() throws InterceptionException, TimeoutException {

		int timer = 0; // counts waiting time in s
		Point endTurn = findImageLocation(waitForPictures(Pictures.endTurn));
		clickCoin();

		while (checkGameEnd()) {

			for (int i = 0; i < coinTimes; i++) {
				clickCoin();
			}

			clickAllShop();

			clickCoord(endTurn);

			wait(6000);
			timer += 9;
			if (timer > 30 * 60) {
				throw new TimeoutException("Game lasted longer than 30 mins!");
			}

		}

	}

	private boolean checkGameEnd() {
		if (findImageLocation(Pictures.playAgain) != null) {
			return false;
		}
		if (findImageLocation(Pictures.levelUp) != null) {
			return false;
		}
		return true;

	}

	/**
	 * Handles levelUp pop up and starts a new game of the same kind
	 * 
	 * @throws InterceptionException
	 * @throws TimeoutException      in case pictures do not load in 10 mins
	 */
	public void startPlayAgain() throws InterceptionException, TimeoutException {
		if (waitForPictures(Pictures.playAgain, Pictures.levelUp) == Pictures.levelUp) {
			clickPicture(Pictures.levelUp);
			System.out.println("Level up!");
			levelUp++;
		}
		wait(500);
		if (findImageLocation(Pictures.victory) != null) {
			victory++;
		} else if (findImageLocation(Pictures.defeat) != null) {
			defeat++;
		} else {
			System.out.println("Victory/defeat not found!");
		}
		waitAndClickPicture(Pictures.playAgain);
		while (waitForPictures(Pictures.clickToContinue, Pictures.endTurn) == Pictures.clickToContinue) {
			clickPicture(Pictures.clickToContinue);
		}

	}

	/**
	 * Waits 10 mins and closes Chrome
	 * 
	 * @throws InterruptedException
	 * @throws InterceptionException
	 */
	public void setNewGame() throws InterruptedException, InterceptionException {
		gameLoaded = false;
		Thread.sleep(60000); // 1 mins

		checkUserIntervention();
		robot.mouseMove(0, 0);
		robot.mouseMove(110, 100);
		robot.mouseMove(0, 0);
		lastCoords = MouseInfo.getPointerInfo().getLocation();
		robot.delay(1000);
		checkUserIntervention();

		// close Chrome:
		clickPicture(Pictures.chromeClose);

		robot.delay(3000);

	}

}
