public class AppliedForce {
    
    long startTimeMilis;
    long finishTimeMilis;
    float strength;
    
    public AppliedForce(float strength, long duration) {
        this.strength = strength;
        this.startTimeMilis = System.currentTimeMillis();
        this.finishTimeMilis = this.startTimeMilis + duration;
    }
    
    public boolean isFinished() {
        return System.currentTimeMillis() > this.finishTimeMilis;
    }
    
    public long getStartMilis() {
        return this.startTimeMilis;
    }
    
    public long getFinishMilis() {
        return this.finishTimeMilis;
    }
    
    public float getStrength() {
        return this.strength;
    }
    
    public Vector getVector() {
        return new Vector(0,strength);
    }
}