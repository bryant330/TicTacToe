package tictactoe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TicTacToe implements ActionListener{
	
	// Instance Variables
	private JFrame window = new JFrame("Tic-Tac-Toe");
	private JButton button1 = new JButton("");
	private JButton button2 = new JButton("");
	private JButton button3 = new JButton("");
	private JButton button4 = new JButton("");
	private JButton button5 = new JButton("");
	private JButton button6 = new JButton("");
	private JButton button7 = new JButton("");
	private JButton button8 = new JButton("");
	private JButton button9 = new JButton("");
	
	public static void main(String[] args){
		new TicTacToe();
	}
	
	public TicTacToe(){
		//create window
		window.setSize(300, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridLayout(3,3));
		
		//add buttons to window
		window.add(button1);
		window.add(button2);
		window.add(button3);
		window.add(button4);
		window.add(button5);
		window.add(button6);
		window.add(button7);
		window.add(button8);
		window.add(button9);
		
		//add action listeners to buttons
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		
		//show the window
		window.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent a) {
		System.out.println("button is pressed");
	}

}
