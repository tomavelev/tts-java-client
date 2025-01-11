package com.programtom.tts_java_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class AudioGeneratorService {

    @Value("${flask.server}")
    String flaskServerUrl;

    @Autowired
    private RestTemplate restTemplate;


    public InputStream generateAudio(String text, String fileName, Language language) throws IOException {
        Resource resource = restTemplate.postForObject(flaskServerUrl + "/generate-audio",
                Map.of("text", text, "fileName", fileName, "language", language.code()), Resource.class);


        assert resource != null;
        return resource.getInputStream();
    }

    public void deleteFile(String fileName) {
        restTemplate.postForObject(flaskServerUrl + "/delete_file",
                Map.of("fileName", fileName), String.class);
        System.out.println("File deleted successfully");
    }

    public void cleanResources() {
        restTemplate.postForObject(flaskServerUrl + "/clean_resources", null, String.class);
        System.out.println("Resources cleaned successfully");
    }

}