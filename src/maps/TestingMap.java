package maps;

import gamelogic.Board;
import gamelogic.Tile;

public class TestingMap extends Board {

	public TestingMap() {
		super(100,100);
		genMap();
	}
	
	private void genMap() {
		int rockY = 50;
		for(int rockX = 0; rockX < ground.length; rockX++) {
			if(!(rockX == ground.length / 2 || rockX == ground.length / 2 + 1)) {
				ground[rockX][rockY] = new Tile(100, Tile.Type.ROCK);
			}
		}
		for(int y = 0; y < ground[0].length; y++) {
			for(int x = 0; x < ground.length; x++) {
				if(ground[x][y] == null) {
					ground[x][y] = new Tile(100, Tile.Type.FLAT);
				}
			}
		}
	}
}
