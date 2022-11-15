package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Entity;
import mariomain.Game;
import mariomain.Id;
import tile_block.Tile;

public class Keyinput implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		for(int i=0;i<Game.handler.entity.size();i++) {
			Entity en =Game.handler.entity.get(i);
			if(en.getid()==Id.Player) {
				if(en.goingDownPipe) return;
			switch(key) {
			case KeyEvent.VK_UP:
				for(int j=0;j<Game.handler.tile.size();j++) {
					Tile t=Game.handler.tile.get(j); 
					if(t.isSolid()) {
						if(en.getBoundsBottom().intersects(t.getBounds())) {
							if(!en.jumping) {
								en.jumping=true;
							    en.gravity=10.5;
							Game.jump.play();    
							}
						}
					}	
				}
					//en.setVely(-1);Entity en:Game.handler.entity
				break;
			case KeyEvent.VK_DOWN:
				
				//for implementing our pipes function
				for(int j=0;j<Game.handler.tile.size();j++) {
					Tile t=Game.handler.tile.get(j); 
					if(t.getid()==Id.pipe) {
						if(en.getBoundsBottom().intersects(t.getBounds())) {
							if(!en.goingDownPipe)en.goingDownPipe=true;
						}
						
					}
					
				}
				//en.setVely(1);
				break;
			case KeyEvent.VK_LEFT:
				if(en.getid()==Id.Player)en.setVelx(-5);
				en.facing=0;
				break;
			case KeyEvent.VK_RIGHT:
				if(en.getid()==Id.Player)en.setVelx(5);
				en.facing=1;
				break;
			case KeyEvent.VK_Q:
				en.die();
			}
			}}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		for(int i=0;i<Game.handler.entity.size();i++) {
			Entity en =Game.handler.entity.get(i);
			switch(key) {
			case KeyEvent.VK_UP:
				if(en.getid()==Id.Player)en.setVely(0);
				break;
			case KeyEvent.VK_DOWN:
				if(en.getid()==Id.Player)en.setVely(0);
				break;
			case KeyEvent.VK_LEFT:
				if(en.getid()==Id.Player)en.setVelx(0);
				break;
			case KeyEvent.VK_RIGHT:
				if(en.getid()==Id.Player)en.setVelx(0);
				break;
			}
	}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//not using at the moment
	}

}
