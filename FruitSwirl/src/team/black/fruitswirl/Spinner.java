package team.black.fruitswirl;

import org.flixel.FlxPoint;
import org.flixel.FlxSprite;

public class Spinner extends FlxSprite {
	private boolean justMoved = false;
	private FlxPoint initPos = new FlxPoint();
	private Point initCollidePos = new Point(0, Grid.FRUITS_PER_COL-1);
	public Point collidePos = new Point();
	
	public boolean hasJustMoved() {
		return justMoved;
	}

	public void setJustMoved(boolean justMoved) {
		this.justMoved = justMoved;
	}

	public Spinner(float X, float Y) {
		super(X, Y);
		loadGraphic("spinner.png");
		collidePos = initCollidePos;
	}
	
	@Override
	public void update(){
		
	}

	public boolean move(FlxPoint dPos, Point cPos){
		if ( x == dPos.x && y == dPos.y )
			return true;
		else{			
			x = dPos.x;
			y = dPos.y;
			
			collidePos = cPos;
			
			return false;
		}
	}

	
	public Point getCollidePos(){
		return new Point((int)collidePos.x, (int)collidePos.y);
	}
	
	public void alignToGridMember(Fruit f) {
		x = f.x;
		y = f.y;
		initPos = new FlxPoint(x,y);
	}
}
