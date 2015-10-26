package Rolit;

import java.util.*;
import static Rolit.Protocol.*;

/**
 * Deze klasse representeert het Spel. Het houdt een lijst van spelers, 
 * een board, de speler die aan zet is en een lijst met de volgorde bij.
 * Verder is de klasse Game de klasse die spelers om de beurt aan zet laat, 
 * de zet controleert en de zet doorgeeft aan het board.
 * @author Kim en James
 */
public class Game {

	private ArrayList<ClientHandler> spelers; // ClientHandlers zijn de spelers
	private Board board;
	private ClientHandler huidig; // speler die aan de beurt is
	private ArrayList<ClientHandler> volgorde;
	
	/**
	 * Creeërt een nieuw spel, maakt een nieuw Board en maakt nieuwe spelers aan de hand van de 
	 * meegegeven namen en geeft iedere speler een eigen kleur.
	 * @param players, het aantal spelers dat mee doet aan het spel
	 * @param clients, de ClientHandlers/spelers die meedoen met het spel
	 * @require 1<players<5 && clients.size()==players
	 */
	public Game(int players, ArrayList<ClientHandler> clients){
		volgorde = new ArrayList<ClientHandler>();
		spelers = new ArrayList<ClientHandler>();
		spelers.addAll(clients);
		int color = 0;
		board = new Board();
		if(players == 3){ // speciaal voor 3 spelers, omdat er volgens BIT protocol geel=4 en blauw=3 LELIJK!!
			spelers.get(0).setColor(1);
			spelers.get(1).setColor(2);
			spelers.get(2).setColor(4);
		} else {
			for(int i = 0; i<players; i++){
				color = color +1;
				spelers.get(i).setColor(color);
			}
		}
		volgorde.addAll(spelers);
		Collections.shuffle(volgorde);
		huidig = volgorde.get(0);
		}
	
	/**
	 * Stuurt het PLAY bericht naar alle spelers.
	 */
	public void play(){
		for(ClientHandler ch:spelers){
			ch.sendMessage(SERVER_PLAY + " " + getHuidig().getName() + " " + board.toString());
		}
	}
	
	/**
	 * Probeert een zet te maken op vakje move voor speler sp
	 * @param move, de gewenste zet
	 * @param speler, de speler die de zet wil zetten
	 * @require 0<=move<=63 && speler!=null
	 * @return nummer van de zet, tenzij de zet niet geldig is, dan result = -1
	 */
	public int makeMove(int move, ClientHandler speler){
		int antwoord = -1;
		if(checkMove(move, speler)){
			antwoord = move;
			board.setMove(move, speler.getColor());
			if(gameOver() == false){
				nextPlayer();
				play();
			}
			else{
				for(ClientHandler ch:spelers){
					ch.sendMessage(SERVER_GAMEOVER + " " + board.toString());
					ch.shutdown();
				}
			}
		}
		return antwoord;
	}
	
	/**
	 * Deze methode zorgt ervoor dat de volgende speler aan de beurt is, volgens de volgorde.
	 */
	public void nextPlayer(){
		int x = volgorde.indexOf(huidig);
		x++;
		if(x > volgorde.size()-1){
			x = 0;
		}
		huidig = volgorde.get(x);
	}
	
	/**
	 * Controleert of een zet grenst aan een andere bal en dat een 
	 * optie tot veroveren/blokkeren genomen wordt.
	 * @param move, de zet die gecheckt dient te worden
	 * @param speler, de speler die de zet wil zetten
	 * @require 0<=move<=63 && speler!=null
	 * @return true if move correct is, else false
	 */
	public boolean checkMove(int move, ClientHandler speler){
		boolean grenst = false;   
		if(board.isNextTo(move)){
			grenst = true;
		}
		
		boolean conquers = false;    
		int color = huidig.getColor();
		ArrayList<Integer> conquering = new ArrayList<Integer>();
		conquering = board.checkBlock(color);
		
		boolean hasColor = false;
		int[] vakjes = board.getVakjes();
		for (int i = 0; i < 64; i++) {
			if (vakjes[i] == huidig.getColor()) {
				hasColor = true;
			}
		}
		if (hasColor == true) {
			if (conquering.contains(move)) {
				conquers = true;
			}
		} else {
			conquers = true;
		}
		if (conquering.isEmpty()) {
			conquers = true;
		}
		int count = 0;
		for (int i = 0; i < 64; i++) {
			if(vakjes[i] == 0) {
				count++;
			}
		}
		if (count == 1) {
			conquers = true;
			grenst = true;
		}
		
		return (grenst && conquers);
	}
	
	/**
	 * Kijkt of spel afgelopen is, spel is afgelopen als het bord vol is.
	 * @return true if spel afgelopen is, else false
	 */
	public boolean gameOver(){
		boolean answer = false;
		if(board.isFull() == true){
			answer = true;
		}
		return answer;
	}
	
	/**
	 * Deze methode geeft het board, dat bij dit spel hoort, terug.
	 * @return het board dat bij dit spel hoort
	 */
	public Board getBoard(){
		return board;
	}
	
	/**
	 * Deze methode geeft de ClientHandlers, oftewel de spelers, die dit spel spelen terug.
	 * @return lijst met ClientHandlers, oftewel de spelers
	 */
	public ArrayList<ClientHandler> getPlayers(){
		return spelers;
	}
	
	/**
	 * Deze methode geeft de ClientHandler terug die nu aan zet is.
	 * @return ClientHandler die aan zet is
	 */
	public ClientHandler getHuidig(){
		return huidig;
	}
}
