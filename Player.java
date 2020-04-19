import java.util.*;
class Player implements PlayerBatStats,PlayerBowlStats {
	private String name;
	private int inn;
	//private int[] batruns,batballs,batfours,batsixes;
	private int[][] BatStats;
	//private int[] bowlruns,bowlbowls,bowlwickets,bowlmaidens,bowlextras;
	private int[][] BowlStats;
	//private boolean[] batplayed,batwicket,bowlplayed;
	private boolean[][] FlagStats;
	private String[] Dismissal;
	Player(String n,int i) {
		name=n;
		inn=i;
		BatStats=new int[4][i];
		BowlStats=new int[5][i];
		FlagStats=new boolean[3][i];
		Dismissal=new String[i];
	}
	public String GetName() {
		return name;
	}
	public void BatPlayed(boolean f,int i) {
		FlagStats[0][i]=f;
	}
	public void Bat(int[] r,int i) {
		//r[0] is flag, 1 is add, 2 is subtract, 3 is set
		//r[1]=runs,r[2]=balls,r[3]=fours,r[4]=sixes
		switch(r[0]) {
			case 1: {
				for(int j=1;j<r.length;j++)
					BatStats[j-1][i]+=r[j];
			}
			break;
			case 2: {
				for(int j=1;j<r.length;j++)
					BatStats[j-1][i]-=r[j];
			}
			break;
			case 3: {
				for(int j=1;j<r.length;j++) {
					if(r[j]!=-1)
						BatStats[j-1][i]=r[j];
				}
			}
			break;
		}
	}
	public void BatDismissed(boolean f,int i) {
		FlagStats[1][i]=f;
	}
	public void DismissalSet(String s,int i) {
		Dismissal[i]=s;
	}
	public String DismissalGet(int i) {
		return Dismissal[i];
	}
	public int[][] GetBatStats() {
		return BatStats;
	}
	public void BowlPlayed(boolean f,int i) {
		FlagStats[2][i]=f;
	}
	public void Bowl(int[] r,int i) {
		switch(r[0]) {
			case 1: {
				for(int j=1;j<r.length;j++)
					BowlStats[j-1][i]+=r[j];
			}
			break;
			case 2: {
				for(int j=1;j<r.length;j++)
					BowlStats[j-1][i]-=r[j];
			}
			break;
			case 3: {
				for(int j=1;j<r.length;j++) {
					if(r[j]!=-1)
						BowlStats[j-1][i]=r[j];
				}
			}
			break;
		}
	}
	public int[][] GetBowlStats() {
		return BowlStats;
	}
	public boolean[][] GetFlagStats() {
		return FlagStats;
	}
}