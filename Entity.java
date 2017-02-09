package bouncysquares;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Random;

public class Entity {
	protected Rectangle hitbox,visible,shadow;
	protected Color color;
	protected Velocity vel;
	protected int x,y;
	private int stuckFrames = 0;
	public static int STUCK_FRAME_LIMIT = 60;
	
	
	public Entity(int x, int y, int width, int height, Color c){
		this.hitbox = new Rectangle(x,y,width,height);
		this.visible = new Rectangle(x,y,width,height);
		this.shadow = new Rectangle();
		this.color = c;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Moves the entity one "step" based on its velocity vector
	 */
	public void move(){
		shadow = new Rectangle((int)this.x, (int)this.y, (int)Math.round(this.visible.getWidth()), (int)Math.round(this.visible.getHeight()));
		this.visible.translate((int)Math.round(vel.getMagnitude() * vel.getxDir()), (int)Math.round(vel.getMagnitude() * vel.getyDir()));
		this.hitbox.translate((int)Math.round(vel.getMagnitude() * vel.getxDir()), (int)Math.round(vel.getMagnitude() * vel.getyDir()));
		this.x += Math.round(vel.getMagnitude() * vel.getxDir());
		this.y += Math.round(vel.getMagnitude() * vel.getyDir());
	}
	
	/**
	 * Called when a collision has been detected. This method does not check for collisions, This does the reaction part
	 * @param e The entity that this collided with
	 * @return if an infinite loop is detected, true is returned. No infinite loop false is returned
	 */
	public boolean collisionReact(Entity e) { //TODO add separate clause so unmoving entities assume the angle of what hit it
		if (e instanceof StaticEntity) { //make sure to use the right override
			e.collisionReact(this);
			return false;
		} else {
			// the first three ifs are for momentum, somewhat (hardly) accurate. Didnt feel like dealing with momentum AND kinetic energy i dont have mass implemented yet
			if(e.vel.equals(Velocity.ZERO_VECTOR) || this.vel.equals(Velocity.ZERO_VECTOR)){
				Velocity temp = this.vel;
				this.setVelocity(e.vel);
				e.setVelocity(temp);
			} else if (Math.abs( (this.vel.getAngle() % (0.5 * Math.PI)) - (e.vel.getAngle() % (0.5 * Math.PI)) ) <= Math.PI * 0.5 
					|| this.vel.magnitude == 0 || e.vel.magnitude == 0) { // opposite
				// System.out.println("headon collision");
				double tempMag = e.vel.getMagnitude();
				e.vel = new Velocity(-1 * e.vel.getxDir(), -1 * e.vel.getyDir(), this.vel.getMagnitude());
				this.vel = new Velocity(-1 * this.vel.getxDir(), -1 * this.vel.getyDir(), tempMag);
			} else if (Math.abs(this.vel.getAngle() - e.vel.getAngle()) <= Math.PI * 0.51) { // same direction angle
				// System.out.println("rear end collision");
				double tempMag = e.vel.getMagnitude();
				e.vel = new Velocity(e.vel.getxDir(), e.vel.getyDir(), this.vel.getMagnitude());
				this.vel = new Velocity(this.vel.getxDir(), this.vel.getyDir(), tempMag);
			} else {
				this.vel = new Velocity(-1 * this.vel.getxDir(), -1 * this.vel.getyDir(), this.vel.getMagnitude());
				e.vel = new Velocity(-1 * e.vel.getxDir(), -1 * e.vel.getyDir(), e.vel.getMagnitude());
			}
			int counter = 0;
//			do {
//				if (new Random().nextBoolean()) {
//					this.move(); //moves if the boxes are still intersecting. really shit way to go about it because of the teleporty bullshit this results in
//					counter++;
//					if(counter >= 300){
//						return true;
//					}
//				} else {
//					e.move(); 
//					counter++;
//					if(counter >= 300){
//						return true;
//					}
//				}
//			} while (this.hitbox.intersects(e.hitbox));
			
			if(this.hitbox.intersects(e.hitbox)){
				this.vel = new Velocity(this.vel.getxDir(),this.vel.getyDir(), this.vel.getMagnitude()*3);
				e.vel = new Velocity(e.vel.getxDir(),e.vel.getyDir(), e.vel.getMagnitude()*3);
				this.move();
				e.move();
				this.vel = new Velocity(this.vel.getxDir(),this.vel.getyDir(), this.vel.getMagnitude()/3);
				e.vel = new Velocity(e.vel.getxDir(), e.vel.getyDir(), e.vel.getMagnitude()/3);
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Removes an entity based on how much of the boxes sides are out of bounds. percTolerance determines this
	 * @param xBound The maximum bound of Visual the entity is found in
	 * @param yBound The minimum bound of Visual the entity is found in
	 * @return 1 if the entity if out of bounds, 0 if it isnt
	 */
	public int outOfBoundsRemove(int xBound, int yBound){
		int upLeftX = this.getX();
		int upLeftY = this.getY();
		int downLeftX = this.getX();
		int downLeftY = (int)this.getHitbox().getMaxY();
		int upRightX = (int)this.getHitbox().getMaxX();
		int upRightY = this.getY();
		int downRightX = (int)this.getHitbox().getMaxX();
		int downRightY = (int)this.getHitbox().getMaxY();
		double percTolerance = 0.3; //percent in decimal for of how much of a side needs to be outside before deletion
		
		if(	upLeftX < 0 - this.hitbox.getWidth()*percTolerance || downLeftX < 0 - this.hitbox.getWidth()*percTolerance || upRightX < 0 - this.hitbox.getWidth()*percTolerance || downRightX < 0 - this.hitbox.getWidth()*percTolerance ||
				upLeftX > xBound + this.hitbox.getWidth()*percTolerance || downLeftX > xBound + this.hitbox.getWidth()*percTolerance || upRightX > xBound + this.hitbox.getWidth()*percTolerance || downRightX > xBound + this.hitbox.getWidth()*percTolerance ||
				upLeftY < 0 - this.hitbox.getHeight()*percTolerance || downLeftY < 0 - this.hitbox.getHeight()*percTolerance || upRightY < 0 - this.hitbox.getHeight()*percTolerance || downRightY < 0 - this.hitbox.getHeight()*percTolerance ||
				upLeftY > yBound + this.hitbox.getHeight()*percTolerance || downLeftY > yBound + this.hitbox.getHeight()*percTolerance|| upRightY > yBound + this.hitbox.getHeight()*percTolerance|| downRightY > yBound + this.hitbox.getHeight()*percTolerance 
				){
			return 1;
		}
		return 0;
	}
	
	
	public void setVelocity(Velocity v){
		this.vel = v;
		
	}

	public Color getColor() {
		return this.color;
	}

	public Shape getVisible() {
		return this.visible;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public Velocity getVel() {
		return vel;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getShadow() {
		return shadow;
	}

	public int getStuckFrames() {
		return stuckFrames;
	}

	public void incrementStuck(){
		this.stuckFrames++;
	}
	
	public void resetStuck(){
		this.stuckFrames = 0;
	}
	
}
