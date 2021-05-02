package gamelogic;

import java.util.HashSet;

/**
 * The internal game server that handles all of the game logic
 * @author Nick Mazey
 *
 */
public class Server {
	private Board board;
	
	private Player activePlayer;
	
	private int tickRate;
	
	public Server(Board board, int tickRate, Player activePlayer) {
		this.board = board;
		this.tickRate = tickRate;
		this.activePlayer = activePlayer;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getTickRate() {
		return tickRate;
	}
	
	
	/**
	 * Handles the logic of the game, every tick is a calculation
	 */
	public void tick() {
		for(Player p : board.getPlayers()) {
			for(Unit u : p.getUnits()) {
				u.tick(this.board);
			}
		}
	}
	
	public void handleRightClick(Position pos) {
		if(!board.posIsValid(pos)) return;
		if(selectObjectAtPos(pos) instanceof Tile) {
		for(Unit u : activePlayer.getUnits()) {
			u.setDestination(pos);
		}
		}
	}
	
	public void toggleHighlight(Position pos) {
		Object toHighlight = selectObjectAtPos(pos);
		if(toHighlight instanceof Unit) {
			if(!activePlayer.getUnits().contains((Unit)toHighlight)) {
				return;
			}
		}
		if(toHighlight instanceof Highlightable) {
			((Highlightable) toHighlight).toggleHighlighted();
		}
	}

	
	/**
	 * Method to select the object at a given position
	 * @param pos - The position to check for an object
	 * @return - The object (if any) on the specified position
	 */
	public Object selectObjectAtPos(Position pos) {
		if(pos == null) return null;
		if(!board.posIsValid(pos)) return null;
		HashSet<Player> players = board.getPlayers();
		for(Player p : players) {
			for(Unit u : p.getUnits()) {
				int x = u.getEffectivePos().getX();
				int y = u.getEffectivePos().getY();
				int width = u.getWidth();
				int height = u.getHeight();
				if(pos.getX() - width <= x && pos.getX() + width >= x + width && pos.getY() - height <= y && pos.getY() + height >= y + height) {
					return u;
				}
			}
		}
		Tile example = null;
		for(Tile[] tiles : board.getGround()) {
			for(Tile tile : tiles) {
				if(tile != null) {
					example = tile;
					break;
				}
				if(example != null) {
					break;
				}
			}
		}
		if(example != null) {
			int xPos = pos.getX() / example.getSize();
			int yPos = pos.getY() / example.getSize();
			Tile[][] tiles = board.getGround();
			if(xPos >= 0 && xPos < tiles.length && yPos >= 0 && yPos <= tiles[0].length) {
				return tiles[xPos][yPos];
			}
		}
		return null;
	}
	
	public void setActivePlayer(Player p) {
		activePlayer = p;
	}
}
