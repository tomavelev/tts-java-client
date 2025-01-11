package com.programtom.tts_java_client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class SampleExecutor implements CommandLineRunner {

    final
    AudioGeneratorService audioGeneratorService;

    public SampleExecutor(AudioGeneratorService audioGeneratorService) {
        this.audioGeneratorService = audioGeneratorService;
    }

    @Override
    public void run(String... args) throws Exception {


        String fileName = "hello_world4.wav";
        try (InputStream inputStream = audioGeneratorService.generateAudio("Здравей красив свят", fileName, Language.bulgarian)) {
            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                // Copy the input stream to the output file
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error writing to output file: " + e.getMessage());
            }

        }

        audioGeneratorService.deleteFile(fileName);
        System.out.println("Hello World");
    }
}
