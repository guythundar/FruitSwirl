package team.black.fruitswirl;

import org.flixel.FlxG;
import org.flixel.FlxPath;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

import com.badlogic.gdx.graphics.Color;

public class Fruit extends FlxSprite {
	
	public static final int POINT_VALUE_NORMAL = 1;
	public static final int POINT_VALUE_FIRE = 3;
	public static final int POINT_VALUE_ELEC = 5;
	
	public static final int SIZE_X = 20;
	public static final int SIZE_Y = 20;
	
	public static final int STATE_NONE		= 0x0000000;
	public static final int STATE_NORMAL	= 0x0000001;
	public static final int STATE_FIRE 		= 0x0000010;
	public static final int STATE_ELEC 		= 0x0000100;
	public static final int STATE_LOCKING	= 0x0001000;
	public static final int STATE_LOCKED	= 0x0010000;
	public static final int STATE_DESTROY	= 0x0100000;
	public static final int STATE_MOVING	= 0x1000000;
	
	public static final int MOVE_NONE	= 0x00000;
	public static final int MOVE_LEFT	= 0x00001;
	public static final int MOVE_RIGHT  = 0x00010;
	public static final int MOVE_UP		= 0x00100;
	public static final int MOVE_DOWN	= 0x01000;
	public static final int MOVE_FALL	= 0x10000;
	
	public long UID;
	
	private int pathTick = 0;
	private int currentState;
	private int rotateDirection;
	private boolean hasStartedPathing = false;
	private FlxPoint curTarget;
	
	
	public Fruit(float _ix, float _iy){
		super(_ix, _iy);
		setCurrentState(STATE_NORMAL);
		setRotateDirection(MOVE_NONE);
		UID = Rg.rng.nextLong();
		visible = true;
	}
		
	public Fruit(float _ix, float _iy, int _initState){
		super(_ix, _iy);
		setCurrentState(_initState);
		setRotateDirection(MOVE_NONE);
		UID = Rg.rng.nextLong();
	}
	
	

	public void makeTestSprite(int color){
		makeGraphic(SIZE_X, SIZE_Y, color);
	}
	
	@Override
	public void update(){
		super.update();

		if ( currentState == STATE_MOVING ){
			pathTick++;
			FlxG.log( String.valueOf(UID), "PathTick: " + pathTick);
			if ( pathSpeed == 0 ){
				stopFollowingPath(true);
				velocity.x = velocity.y = 0;
				setCurrentState(STATE_NORMAL);
				setRotateDirection(MOVE_NONE);
				pathTick = 0;
				x = curTarget.x;
				y = curTarget.y;
			}
		}
		
	}
	
	public void startPathing(FlxPoint endPoint, int direction){
		if ( !hasStartedPathing ){
			currentState = STATE_MOVING;
			FlxPath path = new FlxPath();
			curTarget = endPoint;
			path.add(endPoint.x + (SIZE_X/2 - 2), endPoint.y + (SIZE_Y/2 - 2));
			followPath(path, 100);
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
	
	public int getCurrentPointValue(){
		if ( currentState == STATE_NORMAL )
			return POINT_VALUE_NORMAL;
		else if ( currentState == STATE_FIRE )
			return POINT_VALUE_FIRE;
		else if ( currentState == STATE_ELEC )
			return POINT_VALUE_ELEC;
		else return 0;
	}
	
}
