package bouncysquares;

import java.awt.Color;

import javax.swing.*;

public class Runner {

	public static void main(String[] args) {
		Visual v = new Visual(1200,1200);
		
		v.getEntityList().add(new Entity(200,0,90,90,Color.CYAN));
		v.getEntityList().get(0).setVelocity( new Velocity(Math.random() * 2*Math.PI,7) );
		
		v.getEntityList().add(new Entity(200,800,90,90,Color.ORANGE));
		v.getEntityList().get(1).setVelocity( new Velocity(Math.random() * 2*Math.PI,15) );
		
		v.getEntityList().add(new Entity(200,400,90,90,Color.YELLOW));
		v.getEntityList().get(2).setVelocity( new Velocity(Math.random() * 2*Math.PI,2) );

		v.getEntityList().add(new Entity(400,800,90,90,Color.GREEN));
		v.getEntityList().get(3).setVelocity( new Velocity(Math.random() * 2*Math.PI,10) );
		
		v.getEntityList().add(new Entity(400,400,90,90,Color.BLUE));
		v.getEntityList().get(4).setVelocity( new Velocity(Math.random() * 2*Math.PI,2) );
		
		v.getEntityList().add(new Entity(200,200,90,90,Color.PINK));
		v.getEntityList().get(5).setVelocity( new Velocity(Math.random() * 2*Math.PI,7) );

		v.getEntityList().add(new Entity(500,400,90,90,Color.RED));
		v.getEntityList().get(6).setVelocity( new Velocity(Math.random() * 2*Math.PI,5) );

		v.getEntityList().add(new Entity(500,500,90,90,Color.MAGENTA));
		v.getEntityList().get(7).setVelocity( new Velocity(Math.random() * 2*Math.PI,0) );
		
		v.setVisible(true);
		Refresher rf = new Refresher(v,16);
		JFrame jf = new JFrame("Test");
		jf.add(v);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(0, 0, v.getMaxWidth()+50, v.getMaxHeight()+60);
		jf.setVisible(true);
	}

}
