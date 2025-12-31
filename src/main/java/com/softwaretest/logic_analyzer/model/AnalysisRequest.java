package com.softwaretest.logic_analyzer.model;

public class AnalysisRequest {
    private String expression;
    private String type;
    public AnalysisRequest() {
    }

    public AnalysisRequest(String expression,String type) {
        this.expression = expression;
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}