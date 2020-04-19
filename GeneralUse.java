import java.util.*;
class GeneralUse {
	public static boolean YesNo() {
		while(true) {
			Scanner sc=new Scanner(System.in);
			String choice=sc.next();
			switch(choice.toLowerCase()) {
				case "y": 
				case "yes": return true;
				case "n":
				case "no": return false;
				default: System.out.println("Try again");
				break;
			}
		}
	}
	public static String NextL() {
		Scanner sc=new Scanner(System.in);
		String line=sc.nextLine();
		if(line.equalsIgnoreCase("help")) {
			System.out.println( "The program records and displays scorecard according to ball-by-ball "+
								"data entered by the user\nMatch details could be entered by the user or "+
								"default details could be used.\nFor each ball, the scorer enters a 'code'"+
								" to indicate what happened on that ball.\nThe code is a number with a "+
								"maximum of 3 digits\nThe first digit is the runs scored on that ball. "+
								"This digit is compulsory.\nFor example, enter '4' if batsman hits 4 runs\n"+
								"The second digit is extras, if any, that occured on that ball.\nThis digit "+
								"is optional. Run penalties are automatically added for noballs/wides.\nEnter "+
								"digits according to the following codes:\n1:No ball\n2:Wide\n3:Bye\n"+
								"4:Leg-bye\n5:No-ball+bye\n6:No-ball+leg-bye\nThe third digit is dismiassal, "+
								"if any, that occured on that ball. This digit is optional.\nSanity checks are "+
								"included. For example, caught is not possible on a no-ball.\nEnter digits "+
								"according to the following codes:\n1:Caught\n2:Bowled\n3:LBW\n4:RunOut\n"+
								"5:Stumped\n6:Hit wicket\n7:Obs field\n8:Handled\n9:Hit twice\n10:Time out\n"+
								"11:Mankad\nFor example, '520' means batsman was stumped off a wide ball\n"+
								"Enter code '90' to display scorecard.");
			return NextL();
		}
		else if(line.equalsIgnoreCase("exit")) {
			System.out.println("Do you really want to quit? (yes/no)");
			if(YesNo())
				System.exit(0);
			else
				return NextL();
		}
		return line;
	}
}
