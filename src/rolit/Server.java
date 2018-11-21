package rolit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static Rolit.Protocol.*;

/**
 * Deze klasse luistert naar socketverbindingen op
 * gespecificeerde port en voor elke socketverbinding met een Client wordt
 * een nieuwe ClientHandler thread opgestart.
 * @author Kim en James
 */
public class Server implements Runnable{

	public ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();			
	protected ArrayList<ClientHandler> twoPlayers = new ArrayList<ClientHandler>();	
	protected ArrayList<ClientHandler> threePlayers = new ArrayList<ClientHandler>();
	protected ArrayList<ClientHandler> fourPlayers = new ArrayList<ClientHandler>();
	public ServerSocket ssock;
	public ServerGUI gui;
	
	/**
	 * Dit is de constructor van Server, hierin wordt een nieuwe ServerSocket aangemaakt
	 * @param port, de port waarop de ServerSocket wordt geopend
	 * @param sgui, ServerGUI die deze Server vertegenwoordigd
	 * @require port>=0 && port<=65535 && sgui!=null
	 */
	public Server(int port, ServerGUI sgui){  
		gui = sgui;
		new ArrayList<Game>();      
		try {
			ssock = new ServerSocket(port);
		} catch (IOException e) {
			sendToGui("Given socketnumber could not be used, please try again");
		}
	}

	/**
	 * In deze methode wordt er geluistert naar de ServerSocket en elke keer dat 
	 * er zich een Client meld, wordt er voor deze Client een nieuwe ClientHandler gestart.
	 */
	public void run() {
		Socket csock;
		try {
			while((csock = ssock.accept()) != null) {
				ClientHandler ch = new ClientHandler(this, csock);
				Thread th = new Thread(ch);
				th.start();
				clients.add(ch);
				System.out.println("New client was connected");
			}
		} catch (IOException e) {
			sendToGui("Connection terminated");
		}
	}
	
	/**
	 * Deze methode registreert een Client als een nieuwe speler.
	 * @param client, de ClientHandler die de nieuwe speler vertegenwoordigt
	 * @param number, het aantal spelers waarmee de Client wil spelen
	 * @require client!=null && 1<number<5
	 */
	public synchronized void newPlayer(ClientHandler client, int number) {				
		if(number==2){
			twoPlayers.add(client);
			if(twoPlayers.size()==2){
				createGame(twoPlayers);
				twoPlayers.clear();
			}
		}
		else if(number==3){
			threePlayers.add(client);
			if(threePlayers.size()==3){
				createGame(threePlayers);
				threePlayers.clear();
			}
		}
		else{
			fourPlayers.add(client);
			if(fourPlayers.size()==4){
				createGame(fourPlayers);
				fourPlayers.clear();
			}
		}
	}
	
	/**
	 * Deze methode wordt aangeroepen als er genoeg spelers zijn om een Game te starten. 
	 * In deze methode wordt een nieuwe Game gemaakt en wordt het spel gestart.
	 * @param players, spelers waarmee een nieuw spel gestart word
	 * @require players!=null
	 */
	public void createGame(ArrayList<ClientHandler> players){
		Game game = new Game(players.size(), players);
		String msg = SERVER_NEW + " " + players.size();
		for(ClientHandler ch:players){
			ch.setGame(game);
			msg += " " + ch.getName()+ " " + ch.getColor();	
		}
		broadcast(players, msg);
		game.play();
	}
	
	/**
     * Stuurt een bericht via de collectie van aangesloten ClientHandlers
     * naar alle aangesloten Clients.
     * @param receivers, lijst van ClientHandlers die het bericht moeten ontvangen
     * @param msg, bericht dat verstuurd wordt
     * @require receivers!=null
     */
    public void broadcast(ArrayList<ClientHandler> receivers, String msg) {	
        for(ClientHandler ch:receivers) {									
        	ch.sendMessage(msg);
        }
    }
    
    /**
     * Voegt een ClientHandler aan de collectie van ClientHandlers toe.
     * @param handler, ClientHandler die wordt toegevoegd
     * @require handler!=null && !clients.contains(handler)
     */
    public void addHandler(ClientHandler handler) {						
        clients.add(handler);
    }

    /**
     * Verwijdert een ClientHandler uit de collectie van ClientHandlers.
     * @param clientHandler, ClientHandler die verwijderd wordt
     * @require clientHandler!=null && clients.contains(clientHandler)
     */
    public void removeHandler(ClientHandler clientHandler) {				
        clients.remove(clientHandler);
        twoPlayers.remove(clientHandler);
        threePlayers.remove(clientHandler);
        fourPlayers.remove(clientHandler);
    }
    
    /**
     * Deze methode geeft de lijst met ClientHandlers.
     * @return een list met alle ClientHandlers
     */
    public ArrayList<ClientHandler> getClients(){
    	return clients;
    }
    
    /**
     * Deze methode print berichten in het textvak van de ServerGUI
     * @param msg, bericht dat geprint dient te worden
     */
    public void sendToGui(String msg){
    	gui.addMessage(msg);
    }
}
