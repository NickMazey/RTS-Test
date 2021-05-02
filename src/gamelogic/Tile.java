package gamelogic;

/**
 * Represents one tile on the board
 * @author Nick Mazey
 *
 */
public class Tile implements Highlightable{
	
	protected boolean highlighted;

	public enum Type{
		ROCK,
		FLAT
	}
	
	private int size;
	private Type type;
	
	public Type getType() {
		return type;
	}
	
	public boolean isPassable() {
		return type == Type.ROCK;
	}
	
	public int getSize() {
		return size;
	}
	
	public Tile(int size, Type type) {
		this.size = size;
		this.type = type;
	}
	
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	public boolean isHighlighted() {
		return highlighted;
	}

	public void toggleHighlighted() {
		highlighted = !highlighted;
	}
}
