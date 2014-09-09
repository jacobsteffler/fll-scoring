package org.frogforce503.fllscoring;

public class Team {
	private int teamID, r1, r2, r3, r4;
	private String name;
	
	public Team() {
		this(0, "", 0, 0, 0, 0);
	}

	public Team(int id, String name) {
		this(id, name, 0, 0, 0, 0);
	}

	public Team(int id, String name, int r1, int r2, int r3, int r4) {
		teamID = id;
		this.name = name;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
		this.r4 = r4;
	}

	public String toString() {
		return "" + teamID + ", " + name;
	}

	public int getTeamID() {
		return teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public int getR1() {
		return r1;
	}

	public void setR1(int r1) {
		this.r1 = r1;
	}

	public int getR2() {
		return r2;
	}

	public void setR2(int r2) {
		this.r2 = r2;
	}

	public int getR3() {
		return r3;
	}

	public void setR3(int r3) {
		this.r3 = r3;
	}

	public int getR4() {
		return r4;
	}

	public void setR4(int r4) {
		this.r4 = r4;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
