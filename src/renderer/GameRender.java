package renderer;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gamelogic.Board;
import gamelogic.Player;
import gamelogic.Position;
import gamelogic.Server;
import gamelogic.Unit;
import maps.TestingMap;
import units.BasicMeleeUnit;

public class GameRender extends JFrame {

	private static final long serialVersionUID = 1386419159140404684L;
	
	private LevelRender levelRender;
	private Server server;

	public GameRender(Server server) {
		// Setting up the window
		this.server = server;
		setTitle("RTS");
		setPreferredSize(new Dimension(500, 500));
		
		levelRender = new LevelRender(this.server.getBoard());
		add(levelRender);
		
		addKeyListener(setupKeys());
		addMouseListener(setupMouse());

		// Finalising the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private MouseListener setupMouse() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent m) {
				if(m.getButton() == MouseEvent.BUTTON1) {
					server.toggleHighlight(levelRender.getMousePos());
				} else if(m.getButton() == MouseEvent.BUTTON3) {
					server.handleRightClick(levelRender.getMousePos());
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	public Server getServer() {
		return server;
	}
	
	public int getTickRate() {
		return server.getTickRate();
	}
	
	public void repaint() {
		super.repaint();
		moveWithMouse();
		server.tick();
	}
	
	private void moveWithMouse() {
		double sensitivity = 0.1;
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePos, this);
		//Up movement
		if(mousePos.y >= 0 && mousePos.y < sensitivity * getHeight()) {
			levelRender.moveView(-50, 50);
		}
		//Down movement
		if(mousePos.y > getHeight() - getHeight() * sensitivity && mousePos.y <= getHeight()) {
			levelRender.moveView(50, -50);
		}
		//Left movement
		if(mousePos.x >= 0 && mousePos.x < sensitivity * getWidth()) {
			levelRender.moveView(50, 50);
		}
		//Right movement
		if(mousePos.x > getWidth() - getWidth() * sensitivity && mousePos.x <= getWidth()) {
			levelRender.moveView(-50, -50);
		}
	}
	
	private KeyListener setupKeys() {
		return new KeyListener() {

			@Override
			public void keyPressed(KeyEvent key) {
				switch(key.getKeyCode()) {
				case KeyEvent.VK_UP:
					levelRender.moveView(-50,50);
					break;
				case KeyEvent.VK_DOWN:
					levelRender.moveView(50, -50);
					break;
				case KeyEvent.VK_LEFT:
					levelRender.moveView(50, 50);
					break;
				case KeyEvent.VK_RIGHT:
					levelRender.moveView(-50, -50);
					break;
				case KeyEvent.VK_EQUALS:
					levelRender.changeZoom(-0.2);
					break;
				case KeyEvent.VK_MINUS:
					levelRender.changeZoom(0.2);
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	

	
	public static void main(String[] args) {
		Player p = new Player();
		Unit u = new BasicMeleeUnit(new Position(10,10));
		u.setDestination(new Position(10000,10000));
		p.addUnit(u);
		Board b = new TestingMap();
		b.addPlayer(p);
		Server s = new Server(b,1,p);
		GameRender render = new GameRender(s);
		long initialTime = System.currentTimeMillis();
		long secondTime = System.currentTimeMillis();
		int i = 0;
		while(true) {
			initialTime = System.currentTimeMillis();
			render.repaint();
			//System.out.println("That frame took " + (System.nanoTime() - initialTime) + "ns");
			try {
				Thread.sleep((1000 / 24) - (System.currentTimeMillis() - initialTime));
			} catch (Exception e) {

			}
			i++;
			if(System.currentTimeMillis() - secondTime >= 1000) {
				secondTime = System.currentTimeMillis();
				render.setTitle("RTS - FPS : " + i);
				i = 0;
			}
		}
	}

}
