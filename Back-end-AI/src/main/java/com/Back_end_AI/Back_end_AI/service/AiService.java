package com.Back_end_AI.Back_end_AI.service;

import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class AiService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String getAPIResponse(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        return content.toString();
    }

    public String sendToChatGPT(String jsonResponse, String userInput) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + openaiApiKey);
        conn.setDoOutput(true);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", "gpt-4");
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", "קיבלתי את הנתונים הבאים מה-API: " + jsonResponse + "\n" + userInput);
        messages.put(message);
        jsonBody.put("messages", messages);

        OutputStream os = conn.getOutputStream();
        byte[] input = jsonBody.toString().getBytes("utf-8");
        os.write(input, 0, input.length);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = in.readLine()) != null) {
            response.append(responseLine.trim());
        }
        in.close();
        conn.disconnect();

        JSONObject responseObject = new JSONObject(response.toString());
        return responseObject.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
    }

    public String checkDatabaseConnection(String dbUrl, String username, String password) {
        Connection connection = null;
        String resultMessage = "";
        try {

            connection = DriverManager.getConnection(dbUrl, username, password);
            resultMessage = "Successfully connected to the database!";
        } catch (SQLException e) {
            resultMessage = "Failed to connect to the database: " + e.getMessage();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Handle connection close failure if needed
                }
            }
        }

        try {
            // Send the result message to CHATGPT for analysis
            String chatGPTResponse = sendToChatGPT(resultMessage, "Database connection attempt");
            return chatGPTResponse;
        } catch (IOException e) {
            // Handle error while sending data to CHATGPT
            return "Error while sending data to CHATGPT: " + e.getMessage();
        }
    }
}
