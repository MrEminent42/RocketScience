import java.util.ArrayList;

public class Rocket {
    
	double fuelTank = 1000;
	double fuelLevel = 0;
	
    Vector pos;
    Vector accl;
    Vector vel;
    
    ArrayList<TimedFuel> fuelQueue;
    
    public Rocket() {
        this.pos = new Vector(0, 100);
        this.accl = new Vector(0, 0);
        this.vel = new Vector(0, 0);
        
        this.fuelQueue = new ArrayList<TimedFuel>();
    }
    
    public void update() {
        // add gravitational force
        this.accl.add(new Vector(0, RocketGame.GRAVITY));
        
        // set fuel level
        if (fuelQueue.size() > 0) {
        	TimedFuel f = fuelQueue.get(0);
        	if (f.isFinished()) {
        		fuelQueue.remove(f);
        	} else {
        		setFuelLevel(f.getLevel());
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
    	addTimedFuel(new TimedFuel(strength, durationMilis));
    }
    
    public void setFuelLevel(int level) {
    	this.fuelLevel = level;
    }
    
    private void addTimedFuel(TimedFuel f) {
    	fuelQueue.add(f);
    }
    
}