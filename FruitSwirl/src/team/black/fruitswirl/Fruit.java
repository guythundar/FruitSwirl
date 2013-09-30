package team.black.fruitswirl;

import org.flixel.FlxSprite;

public class Fruit extends FlxSprite {
	
	public static final int STATE_NORMAL = 0x000;
	public static final int STATE_FIERY = 0x001;
	public static final int STATE_ELEC = 0x010;
	public static final int STATE_LOCK = 0x100;
	
	private int curState;
	
	public Fruit(float _ix, float _iy, int _initState){
		super(_ix, _iy);
		curState = _initState;
	}
	
	@Override
	public void update(){
		super.update();
	}
	
}
