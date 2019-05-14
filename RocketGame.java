package game;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RocketGame {
	
	int crashVelocity = 10;
	double forceMultiplier = 1.0;
	final int iterationMilis = 10;
	final int iterationsUntilRefreshScreen = 3;
	double GRAVITY = -0.1/100.0*forceMultiplier;
	double forcePerFuelLevel = 1/4.0 * Math.abs(GRAVITY);
	double startingRocketFuel = 100;
	Vector startingRocketPos = new Vector(0, 100);
    
    private Rocket rocket;
    private RocketScreen screen;
    private Timer gameUpdate;
    private int iterations = 0;
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
    	this.gameUpdate = new Timer();
    	gameUpdate.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
				iterations++;
				if (iterations == iterationsUntilRefreshScreen) {
					iterations = 0;
					screen.update();
				}
			}
    	}, 0, iterationMilis);
    	
    }
    
    /**
     * iterate
     */
    public void update() {
    	if (this.paused) return;
        rocket.update();
        if (this.screen != null) screen.update();
        
        checkFinishGame();
    }
    
    /**
     * check if the game has finished & perform final tasks
     */
    public void checkFinishGame() {
    	
    	// on the ground
    	if (rocket.isTouchingGround()) {
        	cancelTasks();
        	paused = true;
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
        	cancelTasks();
        	paused = true;
    		outOfFuel = true;
    		if (screen != null) screen.displayText("Out of fuel!");
	    	finishMilis = System.currentTimeMillis();
    	}
    }
    
    public void cancelTasks() {
    	if (this.gameUpdate != null) gameUpdate.cancel();
    }
    
    // ACCESSORS \\
    
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
    
    /**
     * sets the force multiplier (basically the speed)
     * @param mult
     */
    public void setForceMultiplier(double mult) {
    	this.forceMultiplier = mult;
    }
    
    public double getStartingFuel() {
    	return this.startingRocketFuel;
    }
    
    /** Set preferences
     * @param crashVelocity threshold for crashing default 10
     * @param forceMultiplier multiply all forces default 1
     * @param screenRefreshPerSecond refreshes per second default 100
     * @param startingRocketFuel starting rocket fuel default 100
     * @param startingRocketPos starting rocket y-pos default 10
     */
    public void setPreferences(int crashVelocity, 
    		double forceMultiplier, 
    		double startingRocketFuel, int startingRocketPos) {
    	this.crashVelocity = crashVelocity;
    	this.forceMultiplier = forceMultiplier;
    	this.startingRocketFuel = startingRocketFuel;
    	this.startingRocketPos = new Vector(0, startingRocketPos);
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