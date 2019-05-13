package game;
import java.util.ArrayList;

public class Rocket {
    
	RocketGame game;
	
	double fuelTank = 0;
	double currentFuelLevel = 0;
	
    Vector pos;
    Vector accl;
    Vector vel;
    
    ArrayList<TimedFuel> fuelQueue;
    ArrayList<TimedFuel> pastFuel;
    
    /**
     * Create a rocket object
     * @param parent RocketGame that contains this rocket
     */
    public Rocket(RocketGame parent) {
        this(parent, new ArrayList<TimedFuel>());
    }
    
    /**
     * Create a rocket object with a queue of TimedFuels to be executed
     * @param parent RocketGame that contains this rocket
     * @param fuelQueue TimedFuels to be executed in order
     */
    public Rocket(RocketGame parent, ArrayList<TimedFuel> fuelQueue) {
    	this.game = parent;
        this.pos = game.startingRocketPos.copy();
        this.accl = new Vector(0, 0);
        this.vel = new Vector(0, 0);
        this.fuelQueue = fuelQueue;
        this.pastFuel = new ArrayList<TimedFuel>();
        this.fuelTank = game.startingRocketFuel;
    }
    
    /**
     * calculate and move to the next position, and use the necessary fuel
     */
    public void update() {
        // add gravitational force
        this.accl.add(new Vector(0, game.GRAVITY));
        
        // set fuel level
        if (fuelQueue.size() > 0) {
        	TimedFuel f = fuelQueue.get(0);
        	if (!f.hasStarted()) {
        		f.start();
        		setFuelLevel(f.getFuelLevel());
        	} else if (f.isFinished()) {
        		fuelQueue.remove(f);
        		pastFuel.add(f);
        		setFuelLevel(0);
        	}
        }
        
        // add fuel force 
        accl.add(new Vector(0, currentFuelLevel*game.velPerFuelLevel/game.refreshPerSecond));
        fuelTank -= currentFuelLevel/game.refreshPerSecond;
        
        vel.add(accl);
        pos.add(vel);
        accl = new Vector(0,0);
    }
    
    /**
     * @return the x-position of the rocket (doesn't change)
     */
    public double getX() {
    	return this.pos.x;
    }
    
    /**
     * @return the y-position of the rocket
     */
    public double getY() {
    	return this.pos.y;
    }
    
    /**
     * @return the amount of fuel left
     */
    public double getFuelLeft() {
    	return this.fuelTank;
    }
    
    /**
     * @return the current fuel level of the rocket
     */
    public double getFuelLevel() {
    	return this.currentFuelLevel;
    }
    
    /**
     * @return velocity per second
     */
    public double getVelocityPerSecond() {
    	return this.vel.getMag()*game.refreshPerSecond;
    }
    
    /**
     * @return velocity per refresh of the screen
     */
    public double getVelocityPerRefresh() {
    	return this.vel.getMag();
    }
    
    public boolean isTouchingGround() {
    	return this.pos.y <= 0;
    }
    
    public void addFuelToQueue(int strength, long durationMilis) {
    	addTimedFuelToQueue(new TimedFuel(strength, durationMilis));
    }
    
    public void addTimedFuelToQueue(TimedFuel f) {
    	fuelQueue.add(f);
    }
    
    public void setFuelLevel(int level) {
    	this.currentFuelLevel = level;
    }
    
    public ArrayList<TimedFuel> getPastFuelLog() {
    	return pastFuel;
    }
    
}