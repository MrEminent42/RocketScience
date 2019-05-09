import java.util.ArrayList;

public class Rocket {
    
	double fuelTank = 200;
	double fuelLevel = 0;
	
    Vector pos;
    Vector accl;
    Vector vel;
    
    ArrayList<TimedFuel> fuelQueue;
    
    public Rocket() {
        this(new ArrayList<TimedFuel>());
    }
    
    public Rocket(ArrayList<TimedFuel> fuelQueue) {
        this.pos = new Vector(0, 100);
        this.accl = new Vector(0, 0);
        this.vel = new Vector(0, 0);
        this.fuelQueue = fuelQueue;
    }
    
    public void update() {
        // add gravitational force
        this.accl.add(new Vector(0, RocketGame.GRAVITY));
        
        // set fuel level
        if (fuelQueue.size() > 0) {
        	TimedFuel f = fuelQueue.get(0);
        	if (!f.hasStarted()) {
        		f.start();
        		setFuelLevel(f.getLevel());
        	} else if (f.isFinished()) {
        		fuelQueue.remove(f);
        		setFuelLevel(0);
        	}
        }
        
        accl.add(new Vector(0, fuelLevel*RocketGame.velPerFuelLevel/RocketGame.refreshPerSecond));
        fuelTank -= fuelLevel/RocketGame.refreshPerSecond;
        
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
    
    public double getVelocityPerSecond() {
    	return this.vel.getMag()*RocketGame.refreshPerSecond;
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
    
}