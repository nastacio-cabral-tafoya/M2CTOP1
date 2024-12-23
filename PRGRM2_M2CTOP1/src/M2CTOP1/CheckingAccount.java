package M2CTOP1;

public class CheckingAccount extends BankAccount
{// Subclass which inherits features from the bank account parent class.
	private static long accountCount = 0;
	
	private double interestRate;
	private double overDraftFee;
	private double feesCharged;
	
	public CheckingAccount(String firstname, String lastname)
	{// Constructor.
		this.firstName(firstname);
		this.lastName(lastname);
		
		CheckingAccount.accountCount++;
		this.interestRate = 0.01;
		this.overDraftFee = 30.00;
		this.feesCharged  = 0.00;
		
		//Sets the account ID when a new account object is instantiated.
		this.id(CheckingAccount.accountCount);
	}
	
	public String getFeesCharged()
	{// Returns the amount of fees that have been charged to the account since it's inception.
		return (this.df.format(this.feesCharged));
	}
	
	public String getInterestRate()
	{// Returns the interest rate set on the account.
		return (this.df.format(this.interestRate * 100) + "%");
	}
	
	public void processWithdrawl(double amt)
	{// Withdraws from the account. If the account goes into the negative, a 30 dollar fee is charged.
		this.withdrawl(amt);
		
		if (Double.parseDouble(this.getBalance()) < 0)
		{
			System.out.println("Overdraft Fee Charged: $" + String.valueOf(this.overDraftFee));

			this.withdrawl(this.overDraftFee, "Overdraft Fee");
			this.feesCharged += this.overDraftFee;
		}
		
		System.out.println("Account Balance: $" + String.valueOf(this.getBalance()));
	}
}





















































