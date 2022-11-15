package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import mariomain.Game;
import mariomain.Handler;
import mariomain.Id;
import mariomain.states.BossState;
import mariomain.states.KoopaState;

public abstract class Entity {
public int x,y;
public int width ,height;
public int facing=1; //00- will be left and 1- will be right
public int hp;//health point 
public int velx,vely;//velocity x and velocity y
public int phaseTime;
public int type;

public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getHeight() {
	return height;
}

public void setHeight(int height) {
	this.height = height;
}
public boolean solid;
public boolean goingDownPipe=false;
public boolean jumping=false, falling=false;
public boolean attackable=false;
public int frame=0,frameDelay=0;//forgoten

public Id id;
public BossState bossState;
public KoopaState koopaState;

public double gravity=0.0;
public Handler handler;

public Entity(int x,int y,int width,int height,boolean solid,Id id,Handler handler) {
	this.x=x; this.y=y; this.width=width; this.height=height; this.solid=solid; this.id=id; this.handler=handler;	}

public abstract void render(Graphics g);
public abstract void tick() ;
public void die() {
	handler.removeEntity(this);
	
	if(getid()==Id.Player) {
		
		Game.lives--;
		Game.showDeathScreen=true;
		if(Game.lives<=0) Game.gameOver=true;
		Game.themesong.stop(); 
		if(Game.lives!=0)Game.mariodie.play();
		else Game.gameover.play(); 
	}
}
public int getX() {
	return x;
} 
public void setX(int x) {
	this.x = x;
}
public int getY() {
	return y;
}
public void setY(int y) {
	this.y = y;
}
public boolean isSolid() {
	return solid;
}
public Id getid() {
	return id;
}
public void setVelx(int velx) {
	this.velx = velx;
}

public void setVely(int vely) {
	this.vely = vely;
}
public int getType() {
	
	return type;
}
//Collision detection
public Rectangle getBounds() {
	return new Rectangle(getX(),getY(),width,height);
}
public Rectangle getBoundsTop() {
	return new Rectangle(getX()+10,getY(),width-20,5);
}
public Rectangle getBoundsBottom() {
	return new Rectangle(getX()+10,getY()+height-5,width-20,5);
}
public Rectangle getBoundsLeft() {
	return new Rectangle(getX(),getY()+10,5,height-20);
}
public Rectangle getBoundsRight() {
	return new Rectangle(getX()+width-5,getY()+10,5,height-20);
}
} 
