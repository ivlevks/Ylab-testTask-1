package com.ivlevks.lesson03.transliterator;

import java.util.*;

public class TransliteratorImpl implements Transliterator {
    private final Map<Character, String> translitedMap = new HashMap<>();

    public TransliteratorImpl() {
        List<Character> originalWord = Arrays.asList('А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н',
                'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ы', 'Ь', 'Ъ', 'Э', 'Ю', 'Я');
        for (int i = 0; i < originalWord.size(); i++) {
            List<String> translitedWord = Arrays.asList("A", "B", "V", "G", "D", "E", "E", "ZH", "Z", "I", "I", "K", "L", "M", "N",
                    "O", "P", "R", "S", "T", "U", "F", "KH", "TS", "CH", "SH", "SHCH", "Y", "", "IE", "E", "IU", "IA");
            translitedMap.put(originalWord.get(i), translitedWord.get(i));
        }
    }

    @Override
    public String transliterate(String source) {
        StringBuilder result = new StringBuilder();

        char[] chars = source.toCharArray();
        for (char character : chars) {
            if (translitedMap.containsKey(character)) {
                result.append(translitedMap.get(character));
            } else result.append(character);
        }

        return result.toString();
    }
}
