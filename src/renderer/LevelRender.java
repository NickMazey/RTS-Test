package renderer;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gamelogic.Board;
import gamelogic.Player;
import gamelogic.Position;
import gamelogic.Tile;
import gamelogic.Unit;

@SuppressWarnings("serial")
public class LevelRender extends JPanel {
	
	private double zoom;
	private int xOffset;
	private int yOffset;
	private final Color GROUNDCOLOR = new Color(0,150,0);
	private final Color ROCKCOLOR = new Color(160,160,160);
	private HashMap<Object,Position> highlights;
	private Board board;
	
	public LevelRender(Board board) {
		highlights = new HashMap<Object, Position>();
		zoom = 5;
		this.board = board;
		xOffset = -1 * (int)((board.getTileSize() / zoom) * ((board.getWidth() - 1) / 2));
		yOffset = -1 * (int)((board.getTileSize() / zoom) * ((board.getHeight() - 1) / 2));
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Tile[][] tiles = board.getGround();
		for(int y = 0; y < tiles[0].length; y++ ) {
			for(int x = 0; x < tiles.length; x++) {
				if(tiles[x][y] != null && tiles[x][y].isHighlighted()) {
					highlights.put(tiles[x][y],new Position(x,y));
					continue;
				} else if(highlights.containsKey(tiles[x][y])) {
					highlights.remove(tiles[x][y]);
				}
				drawTile(g,tiles[x][y],x,y);
			}
		}
		
		for(Object o : highlights.keySet()) {
			if(o instanceof Tile) {
				int x = highlights.get(o).getX();
				int y = highlights.get(o).getY();
				drawTile(g,tiles[x][y],x,y);
			}
		}
		
		for(Player p : board.getPlayers()) {
			for(Unit u : p.getUnits()) {
				if(u != null && u.isHighlighted()) {
					highlights.put(u,u.getPos());
					continue;
				}
				drawUnit(g,u);
			}
		}
		
		for(Object o : highlights.keySet()) {
			if(o instanceof Unit) {
				drawUnit(g,(Unit)o);
			}
		}
		
		//drawMinimap(g,getWidth() - 100,getHeight() - 100,100);
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Finds where the mouse has clicked on the isometric grid
	 * @return - The position corresponding to where the mouse is
	 */
	public Position getMousePos() {
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePoint, this);
		double transformedX = mousePoint.x * 2;
		double transformedY = mousePoint.y * 2;
		double normalY = (transformedX + transformedY) / 2;
		double normalX = transformedX - normalY;
		int calcX = (int)(zoom * (normalX - xOffset));
		int calcY = (int)(zoom * (normalY - yOffset));
		if(calcX > 0 && calcY > 0) {
			return new Position(calcX,calcY);
		} else {
			return null;
		}
	}
	
	
	/**
	 * Method for drawing a diamond instead of a square for an isometric view
	 * @param g - The graphics object to draw it on
	 * @param x - The x position of the diamond
	 * @param y - The y position of the diamond
	 * @param size - How big the diamond should be
	 * @param fill - Whether or not the diamond should be filled
	 */
	public static void drawDiamond(Graphics g, int x, int y, int width, int height, boolean fill) {
		int[] xPoints = {x, x + width / 2, x + width, x + width / 2};
		int[] yPoints = {y, y - height / 2,y, y + height / 2};
		if(fill) {
			g.fillPolygon(xPoints,yPoints,4);
		} else {
			g.drawPolygon(xPoints,yPoints,4);
		}
	}
	
	private void drawTile(Graphics g,Tile t, int x, int y) {
		if(t == null) {
			return;
		}
		double squareSize = (t.getSize() / zoom);
		double normalX = x * squareSize + xOffset;
		double normalY = y * squareSize + yOffset;
		int calcX = (int) ((normalX + normalY) / 2);
		int calcY = (int) ((normalY - normalX) / 2);
		int size = (int)(squareSize);
		setTileColor(g,t);
		drawDiamond(g,calcX ,calcY , size,size,true);
		if(t.isHighlighted()) {
			g.setColor(Color.WHITE);
		}else {
			g.setColor(GROUNDCOLOR);
		}
		drawDiamond(g,calcX, calcY, size,size,false);
	}
	
	private void drawUnit(Graphics g, Unit u) {
		int normalX = (int) (u.getEffectivePos().getX() / zoom + xOffset);
		int normalY = (int) (u.getEffectivePos().getY() / zoom + yOffset);
		g.setColor(Color.ORANGE);
		drawDiamond(g,(normalX + normalY) / 2, (normalY - normalX) / 2, (int)( u.getWidth() / zoom),(int)(u.getHeight() / zoom),true);
		if(u.isHighlighted()) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}
		drawDiamond(g,(normalX + normalY) / 2, (normalY - normalX) / 2, (int)( u.getWidth() / zoom),(int)(u.getHeight() / zoom),false);
	}
	
	private void setTileColor(Graphics g, Tile t) {
		switch(t.getType()) {
		case FLAT :
			g.setColor(GROUNDCOLOR);
			break;
		case ROCK :
			g.setColor(ROCKCOLOR);
			break;
		}
	}
	
	/*
	private void drawMinimap(Graphics g,int xPos, int yPos, int size) {
		int scale = board.getWidth() < board.getHeight() ? size / board.getWidth() : size / board.getHeight();
		Tile[][] tiles = board.getGround();
		for(int y = 0; y < tiles[0].length; y++) {
			for(int x = 0; x < tiles.length; x++) {
				setTileColor(g,tiles[x][y]);
				g.fillRect(xPos + x * scale, yPos + y * scale , scale, scale);
			}
		}
		
		int tileSize = board.getTileSize();
		int unitScale;
		for(Player p : board.getPlayers()) {
			for (Unit u : p.getUnits()) {
				unitScale = u.getWidth() < u.getHeight() ? u.getWidth() / tileSize : u.getHeight() / tileSize; 
				g.setColor(Color.ORANGE);
				int unitX = xPos + (u.getEffectivePos().getX() / tileSize) * scale;
				int unitY = yPos + (u.getEffectivePos().getY() / tileSize) * scale;
				g.fillRect(unitX, unitY, unitScale, unitScale);
			}
		}
		
		g.setColor(Color.WHITE);
		g.drawRect(xPos + xOffset, yPos + yOffset, width, height);
		

		
		
		
		g.setColor(Color.BLACK);
		g.drawRect(xPos,yPos,scale * (board.getWidth()), scale * (board.getHeight()));		
	}
	*/
	
	public void moveView(int x, int y) {
		xOffset += x;
		yOffset += y;
	}
	
	public void changeZoom(double amount) {
		if(zoom + amount > 0) {
		int oldWidth = getWidth();
		int oldHeight = getHeight();
		zoom += amount;
		xOffset += ((oldWidth - getWidth()) / 2);
		yOffset += ((oldHeight - getHeight()) / 2);
		}
	}
	
	
}
