import org.taboola.question2.solution.MyClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Date date = new Date();

        List<Long> tArrayL = new ArrayList<>();
        tArrayL.add(11L);
        tArrayL.add(null);
        tArrayL.add(101L);
        tArrayL.add(null);
        tArrayL.add(11L);
        tArrayL.add(11L);
        tArrayL.add(null);
        tArrayL.add(11L);
        tArrayL.add(11L);
        tArrayL.add(300L);
        tArrayL.add(10L);
        tArrayL.add(101L);

        MyClass myClassL = new MyClass(date, "A_NAME", tArrayL, null);

        System.out.print("myClassL toString : ");
        System.out.println(myClassL);

        System.out.println("myClassL containsNumber 300 : " + myClassL.containsNumber(300));
        System.out.println("myClassL containsNumber 17 : " + myClassL.containsNumber(17));

        System.out.println(myClassL);
    }
}
