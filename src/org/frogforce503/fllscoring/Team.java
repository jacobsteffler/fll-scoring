package org.frogforce503.fllscoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Team implements Comparable<Team> {
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

	public ArrayList<Integer> getScores() {
		return new ArrayList<Integer>(Arrays.asList(new Integer[] { r1, r2, r3,
				r4 }));
	}

	public int compareTo(Team other) {
		ArrayList<Integer> s1 = getScores(), s2 = other.getScores();
		Collections.sort(s1, Collections.reverseOrder());
		Collections.sort(s2, Collections.reverseOrder());

		if (s1.get(0) > s2.get(0))
			return 1;
		if (s1.get(0) < s2.get(0))
			return -1;

		if (s1.get(1) > s2.get(1))
			return 1;
		if (s1.get(1) < s2.get(1))
			return -1;

		if (s1.get(2) > s2.get(2))
			return 1;
		if (s1.get(2) < s2.get(2))
			return -1;

		if (s1.get(3) > s2.get(3))
			return 1;
		if (s1.get(3) < s2.get(3))
			return -1;

		return 0;
	}
}
