package mariomain;

import entity.Entity;

public class Camera {

	public int x,y;
	
	public void tick(Entity player) {
		setX(0-player.getX()+Game.getFrameWidth()/2);
		//setY(0-player.getY()+Game.getFrameHeight()/2);

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

}
