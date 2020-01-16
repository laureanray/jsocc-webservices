package com.fozf.jsoccwebservices.javac;


import com.fozf.jsoccwebservices.domain.CompileTask;

public interface OnCompileFinishListener {
    void onCompile(CompileTask compileTask);
}
