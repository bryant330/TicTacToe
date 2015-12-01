package tictactoe;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// reference: http://thanhcs.blogspot.my/2015/06/java-share-soure-code-game-tictactoe.html
// reference: http://algojava.blogspot.my/2012/05/tic-tac-toe-game-swingjava.html
public class PvC implements ActionListener {

	// Instance Variables
	private JFrame window = new JFrame("Tic-Tac-Toe");
	private JButton buttons[][] = new JButton[3][3];
	private String letter = "";
	private int count = 0;
	private int choice = 0;
	private int pos = -1;
	private boolean win = false;
	private int[][] board = new int[3][3];
	private boolean playAgain = true;
	private int turn;
	//private boolean compTurn;
	
	public PvC() {
		// choose who to start first
		setupOptionPane();
		// create window
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(3, 3));

		//add buttons to window in another way
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				buttons[i][j] = new JButton();
				window.add(buttons[i][j]);
				buttons[i][j].addActionListener(this);
				buttons[i][j].setFocusable(false);
				buttons[i][j].setFont(new Font(null,Font.PLAIN,100));
			}
		}
		
		// show the window
		window.setVisible(true);
		
		while(playAgain){
			board = reset();
			gameLoop();
			playAgain = false;
		}
		
		
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
	
	public void gameLoopV2() {
		int AI = 1;
		if (AI == turn) {
			pos = minimax(board, AI);
			int i = (pos-1)/3;
			int j = ((pos-1) % 3);
			board[i][j] = turn;
			buttons[i][j].doClick();
		}
		else
		{
			System.out.println("waiting for player to choose v2");
		}
	}
	
	
	public void gameLoop() {
		int AI;
		turn = 1;
		if (choice == 1)
			AI = 1;
		else
			AI = 0;
		//while((!checkWin(board,0))&&((!checkWin(board,1)) || (!checkDraw(board,0))) && (!checkDraw(board,1)) && (!isFull(board))){
			// if computer start first
			if (turn == AI) {
				if(count == 0){
					Random r = new Random();
					pos = r.nextInt(9) + 1;
					count++;
				}
				else{
					pos = minimax(board, AI);
					count++;
				}
				int i = (pos-1)/3;
				int j = ((pos-1) % 3);
				board[i][j] = turn;
				buttons[i][j].doClick();
			}
			else{
				System.out.println("waiting for player to choose");
				count++;
				//call actionperformed
				//pos = minimax(board,turn); //take the user input
				//int i = (pos-1)/3;
				//int j = ((pos-1) % 3);
				//board[i][j] = turn;
				//buttons[i][j].doClick();
			}
			System.out.println("turn is " + turn + " now");
		//}
	}

	public void setupOptionPane() {
		Object[] options = { "Player(X)", "Computer(O)" };
		choice = JOptionPane.showOptionDialog(window, "Who will start first?",
				"Please choose", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, null); // player = 0, computer = 1
			
	}
	
	public boolean getTurn(int turn){
		if (turn == 1)  //AI's turn
			return true; 
		else
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
		if (turn == 1){
			pressedButton.setText("O");
			pressedButton.setEnabled(false);
			turn = getOpponent(turn);
		}
		else {
			pressedButton.setText("X");
			pressedButton.setEnabled(false);
			turn = getOpponent(turn);
		}
		System.out.println("turn in action perform is " + turn + " now");
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(a.getSource() == buttons[i][j]){
					pos = getUserInput(i,j) + 1;
					System.out.println("This is the button number: " + pos);
				}
			}
		}
		gameLoopV2();
		
	}
	
	public boolean isFull(int[][] board) {
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(board[i][j] == -1)
					return false;
			}
		}
		return true;
	}
	
	public int getUserInput(int i, int j) {
		if (i == 0 && j == 0)
			return 0;
		else if (i == 0 && j == 1)
			return 1;
		else if (i == 0 && j == 2)
			return 2;
		else if (i == 1 && j == 0)
			return 3;
		else if (i == 1 && j == 1)
			return 4;
		else if (i == 1 && j == 2)
			return 5;
		else if (i == 2 && j == 0)
			return 6;
		else if (i == 2 && j == 1)
			return 7;
		else if (i == 2 && j == 2)
			return 8;
		else
			return -1;
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
			//horizontal
			if((board[i][0] == AI) && (board[i][1] == AI) && (board[i][2] == AI)){
				return true;
			}
			//vertical
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
	
	public boolean checkDrawV2(int[][] board, int AI) {
		if (isFull(board) && (!checkWin(board,AI)) && (!checkWin(board,getOpponent(AI))))
			return true;
		return false;
	}
	
	public boolean checkDraw(int[][] board, int AI) {
		int opp = getOpponent(AI);
		//board length is 3
		for (int i = 0; i < board.length; i++) {
			if(((board[i][0]!=opp) && (board[i][1]!=opp) && (board[i][2]!=opp)) && ((board[i][0]!=opp) || (board[i][1]!=opp) || (board[i][2]!=opp))){
				return false;
			}
			if(((board[0][i]!=opp) && (board[1][i]!=opp) && (board[2][i]!=opp)) && ((board[0][i]!=opp) || (board[1][i]!=opp) || (board[2][i]!=opp))){
				return false;
			}
		}
		if(((board[0][0]!=opp) && (board[1][1]!=opp) && (board[2][2]!=opp)) && ((board[0][0]!=opp) || (board[1][1]!=opp) || (board[2][2]!=opp))){
			return false;
		}
		if(((board[0][2]!=opp) && (board[1][1]!=opp) && (board[2][0]!=opp)) && ((board[0][2]!=opp) || (board[1][1]!=opp) || (board[2][0]!=opp))){
			return false;
		}
		return true;
	}
	
	public boolean gameOver(int[][] board) {
		if(checkWin(board,1))
			return true;
		if(checkWin(board,0))
			return true;
		if((checkDrawV2(board,1)) && (checkDrawV2(board,0)))
			return true;
		
		return false;
	}
	
	
}
