package WarOfOmens;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.HashMap;

public class Places {

	public HashMap<String, Point> picPoints = new HashMap<>();


	public Places() {
		picPoints.put("Apprentice", new Point(2450, 450));
		picPoints.put("ClickToContinue", new Point(2900, 920));
		picPoints.put("EndTurn", new Point(2700, 1000));
		picPoints.put("LeftArrow", new Point(3090, 720));
		picPoints.put("PlayAgain", new Point(2900, 980));
		picPoints.put("SinglePlayer", new Point(2450, 380));
		picPoints.put("Theodox", new Point(2900, 890));
		picPoints.put("WoOinKong", new Point(0, 0));
		picPoints.put("Coins", new Point(2700, 800));
		picPoints.put("Shop1", new Point(2150, 500));
		picPoints.put("Shop2", new Point(2150, 650));
		picPoints.put("Shop3", new Point(2150, 800));
		picPoints.put("Shop4", new Point(2150, 950));
		picPoints.put("4PackCard", new Point(280,300));
	}


	public static void main(String[] args) throws Exception {
		System.out.println("Start!");
		Robot robot = new Robot();
		GameActions acc = new GameActions(robot);
		robot.delay(5000);
		System.out.println(MouseInfo.getPointerInfo().getLocation());

		acc.moveCoord(280, 300);
		
		System.out.println(acc.findImageLocation(Pictures.goldPack));


	}

}
