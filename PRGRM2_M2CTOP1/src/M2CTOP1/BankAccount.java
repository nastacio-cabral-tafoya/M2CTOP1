package M2CTOP1;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankAccount
{
	protected ArrayList<String> transactions;
	public    DecimalFormat     df;
	
	private String firstName;
	private String lastName;
	private long   accountID;
	private double balance;
	
	public static int summaryFieldCount = 4;
	
	public static String [] summaryFieldNames = new String[] {
		"Account Number",
		"First Name",
		"Last Name",
		"Balance"
	};
	
	public BankAccount()
	{
		this.df                = new DecimalFormat("#.##");
		this.transactions      = new ArrayList<String>();
		this.firstName         = "";
		this.lastName          = "";
		this.accountID         = 0;
		this.balance           = 0.00;
	}
	
	private void updateTCList(double amt, String description)
	{// Updates the transaction list each time a new transaction is input.
		double displayAmt = amt;
		
		if (displayAmt < 0)
		{
			displayAmt *= -1;
		}
		
		String record = ("$" + this.df.format(displayAmt));
		
		if (amt < 0)
		{
			record = ("(" + record + ")");
		}
		
		while (record.length() < 20)
		{
			record = ("." + record);
		}
		
		record += (" - " + description);
		
		String [] ymd = M2CTOP1.split(M2CTOP1.split(LocalDateTime.now().toString(), 'T')[0], '-');
		
		String tcDate = (ymd[1] + "/" + ymd[2] + "/" + ymd[0]);
		
		switch (this.transactions.size())
		{
			case 0:
				this.transactions.add(tcDate +  ":" + record);
				break;
			default:
				boolean showTCDate = true;
				
				for (int i = (this.transactions.size() - 1); (i >= 0); i--)
				{
					if (tcDate.equals(M2CTOP1.split(this.transactions.get(i), ':')[0]))
					{
						showTCDate = false;
					}
				}
				
				if (showTCDate)
				{
					this.transactions.add(tcDate +  ":" + record);
				}
				else
				{
					String blankSpace = "";
					
					for (int i = 0; (i < tcDate.length()); i++)
					{
						blankSpace += " ";
					}
					
					this.transactions.add(blankSpace +  ":" + record);
				}
				break;
		}
	}
	
	public void deposit(double amt)
	{// Deposits an amount into the account.
		this.balance += amt;
		
		this.updateTCList(amt, "Deposit");
	}
	
	public void deposit(double amt, String description)
	{// Deposits an amount into the account. Overload with an option for setting the description.
		this.balance += amt;
		
		this.updateTCList(amt, description);
	}
	
	public void firstName(String name)
	{// Sets the first name on the account.
		this.firstName = name;
	}
	
	public void lastName(String name)
	{// Sets the last name on the account.
		this.lastName = name;
	}
	
	public String firstName()
	{// Returns the first name on the account.
		return (this.firstName);
	}
	
	public String lastName()
	{// Returns the last name on the account.
		return (this.lastName); 
	}
	
	public String getBalance()
	{// Returns the balance on the account.
		return (this.df.format(this.balance));
	}
	
	public long id()
	{// Returns the account number.
		return (this.accountID);
	}
	
	public String [] accountSummary()
	{// Returns the account summary information for the terminal.
		return (new String[]{String.valueOf(this.accountID), this.firstName, this.lastName, ("$" + this.df.format(this.balance))});
	}
	
	protected void id(long id)
	{// Sets the account ID number.
		this.accountID = (Long.parseLong(M2CTOP1.replace(M2CTOP1.split(LocalDateTime.now().toString(), 'T')[0], "-", "")) * 1000000);
		this.accountID += id;
	}
	
	protected void withdrawl(double amt)
	{// Withdraws from the account.
		this.balance -= amt;
		
		this.updateTCList((0 - amt), "Withdrawl");
	}
	
	protected void withdrawl(double amt, String description)
	{// Withdraws from the account with an option for setting a description.
		this.balance -= amt;
		
		this.updateTCList((0 - amt), description);
	}
	
	public String [] getTCList()
	{// Returns the list of transactions in the account.
		return (this.transactions.reversed().toArray(new String[this.transactions.size()]));
	}
}




























