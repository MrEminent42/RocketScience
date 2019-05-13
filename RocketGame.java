package game;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RocketGame {
	
	final int crashVelocity = 10;
	double forceMultiplier = 1.0;
	final int screenRefreshPerSecond = 100;
    double GRAVITY = -0.1/1000.0*forceMultiplier;
    double forcePerFuelLevel = 1/4.0 * Math.abs(GRAVITY);
    final double startingRocketFuel = 100;
    final Vector startingRocketPos = new Vector(0, 100);
    
    private Rocket rocket;
    private RocketScreen screen;
    private Timer screenUpdate, gameUpdate;
    private long startMilis;
    private long finishMilis = startMilis;
    
    private boolean paused = false;
    private boolean crashed = false;
    private boolean landed = false;
    private boolean outOfFuel = false;
    
    
    // TO DEL
    int sec = 0;
    int applied = 0;
    double gravsofar = 0;
    
    
    
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
		if (this.screen != null) {
			this.screenUpdate = new Timer();
			screenUpdate.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if (!isPaused()) {
						screen.update();
					}
				}
			}, 0, 1000/screenRefreshPerSecond);
		}
		
    	this.gameUpdate = new Timer();
    	gameUpdate.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				update();
			}
    	}, 1, 1);
    	
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
    	if (this.screenUpdate != null) screenUpdate.cancel();
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