package bouncysquares;

import java.awt.Color;

public class StaticEntity extends Entity{
	public StaticEntity(int x, int y, int width, int height, Color c) {
		super(x, y, width, height, c);
	}
	
	@Override
	public void setVelocity(Velocity v){
		//'3'
		this.vel = new Velocity(0,0);
	}
	
	@Override
	public void collisionReact(Entity e) { //TODO invert one direction at a time
		e.vel = new Velocity(-1 * e.vel.getxDir(), -1 * e.vel.getyDir(), e.vel.getMagnitude());
		do {
			e.move();
		} while (this.hitbox.intersects(e.hitbox));
	}
}
