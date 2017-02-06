package bouncysquares;

import java.awt.*;

public class CollisionFX {
	private Rectangle hit;
	public static final Color hitColor = Color.BLACK;
	private int TTL; //frames to live
	public CollisionFX(Rectangle one, Rectangle two){
		hit = one.intersection(two);
		TTL = 60;
	}
	public Rectangle getHit() {
		return hit;
	}
	public int getTTL() {
		return TTL;
	}
	
	public void decrementTTL(){
		TTL--;
	}
}
