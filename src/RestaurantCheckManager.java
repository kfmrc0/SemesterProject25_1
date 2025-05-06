import java.util.Scanner;


public class RestaurantCheckManager {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);


        int checkCount = 0;
        double totalTips = 0;
        double totalSales = 0;


        double saleAmount;
        double tipAmount;
        double totalAmount;


        System.out.println("Restaurant Check Manager");
        System.out.println("------------------------");


        while (true) {
            // gets the sale amount while validating input
            saleAmount = getValidDoubleInput(scnr, "Enter sale amount: ");


            // gets tip same process
            tipAmount = getValidDoubleInput(scnr, "Enter tip amount (or -1 if not specified): ");


            // gets total amount
            totalAmount = getValidDoubleInput(scnr, "Enter total amount (or -1 if not specified): ");


            // edge cases
            if (totalAmount == -1 && tipAmount == -1) {
                // no tip or total specified
                totalAmount = saleAmount;
                tipAmount = 0;
            } else if (totalAmount == -1) {
                // only the tip specified
                totalAmount = saleAmount + tipAmount;
            } else if (tipAmount == -1) {
                // only the total specified
                tipAmount = totalAmount - saleAmount;
                if (tipAmount < 0) {
                    tipAmount = 0;
                    totalAmount = saleAmount;
                }
            } else {
                // for when sales and tip do not tally up correctly
                if (totalAmount < saleAmount) {
                    // total less than sale, assume tip of 0
                    tipAmount = 0;
                    totalAmount = saleAmount;
                } else {
                    // otherwise accept the total and verify tip
                    tipAmount = totalAmount - saleAmount;
                }
            }


            checkCount++;
            totalSales += saleAmount;
            totalTips += tipAmount;


            System.out.println("\nCheck processed:");
            System.out.printf("Sale amount: $%.2f\n", saleAmount);
            System.out.printf("Tip amount: $%.2f\n", tipAmount);
            System.out.printf("Total amount: $%.2f\n", totalAmount);
            System.out.println("------------------------");
            System.out.println("Check count: " + checkCount);
            System.out.printf("Total sales so far: $%.2f\n", totalSales);
            System.out.printf("Total pooled tips so far: $%.2f\n", totalTips);


            // yes or no, can input y and n as well (not case sensitive)
            boolean validResponse = false;
            while (!validResponse) {
                System.out.print("\nDo you want to stop (yes/no): ");
                String decision = scnr.next().toLowerCase();


                if (decision.equals("yes") || decision.equals("y")) {
                    System.out.println("\nFinal Summary:");
                    System.out.printf("The total sale amount is: $%.2f\n", totalSales);
                    System.out.printf("The total pooled tip amount is: $%.2f\n", totalTips);
                    distributeTips(totalTips);
                    return; // exits the program
                } else if (decision.equals("no") || decision.equals("n")) {
                    validResponse = true;
                    System.out.println(); // line for readability
                } else {
                    System.out.println("Invalid input. Please enter 'yes', 'y', 'no', or 'n'.");
                }
            }
        }
    }


    // help method to get valid double input (this verifies the inputs)
    private static double getValidDoubleInput(Scanner scanner, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextDouble();
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clears the invalid input so user can re enter
            }
        }
    }


    public static void distributeTips(double totalTips) {
        // fair distribution algorithm, uses pdf example


        // staff members
        // int totalServers = 3;    // how many servers
        int workingServers = 2;  // present


        // the distribution percentages
        double serverPercentage = 0.80;  // 80% to servers
        double kitchenPercentage = 0.10; // 10% to kitchen staff
        double hostPercentage = 0.05;    // 5% to host
        double busserPercentage = 0.05;  // 5% to busser


        // the kitchen staff distribution
        double chefPercentage = 0.50;     // 50% of kitchen tips
        double sousChefPercentage = 0.30; // 30% of kitchen tips
        double kitchenAidPercentage = 0.20; // 20% of kitchen tips


        // calculate individual amounts only among working servers
        double serverTip = (totalTips * serverPercentage) / workingServers;


        double kitchenTips = totalTips * kitchenPercentage;
        double chefTip = kitchenTips * chefPercentage;
        double sousChefTip = kitchenTips * sousChefPercentage;
        double kitchenAidTip = kitchenTips * kitchenAidPercentage;


        double hostTip = totalTips * hostPercentage;
        double busserTip = totalTips * busserPercentage;


        System.out.println("\nTip Distribution:");
        System.out.println("------------------");
        System.out.printf("$%.2f goes to each of the %d working servers\n", serverTip, workingServers);
        System.out.printf("$%.2f goes to the chef\n", chefTip);
        System.out.printf("$%.2f goes to the sous-chef\n", sousChefTip);
        System.out.printf("$%.2f goes to the kitchen aid\n", kitchenAidTip);
        System.out.printf("$%.2f goes to the host/hostess\n", hostTip);
        System.out.printf("$%.2f goes to the busser\n", busserTip);


        // verification check
        double totalDistributed = (serverTip * workingServers) + chefTip + sousChefTip + kitchenAidTip + hostTip + busserTip;
        System.out.printf("\nTotal distributed: $%.2f\n", totalDistributed);
    }
}



