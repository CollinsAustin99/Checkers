package practicePerformaceTask;

public class Piece {
//attribute
	private String color;
	private boolean king;
	private int x;
	private int y;
//constructor
	public Piece(String redBlack, boolean king1, int coordX, int coordY){
		this.color = redBlack;
		this.king = king1;
		this.x = coordX;
		this.y = coordY;
	}
//methods
	//getters
	public String getColor(){
		return this.color;
	}
	public boolean getKing(){
		return this.king;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	//setters
	public void setKing(boolean bool){
		this.king = bool;
	}
	//mostly to be used to remove a piece from the board by passing the argument "none"
	public void setColor(String newColor){
		this.color = newColor;
	}
}
