package game;
public class Vector {
    
    double x, y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }
    
    public void mult(double intensity) {
        this.x *= intensity;
        this.y *= intensity;
    }
    
    public void rotate(double theta) {
        double xx = this.x;
        double yy = this.y;
        this.x = (double) (xx*Math.cos(theta) - yy*Math.sin(theta));
        this.y = (double) (yy*Math.cos(theta) + xx*Math.sin(theta));
    }
    
    
    public double getMag() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    
    public double getAngle() {
        return Math.atan(y/x);
    }
    
    public Vector copy() {
    	return new Vector(this.x, this.y);
    }
}