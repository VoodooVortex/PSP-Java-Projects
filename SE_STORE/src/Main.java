/************************************************************************************/
/* Program Assignment: SE STORE */
/* Student ID: 66160080 */
/* Student Name: Pakkapon Chomchoey */
/* Date: 14/08/67 */
/* Description: โปรแกรมจำลองร้านค้า */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*************************************************************************************/

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner kb = new Scanner(System.in);
        File productData = new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE/src/Product data.txt");
        String inputSelect;
        ManageFile manageFile = new ManageFile();
        do {
            System.out.println("===== SE STORE =====");
            System.out.println("1. Show Product");
            System.out.println("2. Exit");
            System.out.println("====================");
            System.out.print("Select (1-2) : ");
            inputSelect = kb.nextLine();
            switch (inputSelect){
                case "1":
                    System.out.println("=========== SE STORE's Products ===========");
                    //System.out.println("#" + "\t" + "Name" + "\t        " + "Price" + "\t    " + "Quantity");
                    System.out.printf("%-5s %-15s %-10s %9s \n", "#" , "Name", "Price" , "Quantity");
                    manageFile.showProductSEStore(productData);
                    System.out.println("===========================================");

                    break;
                case "2":
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;
            }
        }while (!inputSelect.equals("2"));

    }
}