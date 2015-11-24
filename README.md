# Welcome to my java implementation of Rolit!
<<<<<<< HEAD

Manual to Rolit version 1.0.
README: This is the readme file of Rolit version 1.0. 
AUTHORS: James Piggott.
EMAIL: jjhpiggott@gmail.com.
INSTALL:
1. Copy files to Desktop.
2. Start ‘Command Prompt’ and navigate to folder containing the .class files of this software (see file manifest).
Setting up a Server:
1. Enter java ‘foldername’.ServerGUI.java in the command line and press enter to start the Rolit Server.

Figure 1. Server set to port '2727'.
2. Enter an integer value between 0 and 65535 as the port on which the server should listen for incoming client connections. Press ‘Start listening’ to engage the listening mode.


Setting up a Client:
1. Enter java ‘foldername’.ClientGUI.java in the command line and press enter to start the Rolit Client.
2. As the ‘Hostname’ enter the IP-address of the computer running a Rolit-Server. If you’re using you own computer terminal enter ‘localhost’.
3. As port number, enter the integer on which the Server is listening.
4. As name, enter a name of choice, it must not be longer than 20 digits long.
5. For the number of players select a number in the selection box. Standard value is ‘2’. 
6. Press ‘request’ to request a game from the server. Server will acknowledge you’re connection and display the message ‘You have been accepted to a game, wait until game starts!’ if there is not another client for you to play a game with.

Playing a game of Rolit:
1. A player will know when he can play when his board will become activated. He can then click on a square of his choosing to confirm his choice. If the choice is an erroneous move he will not forfeit his round but instead can again select a square.
2. The game’s objective is to capture as many squares as possible before the entire board is full. To that end the game is not over until the board is full.
3. A player can switch on the AI by selecting the button ‘Enable AI’, the user will have to input one more round of gameplay before the AI switches over to play. The AI can be disengaged by selecting ‘Disable AI’.
4. The player can also select the time it will take for the AI to select a move. Using the slide bar the value can be altered between 0 en 10 seconds. This for the purpose of evaluating the moves the AI makes or to give the illusion of a human player playing.
Note of Caution; Sometimes when selecting an erroneous square the board will be lock-out for the player, despite it still being the players round. Select a square in the upper left corner to correctly set the board.
Exit program.
1. If the player wishes to terminate a game. Press ‘Disconnect’ to stop the game.
2. If a player wishes to stop the program select ‘X’ in the upper right corner.
3. If a user wishes to discontinue the services of the Server. Press ‘Stop listening’.

MANIFEST:
Board.java
Game.java
ClientGUI.java
Client.java
ServerGUI.java
Server.java
ClientHandler.java
Strategie.java
AIRandomStrategie.java
Protocol.java

THANKS: Special thanks go out to Alvar Hanso & DHARMA Initiative. 

=======
>>>>>>> d6c49415f3de7ac81cb6f6ecea12d9eac36cec83
