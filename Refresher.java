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
		ArrayList<Entity> toRemove = new ArrayList<>();
		for(int i = 0; i < vis.getEntityList().size(); i++){ //this is collision detecting
			for(int j = i+1; j < vis.getEntityList().size(); j++){
				if(vis.getEntityList().get(i).getHitbox().intersects( vis.getEntityList().get(j).getHitbox() )){
					vis.getHits().add( new CollisionFX(vis.getEntityList().get(i).getHitbox(), vis.getEntityList().get(j).getHitbox()) );
					if (vis.getEntityList().get(i).collisionReact( vis.getEntityList().get(j) )){
						vis.getEntityList().get(i).incrementStuck();
						vis.getEntityList().get(j).incrementStuck();
						if(vis.getEntityList().get(i).getStuckFrames() > Entity.STUCK_FRAME_LIMIT){
							toRemove.add(vis.getEntityList().get(i));
						}
						if(vis.getEntityList().get(j).getStuckFrames() > Entity.STUCK_FRAME_LIMIT){
							toRemove.add(vis.getEntityList().get(i));
						}
					} else {
						vis.getEntityList().get(i).resetStuck();
						vis.getEntityList().get(j).resetStuck();
					}
				}
			}
		}
		for(Entity e : vis.getEntityList()){ //boundary collision detect. intersect is a cool method
			for(StaticEntity se : vis.getBoundries()){
				if(e.getHitbox().intersects(se.getHitbox())){
					if (se.collisionReact(e)) {
						e.incrementStuck();
						if (e.getStuckFrames() > Entity.STUCK_FRAME_LIMIT) {
							toRemove.add(e);
						}
					} else {
						e.resetStuck();
					}
				}
			}
		}
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
