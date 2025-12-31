package com.softwaretest.logic_analyzer.service;

import com.softwaretest.logic_analyzer.model.TestCase;
import com.softwaretest.logic_analyzer.utils.BooleanParser;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CoverageService {

    private final BooleanParser parser;

    public CoverageService(BooleanParser parser) {
        this.parser = parser;
    }
    public List<String> extractVariables(String expr) {
        Set<String> vars = new TreeSet<>();
        Matcher m = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*").matcher(expr);
        Set<String> keywords = Set.of("and", "or", "not", "true", "false");

        while (m.find()) {
            String word = m.group();
            if (!keywords.contains(word.toLowerCase())) {
                vars.add(word);
            }
        }
        return new ArrayList<>(vars);
    }

    public List<TestCase> generateCoC(String expression) {
        List<String> vars = extractVariables(expression);
        List<TestCase> table = new ArrayList<>();
        int numRows = (int) Math.pow(2, vars.size());

        for (int i = 0; i < numRows; i++) {
            Map<String, Boolean> inputs = new LinkedHashMap<>();
            int binaryValue = numRows - 1 - i;

            for (int j = 0; j < vars.size(); j++) {
                boolean val = ((binaryValue >> (vars.size() - 1 - j)) & 1) == 1;
                inputs.put(vars.get(j), val);
            }

            boolean result = parser.evaluate(expression, inputs);
            table.add(new TestCase(i + 1, inputs, result));
        }
        return table;
    }

    public List<TestCase> generateCACC(String expression) {
        List<String> vars = extractVariables(expression);
        List<TestCase> fullTable = generateCoC(expression);
        Set<Integer> requiredIds = new TreeSet<>();

        for (String major : vars) {
            for (int i = 0; i < fullTable.size(); i++) {
                for (int j = i + 1; j < fullTable.size(); j++) {
                    TestCase t1 = fullTable.get(i);
                    TestCase t2 = fullTable.get(j);

                    if (t1.getInputs().get(major) == t2.getInputs().get(major)) continue;

                    boolean minorsMatch = true;
                    for (String var : vars) {
                        if (var.equals(major)) continue;
                        if (!t1.getInputs().get(var).equals(t2.getInputs().get(var))) {
                            minorsMatch = false;
                            break;
                        }
                    }
                    if (!minorsMatch) continue;
                    if (t1.isResult() != t2.isResult()) {
                        requiredIds.add(t1.getId());
                        requiredIds.add(t2.getId());
                    }
                }
            }
        }

        return fullTable.stream()
                .filter(tc -> requiredIds.contains(tc.getId()))
                .collect(Collectors.toList());
    }
    public String generateJUnitCode(String expression, String type) {
        List<TestCase> testCases;
        if ("coc".equalsIgnoreCase(type)) {
            testCases = generateCoC(expression);
        } else {
            testCases = generateCACC(expression);
        }

        List<String> vars = extractVariables(expression);
        StringBuilder sb = new StringBuilder();

        sb.append("/**\n");
        sb.append(" * Generated JUnit 5 Tests\n");
        sb.append(" * Coverage Type: ").append(type.toUpperCase()).append("\n");
        sb.append(" * Expression: ").append(expression).append("\n");
        sb.append(" */\n");

        sb.append("import org.junit.jupiter.api.Test;\n");
        sb.append("import static org.junit.jupiter.api.Assertions.*;\n\n");
        sb.append("public class LogicTest_" + type.toUpperCase() + " {\n\n");
        sb.append("    public boolean checkLogic(");
        for (int i = 0; i < vars.size(); i++) {
            sb.append("boolean ").append(vars.get(i));
            if (i < vars.size() - 1) sb.append(", ");
        }
        sb.append(") {\n        return false; // TODO: Implement logic\n    }\n\n");

        for (TestCase tc : testCases) {
            sb.append("    @Test\n");
            sb.append("    void testCase").append(tc.getId()).append("() {\n");
            String assertion = tc.isResult() ? "assertTrue" : "assertFalse";
            sb.append("        ").append(assertion).append("( checkLogic(");
            for (int i = 0; i < vars.size(); i++) {
                sb.append(tc.getInputs().get(vars.get(i)));
                if (i < vars.size() - 1) sb.append(", ");
            }
            sb.append(") );\n    }\n\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
}