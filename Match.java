import java.util.*;
class Match { //Used to store match format details.
	private int overs,inn,tossresult;
	Match(int o,int i) {
		overs=o;
		inn=i;
	}
	public int GetInnings() {
		return inn;
	}
	public int GetOvers() {
		return overs;
	}
	public void SetToss(int t) {
		tossresult=t;
	}
	public int GetToss() {
		return tossresult;
	}
}
