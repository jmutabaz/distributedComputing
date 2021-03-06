import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class StartUp {
	//Rhett - 1/22/2014
	public static void main(String[] args) throws IOException, InterruptedException {
		// Start Program From Here.
		// Menu Options and Choices to Pick From.
		int choice = -1;
		System.out.println(".---------------------------------------.");
		System.out.println("| Distributed Computing - Project 1     |");
		System.out.println("|    Group:                             |");
		System.out.println("|      Clint Walker                     |");
		System.out.println("|      Paul Lupcke                      |");
		System.out.println("|      Richard (Alex) Phelps            |");
		System.out.println("|      John Mutabazi                    |");
		System.out.println("|      Charles (Rhett) Panter           |");
		System.out.println("| --------------------------------------");
		System.out.println("| Welcome to the Socket Program!        ");
		do{
			System.out.println("|   What Would You Like To Do?          ");
			System.out.println("|     1) Start Listening...             ");
			System.out.println("|     2) Send Message...                ");
			System.out.println("|     3) Start Server Router...         ");
			System.out.println("|     4) Exit...                        ");
			System.out.print("| Please Enter Choice Here-> ");

			//Read in the input into int choice in the Try/Catch.
			try{
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				choice = Integer.parseInt(s);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			System.out.println("|");
			//Show Starting Up Message.
			if(choice >= 1 && choice <= 3)
				System.out.println("| Starting Up...\n|");
			//Break from here with the menu options.
			SocketClient cli = new SocketClient();
			switch(choice){
			case 1:
				System.out.println("| " + cli.RunServer() + "\n|");
				break;
			case 2:
				System.out.println("| " + cli.RunClient() + "\n|");
				break;
			case 3:
				System.out.println("| " + cli.RunServerRouter() + "\n|");
				break;
			case 4:
				//Exit
				break;
			default:
				//All Others.
				System.out.println("| Invalid Option.\n");
			}
		}while(choice != 4);

		//Print Out Finishing Message.
		System.out.println("|\n.---------------------------------------.");
		System.out.println("| Thanks for Using Our Program!         |");
		System.out.println("|                                       |");
		System.out.println("|      Exiting Now...                   |");
		System.out.println("'---------------------------------------'");
	}
}
