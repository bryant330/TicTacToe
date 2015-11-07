package tictactoe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PvC implements ActionListener {

	// Instance Variables
	private JFrame window = new JFrame("Tic-Tac-Toe");
	private int[][] winCombinations = new int[][] { // 2D arrays: int[7][3]
	{ 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // horizontal
			{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // vertical
			{ 0, 4, 8 }, { 2, 4, 6 } // diagonal
	};
	private JButton buttons[] = new JButton[9];
	private String letter = "";
	private String player = "";
	private int count = 0;
	private int choice = 0;
	private boolean win = false;
	private int buttonNum = 0;
	
	public PvC() {

		// choose who to start first
		setupOptionPane();
		// create window
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(3, 3));

		// add buttons to window and action listeners to buttons
		for (int i = 0; i <= 8; i++) {
			buttons[i] = new JButton();
			window.add(buttons[i]);
			buttons[i].addActionListener(this);
			buttons[i].setFocusable(false);
		}
		
		// show the window
		window.setVisible(true);
		checkTurn(); //can change to AI

	}
	

	public void setupOptionPane() {
		Object[] options = { "Player(X)", "Computer(O)" };
		choice = JOptionPane.showOptionDialog(window, "Who will start first?",
				"Please choose", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, null); // player = 0, computer = 1
			
	}
	
	public void AI(){
		int computerButton;
		if (count <=9){
			computerButton = CPU.doMove(buttons[0], buttons[1], buttons[2], buttons[3], buttons[4], buttons[5], buttons[6], buttons[7], buttons[8]) - 1;
			buttons[computerButton].setText("O");
			buttons[computerButton].setEnabled(false);
			count++;
			System.out.println("this is computer move, this is the " + count +" button");
			}
	}
	
	public void checkTurn() {
		// if computer start first
		if (choice == 1) {
			buttonNum = CPU.randomMove();
			buttons[buttonNum].setText("O");
			buttons[buttonNum].setEnabled(false);
			count++;
			System.out.println("this is computer first " + count);
		}
		else
			System.out.println("this is player first " + count);

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
			if(letter == "X"){
				JOptionPane.showMessageDialog(null,"PLAYER WON!");
				System.exit(0);
			}
			else{
				JOptionPane.showMessageDialog(null,"COMPUTER WON!");
				System.exit(0);
			}
		}
		else if (count == 9 && win == false){
			JOptionPane.showMessageDialog(null, "DRAW!");
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		JButton pressedButton = (JButton) a.getSource();
		pressedButton.setText("X");
		pressedButton.setEnabled(false);
		count++;
		AI();
		checkWinner();
	}

}
