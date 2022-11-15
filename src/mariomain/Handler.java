package mariomain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import entity.Coin;
import entity.Entity;
import entity.mob.Goomba;
import entity.mob.Koopa;
import entity.mob.Player;
import entity.mob.TowerBoss;
import entity.powerup.Mushroom;
import entity.powerup.Star;
import tile_block.BackGroundItems;
import tile_block.Brick;
import tile_block.Castle;
import tile_block.Flag;
import tile_block.Pipe;
import tile_block.PowerUpBlock;
import tile_block.Stairs;
import tile_block.Tile;
import tile_block.Wall;

public class Handler {
public LinkedList<Entity> entity = new LinkedList<Entity>();
public LinkedList<Tile> tile = new LinkedList<Tile>();


public void render(Graphics g) {
		for(int i=0;i<tile.size();i++) {
		Tile ti=tile.get(i);
		if(Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea()))ti.render(g);
	}
	for(int i=0;i<entity.size();i++) {
		Entity en=entity.get(i);
		if(Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea()))en.render(g);
	}
	
	g.setColor(Color.WHITE);
	g.setFont(new Font ("courier",Font.BOLD,50));
	g.drawImage(Game.coin.getBufferedImage(),Game.getVisibleArea().x+20,10,75,75,null);
	g.drawString("x"+Game.coins, Game.getVisibleArea().x+100 ,70);
	

	g.setFont(new Font ("courier",Font.BOLD,50));
	g.drawImage(Game.hart.getBufferedImage(),Game.getVisibleArea().x+920,10,75,70,null);
	g.drawString("x"+Game.lives, Game.getVisibleArea().x+1000 ,70);
	
	
	g.setFont(new Font ("courier",Font.BOLD,50));
	g.drawString("Level", Game.getVisibleArea().x+300 ,65);
	g.drawString(""+Game.level, Game.getVisibleArea().x+300 ,100);
	
	g.setFont(new Font ("courier",Font.BOLD,50));
	g.drawString("SCORE", Game.getVisibleArea().x+700 ,65);
	g.drawString(""+Player.distancetraveled, Game.getVisibleArea().x+700 ,100);
}
public void tick() {
	for(int i=0;i<entity.size();i++) {
		Entity en=entity.get(i);
		if(Game.getVisibleArea()!=null&&en.getBounds().intersects(Game.getVisibleArea()))en.tick();
	}
	for(int i=0;i<tile.size();i++) {
		Tile ti=tile.get(i);
		if(Game.getVisibleArea()!=null&&ti.getBounds().intersects(Game.getVisibleArea()))ti.tick();
	}

}
private BufferStrategy getBufferStrategy() {
	// TODO Auto-generated method stub
	return null;
}
public void addEntity(Entity en) {
	entity.add(en);
}
public void removeEntity(Entity en) {
	entity.remove(en);
}

public void addTile(Tile ti) {
	tile.add(ti);
}
public void removeTile(Tile ti) {
	tile.remove(ti);
}

	
public void createLevel(int level){
	Game.themesong.play(); 

	if(level%2==0) {addEntity(new Player(300,Game.HEIGHT*Game.SCALE-300,48,48,true,Id.Player,this));
	addEntity(new TowerBoss(1800,Game.HEIGHT*Game.SCALE-200,70,70,true,Id.towerBoss,this,3));
	addEntity(new Goomba(1200,Game.HEIGHT*Game.SCALE-300,60,60,true,Id.goomba,this));
	addEntity(new Koopa(1300,Game.HEIGHT*Game.SCALE-300,60,60,true,Id.Koopa,this));
	addTile(new PowerUpBlock(800,Game.HEIGHT*Game.SCALE-200,64,64,true,Id.powerUp,this,Game.lifeMushroom,1));
	addTile(new PowerUpBlock(500,Game.HEIGHT*Game.SCALE-200,64,64,true,Id.powerUp,this,Game.mushroom,0));
	for(int i=0;i<=35;i++){
		if(i==1) {for(int j=0;j<12;j++) {addTile(new Wall(i*64,Game.HEIGHT*Game.SCALE-55-(j*50),64,64,true,Id.wall,this));}}
		if(i==35) {for(int j=0;j<12;j++) {addTile(new Wall(i*64,Game.HEIGHT*Game.SCALE-55-(j*50),64,64,true,Id.wall,this));}}
		if(i<=35&&i>=1) {addTile(new Wall(i*64,Game.HEIGHT*Game.SCALE-55,64,64,true,Id.wall,this));}
		if(i<=35&&i>=1) {addTile(new Wall(i*64,Game.HEIGHT*Game.SCALE-100*6,64,64,true,Id.wall,this));}
	}
	}
	
	else {
	//starting point
	addTile(new Castle(-640,Game.HEIGHT*Game.SCALE-625,640,640,true,Id.castle,this));
		
	addEntity(new Player(0,Game.HEIGHT*Game.SCALE-100,48,48,true,Id.Player,this));
	addEntity(new Star(100,Game.HEIGHT*Game.SCALE-300,64,64,true,Id.star,this));
	
	
	addTile(new Wall(1600,Game.HEIGHT*Game.SCALE-100,64,64,true,Id.wall,this));
	addTile(new Wall(1000,Game.HEIGHT*Game.SCALE-100,64,64,true,Id.wall,this));
	addEntity(new Goomba(2560,Game.HEIGHT*Game.SCALE-300,60,60,true,Id.goomba,this));
	addEntity(new Star(2660,Game.HEIGHT*Game.SCALE-300,64,64,true,Id.star,this));
	addEntity(new Koopa(2580,Game.HEIGHT*Game.SCALE-300,70,70,true,Id.Koopa,this));
	addTile(new PowerUpBlock(2000,Game.HEIGHT*Game.SCALE-400,64,64,true,Id.powerUp,this,Game.lifeMushroom,1));
	addTile(new PowerUpBlock(2700,Game.HEIGHT*Game.SCALE-350,64,64,true,Id.powerUp,this,Game.mushroom,0));

	for(int i=0;i< Game.WIDTH*Game.SCALE/64+185;i++){
		if(i!=10&&i!=11&&i!=12&&i!=13&&i!=18&&i!=19&&i!=20&&i!=21&&i!=31&&i!=32&&i!=51&&i!=52&&i!=78&&i!=79
		&&i!=100&&i!=101&&i!=121&&i!=122&&i!=130&&i!=132&&i!=145&&i!=146&&i!=146&&i!=170&&i!=174&&i!=190)
		{
		addTile(new Wall(i*64,Game.HEIGHT*Game.SCALE-55,64,64,true,Id.wall,this));
		}		
	//for the pipe
	if(i==10||i==50||i==99||i==15||i==145) {
		
		addEntity(new Goomba((i+4)*64,Game.HEIGHT*Game.SCALE-200,60,60,true,Id.goomba,this));
		addTile(new Pipe(i*64,Game.HEIGHT*Game.SCALE-200,64*2,64*5,true,Id.pipe,this,0,true));}
	if(i<=16&&i>=9) {addTile(new Wall(i*64,Game.HEIGHT*Game.SCALE-55+(10*50),64,64,true,Id.wall,this));}
	if(i%77==18||i%77==20||i%77==10) {
		
		addTile(new BackGroundItems((i)*20,Game.HEIGHT*Game.SCALE-630,64,64,false,Id.backgrounditems,this));
		addTile(new BackGroundItems((i)*20+100,Game.HEIGHT*Game.SCALE-480,64,64,false,Id.backgrounditems,this));
		
		addTile(new BackGroundItems((i)*40,Game.HEIGHT*Game.SCALE-600,64,64,false,Id.backgrounditems,this));
		addTile(new BackGroundItems((i)*50+150,Game.HEIGHT*Game.SCALE-500,64,64,false,Id.backgrounditems,this));
	}
	if(i>20&&i<30||i>=60&&i<=70||i>=105&&i<=118||i>135&&i<130||i>150&&i<160) {
		
		//for the coin
		addEntity(new Coin(i*64,200,64,64,true,Id.coin,this));
		addTile(new Brick(i*64,300,64,64,true,Id.brick,this));
		}}
	
	//stairs
	for(int j=1;j<=3;j++) {
	addTile(new Stairs((64*131),Game.HEIGHT*Game.SCALE-(110*j),64,64,true,Id.stairs,this)); 
		}
	for(int i=0;i<4;i++) {
		for(int j=0;j<4;j++) {
			if(i>=2||j>=2)addTile(new Stairs(2200+(60*i),Game.HEIGHT*Game.SCALE-300+(60*j),64,64,true,Id.stairs,this)); 
		}		
	}
	for(int i=0;i<4;i++) {
		for(int j=0;j<4;j++) {
			if(i>=2||j>=2)addTile(new Stairs((64*126)+(60*i),Game.HEIGHT*Game.SCALE-300+(60*j),64,64,true,Id.stairs,this)); 
		}		
	}
	for(int i=0;i<4;i++) {
		for(int j=0;j<4;j++) {
			if(i>=2||j>=2)addTile(new Stairs((Game.WIDTH*Game.SCALE/64+170)*64+(60*i),Game.HEIGHT*Game.SCALE-300+(60*j),64,64,true,Id.stairs,this)); 
		}		
	}
	for(int i=0;i<4;i++) {
		for(int j=0;j<4;j++) {
			if(i+j>=3)addTile(new Stairs(10650+(60*i),Game.HEIGHT*Game.SCALE-300+(60*j),64,64,true,Id.stairs,this)); 
		}	
	}
	for(int i=0;i<4;i++) {
		for(int j=0;j<4;j++) {
			if(j>=i)addTile(new Stairs(11200+(60*i),Game.HEIGHT*Game.SCALE-300+(60*j),64,64,true,Id.stairs,this)); 
		}	
	}
	
	for(int i=0;i<4;i++) {
		for(int j=0;j<4;j++) {
			if(i>=2||j>=2)addTile(new Stairs(4700+(60*i),Game.HEIGHT*Game.SCALE-300+(60*j),64,64,true,Id.stairs,this)); 
		}	
	}
	
	
	//ending point
	addTile(new Flag((Game.WIDTH*Game.SCALE/64+180)*64,Game.HEIGHT*Game.SCALE-300,64,64*5,true,Id.flag,this)); 
	
	
	addTile(new Castle(((Game.WIDTH*Game.SCALE/64+180)*64)+5,Game.HEIGHT*Game.SCALE-625,640,640,true,Id.castle,this));

	}
	
	
	


}
public void clearLevel() {
	entity.clear();
	tile.clear();
	
}
}

