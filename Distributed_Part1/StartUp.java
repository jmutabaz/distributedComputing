import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;


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
			System.out.println("|     3) Exit...                        ");
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
			if(choice == 1 || choice == 2)
				System.out.println("| Starting Up...\n|");
			//Break from here with the menu options.
			switch(choice){
			case 1:
				runServer("");
				break;
			case 2:
				sendFile("");
				break;
			case 3:
				//Exit.
				break;
			default:
				//All Others.
				System.out.println("| Invalid Option.\n");
			}
		}while(choice != 3);

		/*
		SThread x = new SThread();
		x.start();
		while(!x.reportIfMessage())
		{
			Thread.sleep(1000);
		}
		System.out.println("| Has Message!!!!!");
		//x.stop();
		System.out.println("| Message: " + x.reportMessage());
		x.join();
		 */

		//Print Out Finishing Message.
		System.out.println("|\n.---------------------------------------.");
		System.out.println("| Thanks for Using Our Program!         |");
		System.out.println("|                                       |");
		System.out.println("|      Exiting Now...                   |");
		System.out.println("'---------------------------------------'");
	}

	public static void sendFile(String x){
		SocketClient cli = new SocketClient();
		try{
			cli.RunClient("l3lawns.com", 5555, "10.0.0.14");
		}
		catch(SocketException e){
			System.out.println("| " + e.toString());
		}
	}
	
	public static void runServer(String x){
		SocketClient cli = new SocketClient();
		try{
			cli.RunServer("l3lawns.com", 5555, "10.0.0.14");
		}
		catch(SocketException e){
			System.out.println("| " + e.toString());
		}
	}

}
