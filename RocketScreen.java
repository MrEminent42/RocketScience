package game;
import java.awt.event.KeyEvent;

public class RocketScreen {
	
	public static final double rocketRectHeight = 30;
	public static final double rocketRectWidth = 30;
	
	protected RocketGame game;
	protected String currentPicture;
	
	public RocketScreen(RocketGame game, double size) {
		this.game = game;
		
		init(width);
	}
	
	public void init(double size) {
		StdDraw.setCanvasSize((int)(100*size), (int)(400*size+100));
		
		StdDraw.setXscale(-50, 50);
		StdDraw.setYscale(-50, 450);
		
		StdDraw.enableDoubleBuffering();
	}
	
	public void update() {
		updatePictureFile();
		
		StdDraw.clear();
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.line(-50, 0, 50, 0);
		StdDraw.picture(game.getRocket().getX(), game.getRocket().getY() + rocketRectHeight/2-5, "./Rocket/" + currentPicture, rocketRectWidth, rocketRectHeight);

		StdDraw.text(0, 440, "TIME " + String.format("%03d", (System.currentTimeMillis() - game.getStartMilis())/1000));
		StdDraw.setPenColor((game.getRocket().fuelTank > 50) ? StdDraw.BLACK : (game.getRocket().fuelTank > 25) ? StdDraw.PRINCETON_ORANGE : StdDraw.RED);
		StdDraw.text(0, 425, "FUEL " + String.format("%03d", (int)(game.getRocket().fuelTank)));
		StdDraw.setPenColor((game.getRocket().getVelocityPerSecond() > 35) ? StdDraw.RED : (game.getRocket().getVelocityPerSecond() <= game.crashVelocity) ? StdDraw.GREEN :StdDraw.BLACK);
		StdDraw.text(0, 410, "VEL   " + String.format("%03d", (int)(game.getRocket().getVelocityPerSecond())));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0, 395, "LEVL " + String.format("%03d", (int)(game.getRocket().fuelLevel)));
		
		StdDraw.show();
		
		checkButtonPress();
	}
	
	public void updatePictureFile() {
		// scaling the 9 levels to 6 pictures (??)
		int d = (int) (6 * game.getRocket().fuelLevel / 10);
		
		if (d < 1) {
			this.currentPicture = "lvl1.png";
		} else if (d < 2) {
			this.currentPicture = "lvl2.png";
		} else if (d < 3) {
			this.currentPicture = "lvl3.png";
		} else if (d < 4) {
			this.currentPicture = "lvl4.png";
		} else if (d < 5) {
			this.currentPicture = "lvl5.png";
		} else if (d < 6) {
			this.currentPicture = "ddd.png";
		}
	}
	
	public void checkButtonPress() {
		if (StdDraw.isKeyPressed(KeyEvent.VK_0)) {
			game.getRocket().setFuelLevel(0);
		} else if(StdDraw.isKeyPressed(KeyEvent.VK_1)) {
			game.getRocket().setFuelLevel(1);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_2)) {
			game.getRocket().setFuelLevel(2);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_3)) {
			game.getRocket().setFuelLevel(3);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_4)) {
			game.getRocket().setFuelLevel(4);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_5)) {
			game.getRocket().setFuelLevel(5);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_6)) {
			game.getRocket().setFuelLevel(6);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_7)) {
			game.getRocket().setFuelLevel(7);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_8)) {
			game.getRocket().setFuelLevel(8);
		} else if (StdDraw.isKeyPressed(KeyEvent.VK_9)) {
			game.getRocket().setFuelLevel(9);
		}
	}
	
	public void displayText(String text) {
		StdDraw.text(0, 200, text);
		StdDraw.show();
	}
	
}
