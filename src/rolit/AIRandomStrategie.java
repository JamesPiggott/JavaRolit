package rolit;
import java.util.ArrayList;
import java.util.Random;

public class AIRandomStrategie implements Strategie {

	public AIRandomStrategie() {
	}
	
	 /**
     * Deze methode zal gebruikt worden als door de client aangegeven is dat er gebruik wordt gemaakt van een AI speler,
     * waarschijnlijk zal deze methode gebruik maken van methode getHint om een lijst met mogelijke zetten te krijgen 
     * en daaruit 1 random te gebruiken.
     */
	public int doeZet(int spelerkleurint, int[] speelbord) {
		ArrayList<Integer> vakzet = new ArrayList<Integer>();
		int move = 0;
		vakzet = getHint(spelerkleurint, speelbord);
		Random random = new Random();
		if (vakzet.isEmpty() == false) {
			move = random.nextInt(vakzet.size());
			return vakzet.get(move);
    	} else {
    		boolean zetisgoed = true;
    		while (zetisgoed) {
    			Board board = new Board();
    			board.vakjes = speelbord;
    			int randomint = random.nextInt(64);
    			if (board.isNextTo(randomint)) {
    				if (board.vakjes[randomint] == 0) {
    					zetisgoed = false;
        				move = randomint;
    				}
    			}
    		}
    	}
		return move;
	}

	/**
     * Deze methode is bedoelt om een hint te genereren voor de speler die aan zet is (de huidige client). Een simpele 
     * implementatie zal gewoon de regels van Board.java gebruiken om te kijken of een zet uberhaupt mogelijk is 
     * (is het een blokkerende zet?) De kleur van de huidige speler is nodig als zowel het huidige spel stand.
     */
	public ArrayList<Integer> getHint(int spelerkleurint, int[] speelbord) {
		ArrayList<Integer> conquering = new ArrayList<Integer>();
		Board bord = new Board();
    	bord.vakjes = speelbord;
    	conquering = bord.checkBlock(spelerkleurint);
    	return conquering;
	}
}
