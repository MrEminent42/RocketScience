package game;
public class TimedFuel {
    
    long startTimeMilis = -1;
    long finishTimeMilis;
    long duration;
    int level;
    
    public TimedFuel(int level, long duration) {
        this.level = level;
        this.duration = duration;
    }
    
    public void start() {
    	this.startTimeMilis = System.currentTimeMillis();
    	this.finishTimeMilis = startTimeMilis + this.duration;
    }
    
    public boolean hasStarted() {
    	return startTimeMilis != -1;
    }
    
    public boolean isFinished() {
        return System.currentTimeMillis() > this.finishTimeMilis;
    }
    
    public int getLevel() {
        return this.level;
    }
    
}