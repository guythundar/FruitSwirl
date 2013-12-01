package team.black.fruitswirl;

import org.flixel.FlxG;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

public class Spinner extends FlxSprite {
	private boolean justMoved = false, flickerDown = true;
	private double flickerCount = 0;
	private FlxPoint initPos = new FlxPoint();
	public Point initCollidePos = new Point(),
				collidePos = new Point();
	
	
	public boolean hasJustMoved() {
		return justMoved;
	}

	public void setJustMoved(boolean justMoved) {
		this.justMoved = justMoved;
	}

	public Spinner(float X, float Y) {
		super(X, Y);
		loadGraphic("spinner.png");
	}
	
	
	@Override
	public void update(){
		if ( _alpha >= 1 ){
			flickerDown = true;
		} else if ( _alpha <= 0.25 ) {
			flickerDown = false;
		}
		_alpha += (flickerDown)?-.025:.025;
	}

	public boolean move(FlxPoint dPos, Point cPos){
		collidePos.setXY(cPos.x, cPos.y);
		_alpha = 1;
		if ( x == dPos.x && y == dPos.y )
			return false;
		else{			
			x = dPos.x;
			y = dPos.y;			
			return true;
		}
	}

	
	public Point getCollidePos(){
		return collidePos;
	}
	
	public void alignToGridMember(Fruit f) {
		x = f.x;
		y = f.y;
		initPos = new FlxPoint(x,y);
	}
}
