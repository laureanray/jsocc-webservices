package com.fozf.jsoccwebservices.domain;

import javax.persistence.*;

public class CompileTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String source;
    private String result;
    private String[] input;
    private boolean compileResult;

    public String getCompileMessage() {
        return compileMessage;
    }

    public void setCompileMessage(String compileMessage) {
        this.compileMessage = compileMessage;
    }

    private String compileMessage;

    public String[] getInput() {
        return input;
    }

    public void setInput(String[] input) {
        this.input = input;
    }
    public boolean isCompileResult() {
        return compileResult;
    }

    public void setCompileResult(boolean compileResult) {
        this.compileResult = compileResult;
    }

    public CompileTask(String source, String result) {
        this.source = source;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return result;
    }
}
