package team.black.fruitswirl;

import org.flixel.FlxGame;

public class FlixelGame extends FlxGame
{
	public FlixelGame()
	{
		super(500, 240, PlayState.class, 2, 50, 50, false);
	}
}
