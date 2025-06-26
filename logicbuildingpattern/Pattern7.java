package logicbuildingpattern;

import java.util.Scanner;

/**
 * bbbb*
 * bbb*b*
 * bb*b*b*
 * b*b*b*b*
 * *b*b*b*b*
 */
public class Pattern7 {
    public static void main(String[] args) {
        System.out.println("Enter the number : ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for(int i=0;i<n;i++){

            for(int k = 0 ; k<n-i-1; k++){
                System.out.print("b");
            }
            for(int j=0;j<=(i*2);j++){
                if(j%2==0){
                    System.out.print("*");
                }else{
                    System.out.print("b");
                }

            }
            System.out.println();
        }
    }
}
