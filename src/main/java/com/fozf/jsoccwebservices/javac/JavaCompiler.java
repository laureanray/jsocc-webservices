package com.fozf.jsoccwebservices.javac;
import com.fozf.jsoccwebservices.domain.CompileTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCompiler {
    private OnCompileFinishListener listener;

    final String JAVAC_PATH = "./jdk/bin/javac.exe";
    final String JAVA_PATH = "./jdk/bin/java.exe";
    final String SOURCE_PATH = "./temp/src/";
    final String CLASS_PATH = "./temp/gen/";
    final String FILENAME =  "Solution";
    final String FILENAME_E = "Solution.java";


    public void registerOnCompileListener(OnCompileFinishListener listener){
        this.listener = listener;
    }

    public JavaCompiler Compile(CompileTask compileTask){
        new Thread(() -> {
            if(listener != null){
                // Settle the filename first



                // Before compiling write src code in the temp directory w/ unique filename
                Path path = Paths.get(SOURCE_PATH.concat(FILENAME_E));
                try {
                    Files.write(path, compileTask.getSource().getBytes());
                } catch (IOException e) {
                    // Do not attempt the rest of this block since there is no file written.
                    e.printStackTrace();
                    return;
                }


                // Spawn process
                ProcessBuilder pb = new ProcessBuilder(JAVAC_PATH, "-nowarn", SOURCE_PATH.concat(FILENAME_E), "-d", CLASS_PATH);
                Process process = null;

                try {

                    pb.redirectErrorStream(true);
                    process = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    StringBuilder builder = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine()) != null) {
                        builder.append(line);
                        builder.append(System.getProperty("line.separator"));
                    }
                    process.waitFor();


                    if(builder.toString().isEmpty()){
                        compileTask.setResult("CALLED");
                    } else {
                        compileTask.setResult(builder.toString());
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                listener.onCompile(compileTask);


                return;
            }
        }).start();
        return this;
    }

    public JavaCompiler Run(String className) {
        new Thread(() -> {

        }).start();

        return this;
    }
}
