import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

/**Cash Register class*/
public class CashRegister {

    /**Main method*/
    public static void main(String[] args) throws IOException {

        //Working variables
        String userInput = "";

        ArrayList<Item> items = new ArrayList<>();

        int totalSale = 0;

        String output = "";

        //Create history array to hold past transactions
        ArrayList<ItemHistory> itemHistory = new ArrayList<>();

        int historyIndex = 0;

        //counter variable for items array
        int count = 0;

        //Scanner for user input
        Scanner input = new Scanner(System.in);

        /*Read in file for item data*/
        //try block for invalid user input for file path
        try {
            //Get file path from user
            System.out.println("Please enter file path for item data: ");

            userInput = input.next();

            //Create file instance
            File file = new File(userInput);

            //Create scanner for the file
            Scanner fileInput = new Scanner(file);

            //since working with CSV file use delimiter
            Scanner delimiter = fileInput.useDelimiter(",");

            //Read file data into ArrayList
            while (fileInput.hasNext()) {

                String code = fileInput.next().trim();

                boolean salesTax = false;

                if (code.charAt(0) == 'A') {
                    salesTax = true;
                }

                String name = fileInput.next().trim();

                String price = fileInput.next().trim();

                double unitPrice = Double.parseDouble(price);

                //add item to ArrayList
                items.add(new Item(code, name, unitPrice, salesTax));
            }



            /* Begin sales by prompting user */

            System.out.print("Beginning a new sale? (Y/N) ");

            userInput = input.next();

            //ensure valid input
            while (userInput.charAt(0) != 'y') {

                if (userInput.charAt(0) == 'Y') //for character sensitivity
                    break;

                //determine if the user is finished
                if (userInput.equalsIgnoreCase("n")) {

                    System.out.println("The total sale for the day is $ " + totalSale);

                    displayTransactionTotal(itemHistory, historyIndex);

                    ModifyCashRegister.updateItemData(file, items);
                }

                //otherwise ask for input again until it is valid input
                System.out.println("Invalid response, please enter Y or N");
                userInput = input.next().trim();
            }


            //repeat sale until user is finished
            while (userInput.equalsIgnoreCase("y")) {

                itemHistory.clear();

                System.out.println("-------------");

                /*Begin individual transaction*/
                //determine item user wants to purchase
                System.out.print("Enter item code: ");

                int itemIndex = 0;

                userInput = input.next();

                while (itemIndex != -1) { // continue until the user enters -1, which denotes end of transactions

                    //Determine initial item index
                    try {       //try catch block for invalid input
                        itemIndex = determineItemIndex(userInput, items);

                    } catch (InputMismatchException x) {
                        System.out.println("!!!Please enter a valid item code.");
                        System.out.println();
                    }
                    //Determine if user entered correct item code
                    while (itemIndex == -3) {

                            System.out.println("!!!Invalid item code, try again");
                            System.out.print("Enter item code: ");
                            userInput = input.next();

                        //Determine initial item index
                        try {       //try catch block for invalid input
                            itemIndex = determineItemIndex(userInput, items);

                        } catch (InputMismatchException x) {
                            System.out.println("!!!Please enter a valid item code.");
                            System.out.println();
                        }
                        }

                    if (itemIndex == -1)
                        break;

                    if (itemIndex == -2) {
                        displayItems(items);
                        System.out.print("Enter item code: ");
                        userInput = input.next();
                        continue;
                    }

                    //display item
                    String itemName = items.get(itemIndex).name;

                    System.out.print("Item name:       " + itemName);

                    System.out.println();


                    //prompt user for quantity of items
                    System.out.print("Enter quantity:  ");

                    int quantity = 0;

                    //ensure user entered valid input of integer for quantity
                    try {
                        quantity = input.nextInt();
                    } catch (InputMismatchException invalidQuantity) {

                        System.out.println("!!!Invalid item quantity, please enter an integer.");

                    }
                    //ensure quantity is greater than zero
                    while (quantity < 0) {
                        System.out.println("!!!Invalid quantity, try again");
                        System.out.print("Enter quantity: ");
                        quantity = input.nextInt();
                    }


                    //determine total cost of items
                    double itemTotal = items.get(itemIndex).unitPrice * quantity;

                    //hold value of total sale
                    totalSale += itemTotal;

                    //store value of purchased item in history
                    itemHistory.add(new ItemHistory(items.get(itemIndex), quantity));

                    historyIndex++;

                    //display total for individual sale to user
                    System.out.println("Item total:     $" + itemTotal);

                    System.out.println();


                    //retrieve user input for new loop iteration
                    System.out.print("Enter item code: ");

                    userInput = input.next();

            } //denotes end of while loop, ie user has entered -1
            //denotes end of addition of items for that sale

            //complete transaction
            displayTransactionTotal(itemHistory, historyIndex);

            //formatting and prompting user
            System.out.println();

            System.out.println("-------------");

            System.out.print("Beginning a new sale? (Y/N) ");

            userInput = input.next().trim();

            //ensure valid input
            while (userInput.charAt(0) != 'y') {

                if (userInput.charAt(0) == 'Y') //for character sensitivity
                    break;

                //determine if the user is finished
                if (userInput.equalsIgnoreCase("n")) {

                    System.out.println("The total sale for the day is $ " + totalSale);

                    ModifyCashRegister.updateItemData(file, items);
                }

                //otherwise ask for input again until it is valid input
                System.out.println("Invalid response, please enter Y or N");
                userInput = input.next().trim();
            }
        }
        } catch(FileNotFoundException wrongInput){
            System.out.println("!!!!Invalid file path, please try again.");
            CashRegister.main(args);
        }
        } //end of main

    /** Determine the index of a given item based upon user input*/
        public static int determineItemIndex (String userInput, ArrayList < Item > items){
            int itemIndex = -3;

            switch (userInput) {
                case "0000": return  itemIndex = -2;
                case "-1": return itemIndex = -1;
                case "A001": return itemIndex = 0;
                case "A002": return itemIndex = 1;
                case "A003": return itemIndex = 2;
                case "A004": return itemIndex = 3;
                case "A005": return itemIndex = 4;
                case "A006": return itemIndex = 5;
                case "B001": return itemIndex = 6;
                case "B002": return itemIndex = 7;
                case "B003": return itemIndex = 8;
                case "A007": return itemIndex = 9;
            }
            return itemIndex;
        }

        /** Display transaction information to user*/
        public static void displayTransactionTotal (ArrayList < ItemHistory > itemHistory,int historyIndex){

            //formatting
            System.out.println("--------------");

            System.out.println("Item list: ");

            double subtotal = 0;

            double taxedAmount = 0;

            int count = 0;

            itemHistory.trimToSize();

           itemHistory = bubbleSort(itemHistory);

            for (int i = 0; i < itemHistory.size() ; i++) {

                System.out.println(itemHistory.get(i).getQuantity() + " " + itemHistory.get(i).getName()

                        + " $" + (itemHistory.get(i).getQuantity() * itemHistory.get(i).getPrice()));

                subtotal += (itemHistory.get(i).getQuantity() * itemHistory.get(i).getPrice());

                if(itemHistory.get(i).hasSalesTax())
                    taxedAmount += (itemHistory.get(i).getQuantity() * itemHistory.get(i).getPrice());
            }

            System.out.println("Subtotal: $ " + subtotal);

            double tax = taxedAmount * 0.06;

            double total = ((int)((tax + subtotal) * 100)) / 100.0 ;

            System.out.println("Total with Tax (%6): $" + total);

            System.out.println();

            System.out.print("Tendered Amount: ");

            Scanner input = new Scanner(System.in);

            double usersMoney = input.nextDouble();

            while (usersMoney < total) {
                System.out.println("Please enter a value greater than the total");
                usersMoney = input.nextDouble();
            }

            System.out.println("Change: $" + ((int) ((usersMoney - total) * 100)) / 100.0);

        }


        /**Method to print out items*/
        public static void displayItems (ArrayList < Item > items) {

            System.out.println("Item Code     Item Name          Unit Price");


                for (int i = 0; i < items.size(); i++) {
                    System.out.printf("%s %20s %10s", items.get(i).code, items.get(i).name, items.get(i).unitPrice);
                    System.out.println();
                }

        }
        /**Bubble sort sorting algorithm to ensure items are listed alphabetically*/
        public static ArrayList<ItemHistory> bubbleSort(ArrayList<ItemHistory> itemHistory){

                boolean needNextPass = true;

                for(int k = 1; k < itemHistory.size() && needNextPass; k++) {
                    //array may be sorted and next pass not needed
                    needNextPass = false;

                    for(int i = 0; i < itemHistory.size() - k; i++) {

                        if(itemHistory.get(i).getName().compareTo(itemHistory.get(i + 1).getName()) > 0) {

                            //swap list[i] with list[i + 1]
                            ItemHistory temp = itemHistory.get(i);
                            itemHistory.set(i, itemHistory.get(i + 1));
                            itemHistory.set(i + 1, temp);
                            needNextPass = true; //next pass still needed
                        }
                    }
                }

        return itemHistory;
        }
}