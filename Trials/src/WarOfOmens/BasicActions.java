package WarOfOmens;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.sikuli.script.FindFailed;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

public class BasicActions {

	Robot robot;
	Point lastCoords;
	static final int timeOut = 600000; // 10 mins
	static final int checkInterval = 1000; // in ms
	Places pl = new Places();

	public BasicActions(Robot rob) {
		robot = rob;
		robot.delay(3000);
		lastCoords = MouseInfo.getPointerInfo().getLocation();
	}

	// check if mouse was moved, if yes, halts process
	void checkUserIntervention() throws Exception {
		if (!lastCoords.equals(MouseInfo.getPointerInfo().getLocation())) {
			throw new Exception("User Intervention! Process Halted!");
		}
	}

	// this check userIntervention every half second
	void wait(int ms) throws Exception {
		while (ms > checkInterval) {
			robot.delay(checkInterval);
			checkUserIntervention();
			ms -= checkInterval;
		}
		robot.delay(ms);
		checkUserIntervention();
	}

	void clickCoord(Point point) throws Exception {
		clickCoord(point.x, point.y);
	}

	void clickCoord(int xCoord, int yCoord) throws Exception {
		checkUserIntervention();

		robot.mouseMove(xCoord, yCoord);
		lastCoords = MouseInfo.getPointerInfo().getLocation();
		wait(500);

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		checkUserIntervention();
	}

	void clickPicture(String picture) throws Exception {
		clickCoord(findImageLocation(picture));
//		System.out.println("Picture " + picture + " clicked!");
	}
	
	void waitAndClickPicture(String picture) throws Exception {
		String picture2 = picture+"2";
		clickPicture(waitForPictures(picture, picture2));
	}

	String waitForPictures(String... picture) throws Exception {
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
		throw new Exception("Timeout");

	}

	Point findImageLocation(String image) {

		Point pp = new Point();
		Screen screen = new Screen();
		String target = image + ".png";
		Match m;
		ImagePath.add("resources/WarOfOmens");

		try {
			m = screen.find(target);
		} catch (FindFailed e) {
			return null;
		}

		pp.setLocation(m.getTarget().getPoint());
//		System.out.println("Picture " + image + " found!");
		return pp;
	}

}
