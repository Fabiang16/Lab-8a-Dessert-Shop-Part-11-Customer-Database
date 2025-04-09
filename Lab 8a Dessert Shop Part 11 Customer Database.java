package DessertShop;

import java.util.*;

public class DessertShop {

    private static final HashMap<String, Customer> customerDB = new HashMap<>();
    private static Scanner input = new Scanner(System.in);
    private static final double TAX_RATE = 0.07;

    public static void main(String[] args) {
        while (true) {
            order();
            System.out.println("Hit enter to start a new order.");
            input.nextLine(); // Wait for user input to continue or exit
        }
    }

    public static double getTaxRate() {
        return TAX_RATE;
    }

    private static void order() {
        ArrayList<DessertItem> orderList = new ArrayList<>();
        double total = 0;
        double tax = 0;

        // Add items to the order (items can be customized)
        while (true) {
            System.out.println("1: Candy");
            System.out.println("2: Cookie");
            System.out.println("3: Ice Cream");
            System.out.println("4: Sundae");
            System.out.println("5: Admin Module");
            System.out.print("What would you like to add to the order? (1-5, Enter for done): ");
            String choice = input.nextLine();

            if (choice.equals("")) {
                break;
            }

            DessertItem item = null;
            switch (choice) {
                case "1":
                    item = new Candy("Gummy Bears", 0.25, 0.35);
                    break;
                case "2":
                    item = new Cookie("Oatmeal Raisin", 2, 3.45);
                    break;
                case "3":
                    item = new IceCream("Pistachio", 2, 0.79);
                    break;
                case "4":
                    item = new Sundae("Hot Fudge", 2, 1.29, "Chocolate Syrup", 0.50);
                    break;
                case "5":
                    adminModule();
                    return;
            }

            if (item != null) {
                orderList.add(item);
                total += item.calculateCost();
                tax += item.calculateTax();
            } else {
                System.out.println("Invalid choice. Please select a valid option.");
            }
        }

        // Ask for the customer name
        System.out.print("Enter the customer name: ");
        String customerName = input.nextLine();
        Customer customer = customerDB.get(customerName);

        // If customer doesn't exist, create a new one
        if (customer == null) {
            customer = new Customer(customerName);
            customerDB.put(customerName, customer);
        }

        // Add order to customer's history
        customer.addOrder(orderList);

        // Ask for payment method
        System.out.print("What form of payment will be used? (CASH, CARD, PHONE): ");
        String payment = input.nextLine();

        // Print the receipt
        printReceipt(orderList, total, tax, payment, customer);
    }

    private static void printReceipt(ArrayList<DessertItem> orderList, double total, double tax, String payment, Customer customer) {
        System.out.println("\n----------------------------------Receipt----------------------------------");
        for (DessertItem item : orderList) {
            System.out.println(item);
        }
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("Total number of items in order: %d\n", orderList.size());
        System.out.printf("Order Subtotals: $%.2f [Tax: $%.2f]\n", total, tax);
        System.out.printf("\nOrder Total: $%.2f\n", total + tax);
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Paid for with " + payment + ".\n");
        System.out.println("---------------------------------------------------------------------------");
        System.out.printf("Customer Name: %s          Customer ID: %d           Total Orders: %d\n", customer.getName(), customer.getId(), customer.getTotalOrders());
    }

    private static void adminModule() {
        System.out.println("\n1: Shop Customer List");
        System.out.println("2: Customer Order History");
        System.out.println("3: Best Customer");
        System.out.println("4: Exit Admin Module");

        System.out.print("What would you like to do? (1-4): ");
        String choice = input.nextLine();

        switch (choice) {
            case "1":
                showCustomerList();
                break;
            case "2":
                showCustomerHistory();
                break;
            case "3":
                showBestCustomer();
                break;
            case "4":
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void showCustomerList() {
        System.out.println("\nCustomer List:");
        for (String name : customerDB.keySet()) {
            System.out.println("Customer Name: " + name + " ID: " + customerDB.get(name).getId());
        }
    }

    private static void showCustomerHistory() {
        System.out.print("Enter the name of the customer: ");
        String name = input.nextLine();

        Customer customer = customerDB.get(name);
        if (customer != null) {
            System.out.println("Customer Name: " + customer.getName() + " ID: " + customer.getId());
            System.out.println("Order History:");
            customer.printOrderHistory();
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void showBestCustomer() {
        Customer bestCustomer = null;
        for (Customer customer : customerDB.values()) {
            if (bestCustomer == null || customer.getTotalOrders() > bestCustomer.getTotalOrders()) {
                bestCustomer = customer;
            }
        }

        if (bestCustomer != null) {
            System.out.println("The Dessert Shop's most valued customer is: " + bestCustomer.getName() + "!");
        }
    }
}










