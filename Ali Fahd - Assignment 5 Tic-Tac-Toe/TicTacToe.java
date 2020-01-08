import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;
/**
 * Class Tic-Tac-Toe is game of tic-tac-toe using java GUI outputting in 
 * JFrame. The game has different modes as well as sounds and pictures.
 * 
 *
 * author of method haveWinner():
 * @author Lynn Marshall
 * @version November 8, 2012
 * 
 * @author Ali Fahd
 * @version Nov. 25, 2019
 */
public class TicTacToe implements ActionListener
{
   public static final String PLAYER_X = "X"; // player using "X"
   public static final String PLAYER_O = "O"; // player using "O"
   public static final String PLAYER_PSI = "Ψ"; // player using "Ψ"
   public static final String PLAYER_OMEGA = "Ω"; // player using "Ω"
   
   private JFrame f;    //frame of game
   private JMenuBar menubar;    //games menubar
   private JMenu menu;  //games menu
   private JMenuItem restartGame, quitGame, goMainMenu; //options in "game" menu item
   
   private JLabel status, displayScore1, displayScore2; // labels in the game status, and both player scores
   
   private JPanel panel;    //game panel
   private JPanel menuPanel;    //main menu panel
   private GridBagConstraints gbc;  //formating for panels
   private JButton newGame, MnewGame, Mcomputer, Mgreek, Mquit, Msound; //main menu buttons
   private String player;   // current player (PLAYER_X or PLAYER_O)

   private int numFreeSquares, score1, score2; // number of squares still free, scores of both players
   
   private boolean compGame, compTurn, greekMode; // boolean for to check for vs computer mode, computers turn, and greek mode
   
   private JButton board[][]; // board buttons for tic tac toe
   
   private static final ImageIcon psi = new ImageIcon("psi.png");   //gets the image for player 1 in greek mode
   private static final Image editPsi = psi.getImage(); //holds image in a new variable to edit
   private static final Image newPsi = editPsi.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH ); //edits the image to make it larger
   private static final ImageIcon omega = new ImageIcon("omega.png");   //gets the image for player 2 in greek mode
   private static final Image editOmega = omega.getImage(); //holds image in a new variable to edit
   private static final Image newOmega = editOmega.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH ); //edits the image to make it larger
   
   private Image currentImg;    //holds current image for greek mode
   
   private String soundString;  //holds text for sound on or off on main menu button sound
   
   private URL url; //holds the path file for sound.wav file
   private AudioClip audioClip; //will hold sound file
   /** 
    * Initializes fields and creates the basics of the GUI involving the 
    * JFrame in additon the menu bar with menu and menu items. These are
    * displayed throughout the game regardless of which screen/panel the
    * user is on. Individual methods are caled to change display of the 
    * screen/panel [ie mainMenu() and createBoard()].
    */
   public TicTacToe() throws Exception
   {
       f = new JFrame("Tic-Tac-Toe");   //initalizes the frame with title
       menubar = new JMenuBar();    //initialize new menu bar
       menu = new JMenu("Game");    //initialize new menu with title game
       panel = new JPanel();    //create game panel
       menuPanel = new JPanel();    //create menu panel
       compGame = false;    //starts off wiht default not a computer game
       compTurn = false;    //starts off wiht default not the computers turn
       greekMode = false;   //default not a greek mode game
       currentImg = newPsi; //in greek mode player 1 will be psi
       soundString = "Sound: ON";   //sound starts off with on
       score1 = 0;  //both players scores are 0 by default
       score2 = 0;
       url = new URL("file:" + System.getProperty("user.dir") + "/" + "sound.wav"); //gets path for sound file
       audioClip = Applet.newAudioClip(url);    //can now use sound file as an audio clip
       numFreeSquares = 9;  //deafult sets number of free squares to 9 because game has not started
       gbc = new GridBagConstraints();  //initalizes constraints
       gbc.fill = GridBagConstraints.HORIZONTAL;    //makes it fill frame
       
       //menu item for new game, by default from main menu will take you to 
       //regular game mode p1 vs p2, iif called durign a game will
       //refresh the board
       restartGame = new JMenuItem(new AbstractAction("New Game") {
           public void actionPerformed(ActionEvent e) {
               if (menuPanel.isVisible()){
                   menuPanel.setVisible(false);
                   f.remove(menuPanel);
                   createBoard();
               }else{
                   createBoard();
               }
           }
       });
       //creates shortcut key of CTRL+N
       restartGame.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,java.awt.Event.CTRL_MASK));
       
       //menu item to go back to main menu, disabled at main menu
       goMainMenu = new JMenuItem(new AbstractAction("Main Menu") {
           public void actionPerformed(ActionEvent e) {
               f.remove(panel);
               mainMenu();
           }
       });
       //creates shortcut key of CTRL+M
       goMainMenu.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M,java.awt.Event.CTRL_MASK));
       
       //menu item to quit the game, closes frame
       quitGame = new JMenuItem(new AbstractAction("Quit Game") {
           public void actionPerformed(ActionEvent e) {
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
           }
       });
       //creates shortcut key of CTRL+Q
       quitGame.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q,java.awt.Event.CTRL_MASK));
       
       
       //adds all menu items to the menu
       menu.add(restartGame);
       menu.add(goMainMenu);
       menu.add(quitGame);
       //adds the game menu to the menubar
       menubar.add(menu);
       //adds menubar to frame
       f.setJMenuBar(menubar);
       
       //calls method to set up main menu screen
       mainMenu();
       
       //close frame on X
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //automatic formatting of frame to look nice
       f.pack();
       //make the frame visible
       f.setVisible(true);
   }
   
   /**
    * Method sets up the main menu screen and adds it to the main menu
    * panel which is then displayed in frame
    */
   private void mainMenu() 
   {
       //when user returns to main menu all settings go back to deafult (except sound)
       greekMode = false;
       compGame = false;
       goMainMenu.setEnabled(false);    //main menu item in Game menu is disabled because already there
       menuPanel.removeAll();   //removes any content previously on the menu panel in case user is visiting menu again, mainly for formatting reasons
       menuPanel.setLayout(new GridBagLayout());    //uses grid bag layout for formatting
       //players scores reset upon visiting the main menu
       score1 = 0;
       score2 = 0;
       
       //title of main menu, formats it
       JLabel mainMenu = new JLabel("<html><u>Main Menu</u></html>");
       mainMenu.setFont(new Font("Arial", Font.PLAIN, 20));
       
       //grid bag constrains for main menu buttons
       gbc.gridy = 0;
       gbc.insets = new Insets(10, 65, 5, 10);
       //add title to menu panel
       menuPanel.add(mainMenu, gbc);
       
       //new game button in menu
       MnewGame = new JButton("New Game");
       MnewGame.setPreferredSize(new Dimension(200, 50));
       MnewGame.setFocusPainted(false); //removes square around text, looks cleaner
       MnewGame.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               f.remove(menuPanel);
               createBoard();
            }
       });
       
       //game agaisnt computer button
       Mcomputer = new JButton("vs Computer");
       Mcomputer.setPreferredSize(new Dimension(200, 50));
       Mcomputer.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               f.remove(menuPanel);
               compGame = true;
               createBoard();
            }
       });
       
       //game in greek mode with images
       Mgreek = new JButton("Ancient Greek Version");
       Mgreek.setPreferredSize(new Dimension(200, 50));
       Mgreek.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               f.remove(menuPanel);
               greekMode = true;
               createBoard();
            }
       });
       
       //button for sound on or off
       Msound = new JButton(soundString);
       Msound.setPreferredSize(new Dimension(200, 50));
       Msound.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (Msound.getText() == "Sound: ON"){
                   soundString = "Sound: OFF";
               }else{
                   soundString = "Sound: ON";
               }
               Msound.setText(soundString); //changes text of button
               Msound.setFocusPainted(false);
            }
       });
       
       //quit button, closes frame, same as menu item quit
       Mquit =new JButton("Quit");
       Mquit.setPreferredSize(new Dimension(200, 50));
       Mquit.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
       });
       
       //formats each button and adds to menu panel
       gbc.insets = new Insets(10, 10, 10, 10);
       gbc.gridy = 1;
       menuPanel.add(MnewGame, gbc);
       gbc.gridy = 2;
       menuPanel.add(Mcomputer, gbc);
       gbc.gridy = 3;
       menuPanel.add(Mgreek, gbc);
       gbc.gridy = 4;
       menuPanel.add(Msound, gbc);
       gbc.gridy = 5;
       menuPanel.add(Mquit, gbc);
       
       //adds menu panel to frame
       f.add(menuPanel);
       //makes it visible
       menuPanel.setVisible(true);
       //formats it
       f.pack();
   }
   
   /**
    * Method creates the game board and changes it based on game mode 
    * selected. Adds board to panel and displays it in the frame.
    * 
    */
   private void createBoard() 
   {
       goMainMenu.setEnabled(true); //enables the main menu item in menu Game
       panel.removeAll();   //removes anything previously on this panel in case user has created a board before, helps with formatting
       panel.setLayout(new GridBagLayout());    //use grid bag layout for panel
       numFreeSquares = 9;  //sets free squares to 9 because game has started
       board = new JButton[3][3];   //3x3 JButtons for board
       
       //If greek mode start player off with psi otherwise regular X
       if (greekMode == true){
           currentImg = newPsi;
           player = PLAYER_PSI;
       }else{
           player = PLAYER_X;
       }
       
       //grid bag constraints for board
       gbc.gridwidth = 1;
       gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.insets = new Insets(0, 0, 0, 0); //no spcaes between buttons
       //creates 9 buttons, adds listener, formats to correct positions, changes size, adds to panel
       for (int i=0; i<3; i++){
           for (int j=0; j<3; j++){
               board[i][j] = null;
               board[i][j] = new JButton();
               board[i][j].addActionListener(this);
               gbc.gridx = i;
               gbc.gridy = j;
               board[i][j].setPreferredSize(new Dimension(100, 100));
               panel.add(board[i][j], gbc);
            }
       }
      
       //label for status of game
       status = new JLabel();
       status.setFont(new Font("Arial", Font.PLAIN, 16));
       
       //updates status
       gameStatus();
       
       //positions status
       gbc.gridwidth = 3;
       gbc.gridx = 0;
       gbc.gridy = 4;
       
       //adds status to panel
       panel.add(status, gbc);
       
       //label for the scores
       displayScore1 = new JLabel();
       displayScore1.setFont(new Font("Arial", Font.PLAIN, 16));
       
       displayScore2 = new JLabel();
       displayScore2.setFont(new Font("Arial", Font.PLAIN, 16));
       
       //updates the scores
       updateScore();
       
       //positons the scores and adds them to the panel
       gbc.gridy = 5;
       panel.add(displayScore1, gbc);
       
       gbc.gridy = 6;
       panel.add(displayScore2, gbc);
       
       //adss panel to frame
       f.add(panel);
       
       //formats frame
       f.pack();
   }
   
   /**
    * Method listening for buttons pushed on the board.
    * 
    */
   public void actionPerformed(ActionEvent e)
   {  
       //plays audi clip every time board button is pushed if sound is on
       if (soundString == "Sound: ON"){
           audioClip.play();
       }
       
       //gets source of the push and puts it in object source
       Object source = e.getSource();
       
       //checks if the source is a JButton
       if (source instanceof JButton) {
           //assigns teh button pushed to a temp button to work with
           JButton button = (JButton) source;
           button.setFont(new Font("Arial", Font.PLAIN, 40));
           button.setText(player);  //sets text of button to current player
           //if greek mode is on then puts a picture in the button
           if (greekMode == true){
               button.setFont(new Font("Arial", Font.PLAIN, 1));
               button.setIcon(new ImageIcon( currentImg ));
           }
           button.setEnabled(false);    //disbales button
           button.setFocusPainted(false);   //removes line around text, cleaner
           numFreeSquares--;    //deducts from free squares
       }
       
       //loops through each board button to see which button was pushed
       for(int i=0; i<board.length; i++){
           for(int j=0; j<board[i].length; j++){
               if(e.getSource() == board[i][j]){
                   //calls method to check if there is a winner
                   if (haveWinner(i, j)){
                       //if true then game is over
                       status.setText("Game Over! "+player+" wins!");
                       //disbale all buttons
                       if (numFreeSquares != 9){
                           disable();
                       }
                       //add to score of the player who won
                       if (player == PLAYER_X||player == PLAYER_PSI){
                           score1++;
                           updateScore();   //show updated score
                       }else if (player == PLAYER_O||player == PLAYER_OMEGA){
                           score2++;
                           updateScore();
                       }
                       //calls method to create new game button for convenience
                       newGameBtn();
                   //if theres no winner and all buttons have been pressed
                   }else if (numFreeSquares == 0){
                       status.setText("Game Over! It's a Tie!");    //output a tie
                       newGameBtn();    //create new game button
                   //if game is still going on
                   }else{
                       //switch players dependign on mode
                       if (greekMode == true){
                           if (currentImg == newPsi){
                               currentImg = newOmega;
                           }else{
                               currentImg = newPsi;
                           }
                           if (player == PLAYER_PSI){
                               player = PLAYER_OMEGA;
                           }else{
                               player = PLAYER_PSI;
                           }
                       }else{
                           if (player==PLAYER_X){
                               player=PLAYER_O;
                           }else{ 
                               player=PLAYER_X;
                           }
                       }
                       //update game status of whose turn it is
                       gameStatus();
                       
                       //if its a computer game then automatticaly choose
                       //a button by calling the method randSpace and switch players
                       if (compGame == true){
                           if (compTurn == false){
                               compTurn = true;
                               randSpace();
                           }else{
                               compTurn = false;
                           }
                       }
                   }
               }
            }
        }
   }
   
   /**
    * Returns true if filling the given square gives us a winner, and false
    * otherwise.
    *
    * @param int row of square just set
    * @param int col of square just set
    * 
    * @return true if we have a winner, false otherwise
    */
   private boolean haveWinner(int row, int col) 
   {
       // unless at least 5 squares have been filled, we don't need to go any further
       // (the earliest we can have a winner is after player X's 3rd move).

       if (numFreeSquares>4) {
           return false;
       }

       // Note: We don't need to check all rows, columns, and diagonals, only those
       // that contain the latest filled square.  We know that we have a winner 
       // if all 3 squares are the same, as they can't all be blank (as the latest
       // filled square is one of them).

       // check row "row"
       if ( board[row][0].getText().equals(board[row][1].getText()) &&
            board[row][0].getText().equals(board[row][2].getText()) ){
                return true;
       }
       
       // check column "col"
       if ( board[0][col].getText().equals(board[1][col].getText()) &&
            board[0][col].getText().equals(board[2][col].getText()) ){
                return true;
       }
       // if row=col check one diagonal
       if (row==col)
          if ( board[0][0].getText().equals(board[1][1].getText()) &&
               board[0][0].getText().equals(board[2][2].getText()) ) {
                return true;
       }
       // if row=2-col check other diagonal
       if (row==2-col)
          if ( board[0][2].getText().equals(board[1][1].getText()) &&
               board[0][2].getText().equals(board[2][0].getText()) ) {
                return true;
       }
       // no winner yet
       return false;
   }
   
   /**
    * Method disbales all the buttons on the board once game is done
    */
   private void disable() 
   {
       //loops through buttons and disbales each one
       for (int i=0; i<3; i++){
           for (int j=0; j<3; j++){
               board[i][j].setEnabled(false);
           }
       }
   }
   
   /**
    * Method updates the status of the game and shows whose turn it is.
    */
   private void gameStatus() 
   {
       status.setText("Game in Progress..." + player +"'s Turn...");
   }
   
   /**
    * Method creates a new game button on the screen once game is done for
    * convenience.
    */
   private void newGameBtn() 
   {
       newGame = new JButton("New Game");
       newGame.setFocusPainted(false);
       //creates a new board if pushed
       newGame.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               createBoard();
            }
       });
       
       //formats its at bottom of the panel and adds it to panel
       gbc.gridwidth = 1;
       gbc.gridx = 1;
       gbc.gridy = 7;
       panel.add(newGame,gbc);
       f.pack();    //format frame with new button
   }
   
   /**
    * Method chooses a random space to place O if playing against computer.
    */
   private void randSpace()
   {
       //new random variable
       Random r = new Random();
       //intialize variables for array
       int i = -1;
       int j = -1;
       //keep looping through random numbers until the combination of
       //numbers equals to a button that is not pressed
       do{
           i = r.nextInt(3);
           j = r.nextInt(3);
       }while(!board[i][j].isEnabled());
       //push the valid button
       board[i][j].doClick();
   }
   
   /**
    * Method updates the score once the game ends. Scores reset once a
    * game mode is left. Depending on mode proper symbol will be used.
    */
   private void updateScore()
   {
       if (greekMode == true){
           displayScore1.setText("<html><br>Player "+PLAYER_PSI+": " + score1+"</html>");
           displayScore2.setText("Player "+PLAYER_OMEGA+": " + score2);
       }else{
           displayScore1.setText("<html><br>Player "+PLAYER_X+": " + score1+"</html>");
           displayScore2.setText("Player "+PLAYER_O+": " + score2);
        }
   }
}
