package bouncysquares;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import javax.swing.*;

public class Visual extends JComponent {
	private StaticEntity[] boundries = new StaticEntity[4];
	private ArrayList<Entity> things = new ArrayList<>();
	private ArrayList<CollisionFX> hits = new ArrayList<>();
	private final int maxWidth, maxHeight;
	public static final int DRAWN_VECTOR_MAGNITUDE_MULTIPLIER = 20;
	private Rectangle mousePlaced = null;
	private Color mousePlacedColor = null;
	private Line2D mousePlacedLine = null;
	
	public Visual(int width, int height){
		this.setBounds(0, 0, width, height);
		this.maxHeight = height;
		this.maxWidth = width;
		this.boundries[0] = new StaticEntity((int)(-width*0.3), 0, (int)(width*0.3), height, Color.DARK_GRAY); //left
		this.boundries[1] = new StaticEntity(0, (int)(-height*0.3), width, (int)(height*0.3), Color.DARK_GRAY); //top
		this.boundries[2] = new StaticEntity(width, 0, (int)(width*0.3), height, Color.DARK_GRAY); //right
		this.boundries[3] = new StaticEntity(0, height, width, (int)(height*0.3), Color.DARK_GRAY); //bottom
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setPaint(Color.gray);
		g2.fill( new Rectangle(0,0,this.maxWidth,this.maxHeight) ); //background
		
		g2.setPaint(Color.DARK_GRAY);
		for(StaticEntity se : boundries){
			g2.fill(se.getVisible());
		}
		
		for(Entity e : things){
			g2.setPaint(Color.BLACK.brighter().brighter());
			g2.fill(e.getShadow());
			g2.setPaint(e.getColor());
			g2.fill(e.getVisible());
			g2.setPaint(Color.BLACK);
			g2.drawLine((int)e.getHitbox().getCenterX(), (int)e.getHitbox().getCenterY(), 
					(int)e.getHitbox().getCenterX() + ( (int)(e.getVel().getxDir()*e.getVel().getMagnitude()) ) * DRAWN_VECTOR_MAGNITUDE_MULTIPLIER,
					(int)e.getHitbox().getCenterY() + ( (int)(e.getVel().getyDir()*e.getVel().getMagnitude()) ) * DRAWN_VECTOR_MAGNITUDE_MULTIPLIER
					); //velocity line
		}
		
		ArrayList<CollisionFX> toRemove = new ArrayList<>();
		for(CollisionFX col : hits){
			g2.setPaint(CollisionFX.hitColor);
			g2.fill(col.getHit());
			col.decrementTTL();
			if(col.getTTL() <= 0){
				toRemove.add(col);
			}
		}
		for(CollisionFX col : toRemove){
			hits.remove(col);
		}
		toRemove.clear();
		if(mousePlaced != null && mousePlacedColor != null){
			g2.setPaint( mousePlacedColor );
			g2.fill(mousePlaced);
		}
		if(mousePlacedLine != null){
			g2.setPaint(Color.BLACK);
			g2.drawLine((int)mousePlacedLine.getX1(), (int)mousePlacedLine.getY1(), (int)mousePlacedLine.getX2(), (int)mousePlacedLine.getY2());
		}
	}
	
	public ArrayList<Entity> getEntityList(){
		return this.things;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public ArrayList<CollisionFX> getHits() {
		return hits;
	}

	public StaticEntity[] getBoundries() {
		return boundries;
	}

	public Rectangle getMousePlaced() {
		return mousePlaced;
	}

	public Color getMousePlacedColor() {
		return mousePlacedColor;
	}

	public void setMousePlaced(Rectangle mousePlaced) {
		this.mousePlaced = mousePlaced;
	}

	public void setMousePlacedColor(Color mousePlacedColor) {
		this.mousePlacedColor = mousePlacedColor;
	}

	public Line2D getMousePlacedLine() {
		return mousePlacedLine;
	}

	public void setMousePlacedLine(Line2D mousePlacedLine) {
		this.mousePlacedLine = mousePlacedLine;
	}
	
	
}
