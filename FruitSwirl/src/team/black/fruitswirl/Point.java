package team.black.fruitswirl;

public class Point {

	public int x, y;
	
	public Point() {
		x = 0;
		y = 0;
	}

	public Point(int _x, int _y){
		x = _x;
		y = _y;
	}
	
	public Point(Point p){
		x = p.x;
		y = p.y;
	}
	
	public boolean setXY(int _x, int _y){
		x = _x;
		y = _y;
		
		return (_x == x && _y == y);
	}
}
