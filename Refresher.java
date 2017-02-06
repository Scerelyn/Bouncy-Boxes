package bouncysquares;

import java.util.*;

public class Refresher extends TimerTask{
	public final static Timer mainTimer = new Timer();
	private Visual vis;
	
	public Refresher(Visual v, long refreshRate){
		this.vis = v;
		mainTimer.schedule(this, 0, refreshRate);
	}
	@Override
	public void run() { //collision detects, moves entities, then redraws the window
		for(int i = 0; i < vis.getEntityList().size(); i++){ //this is collision detecting
			for(int j = i+1; j < vis.getEntityList().size(); j++){
				if(vis.getEntityList().get(i).getHitbox().intersects( vis.getEntityList().get(j).getHitbox() )){
					vis.getHits().add( new CollisionFX(vis.getEntityList().get(i).getHitbox(), vis.getEntityList().get(j).getHitbox()) );
					vis.getEntityList().get(i).collisionReact( vis.getEntityList().get(j) );
				}
			}
		}
		for(Entity e : vis.getEntityList()){ //boundry collision detect. intersect is a cool method
			for(StaticEntity se : vis.getBoundries()){
				if(e.getHitbox().intersects(se.getHitbox())){
					se.collisionReact(e);
				}
			}
		}
		ArrayList<Entity> toRemove = new ArrayList<>();
		for(Entity e : vis.getEntityList()){ //out of bounds checking
			if(e.outOfBoundsRemove(vis.getMaxWidth(), vis.getMaxWidth()) == 1){
				toRemove.add(e);
				System.out.println("out of bounds square removed");
			}
			e.move();
		}
		for(Entity e : toRemove){
			vis.getEntityList().remove(e);
		}
		toRemove.clear();
		vis.repaint();
	}
}
