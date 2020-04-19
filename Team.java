import java.util.*;
class Team {
	private String name;
	private int inn;
	private Player[] players=new Player[11];
	Team(String n,int i,String[] p) {
		name=n;
		inn=i;
		for(int j=0;j<11;j++)
			players[j]=new Player(p[j],i);
	}
	public String TeamName() {
		return name;
	}
	public String[] PlayerList() {
		String[] s=new String[11];
		for(int i=0;i<11;i++)
			s[i]=players[i].GetName();
		return s;
	}
	public Player GetPlayer(int i) {
		return players[i];
	}
}

