package tictactoe;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//reference: http://forum.codecall.net/topic/36472-javatutorial-tic-tac-toe/
//reference: http://forum.codecall.net/topic/43209-java-source-code-tic-tac-toe-game/
public class TicTacToe implements ActionListener{
	
	// Instance Variables
	private JFrame window = new JFrame("Tic-Tac-Toe");
	private int[][] winCombinations = new int [][]{   //2D arrays: int[7][3]
			{0, 1, 2}, {3, 4, 5}, {6, 7, 8},  // horizontal
			{0, 3, 6}, {1, 4, 7}, {2, 5, 8},  // vertical
			{0, 4, 8}, {2, 4, 6}  			  // diagonal
	};
	private JButton buttons[] = new JButton[9];
	private String letter = "";
	private String player = "";
	private int count = 0;
	private int choice = 0;
	private boolean win = false;
	
	public static void main(String[] args){
		new MainMenuPanel();
		//new TicTacToe();
	}
	
	public void setupOptionPane() {
		Object[] options = { "Player 1 (X)", "Player 2 (O)" };
		choice = JOptionPane.showOptionDialog(window,
				"Who will start first?", "Please choose",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, null); //player1 = 0, player2 = 1
		System.out.println(choice);
	}
	
	public TicTacToe(){
		//choose who to start first
		setupOptionPane();
		//create window
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(3,3));
		
		//add buttons to window and action listeners to buttons
		for(int i = 0; i <= 8; i++){
			buttons[i]= new JButton();
			window.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		
		//show the window
		window.setVisible(true);
	}
	
	public String getTurn(){
		if (choice == 0){
			if (count %2 != 0)
				return "X";
			else
				return "O";
		}
		else if (choice == 1){
			if (count %2 != 0)
				return "O";
			else
				return "X";
		}
		else
			return "";
		
	}
	
	public void checkWinner(){
		for(int i=0; i<=7; i++){
			if (buttons[winCombinations[i][0]].getText().equals(
					buttons[winCombinations[i][1]].getText())
					&& buttons[winCombinations[i][1]].getText().equals(
							buttons[winCombinations[i][2]].getText())
					&& buttons[winCombinations[i][0]].getText() != ""){
				win = true;
			}
		}
		
		if (win == true){
			if(letter == "X")
				player = "Player 1";
			else if (letter == "O")
				player = "Player 2";
				
			JOptionPane.showMessageDialog(null, player + " WON!");
			System.exit(0);
		}
		else if (count == 9 && win == false){
			JOptionPane.showMessageDialog(null, "NOBODY WIN!");
			System.exit(0);
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent a) {
		count++;
		letter = getTurn();
		JButton pressedButton = (JButton) a.getSource();
		pressedButton.setText(letter);
		pressedButton.setEnabled(false);
		checkWinner();
	}

}
