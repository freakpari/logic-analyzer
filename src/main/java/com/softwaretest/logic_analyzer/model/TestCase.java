package com.softwaretest.logic_analyzer.model;

import java.util.Map;

public class TestCase {
    private int id;
    private Map<String, Boolean> inputs;
    private boolean result;

    public TestCase(int id, Map<String, Boolean> inputs, boolean result) {
        this.id = id;
        this.inputs = inputs;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public Map<String, Boolean> getInputs() {
        return inputs;
    }

    public boolean isResult() {
        return result;
    }

    @Override
    public String toString() {
        return "TestCase{id=" + id + ", inputs=" + inputs + ", result=" + result + "}";
    }
}