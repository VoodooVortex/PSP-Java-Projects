import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ManageFile {

    public void showProductSEStore(File file) throws FileNotFoundException {
        Scanner readFile = new Scanner(file);
        int i = 1;
        while (readFile.hasNext()){
            String id = readFile.next();
            String name = readFile.next();
            String price = readFile.next();
            String quantity = readFile.next();
            //System.out.println(i + "\t" + Name + "\t        " + Price + "\t    " + Quantity);
            System.out.printf("%-5s %-15s %-10s %6s \n", i , name , price , quantity);
            i++;
        }
    }

}
