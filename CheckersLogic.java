package practicePerformaceTask;

public class CheckersLogic {
//attributes
	public Piece[][] board = new Piece[8][8];  
		//board[y][x]  feels backwards but works best this way. also 0,0 is top left
		//x == column
		//y == row
	private boolean redTurn;
	private boolean jumped;
	private int remainingBlackPieces;
	private int remainingRedPieces;
//constructor
	public CheckersLogic(){
		redTurn = true;
		jumped = false;
		remainingBlackPieces = 0;
		remainingRedPieces = 0;
		makeBoard();
		printBoard();
		testValidMoves();
	}
	
//methods
	public void makeBoard(){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[row].length; col++){
				if(((row == 0 || row == 2) && (col % 2 == 1))  || ((row == 1) && (col % 2 == 0))){
					board[row][col] = new Piece("black", false, col, row);
//					GUI.squares[row][col].setIcon(GUI.blackNormal);
					remainingBlackPieces++;
				}
				else if(((row == 5 || row == 7) && (col % 2 == 0))  || ((row == 6) && (col % 2 == 1))){
					board[row][col] = new Piece("red", false, col, row);
//					GUI.squares[row][col].setIcon(GUI.redNormal);
					remainingRedPieces++;
				}
				else
					board[row][col] = new Piece("none", false, col, row);
			}
		}
	}
	
	// only temporary until paired with the Jframe
	public void printBoard(){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board.length; col++){
				if(board[row][col].getColor().equals("red")){
					if(board[row][col].getKing()){
						System.out.print(" R");						
					}
					else{
						System.out.print(" r");
					}
				}
				else if(board[row][col].getColor() == "black"){
					if(board[row][col].getKing()){
						System.out.print(" B");						
					}
					else{
						System.out.print(" b");
					}
				}
				else{
					System.out.print(" _");
				}
			}
			System.out.println();
		}
	}
	public void testValidMoves(){
		board[3][2].setColor("red");
		board[3][2].setKing(true);
		board[1][0].setColor("none");
		int testRow1 = 5;
		int testCol1 = 6;
		int testRow2 = 4;
		int testCol2 = 7;
		System.out.println("\n\n");
		printBoard();
		redTurn = true;
		System.out.println(board[testRow1][testCol1].getColor());
		System.out.println(board[testRow2][testCol2].getColor());
		System.out.println(validMoves(board[testRow1][testCol1], board[testRow2][testCol2]));
	}
	public boolean validMoves(Piece old, Piece selected){
		if(redTurn){
			if(old.getColor() == "red" && selected.getColor() == "none"){
				if(!jumped && (selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1)) && (selected.getY() == (old.getY() - 1))){
					return true;
				}
				else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()+1].getColor() == "black")){
					jumped = true;
					return true;
				}
				else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()-1].getColor() == "black")){
					jumped = true;
					return true;
				}
				//use the opposite code for if it is a king
				if(old.getKing()){
					if(!jumped && (selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1)) && (selected.getY() == (old.getY() + 1))){
						return true;
					}
					else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()-1].getColor() == "black")){
						jumped = true;
						return true;
					}
					else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()+1].getColor() == "black")){
						jumped = true;
						return true;
					}
				}
			}
		}
		else if(!redTurn){
			if(old.getColor() == "black" && selected.getColor() == "none"){
				if(!jumped && (selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1)) && (selected.getY() == (old.getY() + 1))){
					return true;
				}
				else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()-1].getColor() == "red")){
					jumped = true;
					return true;
				}
				else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() + 2))) && (board[old.getY()+1][old.getX()+1].getColor() == "red")){
					jumped = true;
					return true;
				}
				//use the opposite code for if it is a king
				if(old.getKing()){
					if(!jumped && (selected.getX() == (old.getX() + 1)) || (selected.getX() == (old.getX() - 1)) && (selected.getY() == (old.getY() - 1))){
						return true;
					}
					else if(((selected.getX() == (old.getX() + 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()+1].getColor() == "red")){
						jumped = true;
						return true;
					}
					else if(((selected.getX() == (old.getX() - 2)) && (selected.getY() == (old.getY() - 2))) && (board[old.getY()-1][old.getX()-1].getColor() == "red")){
						jumped = true;
						return true;
					}
				}
			}
		}
		System.out.println("false");
		return false;
	}
public boolean getTurn(){
	return redTurn;
}
	
	
	
}
