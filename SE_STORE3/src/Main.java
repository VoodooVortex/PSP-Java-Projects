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
    public static void displayMenu(){
        System.out.println("===== SE STORE =====");
        System.out.println("1. Show Category");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) : ");
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner kb = new Scanner(System.in);
        File productData = new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE3/src/Product data.txt");
        File categoryData = new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE3/src/Category data.txt");
        String inputSelect;
        String inputCategory;
        FileManage fileManage = new FileManage();
        do {
            displayMenu();
            inputSelect = kb.nextLine();
            switch (inputSelect){
                case "1":
                    do {
                        fileManage.showCategory(categoryData);
                        int count = fileManage.getCountProduct(categoryData);
                        System.out.println("Select Category to Show Product (1-" + count + ") or Q for exit");
                        System.out.print("Select : ");
                        inputSelect = kb.nextLine();
                        if (Integer.parseInt(inputSelect) <= count && Integer.parseInt(inputSelect) > 0) {
                            switch (inputSelect) {
                                case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10":
                                    do {
                                        fileManage.showProductSelect(productData, inputSelect);
                                        System.out.print("Press Q to Exit ");
                                        inputCategory = kb.nextLine();
                                    } while (!inputCategory.equalsIgnoreCase("Q"));
                                    break;
                            }
                        }
                    }while (!inputSelect.equalsIgnoreCase("Q"));
                    break;
                case "2":
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;
            }
        }while (!inputSelect.equals("2"));

    }
}