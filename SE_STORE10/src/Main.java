/************************************************************************************/
/* Program Assignment: SE STORE */
/* Student ID: 66160080 */
/* Student Name: Pakkapon Chomchoey */
/* Date: 14/08/67 */
/* Description: โปรแกรมจำลองร้านค้า */

import java.io.*;
import java.util.Scanner;

/*************************************************************************************/

public class Main {

    public static void displayLogin(){
        System.out.println("===== SE STORE =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) : ");
    }


    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);

        File productData = new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE10/src/Product data.txt");
        File categoryData = new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE10/src/Category data.txt");
        File memberData = new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE10/src/Member data.txt");
        File cartData = new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE10/src/CART.txt");

        String inputLogin;

        String inputCategory;

        FileManage fileManage = new FileManage();

        do {
            String inputSelect = "";
            String inputSelectStaff = "";

            displayLogin();
            inputLogin = kb.nextLine();

            switch (inputLogin){
                case "1":
                    int countError = 0;

                    do {
                        System.out.println("===== LOGIN =====");
                        System.out.print("Email : ");
                        String email = kb.nextLine();
                        System.out.print("Password : ");
                        String password = kb.nextLine();

                        if (fileManage.checkCorrect(memberData, email, password)){

                            if (fileManage.checkExpired()){

                                if (fileManage.checkRole().equals("0")){
                                    fileManage.showDataUser();
                                    do {
                                        System.out.println("====================");
                                        System.out.println("1. Show Category");
                                        System.out.println("2. Add Member");
                                        System.out.println("3. Edit Member");
                                        System.out.println("4. Edit Product");
                                        System.out.println("5. Logout");
                                        System.out.println("====================");
                                        System.out.print("Select (1-5) : ");
                                        inputSelectStaff = kb.nextLine();
                                        switch (inputSelectStaff) {
                                            case "1":
                                                do {
                                                    fileManage.showCategory(categoryData);

                                                    int count = fileManage.getCountProduct();

                                                    System.out.println("Select Category to Show Product (1-" + count + ") or Q for exit");
                                                    System.out.print("Select : ");
                                                    inputSelect = kb.nextLine();

                                                    switch (inputSelect) {
                                                        case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10":
                                                            fileManage.showProductSelect(productData, inputSelect);
                                                            do {
                                                                fileManage.productList.clear();
                                                                System.out.println("1. Show Name By DESC");
                                                                System.out.println("2. Show Quantity By ASC");
                                                                System.out.print("or Press Q to Exit : ");
                                                                inputCategory = kb.nextLine();
                                                                switch (inputCategory){
                                                                    case "1":
                                                                            fileManage.loadProduct(productData);
                                                                            fileManage.sortDESC();
                                                                            fileManage.showProductSelect(productData, inputSelect);
                                                                            fileManage.productList.clear();
                                                                        break;
                                                                    case "2":
                                                                            fileManage.loadProduct(productData);
                                                                            fileManage.sortASC();
                                                                            fileManage.showProductSelect(productData, inputSelect);
                                                                            fileManage.productList.clear();
                                                                        break;
                                                                }

                                                            } while (!inputCategory.equalsIgnoreCase("Q"));
                                                            break;
                                                    }

                                                } while (!inputSelect.equalsIgnoreCase("Q"));
                                                break;

                                            case "2":
                                                addMember(kb,memberData);
                                                break;

                                            case "3":
                                                String editSelect;
                                                boolean checkFinish = false;
                                                do {
                                                    fileManage.loadMemberData(memberData);
                                                    fileManage.showMember();
                                                    editSelect = kb.nextLine();

                                                    if (editSelect.matches("\\d+") && Integer.parseInt(editSelect) >= 1 && Integer.parseInt(editSelect) <= fileManage.amountMember()) {
                                                            fileManage.showMemberEditTitle(editSelect);
                                                            System.out.print("Firstname : ");
                                                            String firstName = kb.nextLine();
                                                            System.out.print("Lastname : ");
                                                            String lastName = kb.nextLine();
                                                            System.out.print("Email : ");
                                                            String emails = kb.nextLine();
                                                            System.out.print("Phone : ");
                                                            String phone = kb.nextLine();
                                                            if ((firstName.length() > 2 || firstName.equals("-")) && (lastName.length() > 2 || lastName.equals("-")) && ((emails.contains("@") && email.length() > 2) || emails.equals("-")) && (phone.length() == 10 || phone.equals("-"))){
                                                                fileManage.editMember(memberData, editSelect, firstName, lastName, emails, phone);
                                                                checkFinish = true;
                                                            }else {
                                                                System.out.println("Error! = - Your Information are Incorrect!");
                                                                checkFinish = true;
                                                            }
                                                    }
                                                }while (!editSelect.equalsIgnoreCase("Q") && !checkFinish);
                                                break;
                                            case "4":
                                                String editProduct;
                                                boolean checkProductFinish = false;
                                                do {
                                                    fileManage.loadProduct(productData);
                                                    fileManage.showProduct();
                                                    editProduct = kb.nextLine();
                                                    if (editProduct.matches("\\d+") && Integer.parseInt(editProduct) >= 1 && Integer.parseInt(editProduct) <= fileManage.amountProduct()) {
                                                        fileManage.showProductEditTitle(editProduct);
                                                        System.out.print("Name : ");
                                                        String name = kb.nextLine();
                                                        System.out.print("Quantity (+ or -) : ");
                                                        String quantity = kb.nextLine();

                                                        if ((quantity.contains("-") || quantity.contains("+")) && !quantity.contains(".")){
                                                            fileManage.editProduct(productData, editProduct, name, quantity);
                                                            checkProductFinish = true;
                                                        }else {
                                                            System.out.println("Error! - Your Information are Incorrect!");
                                                            checkProductFinish = true;
                                                        }
                                                    }
                                                }while (!editProduct.equalsIgnoreCase("Q") && !checkProductFinish);
                                                break;
                                            case "5":
                                                System.out.println("===== SE STORE =====");
                                                System.out.println("Thank you for using our service :3");
                                                break;
                                        }
                                    }while (!inputSelectStaff.equals("5"));

                                }else {
                                    fileManage.showDataUser();
                                    do {
                                        System.out.println("====================");
                                        System.out.println("1. Show Category");
                                        System.out.println("2. Order Product");
                                        System.out.println("3. Search Product");
                                        System.out.println("4. Logout");
                                        System.out.println("====================");
                                        System.out.print("Select (1-4) : ");
                                        inputSelect = kb.nextLine();
                                        switch (inputSelect){
                                            case "1":
                                                do {
                                                    fileManage.showCategory(categoryData);

                                                    int count = fileManage.getCountProduct();

                                                    System.out.println("Select Category to Show Product (1-" + count + ") or Q for exit");
                                                    System.out.print("Select : ");
                                                    inputSelect = kb.nextLine();

                                                    switch (inputSelect) {
                                                        case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10":
                                                            fileManage.showProductSelect(productData, inputSelect);
                                                            do {
                                                                fileManage.productList.clear();
                                                                System.out.println("1. Show Name By DESC");
                                                                System.out.println("2. Show Quantity By ASC");
                                                                System.out.print("or Press Q to Exit : ");
                                                                inputCategory = kb.nextLine();
                                                                switch (inputCategory){
                                                                    case "1":
                                                                        fileManage.loadProduct(productData);
                                                                        fileManage.sortDESC();
                                                                        fileManage.showProductSelect(productData, inputSelect);
                                                                        fileManage.productList.clear();
                                                                        break;
                                                                    case "2":
                                                                        fileManage.loadProduct(productData);
                                                                        fileManage.sortASC();
                                                                        fileManage.showProductSelect(productData, inputSelect);
                                                                        fileManage.productList.clear();
                                                                        break;
                                                                }
                                                            } while (!inputCategory.equalsIgnoreCase("Q"));
                                                            break;
                                                    }

                                                }while (!inputSelect.equalsIgnoreCase("Q"));
                                                break;

                                            case "2":
                                                fileManage.loadProduct(productData);
                                                fileManage.showOrderProduct();
                                                System.out.println("Enter the product number followed by the quantity.");
                                                System.out.println("1. How to Order");
                                                System.out.println("2. List Products");
                                                System.out.println("3. My Cart");
                                                System.out.println("Q. Exit");
                                                String order;
                                                boolean checkSaveCart = false;
                                                do {
                                                    System.out.print("Enter : ");
                                                    order = kb.nextLine();
                                                    String[] selectOrder = order.split("\\s+");

                                                    if (order.length() == 1){
                                                        if (order.equalsIgnoreCase("1")){
                                                            fileManage.showHowTo();
                                                        }else if (order.equalsIgnoreCase("2")){
                                                            fileManage.loadProduct(productData);
                                                            fileManage.showOrderProduct();
                                                        }else if (order.equalsIgnoreCase("3")){
                                                            System.out.println("====================");
                                                            System.out.println("My Cart");
                                                            System.out.println("====================");
                                                            fileManage.loadCart(cartData);
                                                            fileManage.showCart();
                                                            System.out.println("1. Checkout");
                                                            System.out.println("2. Back");
                                                            String selectBill;
                                                            do {
                                                                System.out.print("Enter : ");
                                                                selectBill = kb.nextLine();
                                                                if (selectBill.equalsIgnoreCase("1")){
                                                                    System.out.println("====================");
                                                                    System.out.println("Checkout");
                                                                    System.out.println("====================");
                                                                    fileManage.showCheckOut();
                                                                    System.out.println("1. Confirm");
                                                                    System.out.println("2. Cancel");
                                                                    String selectConfirm;
                                                                    do {
                                                                        System.out.print("Enter : ");
                                                                        selectConfirm = kb.nextLine();
                                                                        if (selectConfirm.equalsIgnoreCase("1")){
                                                                            fileManage.checkBill(cartData, productData);
                                                                            selectBill = "2";
                                                                            order = "q";
                                                                            checkSaveCart = true;
                                                                            break;
                                                                        }else if (selectConfirm.equalsIgnoreCase("2")){
                                                                            System.out.println("====================");
                                                                            System.out.println("Add Something to Cart");
                                                                            System.out.println("====================");
                                                                            selectBill = "2";
                                                                        }
                                                                    }while (!selectConfirm.equalsIgnoreCase("2"));

                                                                }else if (selectBill.equalsIgnoreCase("2")){
                                                                    System.out.println("====================");
                                                                    System.out.println("Add Something to Cart");
                                                                    System.out.println("====================");
                                                                }
                                                            }while (!selectBill.equalsIgnoreCase("2"));

                                                        }else if (!order.equalsIgnoreCase("q")){
                                                            System.out.println("input is invalid!");
                                                        }
                                                    }else if (order.matches("\\d+\\s+[+-]?\\d+") && selectOrder.length == 2){
                                                        if (Integer.parseInt(selectOrder[0]) >= 1 && Integer.parseInt(selectOrder[0]) < fileManage.getOrderProductNumber()){
                                                            fileManage.loadCart(cartData);
                                                            fileManage.addProductToCart(selectOrder[0], selectOrder[1]);
                                                        }else {
                                                            System.out.println("input is invalid!");
                                                        }
                                                    }else {
                                                        System.out.println("input is invalid!");
                                                    }
                                                } while (!order.equalsIgnoreCase("q"));
                                                if (!checkSaveCart){
                                                    fileManage.editCart(cartData);
                                                }
                                                break;
                                            case "3":
                                                fileManage.loadProduct(productData);
                                                System.out.println("====================");
                                                System.out.println("Search Product");
                                                System.out.println("====================");
                                                System.out.print("Type Product Name: ");
                                                String searchName = kb.nextLine();
                                                fileManage.searchProduct(searchName);
                                                break;
                                            case "4":
                                                System.out.println("===== SE STORE =====");
                                                System.out.println("Thank you for using our service :3");
                                                break;
                                        }

                                    }while (!inputSelect.equals("4"));
                                }
                            }else {
                                System.out.println("===== LOGIN =====");
                                System.out.println("Email : " + email);
                                System.out.println("Password : " + password);
                                System.out.println("====================");
                                System.out.println("Error! - Your Account are Expired!");
                                break;
                            }

                        }else {
                            countError++;
                            System.out.println("===== LOGIN =====");
                            System.out.println("Email : " + email);
                            System.out.println("Password : " + password);
                            System.out.println("====================");
                            System.out.println("Error! - Email or Password is Incorrect (" + countError + ")");
                        }

                    }while (countError != 3 && !inputSelect.equals("4") && !inputSelectStaff.equals("5"));

                    if (countError == 3){
                        System.out.println("Sorry, Please try again later :(");
                    }
                    break;

                case "2":
                    System.out.println("===== SE STORE =====");
                    System.out.println("Thank you for using our service :3");
                    break;

            }

        }while (!inputLogin.equals("2"));

    }

    public static void addMember(Scanner kb, File memberData) throws IOException {
        FileManage fileManage = new FileManage();
        System.out.println("====Add Member====");
        System.out.print("Firstname : ");
        String firstName = kb.nextLine();
        System.out.print("Lastname : ");
        String lastName = kb.nextLine();
        System.out.print("Email : ");
        String email = kb.nextLine();
        System.out.print("Phone : ");
        String phone = kb.nextLine();

        if (firstName.length() > 2 && lastName.length() > 2 && email.contains("@") && email.length() > 2 && phone.length() == 10){
            BufferedReader read = new BufferedReader(new FileReader(memberData));
            String line;
            String ID = "";
            while ((line = read.readLine()) != null){
                String[] data = line.split("\\s+");
                ID = data[0].trim();
            }

            String newID = String.valueOf(Integer.parseInt(ID)+1);

            StringBuilder inputFileWriter = new StringBuilder();
            String passNum = fileManage.randomPassword();

            inputFileWriter.append(newID).append("\t")
                    .append(firstName).append("\t")
                    .append(lastName).append("\t")
                    .append(email).append("\t")
                    .append(fileManage.randomChar(passNum)).append(" ")
                    .append(phone).append("  ").append("0.00");

            System.out.println("Success - New Member has been created!");
            System.out.println(firstName + "'" + lastName.substring(0, 1).toLowerCase() + " Password is " + passNum);

            fileManage.writeFile("\n" + inputFileWriter, memberData);

        }else{
            System.out.println("Error! = - Your Information are Incorrect!");
        }
    }

}