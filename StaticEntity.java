package bouncysquares;

import java.awt.Color;

//an entity that is completely immovable, different from zero velocity entities, since those can gain speeds
public class StaticEntity extends Entity{
	public StaticEntity(int x, int y, int width, int height, Color c) {
		super(x, y, width, height, c);
		this.vel = new Velocity(0,0);
	}
	
	@Override
	public void setVelocity(Velocity v){}
	
	@Override
	public void move(){}
	
	/**
	 * Immovable object react. Should be used with a non static entity sent in. Shouldnt break since move() and setVelocity do nothing() 
	 */
	@Override
	public boolean collisionReact(Entity e) {
		//imagine spending 4 hours pondering on a 5 line solution, yeesh
		double eY = e.hitbox.getCenterY();
		if(eY > this.hitbox.getY() + this.hitbox.getHeight() || eY < this.hitbox.getY()){ //below or above
			e.vel = new Velocity(e.vel.getxDir(), -1 * e.vel.getyDir(), e.vel.getMagnitude());
		} else { //on one of the sides
			e.vel = new Velocity(-1 * e.vel.getxDir(), e.vel.getyDir(), e.vel.getMagnitude());
		}
		if(e.hitbox.intersects(this.hitbox)){
			return true;
		}
		return false;
	}
}
