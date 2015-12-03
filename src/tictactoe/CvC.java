package tictactoe;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CvC implements ActionListener {

	// Instance Variables
	private JFrame window = new JFrame("Tic-Tac-Toe");
	private JButton buttons[][] = new JButton[3][3];
	private int count = 0;
	private int choice = 0;
	private boolean playAgain = true;
	private int playAgainChoice = 0;
	
	public CvC() {

		// choose who to start first
		setupOptionPane();
		// create window
		window.setSize(500, 500);
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
	
	public void gameLoop() {
		int[][] board = reset();
		int turn = 1;
		int AI;
		int pos = -1;
		count = 0;
		//comp 2 start first
		if (choice == 1) {
			AI = 1;
			 while (((!checkWin(board,0)) && (!checkWin(board,1))) || ((!checkDrawV2(board,0)) && (!checkDrawV2(board,1)))){
				if (turn == AI) {
					System.out.println("computer 2(O)'s turn, turn is " + turn +"(1) now");
					if(count == 0){
						Random r = new Random();
						pos = r.nextInt(9) + 1;
					}
					else{
						pos = minimax(board, AI);
					}
					System.out.println("pos is " + pos);
					count++;
				}
				else{
					System.out.println("computer 1(X)'s turn, turn is " + turn +"(0) now");
					pos = minimax(board,turn);
					System.out.println("pos is " + pos);
					count++;
				}
				
				int i = (pos-1) / 3;
				int j = (pos-1) % 3;
				board[i][j] = turn;
				if (turn == 1) {
					buttons[i][j].setText("O");
					buttons[i][j].setEnabled(false);
				}
				else {
					buttons[i][j].setText("X");
					buttons[i][j].setEnabled(false);
				}
				turn = getOpponent(turn);
				if(gameOver(board)) break;
			
			}
		}
		//comp 1 start first
		else {        	 
			AI = 1;
			while (((!checkWin(board,0)) && (!checkWin(board,1))) || ((!checkDrawV2(board,0)) && (!checkDrawV2(board,1)))){
				if (turn == AI) {
					System.out.println("computer 1(X)'s turn, turn is " + turn +"(1) now");
					if(count == 0){
						Random r = new Random();
						pos = r.nextInt(9) + 1;
					}
					else{
						pos = minimax(board, AI);
					}
					System.out.println("pos is " + pos);
					count++;
				}
				else{
					System.out.println("computer 2(O)'s turn, turn is " + turn +"(0) now");
					pos = minimax(board,turn);
					System.out.println("pos is " + pos);
					count++;
				}
		
				int i = (pos-1) / 3;
				int j = (pos-1) % 3;
				board[i][j] = turn;
				if (turn == 1) {
					buttons[i][j].setText("X");
					buttons[i][j].setEnabled(false);
				}
				else {
					buttons[i][j].setText("O");
					buttons[i][j].setEnabled(false);
				}
				turn = getOpponent(turn);
				if(gameOver(board)) break;
			} 
		}
		
		checkWinner(board);
	}

	public void setupOptionPane() {
		Object[] options = { "Computer 1 (X)", "Computer 2 (O)" };
		choice = JOptionPane.showOptionDialog(window, "Who will start first?",
				"Please choose", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, null); // computer 1 -> 0, computer 2 -> 1
		if (choice == -1) {
			JOptionPane.showMessageDialog(window,
				    "YOU HAVE TO CHOOSE WHO TO START FIRST! :)",
				    "PLEASE CHOOSE",
				    JOptionPane.WARNING_MESSAGE);
			setupOptionPane();
		}	
	}
	
	
	public void checkWinner(int board[][]) {
		if (checkWin(board, 0)) {
			String result = "computer 1";
			setupPlayAgainOptionPane(result);
			if(playAgainChoice == 0) {
				playAgain = true;
				gameLoop();
			}
			else
				this.window.dispose();
		}
		else if (checkWin(board, 1)) {
			String result = "computer 2";
			setupPlayAgainOptionPane(result);
			if(playAgainChoice == 0) {
				playAgain = true;
				gameLoop();
			}
			else
				this.window.dispose();
		}
		else{
			String result = "draw";
			setupPlayAgainOptionPane(result);
			if(playAgainChoice == 0) {
				playAgain = true;
				gameLoop();
			}
			else
				this.window.dispose();
		}
		
	}
	
	public void setupPlayAgainOptionPane(String result) {
		Object[] options = { "Yes", "No" };
		if (result == "computer 1"){
			playAgainChoice = JOptionPane.showOptionDialog(window,
					"Computer 1 WON! Play Again?", "GAME OVER",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, null);
		}
		else if (result == "computer 2"){
			playAgainChoice = JOptionPane.showOptionDialog(window,
					"Computer 2 WON! Play Again?", "GAME OVER",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, null);
		}
		else
			playAgainChoice = JOptionPane.showOptionDialog(window,
					"Draw! Play Again?", "GAME OVER",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, null);
	}

	@Override
	public void actionPerformed(ActionEvent a) {
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
				//System.out.println("checkwin 1");
				return true;
			}
			//vertical
			if((board[0][i] == AI) && (board[1][i] == AI) &&(board[2][i] == AI)){
				//System.out.println("checkwin 2");
				return true;
			}
		}
		
		//diagonal
		if((board[0][0] == AI) && (board[1][1] == AI) && (board[2][2] == AI)){
			//System.out.println("checkwin 3");
			return true;
		}
		if((board[0][2] == AI) && (board[1][1] == AI) && (board[2][0] == AI)){
			//System.out.println("checkwin 4");
			return true;
		}
		
		return false;
	}
	
	public boolean checkDrawV2(int[][] board, int AI) {
		if (isFull(board) && (!checkWin(board,AI)) && (!checkWin(board,getOpponent(AI))))
			return true;
		return false;
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
