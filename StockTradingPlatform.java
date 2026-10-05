import java.util.ArrayList;
import java.util.Scanner;

// Stock class
class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    void displayStock() {
        System.out.println(symbol + " - " + name + " - Rs." + price);
    }
}

// Transaction class
class Transaction {
    String type;
    String stockSymbol;
    int quantity;
    double price;

    Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    void displayTransaction() {
        System.out.println(
            type + " | " +
            stockSymbol + " | Quantity: " +
            quantity + " | Price: Rs." + price
        );
    }
}

// User class
class User {
    String name;
    double balance;

    ArrayList<Stock> portfolio = new ArrayList<>();
    ArrayList<Integer> quantities = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<>();

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    // Buy stock
    void buyStock(Stock stock, int quantity) {

        double totalCost = stock.price * quantity;

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        if (totalCost > balance) {
            System.out.println("Insufficient balance.");
            return;
        }

        int index = -1;

        for (int i = 0; i < portfolio.size(); i++) {
            if (portfolio.get(i).symbol.equals(stock.symbol)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            portfolio.add(stock);
            quantities.add(quantity);
        } else {
            quantities.set(index, quantities.get(index) + quantity);
        }

        balance -= totalCost;

        transactions.add(
            new Transaction("BUY", stock.symbol, quantity, stock.price)
        );

        System.out.println("Stock purchased successfully!");
        System.out.println("Total cost: Rs." + totalCost);
    }

    // Sell stock
    void sellStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        int index = -1;

        for (int i = 0; i < portfolio.size(); i++) {
            if (portfolio.get(i).symbol.equals(stock.symbol)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("You do not own this stock.");
            return;
        }

        if (quantities.get(index) < quantity) {
            System.out.println("You do not have enough shares.");
            return;
        }

        double totalAmount = stock.price * quantity;

        quantities.set(index, quantities.get(index) - quantity);
        balance += totalAmount;

        transactions.add(
            new Transaction("SELL", stock.symbol, quantity, stock.price)
        );

        System.out.println("Stock sold successfully!");
        System.out.println("Amount received: Rs." + totalAmount);

        if (quantities.get(index) == 0) {
            portfolio.remove(index);
            quantities.remove(index);
        }
    }

    // Display portfolio
    void displayPortfolio() {

        System.out.println("\n----- YOUR PORTFOLIO -----");

        if (portfolio.size() == 0) {
            System.out.println("No stocks in portfolio.");
        } else {

            double totalValue = 0;

            for (int i = 0; i < portfolio.size(); i++) {

                Stock stock = portfolio.get(i);
                int quantity = quantities.get(i);

                double value = stock.price * quantity;
                totalValue += value;

                System.out.println(
                    stock.symbol +
                    " | Quantity: " +
                    quantity +
                    " | Current Price: Rs." +
                    stock.price +
                    " | Value: Rs." +
                    value
                );
            }

            System.out.println("--------------------------");
            System.out.println("Portfolio Value: Rs." + totalValue);
        }

        System.out.println("Available Cash: Rs." + balance);
    }

    // Display transactions
    void displayTransactions() {

        System.out.println("\n----- TRANSACTION HISTORY -----");

        if (transactions.size() == 0) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }
    }
}

// Main class
public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Sample market stocks
        ArrayList<Stock> stocks = new ArrayList<>();

        stocks.add(new Stock("TCS", "Tata Consultancy Services", 3500));
        stocks.add(new Stock("INFY", "Infosys", 1800));
        stocks.add(new Stock("RELIANCE", "Reliance Industries", 2900));
        stocks.add(new Stock("HDFC", "HDFC Bank", 1700));
        stocks.add(new Stock("WIPRO", "Wipro", 550));

        System.out.println("================================");
        System.out.println("     STOCK TRADING PLATFORM");
        System.out.println("================================");

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter initial balance: Rs.");
        double balance = sc.nextDouble();

        User user = new User(name, balance);

        int choice;

        do {

            System.out.println("\n========== MENU ==========");
            System.out.println("1. Display Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. View Balance");
            System.out.println("7. Exit");
            System.out.println("==========================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n----- MARKET DATA -----");

                    for (Stock stock : stocks) {
                        stock.displayStock();
                    }
                    break;

                case 2:

                    System.out.println("\n----- AVAILABLE STOCKS -----");

                    for (int i = 0; i < stocks.size(); i++) {
                        System.out.print((i + 1) + ". ");
                        stocks.get(i).displayStock();
                    }

                    System.out.print("Select stock number: ");
                    int buyChoice = sc.nextInt();

                    if (buyChoice < 1 || buyChoice > stocks.size()) {
                        System.out.println("Invalid stock selection.");
                        break;
                    }

                    Stock buyStock = stocks.get(buyChoice - 1);

                    System.out.print("Enter quantity: ");
                    int buyQuantity = sc.nextInt();

                    user.buyStock(buyStock, buyQuantity);

                    break;

                case 3:

                    System.out.println("\n----- AVAILABLE STOCKS -----");

                    for (int i = 0; i < stocks.size(); i++) {
                        System.out.print((i + 1) + ". ");
                        stocks.get(i).displayStock();
                    }

                    System.out.print("Select stock number: ");
                    int sellChoice = sc.nextInt();

                    if (sellChoice < 1 || sellChoice > stocks.size()) {
                        System.out.println("Invalid stock selection.");
                        break;
                    }

                    Stock sellStock = stocks.get(sellChoice - 1);

                    System.out.print("Enter quantity: ");
                    int sellQuantity = sc.nextInt();

                    user.sellStock(sellStock, sellQuantity);

                    break;

                case 4:
                    user.displayPortfolio();
                    break;

                case 5:
                    user.displayTransactions();
                    break;

                case 6:
                    System.out.println(
                        "Available Balance: Rs." + user.balance
                    );
                    break;

                case 7:
                    System.out.println("\nThank you for using Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 7);

        sc.close();
    }
}