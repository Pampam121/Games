package WarOfOmens;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeoutException;

import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import WarOfOmens.InterceptionException.Interruption;


public class BasicActions {

	Robot robot;
	Point lastCoords;
	static final int timeOut = 600000; // 10 mins
	static final int checkInterval = 1000; // in ms
	boolean gameLoaded = false;


	public BasicActions(Robot rob) {
		robot = rob;
		robot.delay(3000);
		lastCoords = MouseInfo.getPointerInfo().getLocation();
	}


	// check if mouse was moved, if yes, halts process
	void checkUserIntervention() throws InterceptionException {
		if (!lastCoords.equals(MouseInfo.getPointerInfo().getLocation())) {
			throw new InterceptionException("User Interruption! Mouse was " + MouseInfo.getPointerInfo().getLocation() + " but should be " + lastCoords);
		}
	}


	private void checkKongregateAndInternet() throws InterceptionException {
		if (findImageLocation(Pictures.reconnectMe) != null) {
			System.out.println("Kongregate Connection Error!");
			throw new InterceptionException(Interruption.KONGREGATE);
		}
		if (findImageLocation(Pictures.wooError) != null) {
			System.out.println("Kongregate Connection Error!");
			throw new InterceptionException(Interruption.WAROFOMENS);
		}
//		if (findImageLocation(Pictures.achievementOn) == null) {
//			throw new InterceptionException(Interruption.INTERNET);
//		}
	}


	//check mouse move, and if game should be loaded, if it's still loaded
	void checkAllInterruption() throws InterceptionException {
		checkUserIntervention();
		if (gameLoaded) {
			checkKongregateAndInternet();
		}
	}


	// this check userIntervention every second
	void wait(int ms) throws InterceptionException {
		while (ms > checkInterval) {
			robot.delay(checkInterval);
			checkAllInterruption();
			ms -= checkInterval;
		}
		robot.delay(ms);
		checkAllInterruption();
	}


	void clickCoord(Point point) throws InterceptionException {
		clickCoord(point.x, point.y);
	}


	void moveCoord(int xCoord, int yCoord) {
		robot.mouseMove(xCoord, yCoord);
		lastCoords = MouseInfo.getPointerInfo().getLocation();
	}


	void clickCoord(int xCoord, int yCoord) throws InterceptionException {
		checkUserIntervention();

		moveCoord(xCoord, yCoord);
		wait(500);

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		checkUserIntervention();
	}


	void clickPicture(String picture) throws InterceptionException {
		clickCoord(findImageLocation(picture));
		//		System.out.println("Picture " + picture + " clicked!");
	}


	void waitAndClickPicture(String picture) throws InterceptionException, TimeoutException {
		clickPicture(waitForPictures(picture));
	}


	String waitForPictures(String... picture) throws InterceptionException, TimeoutException {
		int time = 0;
		int ms = 1000;
		//		System.out.print("Waiting...   ");
		while (time < timeOut) {

			wait(ms);
			time += ms;

			for (String pic : picture) {
				if (findImageLocation(pic) != null) {
					//					System.out.println(ms + " ms");
					return pic;
				}
			}
		}
		System.out.println("");
		throw new TimeoutException();

	}


	Point findImageLocation(String image) {

		Point pp = new Point();
		Screen screen = new Screen();
		String target = image + ".png";
		Match m;
		ImagePath.add("resources/WarOfOmens");

		try {
			m = screen.find(target);
		}
		catch (FindFailed e) {
			return null;
		}

		pp.setLocation(m.getTarget().getPoint());
		//		System.out.println("Picture " + image + " found!");
		return pp;
	}

}
