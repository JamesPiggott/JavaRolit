/*
* VERANTWOORDELIJKHEDEN
* =====================================
* Bij het afgesproken protocol liggen de verantwoordelijkheden voor het bijhouden van het spel, het spelverloop en de controle van de zetten bij de server.
* Bij elke zet word daarom de complete status van het bord meegegeven zodat alle clients altijd het juiste bord hebben.
* 
* FORMAT
* =====================================
* Dit protocol verzend alle commandos over een socketverbinding in de vorm van Strings.
* Deze zijn als volgt opgebouwd:
* <COMMAND>[DELIM]<ARG1>[DELIM]<ARG2>[DELIM]<ARG3>[DELIM]<...>[DELIM]<ARGn>
* De argumenten word dus gescheiden door de [DELIM], wat in dit geval een spatie is.
* Omdat het scheidingsteken een spatie is mogen in de argumenten geen spaties gebruikt worden.
* Er mogen enkel spaties in de argumenten gebruikt worden als er maar 1 argument word verwacht en meegegeven.
* 
* Elke newline word gezien als een nieuw commando, dus er kunnen geen newlines worden gebruikt in de argumenten.
* 
* KLEUREN
* =====================================
* De kleuren worden gerepresenteerd door integers met de volgende verdeling:
* Kleur 0	Geen balletje
* Kleur 1	Rood
* Kleur 2	Groen
* Kleur 3	Blauw
* Kleur 4	Geel
*
* BORD formaat
* =====================================
* Het bord word gerepresenteerd door door BORD_SIZE x BORD_SIZE = 64 integers die lengte 1 hebben.
* De eerste integer representeerd het eerste vakje, waarbij vervolgens van links naar rechts word gekeken.
* Elke integer staat voor een kleur zoals hier boven uitgelegd.
* 
* ERROR codes
* =====================================
* Om foutmeldingen door te geven aan de client zijn de volgende error codes beschikbaar:
* 1		De gekozen gebruikersnaam is al in gebruik.
* 2		De zet die je wil doen is niet geldig.
* 
* 4		De gekozen gebruikersnaam is te lang
* 5		Er is een foutief bericht binnen gekomen
* COMMANDOs
* =====================================
* Hier staan alle commando's weergegeven, de hele uitwerking staat in de commentaar van de code.
*/
package rolit;

public interface Protocol {
	/** 
	 * =====================================
	 *  VARIABELEN
	 * =====================================
	 */
	
	/** 
	 * Ball representaties 
	*/
	public static final int BALL_NONE = 0;
	public static final int BALL_RED = 1;
	public static final int BALL_GREEN = 2;
	public static final int BALL_BLUE = 3;
	public static final int BALL_YELLOW = 4;
	
	/**
	 * Error codes
	 */
	public static final int ERROR_USERNAME_IN_USE = 1;
	public static final int ERROR_MOVE_NOT_VALID = 2;
	//public static final int ERROR_CLIENT_LEFT = 3;
	public static final int ERROR_LONG_USERNAME = 4;
	public static final int ERROR_INCORRECT_ARGUMENTS = 5;

	
	/**
	 * Delim
	 */
	public static final String DELIM = " ";
	
	/**
	 * Line ending: (hiervoor kan dus println gebruikt worden)
	 */
	public static final String LINE_ENDING= "\n";
	/**
	 * lengte naam
	 */
	public static final int MAX_USERNAME_SIZE = 20;
	
	/** 
	 * =====================================
	 *  COMMANDOS
	 * =====================================
	 */
	
	/**
	 * 	REQUEST command
	 *  Client -> server
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  Na het maken van de socket verbinding zegt de client dat hij een spel wil spelen.
	 *  Hierbij vertel hij wie hij is (naam) en met hoeveel speler hij wil spelen (aantal spelers)
	 *  
	 *  PARAMETERS
	 *  REQUEST <String:Naam> <Int:Spelers>
	 *  Naam: String van maximaal 20 tekens zonder spaties
	 *  Spelers: Een int van 2 t/m 4
	 */
	
	public static final String CLIENT_REQUEST = "REQUEST";
	
	/** *************** Start CHALLENGE protocol  (VOORSTEL)********************** **/
	/**
	 *  DOCHALLENGE command
	 *  Client -> server
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  De speler geeft aan dat hij een spel wil spelen met bepaalde personen.
	 *  
	 *  PARAMETER
	 *  CHALLENGE <String:Naam>[<String: naam1>]
	 *  De eerste naam is je eigen naam
	 *  Waarbij tussen de [] zich 1 tot 3 keer herhaalt, en bevat met wie jij wil spelen.
	 */
	public static final String CLIENT_DO_CHALLENGE = "DOCHALLENGE";
	
	/**
	 *  WAITCHALLENGE command
	 *  Client -> server
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  De speler geeft aan dat hij wil wachten totdat hij uitgenodig word.
	 *  
	 *  PARAMETER
	 *  CHALLENGE <String:Naam>
	 *  Waarbij naam je eigen naam is
	 *  
	 */
	public static final String CLIENT_WAIT_CHALLENGE = "WAITCHALLENGE";
	
	/**
	 *  ACCEPTCHALLENGE command
	 *  Client -> server
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  De speler geeft aan dat hij de uitnodiging accepteerd
	 *  
	 */
	public static final String CLIENT_ACCEPT_CHALLENGE = "ACCEPTCHALLENGE";	
	
	
	/**
	 *  REJECTCHALLENGE command
	 *  Client -> server
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  De speler geeft aan dat hij dit spel niet wil spelen
	 *  
	 */
	public static final String CLIENT_REJECT_CHALLENGE = "REJECTCHALLENGE";	
	
	/**
	 *  REQUESTCHALLENGE command
	 *  Server -> client
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  De server zegt tegen de client dat iemand hem CHALLENGEd
	 *  
	 *  PARAMETERS
	 *  REQUESTCHALLENGE <String:naam>
	 *  Waarbij naam de naam is van de persoon die de request uitvoerd
	 *  
	 */
	public static final String SERVER_REQUEST_CHALLENGE = "REQUESTCHALLENGE";	
	
	/**
	 * PLAYERS command
	 * Server -> Client
	 * Broadcast
	 * 
	 * DESCRIPTION
	 * Een overzicht met alle vrije spelers die je kan CHALLENGEn.
	 * Dit commando word elke keer verzonden zodra er mensen weg gaan en mensen 
	 * 
	 * PARAMETER
	 * PLAYERS [<String:naam>]
	 * waarbij naam alle namen zijn van mensen die je kan CHALLENGEn
	 * 
	 * 
	 */
	public static final String SERVER_PLAYERS = "PLAYERS";	
	/** *************** EIND CHALLENGE  ********************** **/
	
	
	/**
	 * 	ERROR command
	 *  Server -> Client
	 *  Broadcast (alle spelers die in het spel zitten)
	 *  
	 *  DESCRIPTION
	 *  Alle foutmeldingen van de server die aan de client doorgegeven worden.
	 *  
	 *  PARAMETERS
	 *  ERROR <Int:code>
	 *  Code:
	 	* 1		De gekozen gebruikersnaam is al in gebruik.
		* 2		De zet die je wil doen is niet geldig.
		* -		-
		* 4		De gekozen gebruikersnaam is te lang
		* 5		Er is een foutief bericht binnen gekomen
	 */
	
	public static final String SERVER_ERROR = "ERROR";
	
	
	/**
	 * 	ACCEPT command
	 *  Server -> Client
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  De server zegt tegen de client dat hij is geaccepteerd en kan wachten totdat het spel start.
	 *  
	 *  PARAMETERS
	 *  ACCEPT
	 *  
	 */
	
	public static final String SERVER_ACCEPT = "ACCEPT";
	
	/**
	 * 	NEW command
	 *  Server -> Client
	 *  Broadcast (Naar alle spelers voor het spel
	 *  
	 *  DESCRIPTION
	 *  De server geeft aan dat er een nieuw spel klaar is, en welke spelers hierin zitten
	 *  
	 *  PARAMETERS
	 *  NEW <Int:spelers> [<String:Naam> <Int:Kleur>]
	 *  Waarbij alles tussen [ en ] zich herhaald zoveel spelers als er zijn
	 *  
	 *  Naam: String van maximaal 20 tekens zonder spaties
	 *  Spelers: Een int van 2 t/m 4
	 *  Kleur: een mogelijke kleur gedefineerd in de constanten
	 */
	
	public static final String SERVER_NEW = "NEW";
	
	/**
	 * 	PLAY command
	 *  Server -> Client
	 *  Broadcast tegen alle spelers van t spel
	 *  
	 *  DESCRIPTION
	 *  De server zegt tegen alle spelers wie een zet moet doen en geeft huidige situatie van het bord mee
	 *  
	 *  PARAMETERS
	 *  PLAY <String:Naam> <Int:Bord>
	 *  
	 *  Naam: String van maximaal 20 tekens zonder spaties
	 *  Bord: 64 integers die elk de status van een vakje op het bord representeren dmv een kleur
	 */
	
	public static final String SERVER_PLAY = "PLAY";
	
	/**
	 * 	DO command
	 *  Client -> Server
	 *  non-broadcast
	 *  
	 *  DESCRIPTION
	 *  De client die aan zet is zegt tegen de server welk vakje hij een zet wil doen
	 *  
	 *  PARAMETERS
	 *  DO <Int:Locatie>
	 *  
	 *  Locatie: Een integer van 0 tot en met 63 welke een vakje op het bord representeren.
	 *  De vakjes lopen van links naar rechts en vervolgens van boven naar beneden.
	 */
	
	public static final String CLIENT_DO = "DO";
	
	/**
	 * 	GAMEOVER command
	 *  Server -> Client
	 *  Broadcast tegen alle spelers van t spel
	 *  
	 *  DESCRIPTION
	 *  Alle vakjes zijn gevuld en het spel is afgelopen. De server stuurt de eind situatie van eht bord door
	 *  
	 *  PARAMETERS
	 *  GAMEOVER <Int:Bord>
	 *  
	 *  Bord: 64 integers die elk de status van een vakje op het bord representeren dmv een kleur
	 */
	
	public static final String SERVER_GAMEOVER = "GAMEOVER";
	
	/**
	 * 	CHAT command
	 *  Client -> Server
	 *  Non-broadcast
	 *  
	 *  DESCRIPTION
	 *  Een client verstuurt een chatbericht naar de server
	 *  
	 *  PARAMETERS
	 *  CHAT <String:Bericht>
	 *  
	 *  Bericht: Een string representatie van het bericht, dit bericht mag spaties bevallen echter geen newlines
	 */
	
	public static final String CLIENT_CHAT = "CHAT";
	
	/**
	 * 	CHAT command
	 *  Server -> Client
	 *  Broadcast (alle spelers in het spel)
	 *  
	 *  DESCRIPTION
	 *  De server broadcast het bericht naar alle spelers
	 *  
	 *  PARAMETERS
	 *  CHAT <String:Naam> <String:Bericht>
	 *  Naam: String van maximaal 20 tekens zonder spaties
	 *  Bericht: Een string representatie van het bericht, dit bericht mag spaties bevallen echter geen newlines
	 */
	
	public static final String SERVER_CHAT = "CHAT";
}
