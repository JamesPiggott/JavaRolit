package Rolit;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Klasse ClientGUI, maakt een GUI en visualiseert het speelbord.
 * @author James Piggott & Kim Beunder.
 */
@SuppressWarnings("serial")
public class ClientGUI extends JFrame implements ActionListener, KeyListener, ChangeListener {

	/**
	 * De constructor van de klasse ClientGUI, roept de methode buildGUI aan de een GUI bouwt, alle andere functionaliteit van ClientGUI
	 * is een reactie op de hendelingen die gebruikers uitvoeren met de GUI of andere klassen.
	 */
    public ClientGUI() {
        super("Client GUI");
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
        this.gamestrategie = new AIRandomStrategie();
    }
    
    public Strategie gamestrategie;
    public JTextArea Tmessages;
	public JTextArea Hintmessages;
	public JTextArea playersatServer;
	public JButton request;
	public JButton aiplay;
	public JButton hint;
	public JButton buttoncolor;
	public JTextField Tmymessage;
	public JTextField Taddress;
	public JTextField Tport;
	public JTextField Tname;
	public JPanel p3;
	public JPanel p4;
	public JPanel p5;
	public JPanel pp1;
	public Client client;
	public JComboBox<?> Tnumber;
	public JSlider aitime;
	public JScrollPane scrollpanel;
	public JScrollPane scrollpanel2;
	public JScrollPane scrollpanel3;
	public InetAddress internetaddress;
	public boolean connected = false;
	public boolean isaanzet = false;
	public boolean gameRunning = false;
	public boolean AIspeler = false;
	public int numberofplayers = 2;
	public JFrame newframe;
	public int aitimedelay = 5;
    
    /**
     * Deze methode bouwt een grafische interface voor het spel Rolit.
     */
    public void buildGUI() {
        setSize(650,550);

        JPanel p1 = new JPanel(new BorderLayout());
        JPanel pp = new JPanel(new GridLayout(4,2));

        JLabel jaddress = 	new JLabel("Hostname: ");
        Taddress = new JTextField("", 12);
        Taddress.addKeyListener(this);
        
        JLabel jport = new JLabel("Port:");
        Tport = new JTextField("2727", 5);
        Tport.addKeyListener(this);
        
        JLabel jname = new JLabel("Name:");
        Tname = new JTextField("", 12);
        Tname.addKeyListener(this);
        
        JLabel jnumber = new JLabel("Number of players");
        String[] playersize = {"2", "3", "4"};
        this.Tnumber = new JComboBox<Object>(playersize);
        Tnumber.setSelectedIndex(0);
        Tnumber.addActionListener(this);
                        
        pp.add(jaddress);
    	pp.add(Taddress);
    	pp.add(jport);
    	pp.add(Tport);
    	pp.add(jname);
    	pp.add(Tname);
    	pp.add(jnumber);
    	pp.add(Tnumber);
    	
    	request = new JButton("Request");
        request.setEnabled(false);
        request.addActionListener(this);
        
        aiplay = new JButton("Enable AI");
        aiplay.setEnabled(true);
        aiplay.addActionListener(this);
        
        aitime = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        aitime.addChangeListener(this);
        aitime.setMajorTickSpacing(5);
        aitime.setMinorTickSpacing(1);
        aitime.setPaintLabels(true);
        aitime.setPaintTicks(true);
        
        JPanel pp1 = new JPanel(new BorderLayout());
        pp1.add(request, BorderLayout.CENTER);
        pp1.add(aiplay, BorderLayout.EAST);
        pp1.add(aitime, BorderLayout.SOUTH);
       
        p1.add(pp, BorderLayout.WEST);
        p1.add(pp1, BorderLayout.EAST);

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        
        JPanel pm1 = new JPanel();
        JPanel pm2 = new JPanel();
        
        pm1.setLayout(new BorderLayout());
        JLabel lbMyMessage = new JLabel ("My message:");
        Tmymessage = new JTextField("");
        Tmymessage.setEnabled(false);
        Tmymessage.addKeyListener(this);
        pm1.add(lbMyMessage);
        pm1.add(Tmymessage, BorderLayout.SOUTH);
        
        pm2.setLayout(new BorderLayout());
        JLabel lbMessages = new JLabel("Messages:");
        Tmessages = new JTextArea("", 10, 50);
        Tmessages.setEditable(false);
        scrollpanel = new JScrollPane(Tmessages);
        pm2.add(lbMessages);
        pm2.add(scrollpanel, BorderLayout.SOUTH);

        p2.add(pm1, BorderLayout.NORTH);
        p2.add(pm2, BorderLayout.CENTER);
        
        this.p5 = new JPanel(new BorderLayout());
        this.buttoncolor = new JButton("Game grid");
        this.buttoncolor.setEnabled(false);
        this.buttoncolor.setSize(650, 100);
        p5.add(buttoncolor, BorderLayout.NORTH);
        
        this.p3 = new JPanel();
        
       	p3.setLayout(new GridLayout(8,8));
		Dimension d = new Dimension(65, 65);
       	for (int i = 0; i <= 63; i = i+1) {
			( (JButton) p3.add(new JButton(""+i))).addActionListener(this);
			p3.getComponent(i).setPreferredSize(d);
			p3.getComponent(i).setEnabled(false);
		}
       	p5.add(p3, BorderLayout.CENTER);
       	
        this.p4 = new JPanel();
       	
       	this.hint = new JButton("Hint!");
       	hint.setEnabled(false);
        hint.addActionListener(this);
       	p4.add(hint, BorderLayout.NORTH);   
       	
        Hintmessages = new JTextArea("Hints...", 10, 20);
        Hintmessages.setEditable(false);
        scrollpanel2 = new JScrollPane(Hintmessages);
        p4.add(scrollpanel2, BorderLayout.SOUTH);
        
         	
        Container cc = getContentPane();
        cc.setLayout(new FlowLayout());
        cc.add(p1, BorderLayout.NORTH); 
        cc.add(p2, BorderLayout.CENTER);
        cc.add(p4, BorderLayout.SOUTH);
        
        this.newframe = new JFrame();
        this.newframe.add(p5);
        this.newframe.setVisible(true);
        this.newframe.setSize(750, 650);
        
        this.newframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /** 
     * Afhandeling van een actie van het GUI. 
     * @require ActionEvent e != null.
     * */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == request){
    		if (connected == false){
    			connect();
    			try {
    				client.sendMessage("REQUEST" + " " + Tname.getText() + " " + numberofplayers);   
            	   	request.setText("Disconnect");
    			} catch (Exception e1) {
    				addMessage("Kan geen verbinding maken!");
    				addMessage("Poort nummer en/of Hostname ongeldig!");
    			}
        	   	
        	} else {
        		try {
					client.sock.close();
					client.shutDown();
					request.setText("Request");
	    			client.speelbord = null;
	    			connected = false;
	    			addMessage("[Connectie verbroken]");
	    		   	Tport.setText("2727");
	         	   	Tname.setText(client.clientName);  
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}            
        }
    	
    	if (e.getSource() == Tnumber) {
    		JComboBox<?> combo = (JComboBox<?>) e.getSource();
    		this.numberofplayers = Integer.parseInt((String) combo.getSelectedItem());
    	}
    	
    	if (e.getSource() == aiplay) {
    		if (AIspeler == false) {
    			AIspeler = true;
    			addMessage("Artificial intelligence enabled");
    			aiplay.setText("Disable AI");
    		} else {
    			AIspeler = false;
    			addMessage("Artificial intelligence disabled");
    			aiplay.setText("Enable AI");
    		}
    	}
    	
    	if (e.getSource() == hint){
    		ArrayList<Integer> conquering = gamestrategie.getHint(client.spelerkleurint, client.speelbord);
    		if (!conquering.isEmpty()) {
        		Random random = new Random();
        		int randomint = random.nextInt(conquering.size() - 1);
        		Hintmessages.append(conquering.get(randomint).toString() + "\n");
        	} else {
        		Hintmessages.append("No hint for you!" + "\n");
        	}
    	}
    	       
    	for (int i = 0; i <= 63; i++) {
        	if (this.p3.getComponent(i) == e.getSource()) {
        		client.sendMessage("DO " + i);
        		this.isaanzet = false;
        		this.hint.setEnabled(false);
        		for (int j = 0; j < 64; j++) {
        			this.p3.getComponent(j).setEnabled(false);
        		}
        	}
        }
    }
    
    /**
     * Afhandeling van een actie van het GUI.
     * @require KeyEvent e != null.
     */
    public void keyReleased(KeyEvent e){
        if (e.getSource() == Tport || e.getSource() == Tname || e.getSource() == Taddress) {
            if (((Taddress.getText().length() != 0) && (Tport.getText().length() != 0) && (Tname.getText().length() != 0))){  
                request.setEnabled(true);
            } else {
                request.setEnabled(false);
            }
        }
        if (e.getSource() == Tmymessage && e.getKeyCode() == KeyEvent.VK_ENTER){
            client.sendMessage("CHAT " + Tmymessage.getText());
            Tmymessage.setText("");
        }
    }
        
    /**
     * Probeert een socket-verbinding op te zetten met de Server.
     */
    public void connect() {
    	int port = 0;
    	String naam = "";
    	try {    
        	internetaddress = InetAddress.getByName(Taddress.getText());
        } catch (UnknownHostException e) { 
        	internetaddress = null;
        }
        try {
        	port = Integer.parseInt(Tport.getText());
        	@SuppressWarnings("resource")
			Scanner scanner = new Scanner(Tname.getText());
        	scanner.useDelimiter(" ");
        	String naam1 = scanner.next();
        	if (naam1.contains("@") || naam1.contains("!") || naam1.contains("#") || naam1.contains("$") || naam1.contains("%") || naam1.contains("^") || naam1.contains("&") || naam1.contains("*")) {
        		addMessage("Client naam ongeldig! Mag geen aparte tekens bevatten!. Dus geen !@#$%^&*");
        	} else {
        		if (!scanner.hasNext()) {
            		if (Tname.getText().length() <= 20 || !Tname.getText().contains(" ")) {
                		naam = Tname.getText();
                		if (port > 0 && port < 65000 && internetaddress != null) {
                        	try {
                        		client = new Client(naam, internetaddress, port, this);
                        		client.start();
                        		Tmymessage.setEnabled(true);
                        		connected = true;
                        	} catch (IOException e) {
                            	addMessage("Poort nummer en/of Hostname ongeldig!");
                            }
                        } 
                	} else {
                		addMessage("Client naam ongeldig! Mag niet langer dan 20 tekens!");
                	}
            	} else {
            		addMessage("Client naam ongeldig! Mag geen spaties bevatten");
            	}
        	}
        } catch (Exception e) {
        	addMessage("Poort nummer en/of client naam ongeldig!");
        }
    }

    /**
     *  Voegt een bericht toe aan de TextArea op het frame.
     *  @require String msg != null
     */
    public void addMessage(String msg) {
        Tmessages.append(msg + "\n");
    }

    /** 
     * Start een ClientGUI applicatie op. 
     */
    @SuppressWarnings("unused")
	public static void main(String args[]) {
    	System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        ClientGUI gui = new ClientGUI();
    }
           
    /**
     * Deze methode verandert de kleuren van de JButton in de gewenste kleur. Hier kan ook de button meteen op setEnabled(false) worden gezet.
     * @param vakjes, een integer array die de kleuren van de vakjes representeren.
     * @require, vakjes.length == 64.
     */
    public void setKleurVakjes(int[] vakjes) {
    	int[] Jvakjes = new int[64];
    	Jvakjes = vakjes;
    	
    	for (int i = 0; i < 64; i++) {
    		int j = Jvakjes[i];
    		String number = "" + j;
    		
    		if (number.equals("1")) {
    			this.p3.getComponent(i).setBackground(Color.RED);
    			this.p3.getComponent(i).setEnabled(false);
    		}
    		if (number.equals("2")) {
    			this.p3.getComponent(i).setBackground(Color.GREEN);
    			this.p3.getComponent(i).setEnabled(false);
    		}
    		if (number.equals("3")) {
    			this.p3.getComponent(i).setBackground(Color.BLUE);
    			this.p3.getComponent(i).setEnabled(false);
    		}
    		if (number.equals("4")) {
    			this.p3.getComponent(i).setBackground(Color.YELLOW);
    			this.p3.getComponent(i).setEnabled(false);
    		}
    		if ((number.equals("0")) && (this.isaanzet == true)) {
    			this.p3.getComponent(i).setEnabled(true);
    		}
    	}
    }
    
    /**
     * Deze methode verandert de status van de speler zodanig dat deze nu aan zet is.
     * @ensure, if old.isaanzet == false. then new.isaanzet == true.
     */
    public void spelerIsAanZet() {
    	this.isaanzet = true;
    }
    
    /**
     * Deze methode zet het bord zoals het wordt weergeven op het scherm in de start situatie.
     */
	public void SetBordToStart() {
		for (int i = 0; i < 64; i++) {
			this.p3.getComponent(i).setBackground(Color.WHITE);
			this.p3.getComponent(i).setEnabled(false);
		}
		this.p3.getComponent(27).setBackground(Color.RED);
		this.p3.getComponent(27).setEnabled(false);
		
		this.p3.getComponent(28).setBackground(Color.YELLOW);
		this.p3.getComponent(28).setEnabled(false);
		
		this.p3.getComponent(35).setBackground(Color.BLUE);
		this.p3.getComponent(35).setEnabled(false);
		
		this.p3.getComponent(36).setBackground(Color.GREEN);
		this.p3.getComponent(36).setEnabled(false);
	}
	
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		this.aitimedelay = source.getValue();
	}
	
	public void keyTyped(KeyEvent e){
    }
    
    public void keyPressed(KeyEvent e){
    }
}