package rolit;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Scanner;

import static Rolit.Protocol.*;

/**
 * Deze klasse is voor het onderhouden van een 
 * socketverbinding tussen een Client en een Server.
 * @author Kim en James
 */
public class ClientHandler extends Observable implements Runnable {

    private Server           server;
    private BufferedReader   in;
    private BufferedWriter   out;
    private String           clientName;
    private Game			 game;
    private int				 color;

    /**
     * Construeert een ClientHandler object.
     * Initialiseert de beide Data streams.
     * @param server, de server waaraan deze ClientHandler is gekoppelt
     * @param sock, de socket waarop de data stream gemaakt
     * @throws IOException
     * @require server != null && sock != null
     */
    public ClientHandler(Server server, Socket sock) throws IOException {
        this.server = server;
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
    }

    /**
     * Leest de naam van de Client uit de inputstream en stuurt de
     * een broadcast-berichtje naar de Server om te melden dat de
     * Client nu deelneemt aan de chatbox. Merk op dat deze methode
     * meteen na het construeren van een ClientHandler dient te
     * worden aangeroepen.
     */
    public void announce() throws IOException {
        server.broadcast(server.getClients(), SERVER_CHAT + " [" + clientName + " has entered]");
        server.sendToGui(SERVER_CHAT + " [" + clientName + " has entered]");
    }

    /**
     * Deze methode ontvangt berichten van de client, interpreteert ze
     * en zorgt dat het gevraagde gebeurt. Als er bij het lezen 
     * van een bericht een IOException gegooid wordt, concludeert 
     * de methode dat de socketverbinding is verbroken en wordt
     * shutdown() aangeroepen.
     */
    public void run() {
    	String line;
    	Scanner sc;
    	try {
			while((line = in.readLine()) != null){
				server.sendToGui("RECEIVED: "+line);
				sc = new Scanner(line);
				if(line.startsWith(CLIENT_CHAT)){
					String msg = line.substring(5);
					server.sendToGui(clientName + ": " + msg);
					server.broadcast(server.getClients(), SERVER_CHAT + " " + clientName + ": " + msg);
				}
				else if(line.startsWith(CLIENT_DO)){
					sc.next();
					int move = sc.nextInt();
					if(this.equals(game.getHuidig())){
						int done = game.makeMove(move, this);
						if(done<0){
							sendMessage(SERVER_ERROR + " " + ERROR_MOVE_NOT_VALID);
						}
					}
					
				}
				else if(line.startsWith(CLIENT_REQUEST)){
					sc.next();
					String name = sc.next();
					int aantal = Integer.parseInt(sc.next());
					if(aantal>1 && aantal<5){
						if(name.length()<=MAX_USERNAME_SIZE){
							boolean uniqueName = true;
							for(ClientHandler ch:server.getClients()){
								if(!ch.equals(this) && ch.getName() != null && ch.getName().equals(clientName)){
									uniqueName = false;
								}
							}
							if(!uniqueName) sendMessage(SERVER_ERROR + " " + ERROR_USERNAME_IN_USE);
							else {
								clientName = name;
								sendMessage(SERVER_ACCEPT);
								announce();
								server.newPlayer(this, aantal);
								
							}
						}
						else{
							sendMessage(SERVER_ERROR + " " + ERROR_LONG_USERNAME);
						}
					}
					else{
						System.out.println("Number of players must be 2-4");
					}
				}
				else{
					sendMessage(SERVER_ERROR + " " + ERROR_INCORRECT_ARGUMENTS);
				}
			}
		} catch (IOException e) {
			server.sendToGui("Apparently Client has left. ClientHandler wil now shutdown.");
			shutdown();
		}
    }

    /**
     * Deze methode kan gebruikt worden om een bericht over de 
     * socketverbinding naar de Client te sturen. Als het schrijven
     * van het bericht mis gaat, dan concludeert de methode dat de
     * socketverbinding verbroken is en roept shutdown() aan.
     * @param msg, het bericht dat gestuurd word
     */
    public void sendMessage(String msg) {
        try{
        	out.write(msg);
			out.newLine();
			out.flush();
			server.sendToGui("SENT: "+msg);
        } catch(IOException e){
        	shutdown();
        }
    }

    /**
     * Deze ClientHandler meldt zich af bij de Server en stuurt
     * vervolgens een laatste broadcast naar de Server om te melden
     * dat de Client niet langer deelneemt aan de chatbox.
     */
    public void shutdown() {
        server.removeHandler(this);
        server.broadcast(server.getClients(), SERVER_CHAT+" [" + clientName + " has left]");
    }
    
    /**
     * Deze methode koppelt een bepaalde Game aan deze ClientHandler
     * @param game
     */
    public void setGame(Game game){
    	this.game = game;
    }
    
    /**
     * Bij het aanroepen van deze methode wordt de naam van de Client, 
     * die aan deze ClientHandler gekoppelt is, terug gegeven
     * @return clientname
     */
    public String getName() {
    	return clientName;
    }
    
    /**
     * Deze methode koppelt een kleur aan deze ClientHandler
     * @param color
     */
    public void setColor(int color){
    	this.color = color;
    }
    
    /**
     * Bij het aanroepen van deze methode wordt de kleur (als int) van de Client, 
     * die aan deze ClientHandler gekoppelt is, terug gegeven
     * @return kleur, als int
     */
    public int getColor(){
    	return color;
    }
}
