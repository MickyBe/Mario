package entity.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import entity.Entity;
import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import mariomain.states.BossState;
import mariomain.states.KoopaState;
import mariomain.states.PlayerState;
import tile_block.Tile;
import tile_block.Trail;

public class Player extends Entity {

	private int frame=0;
	public static int distancetraveled=0;
	private double frameDelay=0;
	private boolean animate=false;
	private boolean invincible=false;
	private int invincibilityTime=0;
	private PlayerState state;
	private int BossLives=2;
	private int pixelsTravelled=0;

	private Random random;
	public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		state=PlayerState.Small;
		
		random=new Random();
	}

	@Override
	//sepecifiy what our player looks like;	  experimenting  	g.setColor(Color.BLUE);	g.fillRect(x, y, width, height);

	public void render(Graphics g) {
		if(state==PlayerState.FIRE) {
			if(facing==0) {
				g.drawImage(Game.fireplayer[frame+5].getBufferedImage(),x,y,width,height,null);
			}
			else if(facing==1) {
				g.drawImage(Game.fireplayer[frame].getBufferedImage(),x,y,width,height,null);
			}
		}else {
				if(facing==0) {
			g.drawImage(Game.player[frame+5].getBufferedImage(),x,y,width,height,null);
		}
		else if(facing==1) {
			g.drawImage(Game.player[frame].getBufferedImage(),x,y,width,height,null);
		}
		}
	
		
		
	}

	@Override
	public void tick() {
	x+=velx;
	y+=vely;
	if(velx>=0) distancetraveled+=velx;
	if(invincible) {
		if(facing==0) {
	handler.addTile(new Trail(getX(),getY(),getWidth(),getHeight(),false,Id.trail,handler,Game.player[5+frame].getBufferedImage()));
		}
		 else if(facing==1);{
	 handler.addTile(new Trail(getX(),getY(),getWidth(),getHeight(),false,Id.trail,handler,Game.player[frame].getBufferedImage()));
		 }
		
	
		 invincibilityTime++;
		if(invincibilityTime>=500) {
			invincible=false;
			invincibilityTime=0;
		}
	}
	
	//to limit our players Moment like falling down
	if(x<=0) x=0;
	if(y>=10000) {
		die();
		}
	//if(y<=0) y=0;
	//if(x+width>=1090)x=1090-width;
	//if(y+height>=650)y=650-height;
	if(velx!=0) {
		animate =true;
		}
	else animate=false;
	
	for(int i=0;i<handler.tile.size();i++) {
		Tile t =handler.tile.get(i);
		if (t.isSolid()&&!goingDownPipe) {
			
			if (getBoundsTop().intersects(t.getBounds())) {
				setVely(0);
				if(t.getid()==Id.brick) {
					Game.breakblock.play();
					t.die();
					}
				//y=t.getY()+t.height;
				//to fix the colision when it hites its hed
				if(jumping&&!goingDownPipe) {
					jumping=false;
					gravity=0.8;
					falling=true;
				}//for our power up block
				if(t.getid()==Id.powerUp) {
					if(getBoundsTop().intersects(t.getBounds())) {
						t.activated=true;
					}
				}
			}
			if (getBoundsBottom().intersects(t.getBounds())) {
				setVely(0);
				if(falling) falling=false;
				//y=t.getY()-t.height;
			}//fixing it so it can fall of the edge
			else if(!falling&&!jumping) {
				gravity=0.0;
				falling=true;
			}
			if (getBoundsLeft().intersects(t.getBounds())) {
				if(t.getid()==Id.flag) {
                        t.die();
						Game.nextlevel();	
				}
				setVelx(0);
				x=t.getX()+width;
				
			}
			if (getBoundsRight().intersects(t.getBounds())) {
				if(t.getid()==Id.flag) {
					 t.die();
					Game.nextlevel();
				
				}
				setVelx(0);
				x=t.getX()-width;
			
			}
			if(getBounds().intersects(t.getBounds())) {
			if(t.getid()==Id.flag) {
				 t.die();
				Game.nextlevel();
				

			}}
		}
	}
	//for making mario bigger when he collides with the mushroom
	for(int i=0;i<handler.entity.size();i++) {
		Entity e =handler.entity.get(i);
		if(e.getid()==Id.mushroom) {
			switch(e.getType()) {
			case 0:
				if(getBounds().intersects(e.getBounds())) {
				int tpX=getX();
				int tpY=getY();
				width+=(width/3);
				height+=(height/3); 
				//for fixing our player from randomly teleporting
				setX(tpX-width);
				setY(tpY-height);
				if(state==PlayerState.Small)state=PlayerState.Big;
				e.die();
				Game.powerupsound.play();
			}
				break;
			case 1:
				if(getBounds().intersects(e.getBounds())) {
					Game.lives++;
					e.die();
				}
			}
					 
		}else if(e.getid()==Id.goomba||e.getid()==Id.towerBoss||e.getid()==Id.plant) {
			if(invincible&&getBounds().intersects(e.getBounds())) e.die();
				else {//gets  called when we are invincible
				if(getBoundsBottom().intersects(e.getBoundsTop())) {
					if(e.getid()!=Id.towerBoss) {
					e.die();
					Game.goombastomp.play();
					}
				else if(e.attackable) {
					e.hp--;
					e.falling=true;
					e.gravity=0.8;
					e.bossState=BossState.RECOVERING;
					BossLives--;
					e.attackable=false;
					e.phaseTime=0;
					jumping=true;
					falling=false;
					gravity=3.5; 
					if(BossLives<=0) {
						Game.themesong.stop();
						Game.world_clear.play();
						e.die();
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e1) {
							
							e1.printStackTrace();
						}
						Game.nextlevel();
						}
				}
			}else if(getBounds().intersects(e.getBounds())) {
				if(state==PlayerState.Big) {
					int tpX=getX();
					int tpY=getY();
					Game.powerdownsound.play();
					state=PlayerState.Small;
					width-=(width/3);
					height-=(height/3);
					setX(tpX-width);
					setY(tpY-height);
				}
				else if(state==PlayerState.Small) {
					die();
				}
			}
			}
			
			
		}
	else if(e.getid()==Id.coin) {
		if(getBounds().intersects(e.getBounds())&&e.getid()==Id.coin) {
			Game.coins++;
			e.die();
			Game.Coinsound.play();
				
			}
		}else if(e.getid()==Id.Koopa) {
			if(invincible&&getBounds().intersects(e.getBounds())) e.die();
			else {	if(e.koopaState==KoopaState.WALKING) {
				if(getBoundsBottom().intersects(e.getBoundsTop())) {
					e.koopaState=KoopaState.SHELL;
					jumping=true;
					falling=false;
					gravity=3.5; 
				}else if(getBounds().intersects(e.getBounds())) { 
					if(state==PlayerState.Big) {
						int tpX=getX();
						int tpY=getY();
						Game.powerdownsound.play();
						state=PlayerState.Small;
						width=-(width/3);
						height=-(height/3);
						setX(tpX-width);
						setY(tpY-height);
					}
					else if(state==PlayerState.Small) {
						die();
					}
					}
				
			}else if(e.koopaState==KoopaState.SHELL) {
				if(getBoundsBottom().intersects(e.getBoundsTop())) {
					e.koopaState=KoopaState.SPINNING;
					
					int dir =random.nextInt(2);
					switch(dir) {
					case 0:
						e.setVelx(-10);
						facing=0;
						break;
					case 1:
						e.setVelx(10);
						facing=1;
						break;
						}
					
					jumping=true;
					falling=false;
					gravity=3.5; 
				}
				if(getBoundsLeft().intersects(e.getBoundsRight())) {
					e.setVelx(-10);
					e.koopaState=KoopaState.SPINNING;
				}
			 if(getBoundsRight().intersects(e.getBoundsLeft())) {
					e.setVelx(10);
					e.koopaState=KoopaState.SPINNING;
				}
				
			}else if(e.koopaState==KoopaState.SPINNING) {
				
				if(getBoundsBottom().intersects(e.getBoundsTop())) {
					e.koopaState=KoopaState.SHELL;
					jumping=true;
					falling=false;
					gravity=3.5; 
					e.die();
				}else if(getBounds().intersects(e.getBounds())) {
					if(state==PlayerState.Big) {
						int tpX=getX();
						int tpY=getY();
						Game.powerdownsound.play();
						state=PlayerState.Small;
						width-=(width/3);
						height-=(height/3);
						setX(tpX-width);
						setY(tpY-height);
					}
					else if(state==PlayerState.Small) {
						die();
					}
					die();
					}
			}
			}
		
		} else if(e.getid()==Id.star) {
			if(getBounds().intersects(e.getBounds())){
				invincible=true;
				e.die();
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

		//for implimenting our animation
		if(animate) {
			//to set our animation speedk
			frameDelay=frameDelay+0.1;
			if(frameDelay>=1) {
				frame++;
				if(frame>=3) {
					frame=0;
				}
				frameDelay=0;
			}
		}
		if(goingDownPipe) {
			for(int i=0;i<Game.handler.tile.size();i++) {
				Tile t=Game.handler.tile.get(i);
				
				if(t.getid()==Id.pipe) {
					if(getBoundsBottom().intersects(t.getBounds())) {
					switch(t.facing) {
					
					case 0:
						setVely(2);
						setVelx(0);
						pixelsTravelled+=vely;
						break;
					case 2:
						setVely(-2);
						setVelx(0);
						pixelsTravelled+=-vely;
						break;
					}
					if(pixelsTravelled>t.height) {
						pixelsTravelled=0;
						goingDownPipe=false;}
					}
				
				}
			}
		}
		
}
}
