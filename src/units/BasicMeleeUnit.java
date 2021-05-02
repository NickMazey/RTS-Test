package units;


import gamelogic.Position;
import gamelogic.Unit;

public class BasicMeleeUnit extends Unit {
	public BasicMeleeUnit(Position pos) {
		super(pos);
		name = "Basic Melee Unit";
		flying = false;
		health = 50;
		attackSpeed = 5;
		attackRange = 2;
		moveSpeed = 10;
		damage = 5;
		lastAttackTime = 0;
		width = 200;
		height = 200;
		
	}
}
