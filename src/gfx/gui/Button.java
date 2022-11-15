package gfx.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import mariomain.Game;

public class Button {

	public int x,y;
	public int width,height;
	
	public String lable;

	public Button(int x, int y, int width, int height, String lable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.lable = lable;
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Humungous",Font.BOLD,40));
		g.drawRect(getX(), getY(), getWidth(),getHeight());
		
		FontMetrics fm =g.getFontMetrics();
		int stringX=((getWidth()-fm.stringWidth(getLable()))/2);
		int stringY=(fm.getAscent()+(getHeight()-(fm.getAscent()+fm.getDescent()))/2);
		g.drawString(getLable(), stringX+600,getY()+ stringY+10);
	}
	
	public void triggerEvent() {
	if(getLable().toLowerCase().contains("start")) Game.playing=true;
	else if(getLable().toLowerCase().contains("exit"))
		System.exit(0);
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

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}
	
}
