package WarOfOmens;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;


public class BasicActions {

	Robot robot;
	Point lastCoords;
	static final int timeOut = 600000; //10 mins
	static final int checkInterval = 1000; //in ms
	Places pl = new Places();


	public BasicActions(Robot rob) {
		robot = rob;
		robot.delay(3000);
		lastCoords = MouseInfo.getPointerInfo().getLocation();
	}


	//check if mouse was moved, if yes, halts process
	void checkUserIntervention() throws Exception {
		if (!lastCoords.equals(MouseInfo.getPointerInfo().getLocation())) {
			throw new Exception("User Intervention! Process Halted!");
		}
	}


	//this check userIntervention every half second
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
	}


	void waitForPictures(String... picture) throws Exception {
		int time = 0;
		int ms = 1000;
		while (time < timeOut) {

			wait(ms);
			time += ms;

			for (String pic : picture) {
				if (findImageLocation(pic) != null)
					return;
			}
		}

	}


	private BufferedImage getScreenshot(int timeToWait) throws Exception {

		wait(timeToWait);

		Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

		return robot.createScreenCapture(rec);
	}


	Point findImageLocation(String image) throws Exception {

		//		getScreenshot(5);
		//TODO

		if (image == "ClickToContinue" || image == "EndTurn") {
			wait(3000);
		}
		return pl.picPoints.get(image);
	}

}
