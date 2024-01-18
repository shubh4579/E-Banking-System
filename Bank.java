import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Long, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
        loadAccounts();
    }

    private void loadAccounts() {
        try {
            FileInputStream fileIn = new FileInputStream("Bank.data");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while (true) {
                Account account = (Account) in.readObject();
                accounts.put(account.getAccNo(), account);
                Account.setLastAccountNumber(account.getAccNo());
            }
        } catch (EOFException e) {
            // End of file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Account openAccount(String fname, String lname, float balance) {
        Account account = new Account(fname, lname, balance);
        accounts.put(account.getAccNo(), account);
        saveAccounts();
        return account;
    }

    public Account balanceEnquiry(long accountNumber) {
        return accounts.get(accountNumber);
    }

    public Account deposit(long accountNumber, float amount) {
        Account account = accounts.get(accountNumber);
        account.deposit(amount);
        saveAccounts();
        return account;
    }

    public Account withdraw(long accountNumber, float amount) throws InsufficientFunds {
        Account account = accounts.get(accountNumber);
        account.withdraw(amount);
        saveAccounts();
        return account;
    }

    public boolean closeAccount(long accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account Deleted");
            System.out.println(accounts.get(accountNumber));
            accounts.remove(accountNumber);
            saveAccounts();
            return true;
        }
        return false;
    }

    public void showAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts created!");
        } else {
            for (Map.Entry<Long, Account> entry : accounts.entrySet()) {
                System.out.println("Account " + entry.getKey());
                System.out.println(entry.getValue());
            }
        }
    }

    private void saveAccounts() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Bank.data");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Account account : accounts.values()) {
                out.writeObject(account);
            }
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Bank b = new Bank();
        Account acc;

        int choice;
        String fname, lname;
        long accountNumber;
        float balance;
        float amount;

        System.out.println("******* Banking System *******");

        do {
            System.out.println("\nSelect one option below ");
            System.out.println("1 Open an Account");
            System.out.println("2 Balance Enquiry");
            System.out.println("3 Deposit");
            System.out.println("4 Withdrawal");
            System.out.println("5 Close an Account");
            System.out.println("6 Show All Accounts");
            System.out.println("7 Quit");

            System.out.println("Enter your choice: ");
            choice = Integer.parseInt(System.console().readLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter First Name: ");
                    fname = System.console().readLine();
                    System.out.println("Enter Last Name: ");
                    lname = System.console().readLine();
                    System.out.println("Enter initial Balance: ");
                    balance = Float.parseFloat(System.console().readLine());
                    acc = b.openAccount(fname, lname, balance);
                    System.out.println("Congratulations, Account is Created!");
                    System.out.println(acc);
                    break;

                case 2:
                    System.out.println("Enter Account Number:");
                    accountNumber = Long.parseLong(System.console().readLine());
                    acc = b.balanceEnquiry(accountNumber);
                    System.out.println("Your Account Details:");
                    System.out.println(acc);
                    break;

                case 3:
                    System.out.println("Enter Account Number:");
                    accountNumber = Long.parseLong(System.console().readLine());
                    System.out.println("Enter Balance:");
                    amount = Float.parseFloat(System.console().readLine());
                    acc = b.deposit(accountNumber, amount);
                    System.out.println("Amount is Deposited!");
                    System.out.println(acc);
                    break;

                case 4:
                    System.out.println("Enter Account Number:");
                    accountNumber = Long.parseLong(System.console().readLine());
                    System.out.println("Enter Balance:");
                    amount = Float.parseFloat(System.console().readLine());
                    try {
                        acc = b.withdraw(accountNumber, amount);
                        System.out.println("Amount Withdrawn!");
                        System.out.println(acc);
                    } catch (InsufficientFunds e) {
                        System.out.println("Insufficient Funds!");
                    }
                    break;

                case 5:
                    System.out.println("Enter Account Number:");
                    accountNumber = Long.parseLong(System.console().readLine());
                    if (b.closeAccount(accountNumber)) {
                        System.out.println("Successfully account is Closed!");
                    } else {
                        System.out.println("Wrong Account Number!");
                    }
                    break;

                case 6:
                    b.showAllAccounts();
                    break;

                case 7:
                    break;

                default:
                    System.out.println("Enter correct choice");
                    System.exit(0);
            }
        } while (choice != 7);
    }
}
