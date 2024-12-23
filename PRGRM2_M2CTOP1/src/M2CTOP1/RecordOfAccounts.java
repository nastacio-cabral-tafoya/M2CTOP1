package M2CTOP1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RecordOfAccounts
{// Contains a list of checking account objects.
 // I focused mainly on learning the java swing API so I attempted to integrate it into an existing class.
 // If I had to do it again I would create classes specifically for the GUI interfaces separate from the data classes.
	private Account     first;
	private Account     last;
	private Account     iterator;
	private int         length;
	
	//Navigation Panel Stuff
	private JPanel      container;
	private JPanel      searchCtrls;
	private JPanel	    newAccountCtrls;
	private JPanel      dataContainer;
	private JPanel      data;
	private JLabel      fieldName;
	private JScrollPane scrollBox;
	private JTextField  fnTf;
	private JTextField  lnTf;
	
	//Account View Stuff
	public  Long        selectedAccountId;
	private JPanel      accountContainer;
	private JPanel      tcInfoContainer;
	private JPanel      tcListContainer;
	private JTextField  accountNumberTf;
	private JTextField  firstNameTf;
	private JTextField  lastNameTf;
	private JTextField  balanceTf;
	private JTextField  interestRateTf;
	private JTextField  feesTf;
	private JTextField  transactionsTf;
	private JScrollPane tcScrollBox;
	private JComboBox   actionSelect;
	private JTextField  tcAmountTf;
	
	public RecordOfAccounts()
	{// Main constructor.
		this.first    = null;
		this.last     = this.first;
		this.iterator = this.first;
		this.length   = 0;
		
		// Navigation panel stuff.
		this.container       = new JPanel();
		this.searchCtrls     = new JPanel();
		this.newAccountCtrls = new JPanel();
		this.dataContainer   = new JPanel();
		this.data            = new JPanel();
		this.fieldName       = new JLabel();
		this.scrollBox       = new JScrollPane(data);
		this.fnTf            = new JTextField();
		this.lnTf            = new JTextField();
		
		// Methods to assemble the navigation panel stuff.
		this.configureSearchControls();
		this.configureNewAccountControls();
		
		// Account view stuff.
		this.accountContainer = new JPanel();
		this.tcInfoContainer  = new JPanel();
		this.tcListContainer  = new JPanel();
		this.accountNumberTf  = new JTextField();
		this.firstNameTf      = new JTextField();
		this.lastNameTf       = new JTextField();
		this.balanceTf        = new JTextField();
		this.interestRateTf   = new JTextField();
		this.feesTf           = new JTextField();
		this.transactionsTf   = new JTextField();
		this.tcScrollBox      = new JScrollPane(this.tcListContainer);
		this.actionSelect     = new JComboBox(new String[]{"Deposit", "Withdrawl"});
		this.tcAmountTf       = new JTextField();
	}
	
	public JPanel getNavigationControls()
	{// Assembles the navigation panel and returns a single JPanel.
		this.container.setLayout(new BorderLayout());
		this.container.setPreferredSize(new Dimension(233, 610));
		this.container.setBackground(Color.GRAY);
		this.container.add(this.searchCtrls, BorderLayout.NORTH);
		
		// Sets list items.
		this.setListItems(BankAccount.summaryFieldNames[0], this.getAllListItems());
		
		this.dataContainer.setLayout(new BorderLayout());
		this.dataContainer.add(this.fieldName, BorderLayout.NORTH);
		this.dataContainer.add(this.scrollBox, BorderLayout.CENTER);
		
		this.container.add(this.dataContainer, BorderLayout.CENTER);
		
		return (this.container);
	}
	
	public JPanel getAccountView()
	{// Assembles the account view and returns a single JPanel.
		this.accountContainer.setLayout(new BorderLayout());
		
		this.configureAccountView();
		
		return (this.accountContainer);
	}
	
	public void switchToNewAccountControls()
	{// Switches the controls in the navigation panel from search to the ones to create a new checking account.
		try
		{
			this.container.remove(this.searchCtrls);
		}
		catch(Exception e)
		{
			System.out.println();
		}
		
		this.container.add(this.newAccountCtrls, BorderLayout.NORTH);
		
		this.container.revalidate();
		this.container.repaint();
	}
	
	public void switchToSearchControls()
	{// Switches the controls in the navigation panel back to the search controls.
		try
		{
			this.container.remove(this.newAccountCtrls);
			this.fnTf.setText("");
			this.lnTf.setText("");
		}
		catch(Exception e)
		{
			System.out.println();
		}
		
		this.container.add(this.searchCtrls, BorderLayout.NORTH);
		
		this.container.revalidate();
		this.container.repaint();
	}
	
	public void setListItems(String fieldName, String [] listItems)
	{// Sets the account numbers in the list in the navigation panel.
		try
		{
			this.data.removeAll();
		}
		catch (Exception e)
		{
			System.out.println();
		}
		
		this.fieldName.setText(fieldName);
		
		JList listBox = new JList(listItems);
		
		listBox.setFont(new Font("COURIER NEW", Font.PLAIN, 16));
		
		listBox.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (e.getValueIsAdjusting())
				{
					M2CTOP1.accounts.selectedAccountId = Long.parseLong((String) listBox.getSelectedValue());
					M2CTOP1.accounts.displayAccountView(M2CTOP1.accounts.selectedAccountId);
				}
			}
		});
		
		this.data.add(listBox);
		data.revalidate();
		data.repaint();
	}
	
	public void displayAccountView(long accountID)
	{// Updates the information in the account view to display the information about the account.
		CheckingAccount selectedAccount = this.getAccount(accountID);
		String []       tcList          = selectedAccount.getTCList();
		
		this.accountNumberTf.setText(String.valueOf(selectedAccount.id()));
		this.accountNumberTf.setEditable(false);
		this.accountNumberTf.setBackground(Color.WHITE);
		
		this.firstNameTf.setText(selectedAccount.firstName());
		this.lastNameTf.setText(selectedAccount.lastName());
		
		this.balanceTf.setText(selectedAccount.getBalance());
		this.balanceTf.setEditable(false);
		this.balanceTf.setBackground(Color.WHITE);
		
		this.interestRateTf.setText(selectedAccount.getInterestRate());
		this.interestRateTf.setEditable(false);
		this.interestRateTf.setBackground(Color.WHITE);
		
		this.feesTf.setText(selectedAccount.getFeesCharged());
		this.feesTf.setEditable(false);
		this.feesTf.setBackground(Color.WHITE);
		
		this.transactionsTf.setText(String.valueOf(tcList.length));
		this.transactionsTf.setEditable(false);
		this.transactionsTf.setBackground(Color.WHITE);
		
		JList tcListBox = new JList(tcList);
		
		tcListBox.setFont(new Font("COURIER NEW", Font.PLAIN, 16));
		
		try
		{
			this.tcListContainer.removeAll();
		}
		catch (Exception e)
		{
		}
		
		this.tcListContainer.add(tcListBox);
		this.tcListContainer.revalidate();
		this.tcListContainer.repaint();
	}
	
	private void configureAccountView()
	{// Assembles the account view graphical elements and controls.
		JPanel      accountLayoutContainer = new JPanel();
		JPanel      dataContainer          = new JPanel();
		JLabel      accountNumberLb        = new JLabel();
		JLabel      firstNameLb            = new JLabel();
		JLabel      lastNameLb             = new JLabel();
		JLabel      balanceLb              = new JLabel();
		JLabel      interestRateLb         = new JLabel();
		JLabel      feesLb                 = new JLabel();
		JLabel      transactionsLb         = new JLabel();
		
		dataContainer.setLayout(new GridLayout(7, 2));
		
		accountNumberLb.setText("Account Number");
		firstNameLb.setText("First Name");
		lastNameLb.setText("Last Name");
		balanceLb.setText("Current Balance");
		interestRateLb.setText("Interest Rate");
		feesLb.setText("Total Fees Charged");
		transactionsLb.setText("Total Number of Transactions");
		
		dataContainer.add(accountNumberLb);
		dataContainer.add(this.accountNumberTf);
		
		dataContainer.add(firstNameLb);
		dataContainer.add(this.firstNameTf);
		
		dataContainer.add(lastNameLb);
		dataContainer.add(this.lastNameTf);
		
		dataContainer.add(balanceLb);
		dataContainer.add(this.balanceTf);
		
		dataContainer.add(interestRateLb);
		dataContainer.add(this.interestRateTf);
		
		dataContainer.add(feesLb);
		dataContainer.add(this.feesTf);
		
		dataContainer.add(transactionsLb);
		dataContainer.add(this.transactionsTf);
		
		accountLayoutContainer.add(dataContainer);
		
		this.configureTCControls();
		this.tcInfoContainer.add(this.tcScrollBox, BorderLayout.CENTER);
		
		this.accountContainer.add(accountLayoutContainer, BorderLayout.CENTER);
		this.accountContainer.add(this.tcInfoContainer, BorderLayout.EAST);
	}
	
	private void configureTCControls()
	{// Assembles the transaction control graphical elements.
		JPanel     tcCTRLContainer = new JPanel();
		JPanel     inputContainer  = new JPanel();
		JLabel     actionLb        = new JLabel();
		JLabel     amountLb        = new JLabel();
		JButton    submitButton    = new JButton();
		
		actionLb.setText("Transaction Type");
		amountLb.setText("Amount");
		submitButton.setText("Submit Transaction");
		
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				M2CTOP1.accounts.inputTransaction();
			}
		});
		
		inputContainer.setLayout(new GridLayout(2, 2));
		inputContainer.add(actionLb);
		inputContainer.add(actionSelect);
		inputContainer.add(amountLb);
		inputContainer.add(tcAmountTf);
		
		tcCTRLContainer.setLayout(new BorderLayout());
		tcCTRLContainer.add(inputContainer, BorderLayout.NORTH);
		tcCTRLContainer.add(submitButton, BorderLayout.CENTER);
		
		this.tcInfoContainer.setLayout(new BorderLayout());
		this.tcInfoContainer.add(tcCTRLContainer, BorderLayout.NORTH);
	}
	
	public void inputTransaction()
	{// Inputs a new transaction and updates the transaction list.
		CheckingAccount selectedAccount = M2CTOP1.accounts.getAccount(this.selectedAccountId);
		
		switch (String.valueOf(this.actionSelect.getSelectedItem()))
		{
			case "Deposit":
				selectedAccount.deposit(Double.parseDouble(tcAmountTf.getText()));
				break;
			case "Withdrawl":
				selectedAccount.processWithdrawl(Double.parseDouble(tcAmountTf.getText()));
				break;
		}
		
		this.balanceTf.setText(String.valueOf(selectedAccount.getBalance()));
		this.feesTf.setText(String.valueOf(selectedAccount.getFeesCharged()));
		this.transactionsTf.setText(String.valueOf(selectedAccount.getTCList().length));
		
		
		JList tcListBox = new JList(selectedAccount.getTCList());
		
		tcListBox.setFont(new Font("COURIER NEW", Font.PLAIN, 16));
		
		try
		{
			this.tcListContainer.removeAll();
		}
		catch (Exception e)
		{
		}
		
		this.tcListContainer.add(tcListBox);
		this.tcListContainer.revalidate();
		this.tcListContainer.repaint();
	}
	
	private void configureNewAccountControls()
	{// Assembles the new account controls to add a new account.
		JPanel  textBoxGroup   = new JPanel();
		JPanel  fnGroup        = new JPanel();
		JPanel  lnGroup        = new JPanel();
		JPanel  buttonGroup    = new JPanel();
		JLabel  fnLabel        = new JLabel();
		JLabel  lnLabel        = new JLabel();
		JButton openAccountBtn = new JButton();
		JButton cancelBtn      = new JButton();
		
		fnLabel.setText("First Name");
		lnLabel.setText("Last Name");
		
		fnTf.setPreferredSize(new Dimension((233 / 2), 20));
		lnTf.setPreferredSize(new Dimension((233 / 2), 20));
		
		fnGroup.setLayout(new BorderLayout());
		fnGroup.add(fnLabel, BorderLayout.NORTH);
		fnGroup.add(fnTf, BorderLayout.CENTER);
		
		lnGroup.setLayout(new BorderLayout());
		lnGroup.add(lnLabel, BorderLayout.NORTH);
		lnGroup.add(lnTf, BorderLayout.CENTER);
		
		textBoxGroup.setLayout(new BorderLayout());
		textBoxGroup.add(fnGroup, BorderLayout.WEST);
		textBoxGroup.add(lnGroup, BorderLayout.CENTER);
		
		openAccountBtn.setText("Open Account");
		openAccountBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if((fnTf.getText().length() == 0) || (lnTf.getText().length() == 0))
				{
					System.out.println("Error: Both Firstname and LastName  need to be completed.");
				}
				else
				{
					M2CTOP1.accounts.add(new CheckingAccount(fnTf.getText(), lnTf.getText()));
					M2CTOP1.accounts.switchToSearchControls();
				}
			}
		});
		
		cancelBtn.setText("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				M2CTOP1.accounts.switchToSearchControls();
			}
		});
		
		buttonGroup.setLayout(new BorderLayout());
		buttonGroup.add(openAccountBtn, BorderLayout.NORTH);
		buttonGroup.add(cancelBtn, BorderLayout.CENTER);
		
		this.newAccountCtrls.setLayout(new BorderLayout());
		this.newAccountCtrls.add(textBoxGroup, BorderLayout.NORTH);
		this.newAccountCtrls.add(buttonGroup, BorderLayout.CENTER);
	}
	
	private void configureSearchControls()
	{// Configures the search controls.
		JPanel      buttonGroup      = new JPanel();
		JLabel      searchLabel      = new JLabel();
		JTextField  searchBox        = new JTextField();
		JButton     searchButton     = new JButton();
		JButton     newAccountButton = new JButton();
		
		searchLabel.setText("Search Box");
		
		searchBox.setAction(null);
		
		searchButton.setText("Search");
		newAccountButton.setText("Open New Account");
		
		buttonGroup.setLayout(new BorderLayout());
		buttonGroup.add(searchButton, BorderLayout.NORTH);
		buttonGroup.add(newAccountButton, BorderLayout.CENTER);
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String searchStr = searchBox.getText();
				
				if (searchStr.length() > 0)
				{
					String [] foundItems = M2CTOP1.accounts.find(searchStr);
					
					M2CTOP1.accounts.setListItems(BankAccount.summaryFieldNames[0], foundItems);
				}
				else
				{
					M2CTOP1.accounts.setListItems(BankAccount.summaryFieldNames[0], M2CTOP1.accounts.getAllListItems());
				}
			}
		});
		
		newAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				M2CTOP1.accounts.switchToNewAccountControls();
			}
		});
		
		this.searchCtrls.setLayout(new BorderLayout());
		this.searchCtrls.add(searchLabel, BorderLayout.NORTH);
		this.searchCtrls.add(searchBox, BorderLayout.CENTER);
		this.searchCtrls.add(buttonGroup, BorderLayout.SOUTH);
	}
	
	public String [] find(String str)
	{// Returns a list of account numbers corresponding to accounts that contain the search string somewhere.
		ArrayList <String> found = new ArrayList<String>();
		
		this.iterator = this.first;
		
		while (this.iterator != null)
		{
			String [] accSummary = this.iterator.acc.accountSummary();
			
			inner:
			for (int i = 0; (i < accSummary.length); i++)
			{
				if (accSummary[i].toUpperCase().contains(str.toUpperCase()))
				{
					found.add(accSummary[0]);
					break inner;
				}
			}
			
			this.iterator = this.iterator.next;
		}
		
		return (found.toArray(new String[found.size()]));
	}
	
	public String [] getAllListItems()
	{// Gets all of the items in the list.
		String [][] accounts  = this.formatAccSummary();
		String []   listItems = new String[accounts.length - 1];
		
		for (int i = 1; (i < accounts.length); i++)
		{
			listItems[i - 1] = accounts[i][0];
		}
		
		return (listItems);
	}
	
	public CheckingAccount getAccount(long accountID)
	{// Returns a checking account object based on the checking account number.
		this.iterator = this.first;
		
		while (this.iterator != null)
		{
			if (this.iterator.acc.id() == accountID)
			{
				return (this.iterator.acc);
			}
			
			this.iterator = this.iterator.next;
		}
		
		return (null);
	}
	
	public void add(CheckingAccount acc)
	{// Adds a new checking account object to the list.
		switch(this.length)
		{
			case 0:
				this.first     = new Account();
				this.first.acc = acc;
				this.last      = this.first;
				this.iterator  = this.first;
				this.length++;
				break;
			
			default:
				this.last.next = new Account();
				this.last.next.acc  = acc;
				this.last = this.last.next;
				this.length++;
				break;
		}
		
		this.setListItems(BankAccount.summaryFieldNames[0], this.getAllListItems());
	}
	
	public String remove(long accountID)
	{// Removes a checking account item from the list. Not implemented in this program. Not enough time.
		String err = "Account Number " + String.valueOf(accountID) + " does not exist!";
		
		if (this.first.acc.id() == accountID)
		{
			err        = "";
			this.first = this.first.next;
			this.length--;
		}
		else
		{
			this.iterator = this.first;
			
			while(this.iterator != null)
			{
				if (this.iterator.next.acc.id() == accountID)
				{
					err                = "";
					this.iterator.next = this.iterator.next.next;
					this.length--;
					
					if (this.iterator.next == null)
					{
						this.last = this.iterator;
					}
				}
				
				this.iterator = this.iterator.next;
			}
		}
		
		return (err);
	}
	
	private String [][] formatAccSummary()
	{// Formats the account summary to be displayed in a terminal. It is used elsewhere in this GUI application.
		String [][] accounts = new String[this.length + 1][BankAccount.summaryFieldCount];
		int    []   mxLngths = new int[BankAccount.summaryFieldCount];
		
		accounts[0] = BankAccount.summaryFieldNames;
		
		for (int i = 1; (i < mxLngths.length); i++)
		{
			mxLngths[i] = accounts[0][i].length();
		}
		
		this.iterator = this.first;
		
		for (int i = 1; (i < accounts.length); i++)
		{
			accounts[i] = this.iterator.acc.accountSummary();
			
			for (int j = 0; (j < accounts[i].length); j++)
			{
				if (accounts[i][j].length() > mxLngths[j])
				{
					mxLngths[j] = accounts[i][j].length();
				}
			}
			
			this.iterator = this.iterator.next;
		}
		
		for (int i = 0; (i < accounts.length); i++)
		{
			for (int j = 0; (j < accounts[i].length); j++)
			{
				while (accounts[i][j].length() < mxLngths[j])
				{
					accounts[i][j] += " ";
				}
			}
		}
		
		return (accounts);
	}
	
	private class Account
	{// Class for each node in the record of accounts linked list.
		public CheckingAccount acc  = null;
		public Account         next = null;
	}
}





















































