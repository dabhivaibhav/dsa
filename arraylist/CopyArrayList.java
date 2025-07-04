package arraylist;

import java.util.ArrayList;

/*Write a program to copy all the elements from a arraylist*/
public class CopyArrayList  {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        ArrayList<String> list1 = new ArrayList<>();
        list1.addAll(list);
        System.out.println(list1);
    }
}
