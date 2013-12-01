package team.black.fruitswirl;

import org.flixel.FlxG;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.plugin.tweens.TweenPlugin;
import org.flixel.plugin.tweens.TweenSprite;

import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.graphics.Color;

public class PlayState extends FlxState
{
	private Grid g;
	private FlxSprite bg;
	private FlxText t, scoreText, spinText;
	private boolean justTapped = false;
	private FlxPoint mpos = new FlxPoint(0,0);
	
	@Override
	public void create()
	{
		
		//prep
		bg = new FlxSprite(0,0);
		bg.makeGraphic(FlxG.width, FlxG.height, 0xFF000000);
		
		t = new FlxText(0, 0, 600, "FruitSwirl Alpha");
		
		g = new Grid();
		g.makeFirstBoard();
		g.initGravityPoints();
		
		scoreText = new FlxText(g.minPoint.x, g.maxPoint.y + Fruit.SIZE_Y + 3, 200, "Score: 0");
		spinText = new FlxText(g.maxPoint.x - 45, g.maxPoint.y + Fruit.SIZE_Y, 200, "Spins: 0");
		
		
		Rg.spinner.alignToGridMember(g.getFirstVisible());
		
		//render
		add(bg);
		add(t);
		add(scoreText);
		add(spinText);
		add(g.bg);
		add(Rg.spinner);
		add(g.fruits);
		
		for (int i = 0; i < g.gravityPoints.size(); i++){
			FlxSprite s = new FlxSprite(g.gravityPoints.get(i).x,
										g.gravityPoints.get(i).y);
			s.makeGraphic(2, 2, Color.GRAY.toIntBits());
			add(s);
		}
		
		
	}
	
	@Override
	public void update(){
		super.update();
		int fingers_down = 0;		
		justTapped = FlxG.mouse.justPressed();
		if ( FlxG.mouse.pressed() ){
			mpos = FlxG.mouse.getWorldPosition();
			justTapped = false;
		}
		
		for(int i = 0; i < 10; i++){
			if(FlxG.mouse.justReleased(i)){
				fingers_down++;
			}
		}
		
		if ( fingers_down == 1 ){
			FlxPoint moveHere = g.checkOverlap(mpos);
			if ( moveHere != null ){
				boolean moved = Rg.spinner.move(moveHere, 
						                    g.getCollidePosFromPoint(moveHere));
				if (!moved){
					g.rotateFruits();
					Rg.curRotations++;
					spinText.setText("Spins: " + Rg.curRotations);
				}
			}
			justTapped = false;
		}
		
		if (fingers_down == 3){
			g.makeFirstBoard();
			Rg.spinner.alignToGridMember(g.getFirstVisible());
			justTapped = false;
		}
		
	}
	

}