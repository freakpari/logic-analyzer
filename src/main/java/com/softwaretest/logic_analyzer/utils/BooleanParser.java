package com.softwaretest.logic_analyzer.utils;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BooleanParser {

    public boolean evaluate(String expression, Map<String, Boolean> values) {
        String evalStr = normalize(expression);

        List<String> sortedKeys = new ArrayList<>(values.keySet());
        sortedKeys.sort((s1, s2) -> s2.length() - s1.length());

        for (String key : sortedKeys) {
            evalStr = evalStr.replaceAll("\\b" + key + "\\b", values.get(key).toString());
        }
        evalStr = evalStr.replace(" ", "");
        return parseOr(new StringBuilder(evalStr));
    }

    private String normalize(String expr) {
        return expr.replaceAll("(?i)\\band\\b", "&&")
                .replaceAll("(?i)\\bor\\b", "||")
                .replaceAll("(?i)\\bnot\\b", "!");
    }

    private boolean parseOr(StringBuilder sb) {
        boolean left = parseAnd(sb);
        while (sb.length() > 0 && sb.toString().startsWith("||")) {
            sb.delete(0, 2);
            boolean right = parseAnd(sb);
            left = left || right;
        }
        return left;
    }

    private boolean parseAnd(StringBuilder sb) {
        boolean left = parseFactor(sb);
        while (sb.length() > 0 && sb.toString().startsWith("&&")) {
            sb.delete(0, 2);
            boolean right = parseFactor(sb);
            left = left && right;
        }
        return left;
    }

    private boolean parseFactor(StringBuilder sb) {
        if (sb.length() > 0 && sb.charAt(0) == '!') {
            sb.deleteCharAt(0);
            return !parseFactor(sb);
        }
        if (sb.length() > 0 && sb.charAt(0) == '(') {
            sb.deleteCharAt(0);
            boolean result = parseOr(sb);
            if (sb.length() > 0 && sb.charAt(0) == ')') sb.deleteCharAt(0);
            return result;
        }
        if (sb.toString().startsWith("true")) { sb.delete(0, 4); return true; }
        if (sb.toString().startsWith("false")) { sb.delete(0, 5); return false; }
        throw new RuntimeException("Syntax Error");
    }
}