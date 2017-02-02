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
	
	public Entity(int x, int y, int width, int height, Color c){
		this.hitbox = new Rectangle(x,y,width,height);
		this.visible = new Rectangle(x,y,width,height);
		this.shadow = new Rectangle();
		this.color = c;
		this.x = x;
		this.y = y;
	}
	
	public void move(){
		shadow = new Rectangle(this.x, this.y, (int)this.visible.getWidth(), (int)this.visible.getHeight());
		this.visible.translate((int)(vel.getMagnitude() * vel.getxDir()), (int)(vel.getMagnitude() * vel.getyDir()));
		this.hitbox.translate((int)(vel.getMagnitude() * vel.getxDir()), (int)(vel.getMagnitude() * vel.getyDir()));
		this.x += (int)(vel.getMagnitude() * vel.getxDir());
		this.y += (int)(vel.getMagnitude() * vel.getyDir());
	}

	public void collisionReact(Entity e) {
		//the first two ifs are for momentum, somewhat (hardly) accurate
		if(Math.abs( this.vel.getAngle() - e.vel.getAngle() ) <= Math.PI*0.25){ //same direction angle
			System.out.println("rear end collision");
			double tempMag = e.vel.getMagnitude();
			e.vel = new Velocity(e.vel.getxDir(), e.vel.getyDir(), this.vel.getMagnitude());
			this.vel = new Velocity(this.vel.getxDir(), this.vel.getyDir(), tempMag);

		} else if (Math.abs((((this.vel.getAngle() + Math.PI)) % (2.0 * Math.PI)) - e.vel.getAngle()) <= Math.PI * 0.25) { // opposite
			System.out.println("headon collision");
			double tempMag = e.vel.getMagnitude();
			e.vel = new Velocity(-1*e.vel.getxDir(), -1*e.vel.getyDir(), this.vel.getMagnitude());
			this.vel = new Velocity(-1*this.vel.getxDir(), -1*this.vel.getyDir(), tempMag);
		} 
		else {
			this.vel = new Velocity(-1*this.vel.getxDir(), -1*this.vel.getyDir(), this.vel.getMagnitude());
			e.vel = new Velocity(-1*e.vel.getxDir(), -1*e.vel.getyDir(), e.vel.getMagnitude());
		}
		do {
			if(new Random().nextBoolean()){				
				this.move();
			} else {
				e.move();
			}
		} while (this.hitbox.intersects(e.hitbox));
	}
	/*
	public int outOfBoundsBounce(int xBound, int yBound){
		int upLeftX = this.getX();
		int upLeftY = this.getY();
		int downLeftX = this.getX();
		int downLeftY = (int)this.getHitbox().getMaxY();
		int upRightX = (int)this.getHitbox().getMaxX();
		int upRightY = this.getY();
		int downRightX = (int)this.getHitbox().getMaxX();
		int downRightY = (int)this.getHitbox().getMaxY();
		double percTolerance = 0.3;
		
		if(x < 0 || x + hitbox.getWidth() > xBound){
			vel = new Velocity(-1*vel.getxDir(),vel.getyDir(),vel.getMagnitude());
		}
		if(y < 0 || y + hitbox.getHeight() > yBound){
			vel = new Velocity(vel.getxDir(),-1*vel.getyDir(),vel.getMagnitude());
		}
		this.move();
		if(	upLeftX < 0 - this.hitbox.getWidth()*percTolerance || downLeftX < 0 - this.hitbox.getWidth()*percTolerance || upRightX < 0 - this.hitbox.getWidth()*percTolerance || downRightX < 0 - this.hitbox.getWidth()*percTolerance ||
				upLeftX > xBound + this.hitbox.getWidth()*percTolerance || downLeftX > xBound + this.hitbox.getWidth()*percTolerance || upRightX > xBound + this.hitbox.getWidth()*percTolerance || downRightX > xBound + this.hitbox.getWidth()*percTolerance ||
				upLeftY < 0 - this.hitbox.getHeight()*percTolerance || downLeftY < 0 - this.hitbox.getHeight()*percTolerance || upRightY < 0 - this.hitbox.getHeight()*percTolerance || downRightY < 0 - this.hitbox.getHeight()*percTolerance ||
				upLeftY > yBound + this.hitbox.getHeight()*percTolerance || downLeftY > yBound + this.hitbox.getHeight()*percTolerance|| upRightY > yBound + this.hitbox.getHeight()*percTolerance|| downRightY > yBound + this.hitbox.getHeight()*percTolerance 
				){
			return 1;
		}
		return 0;
	}
	*/
	
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
	
	
}
