package Rolit;

import java.util.ArrayList;
import java.util.Observable;

/**
 * De klasse Board.java representeert het model van het spel Rolit. Het bevat zowel een array representaie van het bord als zowel alle spelregels.
 * @author Kim en James.
 */
public class Board extends Observable {
	public int[] vakjes;
	public ArrayList<Integer> blockmoves;
	
	public static final int RED    = 1;
	public static final int GREEN  = 2;
	public static final int BLUE   = 3;
	public static final int YELLOW = 4;
	
	/**
	 * Constructor voor Board, maakt een int array aan van 64 vakjes en roept setBoard aan om de begin situatie op te stellen.
	 */
	public Board() {
		this.vakjes = new int[64];
		this.blockmoves = new ArrayList<Integer>();
		setBoard();
	}

	/**
	 * Methode set het bord op voor het begin van het spel.
	 * @ensure. 
	 * 1.   vakjes[27] = RED;
	 * 2.	vakjes[28] = YELLOW;
	 * 3.	vakjes[35] = BLUE;
	 * 4.	vakjes[36] = GREEN;
	 */
	public void setBoard() {
		for (int i = 0; i <= 63; i++) {
			vakjes[i] = 0;
		}
		vakjes[27] = RED;
		vakjes[28] = YELLOW;
		vakjes[35] = BLUE;
		vakjes[36] = GREEN;
		
		setChanged();
		notifyObservers(vakjes);
	}
		
	
	
	/**
	 * Methode controleert of het bord vol is.
	 * @return true if bord is vol.
	 */
	public boolean isFull() {
		boolean vol = false;
		for (int i = 0; i <= 63; i++) {
			if (vakjes[i] == 0) {
				return false;
			} else {
				vol = true;
			}
		}
		return vol;
	}
	
	/**
	 * Methode geeft de huidige integer array van vakjes.
	 * @return, this.vakjes.
	 */
	public int[] getVakjes() {
		return this.vakjes;
	}
	
	/**
	 * Deze methode zet het gekozen vakje om in de kleur van de speler.
	 * Roept methode setVakjesConnected om andere vakjes in aanmerking ook te veranderen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void setMove(int vakje, int kleur) {
		vakjes[vakje] = kleur;
		setVakjesConnected(vakje, kleur);
		setChanged();
		notifyObservers(vakjes);
	}
	
	/**
	 * Deze method verovert andere vakje van de tegenstander en roept hiervoor 8 methoden aan.
	 * 1. NaarBeneden(vakje, kleur).
	 * 2. NaarBoven(vakje, kleur).
	 * 3. NaarLinks(vakje, kleur).
	 * 4. NaarRechts(vakje, kleur).
	 * 5. RechtsOmhoog(vakje, kleur).
	 * 6. RechtsOmlaag(vakje, kleur)
	 * 7. LinksOmhoog(vakje, kleur)
	 * 8. LinksOmlaag(vakje, kleur)
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void setVakjesConnected(int vakje, int kleur) {
		NaarBeneden(vakje, kleur);
		NaarBoven(vakje, kleur);
		NaarLinks(vakje, kleur);
		NaarRechts(vakje, kleur);
		RechtsOmhoog(vakje, kleur);
		RechtsOmlaag(vakje, kleur);
		LinksOmhoog(vakje, kleur);
		LinksOmlaag(vakje, kleur);
	}
	
	/**
	 * Deze methode verovert vakjes naar beneden. Als er onder van het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void NaarBeneden(int vakje, int kleur) {
		for (int i = vakje + 8; i <= 63; i = i + 8) {
			if (vakjes[i] == 0) {
				break;
			}
			if (vakjes[i] == kleur) {
				for (int j = i; j >= vakje; j = j - 8) {
					vakjes[j] = kleur;
				}
				break;
			}
		}
	}
	
	/**
	 * Deze methode verovert vakjes naar boven. Als er boven het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void NaarBoven(int vakje, int kleur) {
		for (int i = vakje - 8; i >= 0; i = i - 8) {
			if (vakjes[i] == 0) {
				break;
			}
			if (vakjes[i] == kleur) {
				for (int j = i; j <= vakje; j = j + 8) {
					vakjes[j] = kleur;
				}
				break;
			}
		}
	}
	
	/**
	 * Deze methode verovert vakjes naar links. Als er links van het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void NaarLinks(int vakje, int kleur) {
		int modu = vakje % 8;
		int grens = vakje - modu;
		for (int i = vakje - 1; i >= grens ; i-- ) {
			if (vakjes[i] == 0) {
				break;
			}
			if (vakjes[i] == kleur) {
				for (int j = i; j <= vakje; j++) {
					vakjes[j] = kleur;
				}
				break;
			}
		}
	}
	
	/**
	 * Deze methode verovert vakjes naar rechts. Als er rechts van het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void NaarRechts(int vakje, int kleur) {
		int modu = vakje % 8;
		int grens = vakje - modu;
		for (int i = vakje + 1; i <= grens + 7; i++ ) {
			if (vakjes[i] == 0) {
				break;
			}
			if (vakjes[i] == kleur) {
				for (int j = i; j >= vakje; j--) {
					vakjes[j] = kleur;
				}
				break;
			}
		}
	}
	
	/**
	 * Deze methode verovert vakjes rechts omhoog. Als er rechts boven van het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void RechtsOmhoog(int vakje, int kleur) {
		int modu = vakje % 8;
		int modugrens = 7;
		int vakcheck = vakje - 7;
		for (int i = modu; i <= modugrens; i++) {
			if (vakcheck >= 0) {
				if (vakjes[vakcheck] == 0) {
					break;
				}
				if (vakjes[vakcheck] == kleur) {
					for (int j = vakcheck; j <= vakje; j = j + 7) {
						vakjes[j] = kleur;
					}
					break;
				}
				vakcheck = vakcheck - 7;
			}
		}
	}
	
	/**
	 * Deze methode verovert vakjes rechts omlaag. Als er rechts onder van het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void RechtsOmlaag(int vakje,int kleur) {
		int modu = vakje % 8;
		int modugrens = 7;
		int vakcheck = vakje + 9;
		for (int i = modu; i <= modugrens; i++) {
			if (vakcheck < 64) {
				if (vakjes[vakcheck] == 0) {
					break;
				}
				if (vakjes[vakcheck] == kleur) {
					for (int j = vakcheck; j >= vakje; j = j - 9) {
						vakjes[j] = kleur;
					}
					break;
				}
				vakcheck = vakcheck + 9;
			}
		}
	}
	
	/**
	 * Deze methode verovert vakjes links onhoog. Als er links boven van het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void LinksOmhoog(int vakje,int kleur) {
		int modu = vakje % 8;
		int modugrens = 0;
		int vakcheck = vakje - 9;
		for (int i = modu; i >= modugrens; i--) {
			if (vakcheck >= 0) {
				if (vakjes[vakcheck] == 0) {
					break;
				}
				if (vakjes[vakcheck] == kleur) {
					for (int j = vakcheck; j <= vakje; j = j + 9) {
						vakjes[j] = kleur;
					}
					break;
				}
				vakcheck = vakcheck - 9;
			}
		}
	}
	
	/**
	 * Deze methode verovert vakjes links omlaag. Als er links onder van het gekozen vakje een 
	 * ander vakje dezelfde kleur heeft dan worden alle tussenliggende vakjes omgezet, mits er geen lege vakjes tussen liggen.
	 * @require, vakje >= 0 && vakje <= 63 && kleur >= 1 && kleur <= 4.
	 * @param vakje, dat verovert moet worden.
	 * @param kleur, waarin het vakje omgezet moet worden.
	 */
	public void LinksOmlaag(int vakje, int kleur) {
		int modu = vakje % 8;
		int modugrens = 0;
		int vakcheck = vakje + 7;
		for (int i = modu; i >= modugrens; i--) {
			if (vakcheck <= 63) {
				if (vakjes[vakcheck] == 0) {
					break;
				}
				if (vakjes[vakcheck] == kleur) {
					for (int j = vakcheck; j >= vakje; j = j - 7) {
						vakjes[j] = kleur;
					}
					break;
				}
				vakcheck = vakcheck + 7;
			}
		}
	}
	
	/**
	 * Deze methode controleert of het gekozen vakje naast een vakje ligt waar die al bezet is, dit is noodzakelijk anders mag een vakje niet worden veroverd.
	 * Methode controleert in 8 richtingen.
	 * 1. Horizontaal.  keuze + 1 en keuze - 1.
	 * 2. Verticaal. keuze + 8 en keuze - 8.
	 * 3. Diagonaal. keuze + 7 en keuze + 9, keuze - 7  en keuze - 9.
	 * @param keuze, integer waarde van vakje dat gecontroleerd moeten worden. keuze >= 0 && keuze <= 63.
	 * @return true als aan 1 van 8 eisen is voldoen. Anders return false;
	 */
	public boolean isNextTo(int keuze) {
		boolean correctmove = false;
		int horizontaal = keuze % 8;
		int verticaal = keuze / 8;
				
		if (verticaal-1 >= 0) {
			if (horizontaal-1 >= 0) {
				if (vakjes[rekenom(verticaal-1, horizontaal-1)] != 0) {
					correctmove = true;
				}
			}
			if (vakjes[rekenom(verticaal-1, horizontaal)] != 0) {
				correctmove = true;
			}
			if (horizontaal+1 <= 7) {
				if (vakjes[rekenom(verticaal-1, horizontaal+1)] != 0) {
					correctmove = true;
				}
			}
		}
		
		if (horizontaal-1 >= 0) {
			if (vakjes[rekenom(verticaal, horizontaal-1)] != 0) {
				correctmove = true;
			}
		}
		if (horizontaal+1 <= 7) {
			if (vakjes[rekenom(verticaal, horizontaal+1)] != 0) {
				correctmove = true;
			}
		}
				
		if (verticaal+1 <= 7) {
			if (horizontaal-1 >= 0) {
				if (vakjes[rekenom(verticaal+1, horizontaal-1)] != 0) {
					correctmove = true;
				}
			}
			if (vakjes[rekenom(verticaal+1, horizontaal)] != 0) {
				correctmove = true;
			}
			if (horizontaal+1 <= 7) {
				if (vakjes[rekenom(verticaal+1, horizontaal+1)] != 0) {
					correctmove = true;
				}
			}
		}
		return correctmove;
	}
	
	/**
	 * Deze methode controleert het bord met drie hulp methodes of er vakjes zijn doe geblokkeerd moeten worden. 
	 * 1. hasHorizontal. Controleert met behulp van twee methoden naar links en naar rechts.
	 * 2. hasVertical. Controleert met behulp van twee methoden naar boven en naar beneden.
	 * 3. hasDiagonal. Controleert met behulp van 4 methoden in de vier diagonale richtingen.
	 * @param playerkleur, integer tussen 1 en 4.
	 * @return ArrayList<integer> this.blockmoves.
	 */
	public ArrayList<Integer> checkBlock(int playerkleur) {
		this.blockmoves = new ArrayList<Integer>();
		hasHorizontal(playerkleur);
		hasVertical(playerkleur);
		hasDiagonal(playerkleur);
		return blockmoves;
	}
	
	/**
	 * Methode controleert of er op horizontale lijnen een vakje is die geblokkeerd moet worden.
	 * Roept 2 methoden aan voor dit doel.
	 * 1. horizontalLeft(playerkleur).
	 * 2. horizontalRight(playerkleur).
	 * @param playerkleur, integer tussen 1 en 4.
	 */
	public void hasHorizontal(int playerkleur) {
		horizontalLeft(playerkleur);
		horizontalRight(playerkleur);
	}
	
	/**
	 * Methode controleert of er voor de huidige speler ook vakjes zijn die geblokkeert moeten worden, controleert horizontaal naar links.
	 * @param int playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void horizontalLeft(int playerkleur) {
		for (int k = 0; k <= 63; k = k + 8) {     
			for (int i = k ; i <= k + 7; i++) {  
				if (vakjes[i] == playerkleur) {             
					if ((i - 1) >= k) {
						if ((vakjes[i-1] != playerkleur && vakjes[i-1] != 0)){
							for (int j = i - 2; j >= k; j--) {
								if (vakjes[j] == 0) {
									if (!blockmoves.contains(j)) {   
										blockmoves.add(j);
										break;
									} else {
										break;
									}
								} if (vakjes[j] == playerkleur) {
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Methode controleert of er voor de huidige speler ook vakjes zijn die geblokkeert moeten worden, controleert horizontaal naar rechts.
	 * @param int playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void horizontalRight(int playerkleur) {
		for (int k = 0; k <= 63; k = k + 8) {     
			for (int i = k ; i <= k + 7; i++) {  
				if (vakjes[i] == playerkleur) {  
					if ((i + 1) <= (k + 7)) {
						if (vakjes[i+1] != playerkleur && vakjes[i+1] != 0){
							for (int j = i + 2; j <= k + 7; j++) {
								if (vakjes[j] == 0) {
									if (!blockmoves.contains(j)) {   
										blockmoves.add(j);
										break;
									} else {
										break;
									}
								} 
								if (vakjes[j] == 0) {
									break;
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Methode controleert of er op verticale lijnen een vakje is die geblokkeerd moet worden.
	 * Roept 2 methoden aan voor dit doel.
	 * 1. verticalUp(playerkleur).
	 * 2. verticalDown(playerkleur).
	 * @param playerkleur, integer tussen 1 en 4.
	 */
	public void hasVertical(int playerkleur) {
		verticalUp(playerkleur);
		verticalDown(playerkleur);
	}
	
	/**
	 * Methode controleert of er voor de huidige speler ook vakjes zijn die geblokkeert moeten worden, controleert verticaal naar boven.
	 * @param int playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void verticalUp(int playerkleur) {
		for (int i = 0; i <= 63; i++) {
			if (i - 16 >= 0) {
				if (((vakjes[i] == playerkleur) && (vakjes[i - 8] != 0) && (vakjes[i - 8] != playerkleur))) {
					for (int j = i - 16; j >= 0; j = j - 8) {
						if (vakjes[j] == 0) {
							if (!blockmoves.contains(j)) {   
								blockmoves.add(j);
								break;
							} else {
								break;
							}
						}
						if (vakjes[j] == playerkleur) {
							break;
						} 
					}
				}
			}
		}
	}
	
	/**
	 * Methode controleert of er voor de huidige speler ook vakjes zijn die geblokkeert moeten worden, controleert verticaal naar beneden.
	 * @param int playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void verticalDown(int playerkleur) {
		for (int i = 0; i <= 63; i++) {
			if (i + 16 <= 63) {
				if (((vakjes[i] == playerkleur) && (vakjes[i + 8] != 0) && (vakjes[i + 8] != playerkleur))) {
					for (int j = i + 16; j <= 63; j = j + 8) {
						if (vakjes[j] == 0) {
							if (!blockmoves.contains(j)) {   
								blockmoves.add(j);
								break;
							} else {
								break;
							}
						}
						if (vakjes[j] == playerkleur) {
							break;
						} 
					}
				}
			}
		}
	}
		
	
	/**
	 * Methode controleert of er op diagonale lijnen een vakje is die geblokkeerd moet worden.
	 * Roept 4 methoden aan voor dit doel.
	 * 1. hasLinksOmhoog(playerkleur).
	 * 2. hasRechtsOmlaag(playerkleur).
	 * 3. naarlinksomlaag(playerkleur).
	 * 4. naarrechtsomhoog(playerkleur).
	 * @param playerkleur, integer tussen 1 en 4.
	 */
	public void hasDiagonal(int playerkleur) {
		hasLinksOmhoog(playerkleur);
		hasRechtsOmlaag(playerkleur);
		naarlinksomlaag(playerkleur);
		naarrechtsomhoog(playerkleur);
	}
	
	/**
	 * Methode controleert of er op diagonale lijnen een vakje is die geblokkeerd moet worden. Controleert in richting links omhoog.
	 * @param playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void hasLinksOmhoog(int playerkleur) {
		for (int i = 0; i <= 63; i++) {
			int modu = i % 8;
			int grens = (i - modu) - (modu * 8);
			if (grens < 0) {
				grens = 0;
			}
			if (i - 18 >= 0 ) {
				if (((vakjes[i] == playerkleur) && (vakjes[i - 9] != 0) && (vakjes[i - 9] != playerkleur))) {
					for (int j = i - 18; j >= grens || j >= 0; j = j - 9) {
						if (vakjes[j] == 0) {
							if (!blockmoves.contains(j)) {   
								blockmoves.add(j);
								break;
							} else {
								break;
							}
						}
						if (vakjes[j] == playerkleur) {
							break;
						}
					}
				}
			}
		}
	}
		
	/**
	 * Methode controleert of er op diagonale lijnen een vakje is die geblokkeerd moet worden. Controleert in richting rechts omlaag.
	 * @param playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void hasRechtsOmlaag(int playerkleur) {
		for (int i = 0; i <= 63; i++) {
			int modu = i % 8;
			int grens = (i - modu) + (modu * 8) + 7;
			if (grens > 63) {
				grens = 63;
			}
			if(i + 18 <= 63) {
				if (((vakjes[i] == playerkleur) && (vakjes[i + 9] != 0) && (vakjes[i + 9] != playerkleur))) {
					for (int j = i + 18; j <= grens || j <= 63; j = j + 9) {
						if (vakjes[j] == 0) {
							if (!blockmoves.contains(j)) {   
								blockmoves.add(j);
								break;
							} else {
								break;
							}
						}
						if (vakjes[j] == playerkleur) {
							break;
						} 
					}
				}
			}
		}
	}
		
	/**
	 * Methode controleert of er op diagonale lijnen een vakje is die geblokkeerd moet worden. Controleert in richting rechts omhoog.
	 * @param playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void naarrechtsomhoog(int playerkleur) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (vakjes[rekenom(i,j)] == 0) {
					int v = i - 1;
					int h = j + 1;
					while (v > -1 && h < 8) {
						if (vakjes[rekenom(v,h)] == 0) {
							break;
						}
						if (vakjes[rekenom(v,h)] == playerkleur) {
							if ((v < (i - 1) && h > (j + 1))) {
								addvakje(i, j);
								break;
							}
							else {
								break;
							}
						}
						v--;
						h++;
					}
				}
			}
		}
	}
		
	/**
	 * Methode controleert of er op diagonale lijnen een vakje is die geblokkeerd moet worden. Controleert in richting links omlaag.
	 * @param playerkleur, integer tussen 1 en 4.
	 * @ensure. Voor elke waarde die aan de eisen voldoet this.blockmoves.size = old.blockmoves.size + 1.
	 */
	public void naarlinksomlaag(int playerkleur) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (vakjes[rekenom(i,j)] == 0) {
					int v = i + 1;
					int h = j - 1;
					while ((v < 8 && h > -1)) {
						if (vakjes[rekenom(v,h)] == 0) {
							break;
						}
						if (vakjes[rekenom(v,h)] == playerkleur) {
							if ((v > (i + 1) && h < (j - 1))) {
								addvakje(i, j);
								break;
							}
							else {
								break;
							}
						}
						v++;
						h--;
					}
				}
			}
		}
	}
	
	/**
	 * Deze methode converteert twee getallen gebruikt voor coordinaten in een Twee-dimensionale array's in 
	 * een integer waarde die overeenkomt met hetzelfde vakje in een Een-dimensionale array.
	 * @param v, integer waarde (verticaal) tussen 0 en 7.
	 * @param h, integer waarde (horizontaal) tussen 0 en 7.
	 * @return, integer waarde tussen 0 en 63.
	 */
	public int rekenom(int v, int h) {
		int verticaal = v;
		int horizontaal = h;
		int goedevak = (verticaal * 8) + horizontaal;
		return goedevak;
	}
	
	/**
	 * Deze methode voegt een gekozen waarde tussen 0 en 63 toe aan de ArrayList<integer> blockmoves.
	 * Eerst wordt de getallen v (verticaal) en h (horizontaal) vertaald naar de Een-dimensionale getallenreeks met methode rekenom.
	 * @param v integer waarde (verticaal) tussen 0 en 7.
	 * @param h integer waarde (horizontaal) tussen 0 en 7.
	 * @ensure. this.blockmoves.size = old.blockmoves.size + 1.
	 */
	private void addvakje(int v, int h) {
		int goedevak = rekenom(v, h);
		blockmoves.add(goedevak);
	}

	/**
	 * Deze methode geeft een String representatie weer van het speelbord.
	 * @ensure String b met lengte 64 met alle posities een integer tussen 0 en 4.
	 */
	public String toString() {
		int[] vakjes = getVakjes();
		String b = "" + vakjes[0];
		for(int i = 1; i <= 63; i++){
			b = b + vakjes[i];
		}
		return b;
	}
	/**
	 * Deze methode maakt een string van de integer waarden uit ArrayList<Integer> check. De String meegegeven als paramter wordt met deze waarden aangevuld.
	 * @param result is een String met lengte == 0 of lengte > 0.
	 * @return String met waarden tussen 0 en 4.  Lengte result == check.size() + lengte parameter result.
	 */
	public String toString(String result) {
		ArrayList<Integer> check = new ArrayList<Integer>();
		check =	this.blockmoves;
		result = "";
		for(int i = 0; i < check.size(); i++){
			result = result + check.get(i);
		}
		return result;
	}
}
