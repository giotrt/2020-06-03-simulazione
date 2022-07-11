package it.polito.tdp.PremierLeague.model;

public class PlayerDelta implements Comparable<PlayerDelta>{
	
	private Player p1;
	private int delta;
	
	public PlayerDelta(Player p1, int delta) {
		super();
		this.p1 = p1;
		this.delta = delta;
	}
	
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public int getDelta() {
		return delta;
	}
	public void setDelta(int delta) {
		this.delta = delta;
	}

	@Override
	public int compareTo(PlayerDelta o) {
		return -(this.delta-o.delta);
	}
	
	

}
