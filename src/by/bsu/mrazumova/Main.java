package by.bsu.mrazumova;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        runEnTexts("twenty");

        //runRuTexts("ключ");
    }

    public static void runRuTexts(String keyword){
        Wigener wigener = new Wigener("RU");
        wigener.encrypt("texts/textru.txt", keyword, "encoded/textru.txt");

        Kasiski kasiski = new Kasiski(3);
        kasiski.analyse(new File("encoded/textru.txt"));
    }

    public static void runEnTexts(String keyword){
        Wigener wigener = new Wigener("EN");
        Kasiski kasiski = new Kasiski(3);

        for (int i = 1; i < 11; ++i){
            wigener.encrypt("texts/text" + i + ".txt", keyword, "encoded/text" + i + ".txt");
            double[] probableLength = kasiski.analyse(new File("encoded/text" + i + ".txt"));

            System.out.println("File : texts/text" + i + ".txt, " +
                    "probability of success: " + probableLength[keyword.length()] + "  " +
                    getMax(probableLength, keyword.length()));
        }
    }

    public static int getMax(double[] array, int length) {
        double[] arrCopy = Arrays.copyOf(array, array.length);
        Arrays.sort(arrCopy);

        for (int i = 1; i < array.length; ++i){
            if (arrCopy[array.length - 1] == array[i] && i == length)
                return 1;
            if (arrCopy[array.length - 2] == array[i] && i == length)
                return 2;
            if (arrCopy[array.length - 3] == array[i] && i == length)
                return 3;
        }
        return -1;
    }
}
