package team.black.fruitswirl;

public class Apple extends Fruit {

	public Apple(float _ix, float _iy) {
		super(_ix, _iy);
		//super.makeTestSprite(0xFFFF0000);
		loadGraphic("exported_1.png");
	}

	@Override
	public void update(){
		super.update();
		
	}

}
