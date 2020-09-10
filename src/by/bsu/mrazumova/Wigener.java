package by.bsu.mrazumova;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Wigener {

    private int delta;

    private int n;

    public Wigener(String language) {
        switch (language){
            case "EN":
                delta = 97;
                n = 26;
                break;
            case "RU":
                delta = 1072;
                n = 32;
                break;
            default:
                throw new IllegalArgumentException("App doesn't support this language");
        }
    }

    public void encrypt(String inFile, String keyword, String outFile) {
        try {
            Scanner reader = new Scanner(new File(inFile));
            FileWriter writer = new FileWriter(outFile);
            while (reader.hasNext()) {
                String text = reader.nextLine();
                String encoded = encodeLine(text.toCharArray(), keyword.toCharArray());
                writer.write(encoded);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String encodeLine(char[] text, char[] keyword) {
        char[] encoded = new char[text.length];
        int keywordIdx = 0;
        for (int i = 0; i < text.length; ++i) {
            if (Character.isLetter(text[i])) {
                encoded[i] = (char) (((text[i] - delta + keyword[keywordIdx] - delta) % n) + delta);
                keywordIdx = (keywordIdx + 1) % keyword.length;
            } else {
                encoded[i] = ' ';
            }
        }
        return String.valueOf(encoded);
    }
}
