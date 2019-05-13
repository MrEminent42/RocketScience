package game;
import java.util.ArrayList;

public class Rocket {
    
	RocketGame game;
	
	double fuelTank = game.startingRocketFuel;
	double fuelLevel = 0;
	
    Vector pos;
    Vector accl;
    Vector vel;
    
    ArrayList<TimedFuel> fuelQueue;
    ArrayList<TimedFuel> pastFuel;
    
    public Rocket(RocketGame parent) {
        this(parent, new ArrayList<TimedFuel>());
    }
    
    public Rocket(RocketGame parent, ArrayList<TimedFuel> fuelQueue) {
    	this.game = parent;
        this.pos = game.startingRocketPos.copy();
        this.accl = new Vector(0, 0);
        this.vel = new Vector(0, 0);
        this.fuelQueue = fuelQueue;
        this.pastFuel = new ArrayList<TimedFuel>();
    }
    
    public void update() {
        // add gravitational force
        this.accl.add(new Vector(0, game.GRAVITY));
        
        // set fuel level
        if (fuelQueue.size() > 0) {
        	TimedFuel f = fuelQueue.get(0);
        	if (!f.hasStarted()) {
        		f.start();
        		setFuelLevel(f.getLevel());
        	} else if (f.isFinished()) {
        		fuelQueue.remove(f);
        		pastFuel.add(f);
        		setFuelLevel(0);
        	}
        }
        
        // add fuel force 
        accl.add(new Vector(0, fuelLevel*game.velPerFuelLevel/game.refreshPerSecond));
        fuelTank -= fuelLevel/game.refreshPerSecond;
        
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
    	return this.fuelLevel;
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
    	this.fuelLevel = level;
    }
    
    public ArrayList<TimedFuel> getPastFuelLog() {
    	return pastFuel;
    }
    
}