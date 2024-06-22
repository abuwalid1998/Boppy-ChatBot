package com.boopyapi.boppy.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.boopyapi.boppy.Model.Question;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/boppy")
public class ModelController {

    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

    @PostMapping("/analyze")
    public String analyzeText(@RequestBody Question question) {
        // Log the incoming question
        logger.debug("Received question: {}", question);
        System.out.println("Received question: : "+ question.getText());

        // Process the question
        String text = question.getText();
        
        // Call the Python script (or any other processing logic)
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "BankofPalesintChatbot\\API\\boppy\\src\\main\\java\\com\\boopyapi\\boppy\\Scripts\\script.py", text);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                System.out.println(line);
            }
            return result.toString();
        } catch (Exception e) {
            logger.error("Error occurred while analyzing text", e);
            return "Error occurred while analyzing text";
        }
    }


    // @Value("${huggingface.api.token}")
    // private String apiToken;

    @PostMapping("/askme")
    public String AskMe(@RequestBody Question question) {
        String text = question.getText();

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "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");

        // Set up request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("inputs", text);

        // Create HttpEntity
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // Make the request to Hugging Face API
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api-inference.huggingface.co/models/AmjadKha/Boppy",
                HttpMethod.POST,
                entity,
                String.class
        );

               // Extract the generated text from the response
        String generatedText = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.getBody());
            JsonNode generatedTextNode = rootNode.get(0).path("generated_text");
            generatedText = generatedTextNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while parsing response";
        }

        return generatedText;
    }

}
