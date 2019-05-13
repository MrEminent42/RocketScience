package game;
public class TimedFuel {
    
    long startTimeMilis = -1;
    long finishTimeMilis;
    long duration;
    int level;
    
    /**
     * Create a TimedFuel
     * @param level fuel level between 0-9 (recommended)
     * @param duration duration in miliseconds
     */
    public TimedFuel(int level, long duration) {
        this.level = level;
        this.duration = duration;
    }
    
    /**
     * log the start time of the fuel
     */
    public void start() {
    	this.startTimeMilis = System.currentTimeMillis();
    	this.finishTimeMilis = startTimeMilis + this.duration;
    }
    
    /**
     * @return if this TimedFuel has been activated
     */
    public boolean hasStarted() {
    	return startTimeMilis != -1;
    }
    
    /**
     * @return if this TimedFuel has past its finish time in milis
     */
    public boolean isFinished() {
        return System.currentTimeMillis() > this.finishTimeMilis;
    }
    
    /**
     * @return the FuelLevel of this TimedFuel
     */
    public int getFuelLevel() {
        return this.level;
    }
    
}