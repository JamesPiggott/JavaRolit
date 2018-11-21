package rolit;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;


/**
 * Klasse Client, maakt vebinding met Server-side en faciliteert de communicatie tussen ClientGUI en SeverSide.
 * @author James Piggott & Kim Beunder.
 */
public class Client extends Thread {
		
	public String clientName;
	public String playerkleur;
	public int numberOfPlayers;
	public int spelerkleurint;
	public int[] speelbord;
	public String[] names;
	public String nextTurn;
	public Socket sock;
	private ClientGUI gui;
	private BufferedReader in;
	private BufferedWriter out;
	
	/**
	 * De constructor van de klasse Client.
	 * @param name, een String als naam van de gebruiker. 
	 * @param host, een InetAddress als address van de host waarmee een verbinding wordt gemaakt.
	 * @param port, een integer waarde als poortnummer waarop de host zit de luisteren.
	 * @param gui,  een ClientGUI waartoe berichten kunnen worden verstuurd die binnen komen op de Socket verbinding.
	 * @throws IOException
	 */
	public Client(String name, InetAddress host, int port, ClientGUI gui) throws IOException {
        this.clientName = name;
        this.sock = new Socket(host, port);
        this.gui = gui;
        this.in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
        gui.addMessage("[Connected to server]");
    }
	
	/**
	 * Deze methode leest berichtenen en roept de methode actOnMessage aan.
	 * De binnenkomende String wordt meegeven als argument.
	 */
	public void run () {
		try {
    		while(true) {
    			String message = in.readLine();
    			if (message != null) {
    				actOnMessage(message);
    			}
    		}
    	} catch (IOException e) {
    		shutDown();
    	}
	}
	
	/**
	 * Deze methode handelt als een filter voor inkomende berichten. De methode maakt een Scanner instance aan die de String met gebruik
	 * van useDelimiter in stukjes hakt voordat deze kleinere stringen worden geanalyseerd.
	 * @param message, een String dat een bericht representeert van Server-side.
	 */
	private void actOnMessage(String message) throws IOException {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(message);
		scanner.useDelimiter(" ");
		String command = scanner.next();
		if (command.equals("ACCEPT")) {
			gui.addMessage("You have been accepted to a game, wait until game starts!");
			gui.SetBordToStart();
		}
		
		if (command.equals("ERROR")) {
			String commanderror = "";
			commanderror = scanner.next();
			
			if (commanderror.contains("1")) {
				gui.addMessage("1		De gekozen gebruikersnaam is al in gebruik.");
			}
			if (commanderror.equals("2")) {
				gui.addMessage("2		De zet die je wil doen is niet geldig.");
				gui.spelerIsAanZet();
				gui.hint.setEnabled(true);
				gui.setKleurVakjes(speelbord);
				Board bord = new Board();
				bord.vakjes = speelbord;
				if (bord.isFull() == false) {
					if (gui.AIspeler == true) {
						String move = "" + gui.gamestrategie.doeZet(spelerkleurint, speelbord);
						sendMessage("DO " + move);
						for (int j = 0; j < 64; j++) {
		        			gui.p3.getComponent(j).setEnabled(false);
		        		}
					}
				}
			}
			if (commanderror.equals("3")) {
				gui.addMessage("4		De gekozen gebruikersnaam is te lang");
			}
			if (commanderror.equals("5")) {
				gui.addMessage("5		Er is een foutief bericht binnen gekomen");
			} else {
				gui.addMessage("No ERROR!");
			}
		}
				
		if (command.equals("NEW")) { 
			this.numberOfPlayers = Integer.parseInt(scanner.next());
			this.names = new String[this.numberOfPlayers];
			int i = 0;
			while (scanner.hasNext()) {
				String player = scanner.next();
				this.names[i] = player;
				i++;
				int kleur = scanner.nextInt();
				if (player.equals(clientName)) {
					this.playerkleur = "" + kleur;
					this.spelerkleurint = kleur;
					setColorGrid(playerkleur);
				}
			}
			gui.SetBordToStart();
		}
		
		if (command.equals("PLAY")) {
			String nameturn = "";
			String bordarray = "";
			nameturn = scanner.next();
			bordarray = scanner.next();
						
			int[] bord = new int[64];
			for (int i = 0; i < bordarray.length(); i++) {
				bord[i] = Integer.parseInt(bordarray.substring(i, i + 1));
			}
			this.speelbord = bord;
			this.nextTurn = nameturn;
			gui.setKleurVakjes(speelbord);
			if (nameturn.equals(this.clientName)) {
				gui.spelerIsAanZet();
				gui.hint.setEnabled(true);
			}
			gui.setKleurVakjes(speelbord);
			if (gui.AIspeler == true) {
				try {
					Thread.sleep(gui.aitimedelay * 1000);
				} catch (InterruptedException e) {
					gui.addMessage("AI has not delayed!");
				}
				String move = "" + gui.gamestrategie.doeZet(spelerkleurint, speelbord);
				sendMessage("DO " + move);
				for (int j = 0; j < 64; j++) {
        			gui.p3.getComponent(j).setEnabled(false);
        		}
			}
		}
		
		if (command.equals("GAMEOVER")) {
			String bordarray = "";
			bordarray = scanner.next();
			int[] bord = new int[64];
			for (int i = 0; i < bordarray.length(); i++) {
				bord[i] = Integer.parseInt(bordarray.substring(i, i + 1));
			}
			this.speelbord = bord;
			gui.isaanzet = false;
			gui.setKleurVakjes(speelbord);
			gui.addMessage("GAMEOVER!");
			shutDown();
			getScore(bord);
		}
		
		if (command.equals("CHAT")) {
			gui.addMessage((String) message.subSequence(4, message.length()));
		}
	}
	
	/**
	 * Deze methode telt de vakjes die iedere kleur heeft veroverd en geeft weer of de 
	 * speler heeft gewonnnen of verloren en hoeveel vakjes hij heeft veroverd.
	 * @param bord,  het speel bord bestaande uit 64 integers die ieder een getal bevat tussen 1 en 4.
	 */
	private void getScore(int[] bord) {
		JFrame scoreboard = new JFrame();
		scoreboard.setVisible(true);
		scoreboard.setSize(200, 200);
		JTextArea text = new JTextArea();
		text.setSize(200, 200);
		text.setVisible(true);
		scoreboard.add(text);
		scoreboard.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
		}
		);
		
		int Red = 0;
		int Green = 0;
		int Blue = 0;
		int Yellow = 0;
		
		for (int i = 0; i <= 63; i++) {
			if (bord[i] == 1) {
				Red++;
			}
			if (bord[i] == 2) {
				Green++;
			}
			if (bord[i] == 3) {
				Blue++;
			}
			if (bord[i] == 4) {
				Yellow++;
			}
		}
		ArrayList<Integer> score = new ArrayList<Integer>();
		score.add(Red);
		score.add(Green);
		score.add(Blue);
		score.add(Yellow);
		int maxscore = 0;
		for (int i = 0; i < score.size(); i++) {
			if (score.get(i)>= maxscore) {
				maxscore = score.get(i);
			}
		}
		int playerscore = 0;
		text.append(this.names[0].toString() + " has " + score.get(0).toString() + " points!" + "\n");
		text.append(this.names[1].toString() + " has " + score.get(1).toString() + " points!" + "\n");
		
		
		if (this.numberOfPlayers == 3) {
			text.append(this.names[2].toString() + " has " + score.get(3).toString() + " points!" + "\n");  // score is omgedraaid bij 3 spelers.
		}
		
		if (this.numberOfPlayers == 4) {
			text.append(this.names[2].toString() + " has " + score.get(2).toString() + " points!" + "\n");
			text.append(this.names[3].toString() + " has " + score.get(3).toString() + " points!" + "\n");
		}
		
		if (spelerkleurint == 1) {
			playerscore = Red;
		}
		if (spelerkleurint == 2) {
			playerscore = Green;
		}
		if (spelerkleurint == 3) {
			playerscore = Blue;
		}
		if (spelerkleurint == 4) {
			playerscore = Yellow;
		}
				
		if (playerscore == maxscore) {
			text.append("You have won!." + "\n");
		} else {
			text.append("You have lost!." + "\n");
		}
	}
	
	/**
	 * Deze methode zet de balk boven het spel bord in op de juiste kleur, het dient als een visuele herinnering voor de speler.
	 * @param kleur,  1 van de vier speler kleuren. kleur >= 0 en kleur <= 4.
	 */
	private void setColorGrid(String kleur) {
		if (kleur.equals("1")) {
			gui.buttoncolor.setBackground(Color.RED);
		}
		if (kleur.equals("2")) {
			gui.buttoncolor.setBackground(Color.GREEN);		
		}
		if (kleur.equals("3")) {
			gui.buttoncolor.setBackground(Color.BLUE);
		}
		if (kleur.equals("4")) {
			gui.buttoncolor.setBackground(Color.YELLOW);
		}
	}

	/**
	 * Deze methode stuurt berichten naar Server-side.
	 * @param msg, een String die is samengesteld en vervolgens naar Server-side wordt verzonden met behulp van de methode write van BufferedWriter.
	 */
	public void sendMessage(String msg) {
		try {
    		out.write(msg + "\n");
    		out.flush();
        } catch (IOException e) { 
        	gui.addMessage("[Failure to send message properly]");
        }
	}
	
	/**
	 * Deze methode verbreekt de connectie met Server-side.
	 * @ensure, this.in.close && this.out.close && this.sock.close.
	 */
	public void shutDown() {
		gui.addMessage("[Closing socket connection...]");
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
        	gui.addMessage("[Failure to close connection properly]");
        }
    }
}
