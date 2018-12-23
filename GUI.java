package practicePerformaceTask;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GUI extends JFrame {

	// Setup JFrame  
	/**
	 * @author Aaron Vigal
	 */
	protected static JButton[][] squares = new JButton[8][8];
	JPanel boardPanel =  new JPanel();
	JPanel textPanel =  new JPanel();
	JLabel label1 = new JLabel("Your text here");
	JLabel label2 = new JLabel("Your text here");
	JLabel label3 = new JLabel("Your text here");

	// Position
	/**
	 * @author Aaron Vigal
	 */
	public int row = 0;
	public int col = 0;
	Container contents = getContentPane();

	// Images
	protected static ImageIcon redNormal = new ImageIcon("1.jpg");
	protected static ImageIcon redKing = new ImageIcon("2.png");
	protected static ImageIcon blackNormal = new ImageIcon("3.png");
	protected static ImageIcon blackKing = new ImageIcon("4.png");
	
	// Game Logic
	public Piece[][] board = new Piece[8][8];
	private int redWins = 0;
	private int blackWins = 0;
	private boolean redTurn = true;
	private int remainingBlackPieces = 0;
	private int remainingRedPieces = 0;
	private boolean firstRun = true;
	private boolean jumped = false;
	
	/**
	 * lines 59-60, 74-89, 112-113
	 * @author Aaron Vigal
	 */
	public void createBoard(){
		contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
		ButtonHandler buttonHandler = new ButtonHandler();
		if(firstRun){
			contents.add(textPanel);
			contents.add(boardPanel);
			boardPanel.setLayout(new GridLayout(8, 8));
			textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
//			textPanel.setSize(800, 100);
//			boardPanel.setSize(800, 800);
			textPanel.add(label1);
			textPanel.add(label2);
			textPanel.add(label3);
		}
		for(int row1 = 0; row1 < squares.length; row1++){
			for(int col1 = 0; col1 < squares[row1].length; col1++){
				if(firstRun){
					squares[row1][col1] = new JButton();
					board[row1][col1] = new Piece("none", false, col1, row1);
					if((row1 + col1) % 2 == 0){
						squares[row1][col1].setBackground(Color.BLACK);
						squares[row1][col1].setOpaque(true);
						squares[row1][col1].setBorderPainted(false);
					}
					else{
						squares[row1][col1].setBackground(Color.WHITE);
						squares[row1][col1].setOpaque(true);
						squares[row1][col1].setBorderPainted(false);
					}
					boardPanel.add(squares[row1][col1]);
					squares[row1][col1].addActionListener(buttonHandler);
				}
				
				//putting the pieces on the board
				if(((row1 == 0 || row1 == 2) && (col1 % 2 == 1))  || ((row1 == 1) && (col1 % 2 == 0))){
					board[row1][col1].setColor("black");
					squares[row1][col1].setIcon(blackNormal);
				}
				else if(((row1 == 5 || row1 == 7) && (col1 % 2 == 0))  || ((row1 == 6) && (col1 % 2 == 1))){
					board[row1][col1].setColor("red");
					squares[row1][col1].setIcon(redNormal);
				}
				else{
					board[row1][col1].setColor("none");
					squares[firstX][firstY].setIcon(null);
				}
			}
		}
		remainingRedPieces = 12;
		remainingBlackPieces = 12;
		label1.setText("red wins: " + redWins);
		label2.setText("black wins: " + blackWins);
		label3.setText("Red always goes first.");
		firstRun = false;
		setSize(800, 900);
		setVisible(true);
	}

	/**
	 * listens to when a button is clicked
	 * @author Aaron Vigal
	 */
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			for(int x = 0; x < 8; x++){
				for(int y = 0; y < 8; y++){
					if(source == squares[x][y]){
						proccessClick(x,y);
						gameOver();
						return;
					}
				}
			}
		}
	}
	boolean firstClick = true;
	int firstX = 0;
	int firstY = 0;
	int lastX = 0;
	int lastY = 0;
	private void proccessClick(int x, int y){
		if(firstClick && board[x][y].getColor().equals("none")){
			System.out.println("you must first pick the piece that you would like to move");
			return;
		}
		else if(firstClick){
			firstX = x;
			firstY = y;
		}
		else{
			lastX = x;
			lastY = y;
		}
		Icon temp = squares[firstX][firstY].getIcon();
		if(!firstClick && board[firstX][firstY].getColor() != "none"){
			if(validMoves(board[firstX][firstY], board[lastX][lastY], true)){
				if(board[firstX][firstY].getColor().equals("red")){					
					squares[lastX][lastY].setIcon(temp);
					board[lastX][lastY].setColor("red");
					board[lastX][lastY].setKing(board[firstX][firstY].getKing());
					if(lastX == 0){
						System.out.println("This piece is now a king!");
						squares[lastX][lastY].setIcon(redKing);
						board[lastX][lastY].setKing(true);
					}
				}
				else if(board[firstX][firstY].getColor().equals("black")){
					squares[lastX][lastY].setIcon(temp);
					board[lastX][lastY].setColor("black");
					board[lastX][lastY].setKing(board[firstX][firstY].getKing());
					if(lastX == 7){
						System.out.println("This piece should now be a king!");
						squares[lastX][lastY].setIcon(blackKing);
						board[lastX][lastY].setKing(true);
					}
				}
				if(jumped){
					checkForDoubleJump(board[lastX][lastY]);
				}
				else{	
					System.out.println("not jumped");
					redTurn = !redTurn;					
				}
				jumped = false;
				if(redTurn){
					System.out.println("\nIt is now Red's turn");
					label3.setText("Red's Turn");
				}
				else{
					System.out.println("\nIt is now Black's turn");
					label3.setText("Black's Turn");
				}

				board[firstX][firstY].setColor("none");
				squares[firstX][firstY].setIcon(null);
			}
			else{
				System.out.println("that move is not valid");
				System.out.println("redTurn = " + redTurn);
				firstX = lastX;
				firstY = lastY;
			}
		}
		firstClick = !firstClick;		
		row = x;
		col = y;
	}
	public boolean validMoves(Piece old, Piece selected, boolean remove){
		if(redTurn){
			if(old.getColor() == "red" && selected.getColor() == "none" && squares[selected.getY()][selected.getX()].getBackground().equals(Color.WHITE)){
				if(remove && ((selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1))) && (selected.getY() == (old.getY() - 1))){
					return true;
				}
				else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()+1].getColor() == "black")){
					if(remove){
						jumped = true;
						remainingBlackPieces--;
						board[old.getY()-1][old.getX()+1].setColor("none");
						squares[old.getY()-1][old.getX()+1].setIcon(null);
					}
					return true;
				}
				else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()-1].getColor() == "black")){
					if(remove){
						jumped = true;
						board[old.getY()-1][old.getX()-1].setColor("none");
						squares[old.getY()-1][old.getX()-1].setIcon(null);
						remainingBlackPieces--;
					}
					return true;
				}
				//use the opposite code for a king
				if(old.getKing()){
					if(remove && ((selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1))) && (selected.getY() == (old.getY() + 1))){
						return true;
					}
					else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()-1].getColor() == "black")){
						if(remove){
							jumped = true;
							board[old.getY()+1][old.getX()-1].setColor("none");
							squares[old.getY()+1][old.getX()-1].setIcon(null);
							remainingBlackPieces--;
						}
						return true;
					}
					else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()+1].getColor() == "black")){
						if(remove){
							jumped = true;
							board[old.getY()+1][old.getX()+1].setColor("none");
							squares[old.getY()+1][old.getX()+1].setIcon(null);
							remainingBlackPieces--;
						}
						return true;
					}
				}
			}
		}
		else if(!redTurn){
			if(old.getColor() == "black" && selected.getColor() == "none" && squares[selected.getY()][selected.getX()].getBackground().equals(Color.WHITE)){
				if(remove && ((selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1))) && (selected.getY() == (old.getY() + 1))){
					return true;
				}
				else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()-1].getColor() == "red")){
					if(remove){
						jumped = true;
						board[old.getY()+1][old.getX()-1].setColor("none");
						squares[old.getY()+1][old.getX()-1].setIcon(null);
						remainingRedPieces--;
					}
					return true;
				}
				else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()+1].getColor() == "red")){
					if(remove){
						jumped = true;
						board[old.getY()+1][old.getX()+1].setColor("none");
						squares[old.getY()+1][old.getX()+1].setIcon(null);
						remainingRedPieces--;
					}
					return true;
				}	
				//use the opposite code for a king
				if(old.getKing()){
					if(remove && ((selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1))) && (selected.getY() == (old.getY() - 1))){
						return true;
					}
					else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()+1].getColor() == "red")){
						if(remove){
							jumped = true;
							board[old.getY()-1][old.getX()+1].setColor("none");
							squares[old.getY()-1][old.getX()+1].setIcon(null);
							remainingRedPieces--;
						}
						return true;
					}
					else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()-1].getColor() == "red")){
						if(remove){
							jumped = true;
							board[old.getY()-1][old.getX()-1].setColor("none");
							squares[old.getY()-1][old.getX()-1].setIcon(null);
							remainingRedPieces--;
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	private void gameOver(){
		if(remainingBlackPieces == 0){
			System.out.println("Red Wins!");
			redWins++;
			wait(500);
		}
		else if(remainingRedPieces == 0){
			System.out.println("Black Wins!");
			blackWins++;
			wait(500);
		}
		else
			return;
		firstClick = true;
		clearBoard();
		redTurn = true;
		createBoard();
	}
	
	private void wait(int x){
		try{
		    Thread.sleep(x);
		} 
		catch(InterruptedException ex){
		    Thread.currentThread().interrupt();
		}
	}
	private boolean checkForDoubleJump(Piece current){
		try{
			if(redTurn && current.getKing()){
				if(validMoves(current, board[current.getY()-2][current.getX()-2], false) ||
				validMoves(current, board[current.getY()-2][current.getX()+2], false) ||
				validMoves(current, board[current.getY()+2][current.getX()-2], false) ||
				validMoves(current, board[current.getY()+2][current.getX()+2], false)){
					return true;
				}
			}
			else if(redTurn && !current.getKing()){
				if(validMoves(current, board[current.getY()-2][current.getX()-2], false) ||
				validMoves(current, board[current.getY()-2][current.getX()+2], false)){
					return true;
				}
			}
			else if(!redTurn && current.getKing()){
				if(validMoves(current, board[current.getY()-2][current.getX()-2], false) ||
				validMoves(current, board[current.getY()-2][current.getX()+2], false) ||
				validMoves(current, board[current.getY()+2][current.getX()-2], false) ||
				validMoves(current, board[current.getY()+2][current.getX()+2], false)){
					return true;
				}
			}
			else if(!redTurn && !current.getKing()){
				if(validMoves(current, board[current.getY()+2][current.getX()-2], false) ||
				validMoves(current, board[current.getY()+2][current.getX()+2], false)){
					return true;
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException exception) {		    
			
		}
		redTurn = !redTurn;
		return false;
	}
	public void clearBoard(){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[row].length; col++){
				board[row][col].setColor("none");
				squares[row][col].setIcon(null);
				board[row][col].setKing(false);
			}
		}
	}

}
