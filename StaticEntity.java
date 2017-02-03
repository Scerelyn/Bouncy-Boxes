package bouncysquares;

import java.awt.Color;

public class StaticEntity extends Entity{
	public StaticEntity(int x, int y, int width, int height, Color c) {
		super(x, y, width, height, c);
	}
	
	@Override
	public void setVelocity(Velocity v){
		this.vel = new Velocity(0,0);
	}
	
	@Override
	public void collisionReact(Entity e) {
		double eX = e.hitbox.getCenterX();
		double eY = e.hitbox.getCenterY();
		double thisX = this.hitbox.getCenterX();
		double thisY = this.hitbox.getCenterY();
		
		
		
		if(eY > this.hitbox.getY() + this.hitbox.getHeight()){ //below
			e.vel = new Velocity(e.vel.getxDir(), -1 * e.vel.getyDir(), e.vel.getMagnitude());
		} else if(eY < this.hitbox.getY()){ //on the top
			e.vel = new Velocity(e.vel.getxDir(), -1 * e.vel.getyDir(), e.vel.getMagnitude());
		} else { //on one of the sides
			e.vel = new Velocity(-1 * e.vel.getxDir(), e.vel.getyDir(), e.vel.getMagnitude());
		}
		
		do {
			e.move();
		} while (this.hitbox.intersects(e.hitbox));
	}
}
