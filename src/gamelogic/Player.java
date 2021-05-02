package gamelogic;

import java.util.HashSet;

public class Player {
	
	private String name;
	private Faction faction;
	private HashSet<Unit> units;
	
	public Player() {
		this.units = new HashSet<Unit>();
		
	}
	
	public void addUnit(Unit unit) {
		this.units.add(unit);
	}

	public HashSet<Unit> getUnits() {
		return units;
	}

}
