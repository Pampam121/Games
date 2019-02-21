package WarOfOmens;

import javax.naming.directory.InvalidAttributeValueException;

public class Pictures {

	// getting in Game
	public static final String woOinKong = "WoOinKong2";
	public static final String javaPermissonNeed = "JavaDownload";
	public static final String javaEngedely = "JavaEngedely";
	public static final String gameLoaded = "GameLoaded";
	public static final String tooHigh = "TooHigh";

	public static final String newsPopUp = "NewsPopUp";
	public static final String newsX = "NewsX";
	public static final String questPopUp = "DailyPopUp";
	public static final String questX = "DailyX";
	public static final String weekendPopUp = "WeekEndPopUp";
	public static final String homeScreen = "LeftArrow2";
	public static final String collect = "Collect";

	// connection check
	public static final String reconnectMe = "ConnectionLost";
	public static final String wooError = "WoOOpenedElsewhere";
	public static final String achievementOn = "AchivementShows";
	
	public static final String chromeClose = "chromeX";

	// HomeScreen Navigation
	public static final String helpIcon = "HelpIcon";
	public static final String leftArrow = "LeftArrow2";
	public static final String singlePlayer = "SinglePlayer2";
	public static final String tournament = "Tournament";

	// Characters
	public static final String captListrata = "CaptListrata";
	public static final String sofocatro = "Sofocatro";
	public static final String cardinalPocchi = "CPocchi";
	public static final String sisYsadora = "SisYsadora";
	public static final String liet = "Liet";
	public static final String esra = "Esra";
	public static final String mogesh = "Mogesh";
	public static final String babarus = "Babarus";
	public static final String valdorian = "Valdorian";
	public static final String theodox = "Theodox2";
	public static final String birondelle = "Birondelle";
	public static final String gretta = "Gretta";
	public static final String raktabaan = "Raktabaan";
	public static final String calipeth = "Calipeth";
	public static final String jesmai = "Jemai";
	public static final String zalasair = "Zalasair";
	
	//Tournament Characters
	public static final String dogeMonteferro = "DogeMonteferro";
	public static final String shofetDaru = "ShofetDaru";
	public static final String brigandLucca = "BrigandLucca";

	// Game Levels
	public static final String neophyte = "Neophyte";
	public static final String apprentice = "Apprentice2";
	public static final String journeyman = "Journeyman";
	public static final String master = "Master";
	public static final String grandmaster = "Grandmaster";

	// InGame
	public static final String clickToContinue = "clickToContinue2";
	public static final String endTurn = "EndTurn2";
	public static final String playAgain = "PlayAgain2";
	public static final String levelUp = "LevelUp";
	
	//Tournament
	public static final String enterTournament = "Enter";
	public static final String _0Ticket = "";
	public static final String _1Ticket = "1Ticket";
	public static final String _2Ticket = "";
	public static final String _3Ticket = "";
	public static final String _4Ticket = "";
	public static final String _5Ticket = "";
	public static final String heroPack = "HeroPack";
	public static final String _4Pack = "4Pack";
	public static final String _5Pack = "5Pack";
	public static final String _6Pack = "6Pack";
	public static final String ready = "";
	public static final String endTurnTournament = "";
	public static final String continueButton = "Continue";

	public static int coinNumber(String character) throws InvalidAttributeValueException {
		switch (character) {
		case captListrata:
			return 10;
		case sofocatro:
			return 10;
		case cardinalPocchi:
			return 10;
		case sisYsadora:
			return 6;
		case liet:
			return 3;
		case esra:
			return 3;
		case mogesh:
			return 3;
		case babarus:
			return 3;
		case valdorian:
			return 4;
		case theodox:
			return 3;
		case birondelle:
			return 3;
		case gretta:
			return 3;
		case raktabaan:
			return 4;
		case calipeth:
			return 4;
		case jesmai:
			return 4;
		case zalasair:
			return 4;

		default:
			throw new InvalidAttributeValueException();
		}
	}

}
