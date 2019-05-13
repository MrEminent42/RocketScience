package game;
import java.util.ArrayList;

public class Rocket {
    
	RocketGame game;
	
	double fuelTank = game.startingRocketFuel;
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
    
    public double getX() {
    	return this.pos.x;
    }
    
    public double getY() {
    	return this.pos.y;
    }
    
    public double getFuelTank() {
    	return this.fuelTank;
    }
    
    public double getFuelLevel() {
    	return this.currentFuelLevel;
    }
    
    public double getVelocityPerSecond() {
    	return this.vel.getMag()*game.refreshPerSecond;
    }
    
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