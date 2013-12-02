package team.black.fruitswirl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.flixel.*;

public class Rg {

	public static MersenneTwisterFast rng = new MersenneTwisterFast();
	public static int curScore = 0;
	public static int curRotations = 0;
	public static double scoreMultiplier = 1.0;
	public static Spinner spinner = new Spinner(0, 0);
	public static boolean paused = false;
	public static int curLevel = 0;
	public static boolean animLock = false;
	public static double animLockTimer = 0;
	public static final double animLockoutTime = 0.75;
	public static void animationLock(){
		Rg.animLock = true;
	}
	public static void animationUnlock(){
		Rg.animLock = false;
		animLockTimer = 0;
	}
}
