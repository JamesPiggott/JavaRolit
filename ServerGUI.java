package Rolit;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import javax.swing.*;


/**
 * Deze klasse is de grafische representatie van de Server
 * @author Kim en James
 */
@SuppressWarnings("serial")
public class ServerGUI extends JFrame implements ActionListener, KeyListener{
	
	private JButton     bConnect;
    private JTextField  tfPort;
    private JTextField 	tfAddress;
    private JLabel		toestand;
    private JTextArea   taMessages;
    private Server      server;
    private JScrollPane pane1;
    private boolean listening = false;

    /**
     * Dit is de contructor, hier wordt de methode voor het bouwen van de gui 
     * aangeroepen en wordt een WindowListener toegevoegd 
     */
    public ServerGUI() {
        super("ServerGUI");

        buildGUI();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
    }

    /** 
     * Bouwt de daadwerkelijke GUI. 
     * */
    public void buildGUI() {
        setSize(500,400);
        setResizable(false);

        // Panel p1 - Listen

        JPanel p1 = new JPanel(new FlowLayout());
        JPanel pp = new JPanel(new GridLayout(2,2));

        JLabel lbAddress = new JLabel("Address: ");
        tfAddress = new JTextField(getHostAddress(), 12);
        tfAddress.setEditable(true);

        JLabel lbPort = new JLabel("Port:");
        tfPort = new JTextField(5);
        tfPort.addKeyListener(this);

        pp.add(lbAddress);
        pp.add(tfAddress);
        pp.add(lbPort);
        pp.add(tfPort);

        bConnect = new JButton("Start Listening");
        bConnect.addActionListener(this);
        bConnect.setEnabled(false);
        
        toestand = new JLabel("Fill in a portnumber and an address to start the Server");

        p1.add(pp, BorderLayout.WEST);
        p1.add(bConnect, BorderLayout.EAST);

        // Panel p2 - Messages

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        
        JLabel lbMessages = new JLabel("Messages:");
        taMessages = new JTextArea("", 15, 40);
        taMessages.setEditable(false);
        pane1 = new JScrollPane(taMessages);
        p2.add(lbMessages);
        p2.add(pane1, BorderLayout.SOUTH);

        Container cc = getContentPane();
        cc.setLayout(new FlowLayout());
        cc.add(p1); cc.add(toestand); cc.add(p2);
    }
    
    /** 
     * Levert het Internetadres van deze computer op.
     * @return InetAddress van deze computer
     */
    private String getHostAddress() {
        try {
            InetAddress iaddr = InetAddress.getLocalHost();
            return iaddr.getHostAddress();
        } catch (UnknownHostException e) {
            return "?unknown?";
        }
    }

    /**
     * Deze methode wordt aangeroepn wanneer een event plaats vind, 
     * wanneer er op de knop geklikt word
     * @param ev, de event waarbij deze methode werd aangeroepen
     */
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == bConnect) {
			if (listening == false) {
	        	if(tfPort.getText() != null){
	        		String text = tfPort.getText();
	        		int port = Integer.parseInt(text);
		        	if(port >= 0 && port <= 65535 ){
		        		startListening();
		        	}
		        	else {
		        		toestand.setText("Portnumber is incorrect, please try again");
		        	}
	        	}
	        	this.listening = true;
	        	bConnect.setText("Stop listening");
	        }
			else {
	        	try {
	        		server.broadcast(server.clients, "CHAT Connection terminated");
					server.ssock.close();
					bConnect.setText("Start Listening");
					bConnect.setEnabled(true);
					listening = false;
				} catch (IOException e) {
					e.printStackTrace();
				}    
	        }
		}
	}
	
	/**
     * Als de port-veld van de GUI geldig is, zal deze methode
     * een Server-object construeren, die daadwerkelijk op de 
     * gespecificeerde port zal gaan wachten op Clients.
     * Dit gebeurt in een aparte thread van de Server.
     * De knop 'Start listening' wordt omgezet naar 'Stop listening'.
     */
    private void startListening() {
        int port = 0;
        try {
            port = Integer.parseInt(tfPort.getText());
        } catch (NumberFormatException e) {
            addMessage("ERROR: geen geldig portnummer!");
            return;
        }
        server = new Server(port, this);
        Thread th = new Thread(server);
        th.start();
        addMessage("Started listening on port " + port + "...");
    }

    /**
     * Voegt een bericht toe aan de TextArea op het frame.
     * @param msg, het bericht dat toegevoegd moet worden aan het textveld
     */
    public void addMessage(String msg) {
        taMessages.append(msg + "\n");
    }

    /**
     * Start een ServerGUI applicatie op.
     */
    public static void main(String[] args) {
        new ServerGUI();
    }

    /**
     * Deze methode wordt aangeroepen wanneer er een toets wordt ingedrukt
     * @param ev, het event dat deze methode aanriep
     */
	public void keyPressed(KeyEvent ev) {
		if(ev.getSource() == tfPort){
        	if(tfPort.getText() != null && tfAddress.getText() != null){
        		bConnect.setEnabled(true);
        	}
        }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
