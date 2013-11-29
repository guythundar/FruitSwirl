package team.black.fruitswirl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.flixel.*;

public class Rg {

	public static MersenneTwisterFast rng = new MersenneTwisterFast();
	public static int curScore = 0;
	public static Spinner spinner = new Spinner(0, 0);
	
	public static <T> List<T> twoDArrayToList(T[][] twoDArray) {
	    List<T> list = new ArrayList<T>();
	    for (T[] array : twoDArray) {
	        list.addAll(Arrays.asList(array));
	    }
	    return list;
	}
}
