package tictactoe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenuPanel extends JFrame implements ActionListener{
	
	private JLabel label;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	
	public MainMenuPanel(){
		this.setLayout(new GridLayout(5,1));
		this.setTitle("Tic-Tac-Toe");
		label = new JLabel("Please select:");
		this.add(label);
		btn1 = new JButton("Player vs Player");
		btn2 = new JButton("Player vs Computer");
		btn3 = new JButton("Computer vs Computer");
		this.add(btn1);
		this.add(btn2);
		this.add(btn3);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(400, 400);
		btn1.setSize(getMinimumSize());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == btn1)
			new TicTacToe();
		
	}
	

}
