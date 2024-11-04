import java.io.*;
import java.util.*;

public class FileManage {

    Role discounts;

    Member membersLogin = new Member();

    Map<String, String> category = new HashMap<>();
    List<Product> productList = new ArrayList<>();
    List<Member> memberList = new ArrayList<>();
    List<Cart> cartList = new ArrayList<>();

    int memberNumber;
    int productNumber;
    int orderProductNumber;
    
    public int getCountProduct() {return category.size();}
    public int amountProduct(){return productNumber;}
    public int getOrderProductNumber(){return orderProductNumber;}
    public int amountMember(){return memberNumber;}

    public void writeFile(String newLine, File file) throws IOException {
        BufferedWriter writeFile = new BufferedWriter(new FileWriter(file, true));
        writeFile.write(newLine);
        writeFile.close();
    }

    public enum Role{
        STAFF(0), REGULAR(0), SILVER(0.05), GOLD(0.10);
        private final double discount;
        Role(double discount){
            this.discount = discount;
        }

        public double getDiscount() {return discount;}
    }

    public void updateProduct(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Product i : productList){
            writer.write(i.getAmount() + "\t" + i.getName() + "\t" + i.getPrice() + "\t" + i.getQuantity() + "\t" + i.getId());
            writer.newLine();
        }
        writer.close();
    }

    public void updateCart(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        if (cartList.isEmpty()){
            writer.write("");
        }else {
            for (Cart i : cartList) {
                writer.write(i.getMemberID() + "\t" + i.getProductID() + "\t" + i.getQuantity());
                writer.newLine();
            }
        }
        writer.close();
    }

    public void loadProduct(File file) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        productList.clear();
        while (readFile.hasNext()) {
            Product product = new Product();
            product.setAmount(readFile.next().trim());
            product.setName(readFile.next().trim());
            product.setPrice(readFile.next().trim());
            product.setQuantity(readFile.next().trim());
            product.setId(readFile.next().trim());
            productList.add(product);
        }
    }

    public void loadMemberData(File file) throws FileNotFoundException {
        Scanner read = new Scanner(file);
        memberList.clear();
        while (read.hasNext()){
            Member data = new Member();
            data.setId(read.next().trim());
            data.setFirstName(read.next().trim());
            data.setLastName(read.next().trim());
            data.setEmail(read.next().trim());
            data.setPassCode(read.next().trim());
            data.setPhoneNum(read.next().trim());
            data.setPoint(read.next().trim());
            memberList.add(data);
        }
    }

    public void loadCart(File file) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        if (cartList.isEmpty()) {
            while (readFile.hasNext()) {
                Cart c = new Cart();
                c.setMemberID(readFile.next().trim());
                c.setProductID(readFile.next().trim());
                c.setQuantity(readFile.next().trim());
                cartList.add(c);
            }
        }
    }

    public void showCategory(File file) throws IOException {
        BufferedReader readFile = new BufferedReader(new FileReader(file));

        int count = 1;

        String line;
        while ((line = readFile.readLine()) != null){
            String[] data = line.split("\\t");
            category.put(String.valueOf(count), data[1].trim());
            count++;
        }

        System.out.println("===== SE STORE's Product Categories =====");
        System.out.printf("%-7s %-15s \n", "#", "Category");
        for (String i : category.keySet()){
            System.out.printf("%-7s %-15s \n", i, category.get(i));
        }
        System.out.println("=========================================");
        readFile.close();
    }

    public void showProductSelect(File file, String selectType) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);

        int countProduct = 1;

        if (productList.isEmpty()) {
            while (readFile.hasNext()) {
                Product product = new Product();
                product.setAmount(readFile.next().trim());
                product.setName(readFile.next().trim());
                product.setPrice(readFile.next().trim());
                product.setQuantity(readFile.next().trim());
                product.setId(readFile.next().trim());
                productList.add(product);
            }
        }

        String checkTypeId = "";
        for (String i : category.keySet()){
            checkTypeId = i.equals(selectType) ? String.valueOf(((Integer.parseInt(i)-1)+3000)) : "" ;
            if (!checkTypeId.isEmpty()) break;
        }

        double discount = 0;
        if (checkRole().equalsIgnoreCase("2")){
            discount = 0.05;
        }else if (checkRole().equalsIgnoreCase("3")){
            discount = 0.10;
        }


        System.out.println("============ " + category.get(selectType) + " ============");
        String title = discount != 0 ? String.format("%-5s %-17s %-12s %14s \n", "#" ,"Name", "Price (฿)", "Quantity")
                : String.format("%-5s %-15s %-10s %6s \n", "#" ,"Name", "Price (฿)", "Quantity");
        System.out.print(title);
        for (Product i : productList){
            if (i.getId().equals(checkTypeId)){
                double price = Double.parseDouble(i.getPrice().substring(1)) * 34;
                double disPrice = price - (price * discount);
                String printPrice = String.format("%.2f", price);
                String displayProduct = discount != 0 ? String.format("%-5d %-15s %-7.2f %-15s %s\n", countProduct, i.getName(), disPrice, "("+printPrice+")", i.getQuantity())
                        : String.format("%-5s %-17s %-8.2f %5s \n", countProduct, i.getName(), price, i.getQuantity());
                System.out.print(displayProduct);
                countProduct++;
            }
        }

        System.out.println("================================");
    }

    public void showHowTo(){
        System.out.println("How to Order:");
        System.out.println("To Add Product:");
        System.out.println("\tEnter the product number followed by the quantity.");
        System.out.println("\tExample: 1 50 (Adds 50 chips)");
        System.out.println("To Adjust Quantity:");
        System.out.println("\t+ to add more items: 1 +50 (Adds 50 more chips)");
        System.out.println("\t- to reduce items: 1 -50 (Removes 50 chips)");
    }

    public void showOrderProduct() {
        int countProduct = 1;

        double discount = 0;
        if (checkRole().equalsIgnoreCase("2")){
            discount = 0.05;
        }else if (checkRole().equalsIgnoreCase("3")){
            discount = 0.10;
        }

        System.out.println("=========== SE STORE's Products ===========");
        String title = discount != 0 ? String.format("%-5s %-17s %-12s %14s \n", "#" ,"Name", "Price (฿)", "Quantity")
                : String.format("%-5s %-15s %-10s %6s \n", "#" ,"Name", "Price (฿)", "Quantity");
        System.out.print(title);
        for (Product i : productList) {
            double price = Double.parseDouble(i.getPrice().substring(1)) * 34;
            double disPrice = price - (price * discount);
            String printPrice = String.format("%.2f", price);
            String displayProduct = discount != 0 ? String.format("%-5d %-15s %-7.2f %-15s %s\n", countProduct, i.getName(), disPrice, "(" + printPrice + ")", i.getQuantity())
                    : String.format("%-5s %-17s %-8.2f %5s \n", countProduct, i.getName(), price, i.getQuantity());
            System.out.print(displayProduct);
            countProduct++;
        }

        orderProductNumber = countProduct;

        System.out.println("===========================================");
    }

    public void addProductToCart(String type, String amount){
        int typeProduct = Integer.parseInt(type)-1;
        int quantityProduct = Integer.parseInt(productList.get(typeProduct).getQuantity());
        String productID = String.valueOf((typeProduct + 1) + 1000);
        int quantityCart = 0;
        boolean check = false;
        int indexCart = 0;

        for (Cart i : cartList){
            if (i.getProductID().equals(productID) && i.getMemberID().equals(membersLogin.getId())) {
                quantityCart = Integer.parseInt(i.getQuantity());
                check = true;
                break;
            }
            indexCart++;
        }

        if (amount.contains("+")) {
            quantityCart += Integer.parseInt(amount.substring(1));
        } else if (amount.contains("-")) {
            quantityCart -= Integer.parseInt(amount.substring(1));
        } else {
            quantityCart = Integer.parseInt(amount);
        }

        if (quantityCart <= quantityProduct) {
            if (!check){
                if (quantityCart <= 0) return;
                Cart c = new Cart();
                c.setMemberID(membersLogin.getId());
                c.setProductID(productID);
                c.setQuantity(String.valueOf(quantityCart));
                cartList.add(c);
            }else {
                if (quantityCart <= 0) {
                    cartList.remove(indexCart);
                }else {
                    cartList.get(indexCart).setQuantity(String.valueOf(quantityCart));
                }
            }
        } else {
            System.out.println("Product not enough");
        }
    }

    public void editCart(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        if (cartList.isEmpty()){
            writer.write("");
        }else {
            for (Cart i : cartList) {
                writer.write(i.getMemberID() + "\t" + i.getProductID() + "\t" + i.getQuantity());
                writer.newLine();
            }
        }
        System.out.println("Your cart has been saved!");
        writer.close();
    }

    public void showMember(){
        int i = 0;
        System.out.println("===== SE STORE's Member =====");
        System.out.printf("%-5s %-25s %s\n", "#", "Name", "Email");

        for (Member data : memberList){
            i++;
            String fullName = data.getFirstName() + " " + data.getLastName();
            System.out.printf("%-5d %-25s %s\n",i, fullName, data.getEmail());
        }
        System.out.println("================================");
        System.out.println("Type Member Number, You want to edit or Press Q to Exit");
        System.out.print("Select (1-" + i + ") : ");
        memberNumber = i;
    }

    public void showMemberEditTitle(String select){
        System.out.println("==== Edit info of " + memberList.get(Integer.parseInt(select)-1).getFirstName() + " " + memberList.get(Integer.parseInt(select)-1).getLastName() + " ====");
        System.out.println("Type new info or Hyphen (-) for none edit.");
    }

    public void editMember(File file, String select, String F, String L, String E, String P) throws IOException {
        if (!F.equals("-")){
            memberList.get(Integer.parseInt(select)-1).setFirstName(F);
        }
        if (!L.equals("-")){
            memberList.get(Integer.parseInt(select)-1).setLastName(L);
        }
        if (!E.equals("-")){
            memberList.get(Integer.parseInt(select)-1).setEmail(E);
        }
        if (!P.equals("-")){
            memberList.get(Integer.parseInt(select)-1).setPhoneNum(P);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Member i : memberList){
            writer.write(i.getId() + "\t" + i.getFirstName() + "\t" + i.getLastName() + "\t" + i.getEmail() + "\t" + i.getPassCode() + "\t" + i.getPhoneNum() + "\t" + i.getPoint());
            writer.newLine();
        }
        writer.close();
        System.out.println("Success - Member has been updated!");
    }

    public boolean checkExpired() {
        return membersLogin.passCode.charAt(2) == '1';
    }

    public String checkRole() {
        String role;
        role = String.valueOf(membersLogin.getPassCode().charAt(6));
        return role;
    }

    public String randomPassword(){
        StringBuilder pass = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int randomInt = random.nextInt(10);
            pass.append(randomInt);
        }
        return String.valueOf(pass);
    }

    public String randomChar(String pass){
        String character = "ABCDEFGHIJKLMNOPQRSPUVWUSYZ";
        StringBuilder passWord = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 19 ; i++) {
            int randomInt = random.nextInt(26);
            passWord.append(character.charAt(randomInt));
        }

        passWord.replace(2,3, "1");
        passWord.replace(6,7,"1");
        //passWord.setCharAt(9,pass.charAt(0));
        passWord.replace(9,10,String.valueOf(pass.charAt(0)));
        passWord.replace(10,11,String.valueOf(pass.charAt(1)));
        passWord.replace(13,14,String.valueOf(pass.charAt(2)));
        passWord.replace(14,15,String.valueOf(pass.charAt(3)));
        passWord.replace(15,16,String.valueOf(pass.charAt(4)));
        passWord.replace(16,17,String.valueOf(pass.charAt(5)));

        return String.valueOf(passWord);
    }

    public boolean checkCorrect(File file, String Email, String Password) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);

        while (readFile.hasNext()){
            Member member = new Member();

            member.setId(readFile.next().trim());
            member.setFirstName(readFile.next().trim());
            member.setLastName(readFile.next().trim());
            member.setEmail(readFile.next().trim());
            member.setPassCode(readFile.next().trim());
            member.setPhoneNum(readFile.next().trim());
            member.setPoint(readFile.next().trim());

            StringBuilder pass = new StringBuilder();
            pass.append(member.getPassCode().charAt(9));
            pass.append(member.getPassCode().charAt(10));
            pass.append(member.getPassCode().charAt(13));
            pass.append(member.getPassCode().charAt(14));
            pass.append(member.getPassCode().charAt(15));
            pass.append(member.getPassCode().charAt(16));

            if (Password.equals(String.valueOf(pass)) && Email.equals(member.getEmail())){
                membersLogin = member;
                readFile.reset();
                return true;
            }
        }
        return false;
    }

    public void showDataUser() {
        String FirstName = "";
        StringBuilder FullName = new StringBuilder("Hello, ");
        StringBuilder Email = new StringBuilder();
        StringBuilder Phone = new StringBuilder();
        StringBuilder Point = new StringBuilder();

        String role = "";

        FirstName += membersLogin.getLastName().charAt(0);
        FirstName += ".";

        if (membersLogin.getPassCode().charAt(6) == '0') {
            discounts = Role.STAFF;
            role = "STAFF";
        }
        if (membersLogin.getPassCode().charAt(6) == '1') {
            discounts = Role.REGULAR;
            role = "REGULAR";
        }
        if (membersLogin.getPassCode().charAt(6) == '2') {
            discounts = Role.SILVER;
            role = "SILVER";
        }
        if (membersLogin.getPassCode().charAt(6) == '3') {
            discounts = Role.GOLD;
            role = "GOLD";
        }

        FullName.append(FirstName).append(" ").append(membersLogin.getFirstName()).append(" (").append(role).append(")");

        Email.append(membersLogin.getEmail());
        Email.replace(2, membersLogin.getEmail().indexOf("@"), "***");
        Email.replace(Email.indexOf("@") + 3, Email.length(), "***");

        Phone.append(membersLogin.getPhoneNum());
        Phone.insert(3, "-");
        Phone.insert(7, "-");

        Point.append(membersLogin.getPoint(), 0, membersLogin.getPoint().indexOf("."));

        System.out.println("===== SE STORE =====");
        System.out.println(FullName);
        System.out.println("Email: " + Email);
        System.out.println("Phone: " + Phone);
        System.out.println("You have " + Point + " Point");

    }

    public void showProduct(){
        int i = 0;
        System.out.println("=========== SE STORE's Products ===========");
        System.out.printf("%-5s %-15s %-10s %6s \n", "#", "Name", "Price (฿)", "Quantity");

        for (Product data : productList){
            i++;
            double price = Double.parseDouble(data.getPrice().substring(1)) * 34;
            System.out.printf("%-5s %-17s %-8.2f %5s \n", i, data.getName(), price, data.getQuantity());
        }

        System.out.println("===========================================");
        System.out.println("Type Product Number, You want to edit or Press Q to Exit");
        System.out.print("Select (1-" + i + ") : ");
        productNumber = i;
    }

    public void showProductEditTitle(String select){
        System.out.println("==== Edit info of " + productList.get(Integer.parseInt(select)-1).getName() + " ====");
        System.out.println("Type new info or Hyphen (-) for none edit.");
    }

    public void editProduct(File file, String select, String N, String Q) throws IOException {
        if (!N.equals("-")){
            productList.get(Integer.parseInt(select)-1).setName(N);
        }

        if (Q.length() > 1){
            if (Q.substring(1).contains("+") || Q.substring(1).contains("-")) {
                System.out.println("Error! - Your Information are Incorrect!");
                return;
            }
            if (Q.contains("+")){
                int editQ = Integer.parseInt(productList.get(Integer.parseInt(select)-1).getQuantity()) + Integer.parseInt(Q.substring(1));
                productList.get(Integer.parseInt(select)-1).setQuantity(String.valueOf(editQ));
            }
            if (Q.contains("-")){
                int editQ = Integer.parseInt(productList.get(Integer.parseInt(select)-1).getQuantity()) - Integer.parseInt(Q.substring(1));
                productList.get(Integer.parseInt(select)-1).setQuantity(String.valueOf(editQ));
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (Product i : productList){
            writer.write(i.getAmount() + "\t" + i.getName() + "\t" + i.getPrice() + "\t" + i.getQuantity() + "\t" + i.getId());
            writer.newLine();
        }
        writer.close();

        System.out.println("Success - " + productList.get(Integer.parseInt(select)-1).getName() + " has been updated!");
    }

    public void sortDESC(){
        Product temp;
        int min;
        for (int i = 0; i < productList.size()-1; i++) {
            min = i;
            temp = productList.get(i);
            for (int j = i+1; j < productList.size(); j++) {
                if (productList.get(min).getName().compareTo(productList.get(j).getName()) < 0){
                    min = j;
                }
            }
            productList.set(i, productList.get(min));
            productList.set(min, temp);
        }
    }

    public void sortASC(){
        Product temp;
        int min;
        for (int i = 0; i < productList.size()-1; i++) {
            min = i;
            temp = productList.get(i);
            for (int j = i+1; j < productList.size(); j++) {
                if (Integer.parseInt(productList.get(min).getQuantity()) > Integer.parseInt(productList.get(j).getQuantity())){
                    min = j;
                }
            }
            productList.set(i, productList.get(min));
            productList.set(min, temp);
        }
    }

    public double getFullPriceProduct(String price, String quantity){
        return (Double.parseDouble(price) * 34) * Double.parseDouble(quantity);
    }

    public double getDiscountPriceProduct(String price, String quantity){
        double discount;
        double priceDiscount;
        if (discounts.getDiscount() != 0){
            discount = Double.parseDouble(price) * discounts.getDiscount();
            priceDiscount = Double.parseDouble(price) - discount;
        }else {
            priceDiscount = Double.parseDouble(price) * 34;
        }
        return priceDiscount * Double.parseDouble(quantity);
    }




//    public boolean checkEmptyCart(){
//        if (cartList.isEmpty()) return true;
//
//        for (Cart i : cartList){
//            if (i.getMemberID().equals(membersLogin.getId())){
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public void checkBill(){
//        int idProduct;
//        List<Cart> removeCart = new ArrayList<>();
//
//        for (int i = 0; i < cartList.size(); i++) {
//            idProduct = Integer.parseInt(cartList.get(i).getProductID()) - 1001;
//
//            if (membersLogin.getId().equals(cartList.get(i).getMemberID())){
//                int remain = Integer.parseInt(productList.get(idProduct).getQuantity()) - Integer.parseInt(cartList.get(i).getQuantity());
//                if (remain < 0) {
//                    System.out.println("Product not enough");
//                    return;
//                }
//                productList.get(idProduct).setQuantity(String.valueOf(remain));
//                removeCart.add(cartList.get(i));
//            }
//        }
//        System.out.println("Check Bill Success!!");
//        cartList.removeAll(removeCart);
//    }


}
