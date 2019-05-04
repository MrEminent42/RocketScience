public class Vector {
    
    float x, y;
    
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }
    
    public void mult(float intensity) {
        this.x *= intensity;
        this.y *= intensity;
    }
    
    public void rotate(float theta) {
        float xx = this.x;
        float yy = this.y;
        this.x = (float) (xx*Math.cos(theta) - yy*Math.sin(theta));
        this.y = (float) (yy*Math.cos(theta) + xx*Math.sin(theta));
    }
    
    
    public double getMag() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    
    public double getAngle() {
        return Math.atan(y/x);
    }
}