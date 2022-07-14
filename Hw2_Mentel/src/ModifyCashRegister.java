import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class ModifyCashRegister extends CashRegister{

    /**Update item data */
    public static void updateItemData(File file, ArrayList<Item> items) throws IOException {
        System.out.print("Do you want to update the items' data? (A/D/M/Q)");

        Scanner input = new Scanner(System.in);

        char userInput = input.next().charAt(0);

        switch(userInput) {
            case'A': addItem(file, items); break;
            case'D': deleteItem(file, items); break;
            case'M': modifyItem(file, items); break;
            case'Q': quit(items);
        }

    }

    /**Add item data */
    public static void addItem(File file, ArrayList<Item> items) throws IOException {
// create object to write in file
        FileWriter output = new FileWriter(file, true);

        Scanner input = new Scanner(System.in);

        System.out.print("Please enter code: ");

        String code = input.next();

        boolean salesTax = false;

        if (code.charAt(0) == 'A') {
            salesTax = true;
        }

        System.out.print("Please enter name: ");

        String name = input.next().trim();

        System.out.print("Please print price: ");

        String price = input.next().trim();

        double unitPrice = Double.parseDouble(price);

        items.add(new Item(code, name, unitPrice, salesTax));

        output.write("");
        // Write formatted output to the file
        output.write(code + ", " + name + ", " + unitPrice);

        // Close the file
        output.close();

        //Show user that the item was added to the file
        CashRegister.displayItems(items);

        updateItemData(file, items);

        ModifyCashRegister.updateItemData(file, items);
    }

    /**Delete item data */
    public static void deleteItem(File file, ArrayList<Item> items) throws IOException {

    System.out.print("Enter code of item to be deleted: ");
        FileWriter output = new FileWriter(file, true);

        Scanner input = new Scanner(System.in);

        int itemIndex = 0;

        String userInput;

        userInput = input.next();

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

        items.remove(items.get(itemIndex));

        CashRegister.displayItems(items);

        ModifyCashRegister.updateItemData(file, items);

    }
    /**Modify item data */
    public static void modifyItem(File file, ArrayList<Item> items) throws IOException {
        System.out.print("Enter code of item to be modified: ");
        FileWriter output = new FileWriter(file, true);

        Scanner input = new Scanner(System.in);

        int itemIndex = 0;

        String userInput;

        userInput = input.next();

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

        System.out.println("Item code: " + items.get(itemIndex).code);
        System.out.print("Modification: ");
        String code = input.next();

        System.out.println("Item name: " + items.get(itemIndex).name);
        System.out.print("Modification: ");
        String name= input.next() ;

        System.out.println("Item cost: " + items.get(itemIndex).unitPrice);
        System.out.print("Modification: ");
        Double unitPrice = input.nextDouble();

        items.get(itemIndex).setCode(code);
        items.get(itemIndex).setName(name);
        items.get(itemIndex).setUnitPrice(unitPrice);

        CashRegister.displayItems(items);

        ModifyCashRegister.updateItemData(file, items);
    }

    /**Quit all transactions */
    public static void quit(ArrayList<Item> items) {
        CashRegister.displayItems(items);

        System.out.println();
        System.out.println("Thank you for using POST System, GoodBye.");
        System.exit(1);
    }
}
