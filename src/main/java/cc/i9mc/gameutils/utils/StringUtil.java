package cc.i9mc.gameutils.utils;

import java.util.List;

public class StringUtil {

    public static String arrayToString(String[] strings, int start, int end) {
        if (start == -1) {
            start = 0;
        }

        if (end == -1) {
            end = strings.length;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = start; i < end; ++i) {
            stringBuilder.append(' ');
            stringBuilder.append(strings[i]);
        }

        return stringBuilder.deleteCharAt(0).toString();
    }

    public static String arrayToString(String[] strings) {
        return arrayToString(strings, 0, strings.length);
    }

    public static String arrayToString(List<String> list, int start, int end) {
        if (start == -1) {
            start = 0;
        }

        if (end == -1) {
            end = list.size();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = start; i < end; ++i) {
            stringBuilder.append(' ');
            stringBuilder.append(list.get(i));
        }

        return stringBuilder.deleteCharAt(0).toString();
    }

    public static String arrayToString(List<String> list) {
        return arrayToString(list, 0, list.size());
    }
}
