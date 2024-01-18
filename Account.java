import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private long accountNumber;
    private String firstName;
    private String lastName;
    private float balance;
    private static long nextAccountNumber = 0;
    private static final float MIN_BALANCE = 500;

    public Account() {
    }

    public Account(String fname, String lname, float balance) {
        nextAccountNumber++;
        accountNumber = nextAccountNumber;
        firstName = fname;
        lastName = lname;
        this.balance = balance;
    }

    public long getAccNo() {
        return accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getBalance() {
        return balance;
    }

    public void deposit(float amount) {
        balance += amount;
    }

    public void withdraw(float amount) throws InsufficientFunds {
        if (balance - amount < MIN_BALANCE)
            throw new InsufficientFunds();
        balance -= amount;
    }

    public static void setLastAccountNumber(long accountNumber) {
        nextAccountNumber = accountNumber;
    }

    public static long getLastAccountNumber() {
        return nextAccountNumber;
    }

    @Override
    public String toString() {
        return "First Name: " + getFirstName() + "\n" +
                "Last Name: " + getLastName() + "\n" +
                "Account Number: " + getAccNo() + "\n" +
                "Balance: " + getBalance() + "\n";
    }
}
