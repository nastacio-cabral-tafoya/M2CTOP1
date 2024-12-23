package M2CTOP1;

import java.awt.*;
import javax.swing.*;

public class M2CTOP1
{
	private static String           displayError = "";
	public  static RecordOfAccounts accounts     = new RecordOfAccounts();
	private static JFrame           mainFrame    = new JFrame();
	
	private static final int MF_HEIGHT = 377;
	private static final int MF_WIDTH  = 987;
	
	public static void main(String [] args)
	{// Main Method.
		
		// Some accounts were added already for testing.
		M2CTOP1.accounts.add(new CheckingAccount("John", "Smith"));
		M2CTOP1.accounts.add(new CheckingAccount("Jane", "Smith"));
		M2CTOP1.accounts.add(new CheckingAccount("John", "Schmidt"));
		M2CTOP1.accounts.add(new CheckingAccount("Jane", "Schmidt"));
		
		M2CTOP1.generateUI();
		
		M2CTOP1.mainFrame.setSize(M2CTOP1.MF_WIDTH, M2CTOP1.MF_HEIGHT);
		M2CTOP1.mainFrame.setTitle("Bank Application");
		M2CTOP1.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		M2CTOP1.mainFrame.setVisible(true);
	}
	
	private static void generateUI()
	{// Generates the UI.
		M2CTOP1.mainFrame.setLayout(new BorderLayout());
		M2CTOP1.mainFrame.add(M2CTOP1.accounts.getNavigationControls(), BorderLayout.WEST);
		M2CTOP1.mainFrame.add(M2CTOP1.accounts.getAccountView(), BorderLayout.CENTER);
	}
	
	private static void displayErrorMessage()
	{// Not used for this program but kept in case I had time to implement it.
		if (M2CTOP1.displayError.length() > 0)
		{
			System.out.println("ERROR:\n\t" + M2CTOP1.displayError.replace("\n", "\n\t"));
			
			M2CTOP1.displayError = "";
		}
	}
	
	public static String replace(String str, String find, String replacement)
	{// Replaces a portion of a string if it matches the find string.
		String retVal = "";
		
		for (int i = 0; (i < str.length()); i++)
		{
			int    j;
			String tempStr = "";
			
			for (j = i; (tempStr.length() < find.length()) && (j < str.length()); j++)
			{
				tempStr += str.charAt(j);
			}
			
			if (find.equals(tempStr))
			{
				retVal += replacement;
			}
			else
			{
				retVal += tempStr;
			}
			
			i = (j - 1);
		}
		
		return (retVal);
	}
	
	public static String [] split(String str, char delimiter)
	{// Splits a string into an array based on a delimiting character.
		int j = 0;
		
		for (int i = 0; (i < str.length()); i++)
		{
			if (str.charAt(i) == delimiter)
			{
				j++;
			}
		}
		
		String [] retVal = new String[j + 1];
		j = 0;
		
		for (int i = 0; (i < str.length()); i++)
		{
			if (str.charAt(i) == delimiter)
			{
				j++;
			}
			else
			{
				if (retVal[j] == null)
				{
					retVal[j] = "";
				}
				
				retVal[j] += str.charAt(i);
			}
		}
		
		return (retVal);
	}
}















































