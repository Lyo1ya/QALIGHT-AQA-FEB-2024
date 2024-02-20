package org.sorting;

import static org.sorting.SortingMain.fillArray;
import static org.sorting.SortingMain.printArray;

public class GnomeSorting {
    public static void main(String[] args) {
        int[] ints = new int[30];

        fillArray(ints);
        gnomeSort(ints);
        printArray(ints);
    }

    private static void  gnomeSort(int[] ints) {
         int i = 0;
        while (i < ints.length) {
            if (i==0) {
                    i++;
            } else if (ints[i] < ints[i-1]) {
                    System.out.println("Numbers " + ints[i] + " and " + ints[i-1] + " are swapped");
                    int temp = ints[i - 1];
                    ints[i - 1] = ints[i];
                    ints[i] = temp;
                    i--;
            } else if (ints[i] >= ints[i-1]) {
                i++;
            }
        }
       // Alternative way:

//        for (int i = 0; i < ints.length; i++) {
//                if (i==0) {
//                    i++;
//                } else if (ints[i] < ints[i-1]) {
//                    System.out.println("Numbers " + ints[i] + " and " + ints[i-1] + " are swapped");
//                    int temp = ints[i - 1];
//                    ints[i - 1] = ints[i];
//                    ints[i] = temp;
//                    i-=2; // because cycle iterates +1 by default
//                } else if (ints[i] > ints[i-1]) {
//                }
//            }
   }
}
