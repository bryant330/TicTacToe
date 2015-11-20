package tictactoe;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// reference: http://thanhcs.blogspot.my/2015/06/java-share-soure-code-game-tictactoe.html
// reference: http://algojava.blogspot.my/2012/05/tic-tac-toe-game-swingjava.html
public class PvC implements ActionListener {

	// Instance Variables
	private JFrame window = new JFrame("Tic-Tac-Toe");
	private int[][] winCombinations = new int[][] { // 2D arrays: int[7][3]
	{ 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // horizontal
			{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // vertical
			{ 0, 4, 8 }, { 2, 4, 6 } // diagonal
	};
	private JButton buttons[] = new JButton[9];
	private JButton button[][] = new JButton[3][3];
	private String letter = "";
	private int count = 0;
	private int choice = 0;
	private boolean win = false;
	private int buttonNum = 0;
	private int[][] board = new int[3][3];
	private boolean playAgain = true;
	
	public PvC() {

		// choose who to start first
		setupOptionPane();
		// create window
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(3, 3));

		// add buttons to window and action listeners to buttons
		/*for (int i = 0; i <= 8; i++) {
			buttons[i] = new JButton();
			window.add(buttons[i]);
			buttons[i].addActionListener(this);
			buttons[i].setFocusable(false);
			buttons[i].setFont(new Font(null,Font.PLAIN,100));
		}*/
		//add buttons to window in another way
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				button[i][j] = new JButton();
				window.add(button[i][j]);
				button[i][j].addActionListener(this);
				button[i][j].setFocusable(false);
				button[i][j].setFont(new Font(null,Font.PLAIN,100));
			}
		}
		
		// show the window
		window.setVisible(true);
		
		/*while(playAgain){
			gameLoop();
			playAgain = false;
		}*/
		
	}
	
	public int[][] reset(){
		int[][] newBoard = new int[3][3];
		for(int i = 0; i< 3; i++){
			for(int j = 0; j < 3; j++){
				newBoard[i][j] = -1;
			}
		}
		return newBoard;
	}
	
	public void gameLoop() {
		checkWinner();
		int[][] board = reset();
		int turn = 1;
		int AI;
			int pos = -1;
			
			// if computer start first
			if (choice == 1) {
				AI = 1;
				pos = minimax(board, AI);
				System.out.println("pos is " + pos);
				buttons[pos-1].setText("O");
				buttons[pos-1].setEnabled(false);
				count++;
			}
			else{
				System.out.println("player start 1st");
			}
		
			

	
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
			//computerButton = maxMove();
			computerButton = CPU.doMove(buttons[0], buttons[1], buttons[2], buttons[3], buttons[4], buttons[5], buttons[6], buttons[7], buttons[8]) - 1;
			buttons[computerButton].setText("O");
			buttons[computerButton].setEnabled(false);
			count++;
			System.out.println("this is computer move, this is the " + count +" button");
			}
	}
	
	public boolean checkWin(){
		for(int i=0; i<=7; i++){
			if (buttons[winCombinations[i][0]].getText().equals(
					buttons[winCombinations[i][1]].getText())
					&& buttons[winCombinations[i][1]].getText().equals(
							buttons[winCombinations[i][2]].getText())
					&& buttons[winCombinations[i][0]].getText() != "")
				return true;
			}
		return false;
	}
	
	public void checkWinner(){
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
			//System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		JButton pressedButton = (JButton) a.getSource();
		pressedButton.setText("X");
		pressedButton.setEnabled(false);
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(a.getSource() == button[i][j]){
					System.out.println("button location is [" + i +"][" + j + "]");
				}
			}
		}
		count++;
		
		
		//AI();
		//checkWinner();
	}
	
	public int minimax(int board[][], int piece) {
		int bestMove[] = new int[2];
		if (piece == 1) {
			bestMove = maxMove(board,piece, 0);
			return bestMove[0];
		} else {
			bestMove = minMove(board, piece, 0);
			return bestMove[0];
		}
	}
	
	public int pos(int i, int j) {
		return (i*3) + (j+1);
	}
	
	public int[][] copyBoard(int[][] board) {
		int[][] newBoard = new int[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j< 3; j++){
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}
	
	public int[] maxMove(int[][] board, int piece, int depth) {
		int bestMove[] = new int[2];
		int newBoard[][] = copyBoard(board);
		
		if(gameOver(board)){
			return maxEvaluation(board,depth);
		}
		int first = 0;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(newBoard[i][j] == -1) {
					newBoard[i][j] = piece;
					int value = minMove(newBoard, getOpponent(piece), depth + 1)[1];
					if(first == 0) {
						bestMove[0] = pos(i,j);
						bestMove[1] = value;
						first++;
					}
					if (bestMove[1] < value) {
						bestMove[0] = pos(i,j);
						bestMove[1] = value;
					}
					newBoard[i][j] = -1;
				}
			}
		}
		
		return bestMove;
	}
	
	public int[] minMove(int [][]board, int piece, int depth) {
		int bestMove[] = new int[2];
		int newBoard[][] = copyBoard(board);
		
		if(gameOver(board)) {
			return maxEvaluation(board,depth);
		}
		
		int first = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (newBoard[i][j] == -1){
					newBoard[i][j] = piece;
					int value = maxMove(newBoard, getOpponent(piece), depth+1)[1];
					//System.out.println(value);
					if (first == 0) {
						bestMove[0] = pos(i,j);
						bestMove[1] = value;
						first++;
					}
					if(bestMove[1] > value) {
						bestMove[0] = pos(i,j);
						bestMove[1] = value;
					}
					
					newBoard[i][j] = -1;
				}
			}
		}
		
		return bestMove;
	}
	
	public int getOpponent(int you){
		if(you == 1)
			return 0;
		return 1;
	}
	
	public int[] maxEvaluation(int[][] board, int depth) {
		int ans[] = new int[2];
		ans[0] = 0;
		ans[1] = 0;
		if(checkWin(board,1)){
			ans[1] = 2*(10-depth);
			return ans;
		}
		if(checkWin(board,0)){
			ans[1] = -2*(10-depth);
			return ans;
		}
		return ans;
	}
	
	public boolean checkWin(int[][] board, int AI) {
		for(int i = 0; i<board.length;i++) {
			if((board[i][0] == AI) && (board[i][1] == AI) && (board[i][2] == AI)){
				return true;
			}
			if((board[0][i] == AI) && (board[1][i] == AI) &&(board[2][i] == AI)){
				return true;
			}
		}
		
		//diagonal
		if((board[0][0] == AI) && (board[1][1] == AI) && (board[2][2] == AI)){
			return true;
		}
		if((board[0][2] == AI) && (board[1][1] == AI) && (board[2][0] == AI)){
			return true;
		}
		
		return false;
	}
	
	public boolean gameOver(int[][] board) {
		if(checkWin(board,1))
			return true;
		if(checkWin(board,0))
			return true;
		//if((checkDraw(board,1)) && (checkDraw(board,0)))
			//return true;
		
		return false;
	}
	
	
}
