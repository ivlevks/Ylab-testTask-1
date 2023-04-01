package com.ivlevks.lesson03.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();

        String res = transliterator
                .transliterate("HELLO! ПРИВЕТ! Go, boy!");
        System.out.println(res);
        res = transliterator
                .transliterate("ДЕРЖИМОРДА");
        System.out.println(res);
        res = transliterator
                .transliterate("СИМЕОНОВ-ПИЩИК");
        System.out.println(res);
        res = transliterator
                .transliterate("ЧАЦКИЙ");
        System.out.println(res);
        res = transliterator
                .transliterate("ШПЕКИН");
        System.out.println(res);
        res = transliterator
                .transliterate("ВРАЛЬМАН");
        System.out.println(res);
        res = transliterator
                .transliterate("ЯИЧНИЦА");
        System.out.println(res);
        res = transliterator
                .transliterate("БУЛЬБА");
        System.out.println(res);
        res = transliterator
                .transliterate("ЛЮЛЮКОВ");
        System.out.println(res);
        res = transliterator
                .transliterate("ТЕСТтестТЕСТ");
        System.out.println(res);
        res = transliterator
                .transliterate("ДОБЧИНСКИЙ");
        System.out.println(res);
    }
}
