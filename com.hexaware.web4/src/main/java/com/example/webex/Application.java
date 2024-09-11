package com.example.webex;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.webex.Entities.Account;
import com.example.webex.UserRepo.UserRepository;
 

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        UserRepository rep = context.getBean(UserRepository.class);
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nBanking System Menu:");
            System.out.println("1. Open Account in Bank (minimum amount required 1000)");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Search by Account Number");
            System.out.println("5. Transfer Amount");
            System.out.println("6. Close Account");
            System.out.println("7. Display All Accounts");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                   
            case 1:// open account
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    Double initialBalance = scanner.nextDouble();
                    scanner.nextLine(); 
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    openAccount(rep, name, initialBalance, email);
                    break;  
                case 2://deposit 
                    System.out.print("Enter account number: ");
                    Integer depositAccNumber = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    Double depositAmount = scanner.nextDouble();
                    deposit(rep, depositAccNumber, depositAmount);
                    break;
                case 3: //withdraw amount
                    System.out.print("Enter account number: ");
                    Integer withdrawAccNumber = scanner.nextInt();
                    System.out.print("Enter withdrawal amount: ");
                    Double withdrawAmount = scanner.nextDouble();
                    withdraw(rep, withdrawAccNumber, withdrawAmount);
                    break;
                case 4://search accounts
                    System.out.print("Enter account number: ");
                    Integer searchAccNumber = scanner.nextInt();
                    searchByAccountNumber(rep, searchAccNumber);
                    break;
                case 5:// transfer
                    System.out.print("Enter source account number: ");
                    Integer fromAccNumber = scanner.nextInt();
                    System.out.print("Enter destination account number: ");
                    Integer toAccNumber = scanner.nextInt();
                    System.out.print("Enter transfer amount: ");
                    Double transferAmount = scanner.nextDouble();
                    transferAmount(rep, fromAccNumber, toAccNumber, transferAmount);
                    break;
                case 6://close or delete acc 
                    System.out.print("Enter account number to close: ");
                    Integer closeAccNumber = scanner.nextInt();
                    closeAccount(rep, closeAccNumber);
                    break;
                case 7:// to display existing ones
                    displayAllAccounts(rep);
                    break;
                case 8://exit
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 8);

        scanner.close();
    }

    public static void openAccount(UserRepository rep, String name, Double initialBalance, String email) {
        if (initialBalance >= 1000.0) {
            Account account = new Account(name, initialBalance, email);
            rep.save(account);
            System.out.println("Account opened");
        } else {
            System.out.println("Minimum amount to open an account is 1000");
        }
    }


    public static void deposit(UserRepository rep, Integer accountNumber, Double amount) {
        Optional<Account> acc = rep.findById(accountNumber);
        if (acc.isPresent()) {
            Account account = acc.get();
            account.setBalance(account.getBalance() + amount);
            rep.save(account);
            System.out.println("Deposited " + amount + " into account " + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void withdraw(UserRepository rep, Integer accountNumber, Double amount) {
        Optional<Account> acc = rep.findById(accountNumber);
        if (acc.isPresent()) {
            Account account = acc.get();
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                rep.save(account);
                System.out.println("Withdrew " + amount + " from account " + accountNumber);
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void searchByAccountNumber(UserRepository rep, Integer accountNumber) {
        Optional<Account> acc = rep.findById(accountNumber);
        if (acc.isPresent()) {
            System.out.println("Account found: " + acc.get());
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void transferAmount(UserRepository rep, Integer fromAccountNumber, Integer toAccountNumber, Double amount) {
        Optional<Account> fromAcc = rep.findById(fromAccountNumber);
        Optional<Account> toAcc = rep.findById(toAccountNumber);

        if (fromAcc.isPresent() && toAcc.isPresent()) {
            Account fromAccount = fromAcc.get();
            Account toAccount = toAcc.get();

            if (fromAccount.getBalance() >= amount) {
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                rep.save(fromAccount);
                rep.save(toAccount);
                System.out.println("Transferred " + amount + " from account " + fromAccountNumber + " to account " + toAccountNumber);
            } else {
                System.out.println("Insufficient balance for transfer.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public static void closeAccount(UserRepository rep, Integer accountNumber) {
        Optional<Account> acc = rep.findById(accountNumber);
        if (acc.isPresent()) {
            rep.deleteById(accountNumber);
            System.out.println("Account " + accountNumber + " closed.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public static void displayAllAccounts(UserRepository rep) {
        Iterable<Account> accounts = rep.findAll();
        System.out.println("Existing Accounts:");
        accounts.forEach(account -> System.out.println(account));
    }
}
