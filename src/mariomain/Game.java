package mariomain;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import entity.Entity;
import entity.mob.Player;
import gfx.Sprite;
import gfx.SpriteSheet;
import gfx.gui.Launcher;
import input.Keyinput;
import input.MouseInput;
import tile_block.Wall;

public class Game extends Canvas implements Runnable {
	//creating our game frame
	public static final int WIDTH=270;//270
	public static final int HEIGHT=WIDTH/16*10;
	public static final int SCALE=4;
	public static final String TITLE= "MARIO";
	
	private Thread thread;
	private boolean running=false;

	public static BufferedImage launcherbackground;
	public static BufferedImage background;
	
	private static int playerX,playerY;
	public static int level=1;
	
	public static int coins=0;
	public static int lives=3;
	
	public static int deathScreenTime=0;
	public static boolean showDeathScreen=true;
	public static boolean gameOver=false;
	public static boolean playing=false;
	
	
	public static Handler handler;
	public static SpriteSheet sheet;
	public static Camera cam;
	public static Launcher launcher;
	public static MouseInput mouse;
	
	public static Sprite Castle;
	public static Sprite grass;
	public static Sprite coin;
	public static Sprite star;
	public static Sprite fireball;
	public static Sprite flower;
	
	public static Sprite brick;
	public static Sprite clouds;
	
	
	public static Sprite mushroom;
	public static Sprite lifeMushroom;
	public static Sprite powerUp;
	public static Sprite usedpowerUp;
	public static Sprite pipe;
	public static Sprite Plant;
	public static Sprite hart;
	public static Sprite player [] = new Sprite[10];
	public static Sprite fireplayer [] = new Sprite[10];
	public static Sprite Goomba [] = new Sprite[10];
	public static Sprite flag   [] = new Sprite[3];
	public static Sprite TowerBoss[]= new Sprite[8];
	public static Sprite Koopa[]= new Sprite[6];
	
	//for implimenting sounds
	public static Sound jump;
	public static Sound goombastomp;
	public static Sound levelcompleted;
	public static Sound world_clear;
	public static Sound themesong;
	public static Sound mariodie;
	public static Sound Coinsound;
	public static Sound gameover;
	public static Sound powerupsound;
	public static Sound powerdownsound;
	public static Sound breakblock;
	
	
	public Game(){
		//dimension must be imported from java.awt
		Dimension size=new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
		//must extend and import Canvas class
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	//short for initialise
	private void init() {
		handler=new Handler();
		sheet=new SpriteSheet("/spritesheet.png");
		cam=new Camera(); 
		launcher =new Launcher();
		mouse=new MouseInput();
		
		addKeyListener(new Keyinput());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		//refering in the gimp file
		
		grass=new Sprite(sheet,1,1);
		brick=new Sprite(sheet,1,14);
		clouds=new Sprite(sheet,1,16);
		Plant=new Sprite(sheet,7,9);
		Castle=new Sprite(sheet,4,1);
		pipe=new Sprite(sheet,4,2);
		mushroom =new Sprite(sheet,1,4);
		lifeMushroom=new Sprite(sheet,2,4);
		coin =new Sprite(sheet,3,1);
		star =new Sprite(sheet,3,4);
		powerUp=new Sprite(sheet,1,3);
		usedpowerUp=new Sprite(sheet,2,3);
		hart=new Sprite(sheet,11,6);
		//for the animation effect of the players movment
		for(int i=0;i<player.length;i++) {
			player[i]=new  Sprite(sheet,i+1,6);
		}
		for(int i=0;i<fireplayer.length;i++) {
			fireplayer[i]=new  Sprite(sheet,i+1,9);
		} 
		for(int i=0;i<Goomba.length;i++) {
			Goomba[i]=new  Sprite(sheet,i+1,7);
		}
		for(int i=0;i<flag.length;i++) {
			flag[i]=new Sprite(sheet,i+1,5);
		}
		
		for(int i=0;i<TowerBoss.length;i++) {
			TowerBoss[i]=new Sprite(sheet,i+1,11);
		}
		for(int i=0;i<Koopa.length;i++) {
			Koopa[i]=new Sprite(sheet,i+1,9);
		}
		try {
			background=ImageIO.read(getClass().getResource("/background.jpg"));
			launcherbackground=ImageIO.read(getClass().getResource("/launcherbackground.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		jump=new Sound("/Mario_Jump.wav");
		goombastomp=new Sound("/stomp.wav");
		levelcompleted=new Sound("/levelecomplet.wav");
		themesong=new Sound("/1.wav");
		mariodie=new Sound("/mariodie.wav");
		Coinsound=new Sound("/coin.wav");
		gameover=new Sound("/gameover.wav");
		powerupsound=new Sound("/powerup.wav");
		powerdownsound=new Sound("/powerdown.wav");
		world_clear=new Sound("/world_clear.wav");
		breakblock=new Sound("/breakblock.wav");
		//handler.createLevel(image); 
		//player=new Sprite(sheet,1,6); handler=new Handler();
		//handler.addTile(new Wall(200,200,64,64,true,Id.wall,handler));
	}
	//Synchronised protects against interference and memory errors
	private synchronized void start() {
		if(running) {
			return;
			}
		running=true; 
		thread = new Thread(this,"Thread");
		//thread.run();
		thread.start();
	}
	private synchronized void stop() {
		if(!running) {
			return;}
		running=false;
		//Risky code corection procedure
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	//Method for the Runnable 
	public void run() {
		
		init();
	
		//Give time in nano and milli seconds 
		long lasttime=System.nanoTime();
		long timer=System.currentTimeMillis();
		double delta=0.0;
		double ns=100000000.0/60.0;
		int frames=0;
		int ticks=0;
		while(running) {
			//long now=System.nanoTime();
			long now=System.nanoTime();
			delta+=(now-lasttime)/ns;
			lasttime=now;
			while(delta>=1) {
				tick();
				ticks++;
				//controls the game speed
				delta=delta-4;
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer>1000) {
				timer+=100;
				System.out.println(frames+"Frames per seccond "+ticks+"Updates per seconds ");
				frames=0;
				ticks=0;
			}
			//focus the screen every time it runs
			requestFocus();}
		stop();
	}
	//displays things on the screean like graphics
	public void render() {
		BufferStrategy bs= getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
			return;
		}
		//creating graphics and rectangles
		Graphics g = bs.getDrawGraphics();
	
		//to count our coin value
		if(!showDeathScreen) {
			g.drawImage(background, 0, 0, getWidth(),getHeight(), null);
			}
		//to show our death screen and remaining lifes
		if(showDeathScreen) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
			if(!gameOver) {
				
				
				g.setColor(Color.WHITE);
				g.setFont(new Font ("courier",Font.BOLD,50));
				g.drawImage(player[0].getBufferedImage(),500,300,100,100,null);
				g.drawString("x"+lives, 610 , 400);
			}else  {
				g.setColor(Color.WHITE);
				g.setFont(new Font ("courier",Font.BOLD,50));
				g.drawString("Game Over  :(", 380 , 350);
				deathScreenTime++;
				if(deathScreenTime>=400) {
					deathScreenTime=0;
					
				gameOver=false;
				showDeathScreen=true;
				playing=false;
				launcher.render(g);
				lives=1;
				}
				
			}
		
		}
		
		//translate means move #for the pourpose of our camera
		if(playing) {
			g.translate(cam.getX(), cam.getY());}
		  if(!showDeathScreen&&playing)handler.render(g);
		else if(!playing) launcher.render(g);
		//showing our created graphics and rectangle on the screen
		g.dispose();
		bs.show();
		
	}
	//thick or update
	public static void tick() {
		if(playing) handler.tick();
		//for the pourpose of our camer
		for(Entity e:handler.entity) {
			if(e.getid()==Id.Player) {
				if(!e.goingDownPipe) cam.tick(e);
			}
		}
		if(showDeathScreen&&!gameOver&&playing) deathScreenTime++;
		if(deathScreenTime>=400) {
			if(!gameOver) {
			showDeathScreen=false;
			deathScreenTime=0;
			handler.clearLevel();
			
			handler.createLevel(level);	
			
			}else if(gameOver) {
				
				showDeathScreen=false;
				deathScreenTime=0;
				playing=false;
				gameOver=false;
			}
		}
	}
	
	public static int getFrameWidth() {
		return WIDTH*SCALE;
	}
	public static int getFrameHeight() {
		return HEIGHT*SCALE;
	}
	public void trancerender(Graphics g) {
		//g.drawImage(Game.launcherbackground, 0, 0, Game.getFrameWidth()+10, Game.getFrameHeight()+10, null);
	g.setColor(Color.WHITE);
		g.setFont(new Font ("courier",Font.BOLD,50));
		g.drawImage(player[0].getBufferedImage(),500,300,100,100,null);
		g.drawString("x"+lives, 610 , 400);}
	
	public static void nextlevel() {
		Game.level++;
		Game.themesong.stop();
		Game.levelcompleted.play();
		handler.clearLevel();
	
		
		try {
			Thread.sleep(4000);
			handler.clearLevel();
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Game.levelcompleted.stop();
		handler.createLevel(level);
	}
	public static Rectangle getVisibleArea() {
		for(int i=0;i<handler.entity.size();i++) {
			Entity e=handler.entity.get(i);
			if(e.getid()==Id.Player) {
				if(!e.goingDownPipe) {
				playerX=e.getX();
				playerY=e.getY();
	return new Rectangle(playerX-(getFrameWidth()/2-5),playerY-500,getFrameWidth()+10,getFrameHeight()+600);
			}
				}//-(getFrameHeight()/2-5)-(getFrameHeight()/2-5)
	}
		return new Rectangle(playerX-(getFrameWidth()/2-5),playerY-500,getFrameWidth()+10,getFrameHeight()+600);
	}
	//setting the dimension 

	//main method
	public static void main(String[] args) {
		Game game= new Game();
		//JFrame must be imported
		JFrame frame=new JFrame(TITLE);
		//add Game to the JFrame
		frame.add(game);
		//packing everything together
		frame.pack();
		//Can't resize our frame
		frame.setResizable(false);
		//set the frame in the middle of the screen
		frame.setLocationRelativeTo(null);
		//exits when we press the close button
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//to make the frame visible
		frame.setVisible(true);
		game.start();
		
	}	
}
