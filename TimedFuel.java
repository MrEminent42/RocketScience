public class TimedFuel {
    
    long startTimeMilis;
    long finishTimeMilis;
    int level;
    
    public TimedFuel(int level, long duration) {
        this.level = level;
        this.startTimeMilis = System.currentTimeMillis();
        this.finishTimeMilis = this.startTimeMilis + duration;
    }
    
    public boolean isFinished() {
        return System.currentTimeMillis() > this.finishTimeMilis;
    }
    
    public int getLevel() {
        return this.level;
    }
    
}