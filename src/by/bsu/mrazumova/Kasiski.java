package by.bsu.mrazumova;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Kasiski {

    private int lGramLength;

    private int maxProbableSize = 20;

    public Kasiski(int lGramLength) {
        this.lGramLength = lGramLength;
    }

    public double[] analyse(File inFile) {
        String text = readFromFile(inFile);

        List<Integer> intervals = new ArrayList<>();
        Set<String> lgrams = new HashSet<>();

        for (int i = 0; i < text.length() - lGramLength; ++i) {
            String lgramma = text.substring(i, lGramLength + i);
            if (!lgrams.contains(lgramma)) {
                List<Integer> entries = getAllLgramEntries(lgramma, text);
                if (entries.size() > 2)
                    intervals.addAll(entries);
            }
        }

        int[] gcd = countGCD(intervals);
        return analyzeGCDs(gcd);
    }

    private String readFromFile(File inFile) {
        try {
            Scanner scanner = new Scanner(inFile);
            String text = scanner.nextLine().replace(" ", "");
            scanner.close();
            return text;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    private List<Integer> getAllLgramEntries(String lgramma, String text) {
        List<Integer> entries = new ArrayList<>();
        int prevEntry = text.indexOf(lgramma);
        while (text.substring(prevEntry + lGramLength).contains(lgramma)) {
            int currEntry = text.substring(prevEntry + lGramLength).indexOf(lgramma) + prevEntry + lGramLength;
            entries.add(currEntry - prevEntry);
            prevEntry = currEntry;
        }
        return entries;
    }

    private int[] countGCD(List<Integer> intervals) {
        int[] gcd = new int[maxProbableSize];
        for (int i = 0; i < intervals.size(); ++i) {
            for (int j = i + 1; j < intervals.size(); ++j) {
                try {
                    gcd[gcd(intervals.get(i), intervals.get(j))]++;
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }
        return gcd;
    }

    private double[] analyzeGCDs(int[] gcd) {
        int count = 0;
        double[] probabilities = new double[gcd.length];
        for (int i = 2; i < gcd.length; ++i)
            count += gcd[i];

        for (int i = 2; i < gcd.length; ++i)
            probabilities[i] = ((double) gcd[i]) / count;

        return probabilities;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
