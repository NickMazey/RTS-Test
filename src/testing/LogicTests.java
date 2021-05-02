package testing;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import gamelogic.Board;
import gamelogic.Position;
import maps.TestingMap;

public class LogicTests {
	@Test
	public void positionTest1() {
		Position pos1 = new Position(16,6);
		Position pos2 = new Position(24,8);
		assertTrue(pos1.distanceTo(pos2) == 8);
	}
	
	
	@Test
	public void mapTest1() {
		Board newBoard = new TestingMap();
		assertTrue(newBoard.getGround() != null);
	}
}
