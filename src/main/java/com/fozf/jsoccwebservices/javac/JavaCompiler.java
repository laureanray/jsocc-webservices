package com.fozf.jsoccwebservices.javac;

import com.fozf.jsoccwebservices.domain.CompileTask;

public class JavaCompiler {
    private OnCompileFinishListener listener;

    public void registerOnCompileListener(OnCompileFinishListener listener){
        this.listener = listener;
    }

    public JavaCompiler Compile(CompileTask compileTask){
        new Thread(() -> {
            if(listener != null){
                listener.onCompile(compileTask);
                return;
            }
        }).start();
        return this;
    }
}
