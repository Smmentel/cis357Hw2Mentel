//Homework 2: Cash Register Program
//Course: CIS357
//Due date: 7-12-2022
//Name: Sarah Mentel
//GitHub: cis357-hw1-Mentel
//Insructor: Il-Hyung Cho
//Program Description: Cash register emulation, able to conduct typical transactions as well as add/delete/modify item data
/*
Program features:
Change the item code type to String: Y
Provide the input in CSV format. Ask the user to enter the input file name: Y
Implement exception handling for
    File input: Y
    Checking wrong input data type: Y
    Checking invalid data value: Y
    Tendered amount less than the total amount: y
Use ArrayList for the items data: y
Adding item data: P
Deleting item data: Y
Modifying item data: P
*/

import java.io.IOException;

public class HW2_Mentel {

    /**Main method*/
    public static void main(String[] args) throws IOException {
        CashRegister sale = new CashRegister();

        sale.main(args);

        ModifyCashRegister modify = new ModifyCashRegister();
    }
}