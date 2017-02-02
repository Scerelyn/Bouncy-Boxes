package bouncysquares;

public class Velocity {
	double xDir,yDir,magnitude; //+-1 or 0 only for direction
	
	public Velocity(double xDir, double yDir, double mag){
		if(xDir > 1 || xDir < -1 || yDir > 1 || yDir < -1){
			throw new RuntimeException("Invalid Velocity Direction, values should be only from 1 to -1");
		}
		this.xDir = xDir;
		this.yDir = yDir;
		this.magnitude = mag;
		//System.out.println(xDir + " " + yDir + " " + getAngle() + " " + mag);
	}
	
	public Velocity(double angle, double mag){
		this(Math.cos(angle),-Math.sin(angle),mag);
	}
	
	public double getAngle(){
		if(xDir >= 0 &&xDir <= 1e-14 && yDir >= 0 && yDir <= 1e-14){
			throw new ArithmeticException("Failed to get angle, Zero vector found, what the hell");
		} else if(xDir <= 1e-14){
			if(yDir > 0){
				return Math.PI/2;
			} else if(yDir < 0){
				return 3*Math.PI/2;
			}
		} else if(yDir <= 1e-14){
			if(xDir > 0){
				return 0;
			} else if(xDir < 0){
				return Math.PI;
			}
		}
		return (yDir < 0 && xDir > 0) || (yDir > 0 && xDir < 0)
				? Math.atan(yDir/xDir) + Math.PI //Qudrants II and III
				: (xDir > 0 && yDir < 0)
					? Math.atan(yDir/xDir) + 2*Math.PI //Quadrant IV
					: Math.atan(yDir/xDir); //quadrant I
	}

	public double getxDir() {
		return xDir;
	}

	public double getyDir() {
		return yDir;
	}

	public double getMagnitude() {
		return magnitude;
	}
	
	
}
