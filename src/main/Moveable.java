package main;

/**
 * default를 사용하면 인터페이스도 몸체가 있는 메소드를 만들수 있다.
 * Java는 다중상속을 지원하지 않기 때문에 Adapter 패턴을 사용하지 못하는 경우가 발생하므로
 * default를 사용하여 Adapter 패턴을 대체할 수 있다.
 */

public interface Moveable {
	public abstract void left();
	public abstract void right();
	public abstract void up();
	default public void down() {}
	default public void attack() {}
}
