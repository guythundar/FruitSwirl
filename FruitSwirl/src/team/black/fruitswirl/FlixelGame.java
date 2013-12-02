package team.black.fruitswirl;

import org.flixel.FlxG;
import org.flixel.FlxGame;
import org.flixel.plugin.tweens.TweenPlugin;

public class FlixelGame extends FlxGame
{
	public FlixelGame()
	{
		super(500, 250, AlphaMenuState.class, 1, 50, 50, false);
		//super(500, 250, PlayState.class, 1, 50, 50, false);
		
		//load Plugins
		FlxG.addPlugin(new TweenPlugin());
	}
}
