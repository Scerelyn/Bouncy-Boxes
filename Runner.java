package bouncysquares;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.*;

public class Runner {

	public static void main(String[] args) {
		Visual v = new Visual(20,20,1200,1200);
		int boxLength = 90;
		
		v.setVisible(true);
		Refresher rf = new Refresher(v,16);
		JFrame jf = new JFrame("Bouncy");
		jf.addMouseListener(new MouseAdapter(){
			
			/**
			 * Creates the box image, showing the user the box location and its size
			 */
			@Override
			public void mousePressed(MouseEvent me){
				v.setMousePlacedColor( new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)) );
				v.setMousePlaced( new Rectangle(me.getX()-12-(boxLength/2), me.getY()-45-(boxLength/2), boxLength,boxLength) );	
			}
			
			/**
			 * Actaully places the box, taking in visual data and turing it into data that Velocity can use. Obnoxious trig happens here
			 */
			@Override
			public void mouseReleased(MouseEvent me){
				v.getEntityList().add( new Entity((int)v.getMousePlaced().getX(), (int)v.getMousePlaced().getY(), boxLength, boxLength, v.getMousePlacedColor()) );
				double lineLength = Point2D.distance(me.getX()-12, me.getY()-45, v.getMousePlaced().getCenterX(), v.getMousePlaced().getCenterY());
				double mag = lineLength/Visual.DRAWN_VECTOR_MAGNITUDE_MULTIPLIER;
				double vectX = Math.abs( me.getX()-12 - v.getMousePlaced().getCenterX());
				double vectY = Math.abs( me.getY()-45 - v.getMousePlaced().getCenterY());
				double angle = Math.atan( vectY/vectX );
				if(Double.isNaN(angle) || (Double.isNaN(vectX) &&  Double.isNaN(vectY))){
					v.getEntityList().get( v.getEntityList().size()-1 ).setVelocity( Velocity.ZERO_VECTOR );
				} else if(vectX < 1e-14){ //result in atan being NaN if this is true, or almost NaN, since division by zero
					if(vectY < 0){
						v.getEntityList().get( v.getEntityList().size()-1 ).setVelocity( new Velocity(1.5*Math.PI, mag) ); //recall that atan retuns angles from pi/2 to -pi/2, which is why the + pi/2 shit happens and a bunch of ifs are used
					} else {
						v.getEntityList().get( v.getEntityList().size()-1 ).setVelocity( new Velocity(Math.PI/2, mag) );
					}
				} else if(me.getX()-12 < v.getMousePlaced().getCenterX()){ //left
					if(me.getY()-45 > v.getMousePlaced().getCenterY()){ //bottom
						v.getEntityList().get( v.getEntityList().size()-1 ).setVelocity( new Velocity(angle+(Math.PI), mag) );
					} else { //top
						v.getEntityList().get( v.getEntityList().size()-1 ).setVelocity( new Velocity( (Math.PI)-angle, mag) ); //had to invert the angle for some reason
					}
				} else { //right
					if (me.getY() - 45 > v.getMousePlaced().getCenterY()) { // bottom
						v.getEntityList().get( v.getEntityList().size()-1 ).setVelocity( new Velocity(( (2*Math.PI)-angle ), mag));
					} else { // top
						v.getEntityList().get( v.getEntityList().size()-1 ).setVelocity( new Velocity(angle, mag) );
					}
				}
				v.setMousePlaced(null);
				v.setMousePlacedLine(null);
			}
		});
		jf.addMouseMotionListener(new MouseMotionListener(){
			/**
			 * Dragging the mouse moves the velocity vector of the box
			 */
			@Override
			public void mouseDragged(MouseEvent me){ //this isnt invoked on mouse adapter nice one dog
				v.setMousePlacedLine( new Line2D.Double( v.getMousePlaced().getCenterX(), v.getMousePlaced().getCenterY(), me.getX()-12, me.getY()-45 ) );
			}
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				//what do
			}
		});
		jf.add(v);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(0, 0, v.getMaxWidth()+50, v.getMaxHeight()+60);
		jf.setVisible(true);
	}

}
