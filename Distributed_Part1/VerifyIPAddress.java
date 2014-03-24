import java.util.Scanner;

public class VerifyIPAddress {
	public static void main(String [] args)
	{
		Scanner scan = new Scanner (System.in);
		System.out.println("Enter IP address");
		String IP =scan.nextLine();
		String[] n = IP.split("\\.");
		
		/*
		System.out.println("\n" + n[0]);
		System.out.println(n[1]);
		System.out.println(n[2]);
		System.out.println(n[3]);
		*/
		
		if((Integer.parseInt(n[0]) < 0) || (Integer.parseInt(n[0]) > 255 ))
		{
			if((Integer.parseInt(n[0]) < 0))
				System.err.println("Error!!! " + n[0] + " is less than 0. " + n[0] 
						+ " has to be greater than 0 or equal to 255");
			else
				System.err.println("Error!!! " +n[0] + " is greater than 255. " + n[0] 
						+ " has to be greater than 0 or equal to 255" );
		}
		else if((Integer.parseInt(n[1]) < 0) || (Integer.parseInt(n[1]) > 255 ))
		{
			if((Integer.parseInt(n[1]) < 0))
			{
				System.err.println("Error!!! " + n[1] + " is less than 0. " + n[1] 
						+ " has to be greater than 0 or equal to 255");
			}
			else
			{
				System.err.println("Error!!! " +n[1] + " is greater than 255. " + n[1] 
						+ " has to be greater than 0 or equal to 255" );
			}
		}
		else if((Integer.parseInt(n[2]) < 0) || (Integer.parseInt(n[2]) > 255 ))
		{
			if((Integer.parseInt(n[2]) < 0))
			{
				System.err.println("Error!!! " + n[2] + " is less than 0. " + n[2] 
						+ " has to be greater than 0 or equal to 255");
			}
			else
			{
				System.err.println("Error!!! " +n[2] + " is greater than 255. " + n[2] 
						+ " has to be greater than 0 or equal to 255" );
			}
		}
		else if((Integer.parseInt(n[3]) < 0) || (Integer.parseInt(n[3]) > 255 ))
		{
			if((Integer.parseInt(n[3]) < 0))
			{
				System.err.println("Error!!!" + n[3] + " is less than 0. " + n[3] 
						+ " has to be greater than 0 or equal to 255");
			}
			else
			{
				System.err.println("Error!!!" +n[3] + " is greater than 255. " + n[3] 
						+ " has to be greater than 0 or equal to 255" );
			}
		}
		else 
		{
			System.out.println(n[0] + "." + n[1] + "." + n[2] + "." + n[3]);
		}
	}
	
}
