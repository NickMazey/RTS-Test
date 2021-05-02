package gamelogic;

/**
 * The position of an object on the map
 * @author Nick Mazey
 *
 */
public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}
	
	/**
	 * Calculates the distance from one position to another
	 * @param p2 - The position to find the distance to
	 * @return - The distance between the two positions
	 */
	public int distanceTo(Position p2) {
		return (int)(Math.sqrt(Math.pow(x-p2.getX(),2) + Math.pow(y-p2.getY(),2)));
	}
	
	public boolean equals(Object other) {
		if(other instanceof Position) {
			Position otherPos = (Position) other;
			return x == otherPos.getX() && y == otherPos.getY();
		}
		return false;
	}
	
	public double gradientTo(Position other) {
		return ((double)(other.getY() - this.getY()) / (double)(other.getX() - this.getX()));
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
