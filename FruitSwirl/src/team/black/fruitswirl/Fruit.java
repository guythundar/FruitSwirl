package team.black.fruitswirl;

import org.flixel.FlxG;
import org.flixel.FlxPath;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

public class Fruit extends FlxSprite {
	
	public static final int POINT_VALUE_NORMAL = 1;
	public static final int POINT_VALUE_FIRE = 3;
	public static final int POINT_VALUE_ELEC = 5;
	
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
	public static final int STATE_ROTATE	= 0x10000000;
	
	public static final int ROTATE_NONE	= 0x0000;
	public static final int ROTATE_LEFT	= 0x0001;
	public static final int ROTATE_RIGHT = 0x0010;
	public static final int ROTATE_UP = 0x0100;
	public static final int ROTATE_DOWN	= 0x1000;
	
	public long UID;
	
	private int pathTick = 0;
	private int currentState;
	private int rotateDirection;
	private boolean hasStartedPathing = false;
	
	public Fruit(float _ix, float _iy){
		super(_ix, _iy);
		setCurrentState(STATE_NORMAL);
		setRotateDirection(ROTATE_NONE);
		UID = Rg.rng.nextLong();
		visible = true;
	}
		
	public Fruit(float _ix, float _iy, int _initState){
		super(_ix, _iy);
		setCurrentState(_initState);
		setRotateDirection(ROTATE_NONE);
		UID = Rg.rng.nextLong();
	}
	
	public void makeTestSprite(int color){
		makeGraphic(SIZE_X, SIZE_Y, color);
	}
	
	@Override
	public void update(){
		super.update();
		
		
		if ( currentState == STATE_ROTATE ){
			pathTick++;
			FlxG.log( String.valueOf(UID), "i feel like rotating");
			FlxG.log( String.valueOf(UID), "PathTick: " + pathTick);
			if ( pathSpeed == 0 ){
				FlxG.log( String.valueOf(UID), "I'm done rotating, did I move?");
				FlxG.log( String.valueOf(UID), "Total PathTicks Taken: " + pathTick);
				stopFollowingPath(true);
				velocity.x = velocity.y = 0;
				setCurrentState(STATE_NORMAL);
				setRotateDirection(ROTATE_NONE);
				pathTick = 0;
			}
		}
		
	}
	
	public void startPathing(FlxPoint endPoint){
		if ( !hasStartedPathing && currentState == STATE_ROTATE && rotateDirection != ROTATE_NONE){
			FlxPath path = new FlxPath();
			path.addPoint(endPoint);
			followPath(path, 50);
			hasStartedPathing = true;
		}
	}
	
	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int curState) {
		this.currentState = curState;
	}

	public int getRotateDirection() {
		return rotateDirection;
	}

	public void setRotateDirection(int dir) {
		this.rotateDirection = dir;
	}
	
	public int getValue(){
		if ( currentState == STATE_NORMAL )
			return POINT_VALUE_NORMAL;
		else if ( currentState == STATE_FIRE )
			return POINT_VALUE_FIRE;
		else if ( currentState == STATE_ELEC )
			return POINT_VALUE_ELEC;
		else return 0;
	}
	
}
