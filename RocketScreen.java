import java.awt.event.KeyEvent;

public class RocketScreen {
	
	public static final double rocketRectHeight = 20;
	public static final double rocketRectWidth = 20;
	
	RocketGame game;
	
	public RocketScreen(RocketGame game) {
		this.game = game;
		
		init(100);
	}
	
	private void init(int size) {
		StdDraw.setCanvasSize(size, 4*size+100);
		
		StdDraw.setXscale(-50, 50);
		StdDraw.setYscale(-50, 450);
		
		StdDraw.enableDoubleBuffering();
	}
	
	public void update() {
		StdDraw.clear();
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.line(-100, 0, 100, 0);
		StdDraw.rectangle(game.rocket.getX(), game.rocket.getY() + rocketRectHeight/2, 
				rocketRectWidth/2, rocketRectHeight/2);

		StdDraw.text(0, 440, "TIME " + String.format("%03d", (System.currentTimeMillis() - game.startMilis)/1000));
		StdDraw.text(0, 425, "FUEL " + String.format("%03d", (int)(game.rocket.fuelTank)));
		StdDraw.text(0, 410, "VELO " + String.format("%03d", (int)(game.rocket.getVelocityPerSecond())));
		StdDraw.text(0, 395, "LEVL " + String.format("%02d", (int)(game.rocket.fuelLevel)));
		
		StdDraw.show();
		
		checkButtonPress();
	}
	
	public void checkButtonPress() {
		if (StdDraw.isKeyPressed(KeyEvent.VK_0)) {
			game.rocket.setFuelLevel(0);
		} else if(StdDraw.isKeyPressed(KeyEvent.VK_1)) {
			game.rocket.setFuelLevel(1);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
			game.rocket.setFuelLevel(2);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
			game.rocket.setFuelLevel(3);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_4)) {
			game.rocket.setFuelLevel(4);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_5)) {
			game.rocket.setFuelLevel(5);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_6)) {
			game.rocket.setFuelLevel(6);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_7)) {
			game.rocket.setFuelLevel(7);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_8)) {
			game.rocket.setFuelLevel(8);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_9)) {
			game.rocket.setFuelLevel(9);
		}
	}
	
	public void displayText(String text) {
		StdDraw.text(0, 200, text);
		StdDraw.show();
	}
	
}
