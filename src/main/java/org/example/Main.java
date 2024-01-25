package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger count3 = new AtomicInteger(0);
    private static final AtomicInteger count4 = new AtomicInteger(0);
    private static final AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    if (text.length() == 3)
                        count3.incrementAndGet();
                    else if (text.length() == 4)
                        count4.incrementAndGet();
                    else if (text.length() == 5)
                        count5.incrementAndGet();
                }
            }
        });

        Thread letterThread = new Thread(() -> {
            for (String text : texts) {
                if (oneLetter(text)) {
                    if (text.length() == 3)
                        count3.incrementAndGet();
                    else if (text.length() == 4)
                        count4.incrementAndGet();
                    else if (text.length() == 5)
                        count5.incrementAndGet();
                }
            }
        });

        Thread sortedThread = new Thread(() -> {
            for (String text : texts) {
                if (sortedText(text)) {
                    if (text.length() == 3)
                        count3.incrementAndGet();
                    else if (text.length() == 4)
                        count4.incrementAndGet();
                    else if (text.length() == 5)
                        count5.incrementAndGet();
                }
            }
        });

        palindromeThread.start();
        letterThread.start();
        sortedThread.start();

        palindromeThread.join();
        letterThread.join();
        sortedThread.join();

        System.out.println("Красивых слов с длиной 3: " + count3 + " шт.");
        System.out.println("Красивых слов с длиной 4: " + count4 + " шт.");
        System.out.println("Красивых слов с длиной 5: " + count5 + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean oneLetter(String text) {
        return text.chars().distinct().count() == 1;
    }

    public static boolean sortedText(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}

