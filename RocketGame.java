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
    
    /**
     * Create a rocket game with an empty fuel queue, and a screen
     */
    public RocketGame() {
    	this(new ArrayList<TimedFuel>());
    }
    
    /**
     * Create a RocketGame with an empty fuel queue, and specify whether or not a screen should be created
     * @param createScreen
     */
    public RocketGame(boolean createScreen) {
    	this(new ArrayList<TimedFuel>(), createScreen);
    }
    
    /**
     * Create a RocketGame with a fuel queue and a screen
     * @param fuelQueue list of TimedFuel to be executed in order
     */
    public RocketGame(ArrayList<TimedFuel> fuelQueue) {
    	this(fuelQueue, true);
    }
    
    /** 
     * Create a RocketGame with a fuel queue and specify whether or not a screen should be created
     * @param fuelQueue list of TimedFuel to be executed in order
     * @param createScreen
     */
    public RocketGame(ArrayList<TimedFuel> fuelQueue, boolean createScreen) {
        this.rocket = new Rocket(this, fuelQueue);
        if (createScreen) initScreen();
    }
    
    private void initScreen() {
        this.screen = new RocketScreen(this, 1);
        this.screen.update();
        this.screen.displayText("Click to play");
    }
    
    /**
     * Run the game
     */
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
    
    /**
     * iterate
     */
    public void update() {
    	if (this.paused) return;
        rocket.update();
        if (screen != null) screen.update();
        
        checkFinishGame();
    }
    
    /**
     * check if the game has finished & perform final tasks
     */
    public void checkFinishGame() {
    	
    	// on the ground
    	if (rocket.isTouchingGround()) {
        	paused = true;
        	if (updateTask != null) updateTask.cancel();
	    	// if crashed
	    	if (rocket.getVelocityPerSecond() > crashVelocity) {
	    		crashed = true;
	    		if (screen != null) screen.displayText("You crashed!");
	    	} else {
	    		landed = true;
	    		if (screen != null) screen.displayText("You landed!");
	    	}
	    	
	    	finishMilis = System.currentTimeMillis();
    	}
    	
    	// out of fuel
    	else if (rocket.fuelTank <= 0) {
        	paused = true;
        	if (updateTask != null) updateTask.cancel();
    		outOfFuel = true;
    		if (screen != null) screen.displayText("Out of fuel!");
	    	finishMilis = System.currentTimeMillis();
    	}
    }
    
    /** 
     * @return this game's rocket
     */
    public Rocket getRocket() {
    	return this.rocket;
    }
    
    /**
     * @return this game's screen
     */
    public RocketScreen getScreen() {
    	return this.screen;
    }
    
    /**
     * Set this game's screen 
     * @param RocketScreen to use for this RocketGame
     */
    public void setScreen(RocketScreen screen) {
    	this.screen = screen;
    }
    
    /**
     * @return miliseconds when this game started
     */
    public long getStartMilis() {
    	return this.startMilis;
    }
    
    
    /**
     * @param s the miliseconds to set the start time to
     */
    public void setStartMilis(long s) {
    	this.startMilis = s;
    }
    
    /**
     * @return the time elapsed so far, or the duration of the game if ended
     */
    public long getTimeMilis() {
    	if (this.hasLanded() || this.hasCrashed() || this.isOutOfFuel()) {
    		return this.finishMilis - this.startMilis;
    	} else return System.currentTimeMillis() - this.startMilis;
    }
    
    // FINAL GAME STATES \\
    
    /**
     * @return if the rocket has landed
     */
    public boolean hasLanded() {
    	return landed;
    }

    /**
     * @return if the rocket has crashed
     */
    public boolean hasCrashed() {
    	return crashed;
    }

    /**
     * @return if the rocket is out of fuel
     */
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