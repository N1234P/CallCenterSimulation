/**
 * This class will specify where the customer object will be located on the simulator view.
 *
 */

public class Coordinate {
    int x, y;
    
    public Coordinate(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public int getX() {
    	return x;
    }
    
    public int getY() {
    	return y;
    }
    
    public String toString() {
    	return x + " " + y;
    }
}
