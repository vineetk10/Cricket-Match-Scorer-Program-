import java.util.*;
class Innings {
	//private int runs,balls,wickets,extras;
	private int[] Stats=new int[4];
	private int[] BatOrder=new int[11];
	private int[] BowlOrder=new int[11];
	public void Modify(int[] r) {
		switch(r[0]) {
			case 1: {
				for(int j=1;j<r.length;j++)
					Stats[j-1]+=r[j];
			}
			break;
			case 2: {
				for(int j=1;j<r.length;j++)
					Stats[j-1]-=r[j];
			}
			break;
			case 3: {
				for(int j=1;j<r.length;j++) {
					if(r[j]!=-1)
						Stats[j-1]=r[j];
				}
			}
			break;
		}
	}
	public void ModifyBatOrder(int n) {
		int i;
		for(i=0;BatOrder[i]!=0;i++);
		BatOrder[i]=n;
	}
	public void ModifyBowlOrder(int n) {
		int i;
		for(i=0;BowlOrder[i]!=0;i++);
		BowlOrder[i]=n;
	}
	public int[] GetBatOrder(int n) {
		return BatOrder;
	}
	public int[] GetBowlOrder(int n) {
		return BowlOrder;
	}
	public int[] Get() {
		return Stats;
	}
}