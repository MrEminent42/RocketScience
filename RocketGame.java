package game;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RocketGame {
	
	final int crashVelocity = 10;
	final int refreshPerSecond = 1000;
    final double GRAVITY = -0.1/refreshPerSecond;
    final double velPerFuelLevel = 0.025;
    final double startingRocketFuel = 100;
    final Vector startingRocketPos = new Vector(0, 100);
    
    private Rocket rocket;
    private RocketScreen screen;
    private Timer updateTask;
    private long startMilis;
    private long finishMilis = startMilis;
    
    private boolean paused = false;
    private boolean crashed = false;
    private boolean landed = false;
    private boolean outOfFuel = false;
    
    public RocketGame() {
    	this(new ArrayList<TimedFuel>());
    }
    
    public RocketGame(boolean screen) {
    	this(new ArrayList<TimedFuel>(), screen);
    }
    
    public RocketGame(ArrayList<TimedFuel> fuelQueue) {
    	this(fuelQueue, true);
    }
    
    public RocketGame(ArrayList<TimedFuel> fuelQueue, boolean screen) {
        this.rocket = new Rocket(this, fuelQueue);
        if (screen) initScreen();
    }
    
    private void initScreen() {
        this.screen = new RocketScreen(this, 1);
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
    	}, 1000/refreshPerSecond, 1000/refreshPerSecond);
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
        	if (updateTask != null) updateTask.cancel();
	    	// if crashed
	    	if (rocket.getVelocityPerSecond() > crashVelocity) {
	    		crashed = true;
	    		screen.displayText("You crashed!");
	    	} else {
	    		landed = true;
	    		screen.displayText("You landed!");
	    	}
	    	
	    	finishMilis = System.currentTimeMillis();
    	}
    	
    	// out of fuel
    	else if (rocket.fuelTank <= 0) {
        	paused = true;
        	if (updateTask != null) updateTask.cancel();
    		outOfFuel = true;
    		screen.displayText("Out of fuel!");
	    	finishMilis = System.currentTimeMillis();
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
    	return this.startMilis;
    }
    
    public void setStartMilis(long s) {
    	this.startMilis = s;
    }
    
    public long getTimeMilis() {
    	if (this.hasLanded() || this.hasCrashed() || this.isOutOfFuel()) {
    		return this.finishMilis - this.startMilis;
    	} else return System.currentTimeMillis() - this.startMilis;
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