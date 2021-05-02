package gamelogic;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Basic class to represent the units a player will be using
 * 
 * @author Nick Mazey
 *
 */
public class Unit implements Highlightable {
	protected Position pos;
	protected Queue<Position> positionQueue;
	protected Position destination;
	protected int damage;
	protected int attackSpeed;
	protected int width;
	protected int height;
	protected int moveSpeed;
	protected int attackRange;
	protected int health;
	protected long lastAttackTime;
	protected String name;
	protected boolean highlighted;
	protected boolean flying;

	public Unit(Position pos) {
		this.pos = pos;
		this.destination = pos;
		highlighted = false;
		positionQueue = new ArrayDeque<Position>();
	}

	public void move(Board b) {
		if (!destination.equals(pos)) {
			int deltaX = 0;
			int deltaY = 0;
			if (flying) {
				if ((int) pos.distanceTo(destination) > moveSpeed) {
					deltaX = (int) (moveSpeed / (Math.sqrt(Math.pow(pos.gradientTo(destination), 2) + 1)));
					deltaY = (int) Math.sqrt(Math.pow(moveSpeed, 2) - Math.pow(deltaX, 2));
				} else {
					pos.setX(destination.getX());
					pos.setY(destination.getY());
				}
				if (destination.getX() < pos.getX()) {
					deltaX *= -1;
				}
				if (destination.getY() < pos.getY()) {
					deltaY *= -1;
				}
			} else {
				if (positionQueue.size() > 0) {
					Position next = positionQueue.peek();
					if((int) pos.distanceTo(next) > moveSpeed) {
						deltaX = (int) (moveSpeed / (Math.sqrt(Math.pow(pos.gradientTo(next), 2) + 1)));
						deltaY = (int) Math.sqrt(Math.pow(moveSpeed, 2) - Math.pow(deltaX, 2));
					}else {
						pos.setX(next.getX());
						pos.setY(next.getY());
					}
					if(next.getX() < pos.getX()) {
						deltaX *= -1;
					}
					if(next.getY() < pos.getY()) {
						deltaY *= -1;
					}
					if(pos.equals(positionQueue.peek())) {
						positionQueue.poll();
					}
				} else {
					generatePathToDestination(b);
				}
			}

			pos.move(deltaX, deltaY);
		}
		boolean moveDest = false;
		if(destination.equals(pos)) {
			moveDest = true;
		}
		validatePos(b);
		if(moveDest) {
			destination = pos;
		}
	}
	
	private void generatePathToDestination(Board b) {
		Tile[][] ground = b.getGround();
		int tileSize = b.getTileSize();
		
		//Create open set
		PriorityQueue<Position> openSet = new PriorityQueue<Position>((Position p1,Position p2) -> p1.distanceTo(destination) - p2.distanceTo(destination));
		for(int y = 0; y < ground[0].length; y++) {
			for(int x = 0; x < ground.length; x++) {
				if(ground[x][y] != null && ground[x][y].isPassable()) {
					openSet.add(new Position(x * tileSize, y * tileSize));
				}
			}
		}
		
		//TODO: look at the https://en.wikipedia.org/wiki/A*_search_algorithm page and finish this
		
		
	}

	private void validatePos(Board b) {
		if (pos.getX() < width / 2) {
			pos.setX(width / 2);
		}
		if (pos.getX() > b.getWidth() * b.getTileSize() - width / 2) {
			pos.setX(b.getWidth() * b.getTileSize() - width / 2);
		}
		if (pos.getY() < height / 2) {
			pos.setY(height / 2);
		}
		if (pos.getY() > b.getHeight() * b.getTileSize() - height / 2) {
			pos.setY(b.getHeight() * b.getTileSize() - height / 2);
		}

	}

	public void tick(Board b) {
		if (needsToMove()) {
			move(b);
		}
	}

	public boolean needsToMove() {
		return !pos.equals(destination);
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

	public Position getPos() {
		return pos;
	}

	public Position getEffectivePos() {
		return new Position(pos.getX() - width / 2, pos.getY() - height / 2);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public void toggleHighlighted() {
		highlighted = !highlighted;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public boolean isFlying() {
		return flying;
	}

}
