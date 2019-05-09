import java.util.Timer;
import java.util.TimerTask;

public class RocketGame {
	
	public static final int crashVelocity = 2;
	public static final int refreshPerSecond = 1000;
    public static final double GRAVITY = -0.01/((double) refreshPerSecond);
    public static final double velPerFuelLevel = 0.0025;
    
    Rocket rocket;
    RocketScreen screen;
    Timer updateTask;
    long startMilis;
    
    private boolean paused = false;
    private boolean crashed = false;
    private boolean landed = false;
    private boolean outOfFuel = false;
    
    public RocketGame() {
        this.rocket = new Rocket();
        this.screen = new RocketScreen(this);
        
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
    		screen.displayText("You ran out of fuel!");
    	}
    	
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