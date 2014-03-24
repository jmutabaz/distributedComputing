package Model;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;

public class StartUp {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Start Program From Here.
		// Menu Options and Choices to Pick From.
		int choice = -1;
		p(".---------------------------------------.");
		p("| Distributed Computing - Project 2 |");
		p("| Group: |");
		p("| Clint Walker |");
		p("| Paul Lupcke |");
		p("| Richard (Alex) Phelps |");
		p("| John Mutabazi |");
		p("| Charles (Rhett) Panter |");
		p("| --------------------------------------");
		p("| Welcome to the Awesome Socket Program! ");
		SocketClient ser = new SocketClient();
		ser.RunServer("SERVER-ROUTER IP", 5555);
		do{
			p("| What Would You Like To Do? ");
			p("| 1) Send Message... ");
			p("| 2) Start Server Router... ");
			p("| 3) Exit... ");
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
			p("|");
			//Show Starting Up Message.
			if(choice >= 1 && choice <= 3)
				p("| Starting Up...\n|");
			//Break from here with the menu options.
			Message msg = new Message();
			SocketClient cli = new SocketClient();
			msg.setData("Hi Paul!");
			msg.setMyIP("192.168.1.5");
			msg.setType(true);//String Message
			msg.setServerName("Paul-MBP");
			
			switch(choice){
			case 1:
				p("| " + cli.RunClient("WHO TO SEND TO", 5555, msg) + "\n|");
				break;
			case 2:
				p("| " + "cli.RunClient(SERVER ROUTER BANANA, 5555)" + "\n|");
				p("Need to Kill ser...");
				break;
			case 3:
				//Exit
				break;
			default:
				//All Others.
				p("| Invalid Option.\n");
			}
		}while(choice != 3);

		//Print Out Finishing Message.
		p("|\n.---------------------------------------.");
		p("| Thanks for Using Our Program! Now BANANA! |");
		p("| |");
		p("| Exiting Now... |");
		p("'---------------------------------------'");
	}

	public static void p(Object x){
		System.out.println(x);
	}

}


