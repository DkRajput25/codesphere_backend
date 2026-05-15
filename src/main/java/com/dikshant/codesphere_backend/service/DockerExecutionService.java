package com.dikshant.codesphere_backend.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DockerExecutionService {

    public Map<String, Object> runCode(String code, String language, String input) {

        Map<String, Object> result = new HashMap<>();

        try {

            Path tempDir = Files.createTempDirectory("code");

            File sourceFile = null;
            String command = "";

            switch(language.toLowerCase()) {

                case "python":
                    sourceFile = new File(tempDir.toFile(),"main.py");
                    Files.writeString(sourceFile.toPath(),code);

                    command = "docker run -i --rm -v "+tempDir+":/app python-runner python /app/main.py";
                    break;

                case "java":

                    String className = "Main";

                    Pattern pattern = Pattern.compile("(?:public\\s+)?class\\s+(\\w+)");
                    Matcher matcher = pattern.matcher(code);

                    if (matcher.find()) {
                        className = matcher.group(1);
                    }

                    sourceFile = new File(tempDir.toFile(), className + ".java");
                    Files.writeString(sourceFile.toPath(),code);

                    command = "docker run -i --rm -v "+tempDir+":/app java-runner bash -c \"javac /app/"+className+".java && java -cp /app "+className+"\"";
                    break;

                case "cpp":
                    sourceFile = new File(tempDir.toFile(),"main.cpp");
                    Files.writeString(sourceFile.toPath(),code);

                    command = "docker run -i --rm -v "+tempDir+":/app cpp-runner bash -c \"g++ /app/main.cpp -o /app/main && /app/main\"";
                    break;

                case "c":
                    sourceFile = new File(tempDir.toFile(),"main.c");
                    Files.writeString(sourceFile.toPath(),code);

                    command = "docker run -i --rm -v "+tempDir+":/app c-runner bash -c \"gcc /app/main.c -o /app/main && /app/main\"";
                    break;

                case "javascript":
                    sourceFile = new File(tempDir.toFile(),"main.js");
                    Files.writeString(sourceFile.toPath(),code);

                    command = "docker run -i --rm -v "+tempDir+":/app js-runner node /app/main.js";
                    break;

                default:
                    result.put("error","Language not supported");
                    return result;
            }

            ProcessBuilder pb = new ProcessBuilder("cmd","/c",command);
            Process process = pb.start();

            // Send user input to program
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

            if(input != null && !input.isEmpty()){
                writer.write(input);
            }

            writer.flush();
            writer.close();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader errorReader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            String line;

            while((line = reader.readLine())!=null){
                output.append(line).append("\n");
            }

            while((line = errorReader.readLine())!=null){
                output.append(line).append("\n");
            }

            result.put("stdout",output.toString());

        } catch(Exception e){

            result.put("stderr",e.getMessage());

        }

        return result;
    }
}