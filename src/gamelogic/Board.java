package gamelogic;

import java.util.HashSet;

public class Board {

	protected Tile[][] ground;
	
	protected HashSet<Player> players;

	public Board(int width, int height) {
		ground = new Tile[width][height];
		players = new HashSet<Player>();
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public Tile[][] getGround() {
		return ground;
	}

	public String toString() {

		StringBuilder toReturn = new StringBuilder();
		for(int y = 0; y < ground[0].length; y++) {
			for(int x = 0; x < ground.length; x++) {
				if(ground[x][y] == null) {
					toReturn.append("■");
				} else {
					switch(ground[x][y].getType()) {
					case FLAT:
						toReturn.append("□");
						break;
					case ROCK:
						toReturn.append("■");
						break;
					}
				}
			}
			toReturn.append("\n");
		}
		return toReturn.toString();
	}
	
	public int getWidth() {
		return ground.length;
	}
	
	public int getHeight() {
		if(ground.length == 0) {return 0;}
		return ground[0].length;
	}
	
	public int getTileSize() {
		for(Tile[] tiles : ground) {
			for (Tile t : tiles) {
				if(t != null) {
					return t.getSize();
				}
			}
		}
		return 0;
	}

	
	public HashSet<Player> getPlayers() {
		return players;
	}

	public boolean posIsValid(Position pos) {
		if(pos == null) return false;
		if(pos.getX() >= 0 && pos.getX() <= getTileSize() * getWidth() && pos.getY() >= 0 && pos.getY() <= getTileSize() * getHeight()) {
			return true;
		}
		return false;
	}
}
