package entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import entity.Entity;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import mariomain.states.BossState;
import tile_block.Tile;

public class TowerBoss extends Entity {

	public int jumpTime=0;
	int nextPhase=0;
	int rand=0;
	public boolean addJumpTime=false;
	private Random random;
	private boolean bossFacing=false;  
	
	public TowerBoss(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int hp) {
		
		super(x, y, width, height, solid, id, handler);
		this.hp=hp;
		
		bossState=BossState.IDLE;
	
	}

	@Override
	public void render(Graphics g) {
		if(bossFacing) {
		if(bossState==BossState.IDLE||bossState==BossState.SPINNING) {
			g.drawImage(Game.TowerBoss[4].getBufferedImage(),x,y,width,height,null);}
		else if(bossState==BossState.RECOVERING) {
			g.drawImage(Game.TowerBoss[5].getBufferedImage(),x,y,width,height,null);}
		
		else if ( (bossState==BossState.JUMPING)) {
			g.drawImage(Game.TowerBoss[7].getBufferedImage(),x,y,width,height,null);}
		else g.drawImage(Game.TowerBoss[6].getBufferedImage(),x,y,width,height,null);
	}
		else if(!bossFacing) {
		if(bossState==BossState.IDLE||bossState==BossState.SPINNING) {
			g.drawImage(Game.TowerBoss[0].getBufferedImage(),x,y,width,height,null);}
		else if(bossState==BossState.RECOVERING) {
			g.drawImage(Game.TowerBoss[1].getBufferedImage(),x,y,width,height,null);}
		
		else if ( (bossState==BossState.JUMPING)) {
			g.drawImage(Game.TowerBoss[3].getBufferedImage(),x,y,width,height,null);}
		else g.drawImage(Game.TowerBoss[2].getBufferedImage(),x,y,width,height,null);
	}
		
		}

	@Override
	public void tick() {
	
		x+=velx;
		y+=vely;
		if(velx>0)bossFacing=true;
		else bossFacing=false;
		if(hp<=0) die();
		phaseTime++;
		
		if((phaseTime>=180&&bossState==BossState.IDLE)||(phaseTime>=600&&bossState!=BossState.SPINNING)) chooseState();
		
		if(bossState==BossState.RECOVERING&&phaseTime>=180) {
			bossState=BossState.SPINNING;
			phaseTime=0;
		}
		if(phaseTime>=360&&bossState==BossState.SPINNING) {
			phaseTime=0;
			bossState=BossState.IDLE;
		}
		
		if(bossState==BossState.IDLE||bossState==BossState.RECOVERING){
			setVelx(0);
			setVely(0);
		}
		
		if(bossState==BossState.JUMPING||bossState==BossState.RUNNING) attackable=true;
		else attackable=false;
		
		if(bossState!=BossState.JUMPING) {
			addJumpTime=false;
			jumpTime=0;
		}
		
		if(addJumpTime) {
			jumpTime++;
			if(jumpTime>=30) {
				addJumpTime=false;
				jumpTime=0;
			}
			if(!jumping&&!falling) {
				jumping=true;
				gravity=6.0;
			}
		}
		
		for(int i=0;i<handler.tile.size();i++) {
			Tile t =handler.tile.get(i);
			if (t.isSolid()&&!goingDownPipe) {
				
				if (getBoundsTop().intersects(t.getBounds())) {
					setVely(0);
			//to fix the colision when it hites its hed
					if(jumping) {
						jumping=false;
						gravity=0.0;
						falling=true;
					}
					
				}
				if (getBoundsBottom().intersects(t.getBounds())) {
					setVely(0);
					if(falling) {
						falling=false;
						addJumpTime=true;
				}
				} 
				if (getBoundsLeft().intersects(t.getBounds())) {
					setVelx(0);
					if(bossState==BossState.RUNNING)setVelx(2);
					x=t.getX()+t.width;
				}
				if (getBoundsRight().intersects(t.getBounds())) {
					setVelx(0);
					if(bossState==BossState.RUNNING)setVelx(-2);
					x=t.getX()-t.width;
				}
			
			}
		}
		for(int i=0; i<handler.entity.size();i++) {
			Entity e= handler.entity.get(i);
			if(e.getid()==Id.Player) {
				if(bossState==BossState.JUMPING) {
					if(jumping||falling) {
						if(getX()>=e.getX()-4&&getX()<=e.getX()+4)setVelx(0);
						else if(e.getX()<getX())setVelx(-2);
						else if(e.getX()>getX())setVelx(2); 
					}else setVelx(0);
				}else if(bossState==BossState.SPINNING) {
					if(e.getX()<getX())setVelx(-2);
					else if(e.getX()>getX())setVelx(2); 
				}
			}
		}
		if(jumping&&!goingDownPipe) {
			gravity-=0.17;
			setVely((int)-gravity); 
			if(gravity<=0.5) {
				jumping=false;
				falling=true;
			}
			
			}if(falling&&!goingDownPipe) {
				gravity+=0.17;
				setVely((int)gravity);
		}
}

	public void chooseState() {
			if(nextPhase==0)nextPhase++;
			else nextPhase--;
			if(nextPhase==0) {
				bossState=BossState.RUNNING;
				if(rand==0)rand++;
				else rand--;
				if(rand==0)setVelx(-2);
				else if(rand==1) setVelx(2);
			}else if(nextPhase==1) {
				bossState=BossState.JUMPING;
				jumping=true;
				gravity=8.0;
			}
			phaseTime=0;
		}
}
