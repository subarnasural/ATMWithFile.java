import java.io.*;
import java.util.*;

class Account {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
        loadTransactions();
    }

    public boolean authenticate(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void checkBalance() {
        System.out.println("Current Balance: $" + balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            recordTransaction("Deposited: $" + amount);
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recordTransaction("Withdrawn: $" + amount);
            System.out.println("Withdrawn: $" + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void recordTransaction(String transaction) {
        transactionHistory.add(transaction);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions_" + userId + ".txt", true))) {
            writer.write(transaction);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing transaction history.");
        }
    }

    private void loadTransactions() {
        File file = new File("transactions_" + userId + ".txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    transactionHistory.add(line);
                }
            } catch (IOException e) {
                System.out.println("Error reading transaction history.");
            }
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }
}

public class ATMWithFile {
    private static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        loadAccounts();

        Scanner scanner = new Scanner(System.in);
        Account currentAccount = null;

        System.out.println("Welcome to the ATM!");

        while (currentAccount == null) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            Account acc = accounts.get(userId);
            if (acc != null && acc.authenticate(pin)) {
                currentAccount = acc;
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }

        while (true) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    currentAccount.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    try {
                        double depositAmount = Double.parseDouble(scanner.nextLine());
                        currentAccount.deposit(depositAmount);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    try {
                        double withdrawAmount = Double.parseDouble(scanner.nextLine());
                        currentAccount.withdraw(withdrawAmount);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case 4:
                    currentAccount.showTransactionHistory();
                    break;
                case 5:
                    saveAccounts();
                    System.out.println("Thank you for using the ATM!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void loadAccounts() {
        File file = new File("accounts.txt");
        if (!file.exists()) {
            System.out.println("No accounts file found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) continue;

                String userId = parts[0];
                String pin = parts[1];
                double balance = Double.parseDouble(parts[2]);
                accounts.put(userId, new Account(userId, pin, balance));
            }
        } catch (IOException e) {
            System.out.println("Error reading accounts file.");
        }
    }

    private static void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.txt"))) {
            for (Account acc : accounts.values()) {
                writer.write(acc.getUserId() + "," + acc.getPin() + "," + acc.getBalance());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }
}
