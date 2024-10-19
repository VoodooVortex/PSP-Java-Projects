import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManage {

    public void showCategory(File file) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        int i = 1;
        //อ่านไฟล์ category
        System.out.println("===== SE STORE's Product Categories =====");
        System.out.printf("%-7s %-15s \n", "#", "Category");
        while (readFile.hasNext()){
            String ID = readFile.next();
            String Name = readFile.nextLine();
            System.out.printf("%-5s %-15s \n", i , Name);
            i++;
        }
        System.out.println("=========================================");
        readFile.close();
    }

    public void showProductSelect(File file, String selectType) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        int countProduct = 1;
        int countCategory = 1;
        String checkIDProduct = "";
        String categoryName = "";
        //อ่านไฟล์ category
        Scanner readFileCate = new Scanner(new File("/Users/pk/Desktop/Year 2/Term 1/PSP/SE_STORE2/src/Category data.txt"));
        while (readFileCate.hasNext()){
            if (selectType.equals(String.valueOf(countCategory))){
                checkIDProduct = readFileCate.next();
                categoryName = readFileCate.nextLine().trim();
                break;
            }else {
                checkIDProduct = readFileCate.next();
                categoryName = readFileCate.nextLine();
            }
            countCategory++;
        }
        System.out.println("============ " + categoryName + " ============");
        System.out.printf("%-5s %-15s %-10s %6s \n", "#" ,"Name", "Price", "Quantity");
        //อ่านไฟล์ product
        while (readFile.hasNext()){
            String Id = readFile.next();
            String Name = readFile.next();
            String Price = readFile.next();
            String Quantity = readFile.next();
            String type = readFile.next();

            if (type.equals(checkIDProduct)){
                System.out.printf("%-5s %-15s %-10s %6s \n", countProduct, Name, Price, Quantity);
            }
            countProduct++;
        }
        System.out.println("================================");
    }

    public int getCountProduct(File file) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        int i = 0;
        //อ่านไฟล์ category
        while (readFile.hasNext()){
            String ID = readFile.next();
            String Name = readFile.nextLine();
            i++;
        }
        return i;
    }
}
