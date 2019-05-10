package game;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RocketGame {
	
	public static final int crashVelocity = 3;
	public static final int refreshPerSecond = 1000;
    public static final double GRAVITY = -0.01/((double) refreshPerSecond);
    public static final double velPerFuelLevel = 0.0025;
    public static final double startingRocketFuel = 200;
    public static final Vector startingRocketPos = new Vector(0, 100);
    
    private Rocket rocket;
    private RocketScreen screen;
    private Timer updateTask;
    private long startMilis;
    
    private boolean paused = false;
    private boolean crashed = false;
    private boolean landed = false;
    private boolean outOfFuel = false;
    
    public RocketGame() {
    	this(new ArrayList<TimedFuel>());
    }
    
    public RocketGame(ArrayList<TimedFuel> fuelQueue) {
        this.rocket = new Rocket(fuelQueue);
        this.screen = new RocketScreen(this, 100);
        
        this.screen.update();
        this.screen.displayText("Click to play");
    }
    
    public void start() {
    	this.startMilis = System.currentTimeMillis();
    	this.updateTask = new Timer();
    	updateTask.scheduleAtFixedRate(new TimerTask() {
    		@Override
			public void run() {
				if (!isPaused()) {
					update();
				}
			}
    	}, refreshPerSecond/1000, refreshPerSecond/1000);
    }
    
    public void update() {
    	if (this.paused) return;
        rocket.update();
        screen.update();
        
        checkFinishGame();
        
    }
    
    public void checkFinishGame() {
    	
    	// on the ground
    	if (rocket.isTouchingGround()) {
        	paused = true;
        	updateTask.cancel();
	    	// if crashed
	    	if (rocket.getVelocityPerSecond() > crashVelocity) {
	    		crashed = true;
	    		screen.displayText("You crashed!");
	    	} else {
	    		landed = true;
	    		screen.displayText("You landed!");
	    	}
    	}
    	
    	// out of fuel
    	else if (rocket.fuelTank <= 0) {
        	paused = true;
        	updateTask.cancel();
    		outOfFuel = true;
    		screen.displayText("Out of fuel!");
    	}
    	
    }
    
    public Rocket getRocket() {
    	return this.rocket;
    }
    
    public RocketScreen getScreen() {
    	return this.screen;
    }
    
    public void setScreen(RocketScreen screen) {
    	this.screen = screen;
    }
    
    public long getStartMilis() {
    	return this.getStartMilis();
    }
    
    // FINAL GAME STATES \\
    
    public boolean hasLanded() {
    	return landed;
    }
    
    public boolean hasCrashed() {
    	return crashed;
    }
    
    public boolean isOutOfFuel() {
    	return outOfFuel;
    }
    
    
    // PAUSE / UNPAUSE \\
    
    public void resume() {
    	this.paused = false;
    }
    
    public void pause() {
    	this.paused = true;
    }
    
    public boolean isPaused() {
    	return this.paused;
    }
    
}