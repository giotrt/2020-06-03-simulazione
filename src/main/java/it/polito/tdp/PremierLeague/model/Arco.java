package it.polito.tdp.PremierLeague.model;

public class Arco {
	
	private Player p1;
	private Player p2;
	private int t1;
	private int t2;
	
	public Arco(Player p1, Player p2, int t1, int t2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.t1 = t1;
		this.t2 = t2;

	}

	public Player getP1() {
		return p1;
	}

	public void setP1(Player p1) {
		this.p1 = p1;
	}

	public Player getP2() {
		return p2;
	}

	public void setP2(Player p2) {
		this.p2 = p2;
	}

	public int getT1() {
		return t1;
	}

	public void setT1(int t1) {
		this.t1 = t1;
	}

	public int getT2() {
		return t2;
	}

	public void setT2(int t2) {
		this.t2 = t2;
	}
}
