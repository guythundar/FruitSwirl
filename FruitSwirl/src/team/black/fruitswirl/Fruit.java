package team.black.fruitswirl;

import org.flixel.FlxSprite;

public class Fruit extends FlxSprite {
	
	public static final int DEFAULT_POINT_VALUE = 1;
	public static final int FIRE_POINT_VALUE = 3;
	public static final int ELEC_POINT_VALUE = 5;
	
	public static final int SIZE_X = 20;
	public static final int SIZE_Y = 20;
	
	public static final int STATE_NONE		= 0x00000000;
	public static final int STATE_NORMAL	= 0x00000001;
	public static final int STATE_FIRE 		= 0x00000010;
	public static final int STATE_ELEC 		= 0x00000100;
	public static final int STATE_LOCKING	= 0x00001000;
	public static final int STATE_LOCKED	= 0x00010000;
	public static final int STATE_DESTROY	= 0x00100000;
	public static final int STATE_FALLING	= 0x01000000;
	public static final int STATE_FLIPPING	= 0x10000000;
	
	public static final int FLIP_NONE	= 0x0000;
	public static final int FLIP_LEFT	= 0x0001;
	public static final int FLIP_RIGHT	= 0x0010;
	public static final int FLIP_UP		= 0x0100;
	public static final int FLIP_DOWN	= 0x1000;
	
	public long UID;
	
	private int currentState;
	private int flipDirection;
	
	public int gridx, gridy;
	
	public Fruit(float _ix, float _iy, int _gridx, int _gridy){
		super(_ix, _iy);
		setCurrentState(STATE_NONE);
		setFlipDirection(FLIP_NONE);
		UID = Rg.rng.nextLong();
		gridx = _gridx;
		gridy = _gridy;
	}
		
	public Fruit(float _ix, float _iy, int _initState){
		super(_ix, _iy);
		setCurrentState(_initState);
		setFlipDirection(FLIP_NONE);
	}
	
	public void makeTestSprite(int color){
		makeGraphic(SIZE_X, SIZE_Y, color);
	}
	
	@Override
	public void update(){
		super.update();
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int curState) {
		this.currentState = curState;
	}

	public int getFlipDirection() {
		return flipDirection;
	}

	public void setFlipDirection(int flipDirection) {
		this.flipDirection = flipDirection;
	}
	
}
