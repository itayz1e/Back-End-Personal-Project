package com.Back_end_AI.Back_end_AI.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;


@Service
public class AiService {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String testDatabaseConnection() {
        DataSource dataSource = new DriverManagerDataSource(dbUrl, dbUsername, dbPassword);
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                return "Connection to the database is successful!";
            } else {
                return "Failed to connect to the database.";
            }
        } catch (SQLException e) {
            // טיפול בשגיאות
            return "Error connecting to the database: " + e.getMessage();
        }
    }

    public String generateSQLQuery(String userInput) throws IOException {
        String prompt = "Please generate a PostgreSQL SQL query based on the following request: \"" + userInput + "\". " +
                "use the table `schema_info` . " +
                "Return only the SQL query." +
                "Return only answer in English" +
                "Ensure the query is syntactically correct and optimized.";

        return sendToChatGPT(prompt);
    }

    public String sendToChatGPT(String prompt) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + openaiApiKey);
        conn.setDoOutput(true);

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", "gpt-3.5-turbo");
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
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
        JSONArray choicesArray = responseObject.getJSONArray("choices");
        if (choicesArray.length() > 0) {
            JSONObject firstChoice = choicesArray.getJSONObject(0);
            if (firstChoice.has("message") && firstChoice.getJSONObject("message").has("content")) {
                return firstChoice.getJSONObject("message").getString("content").trim();
            }
        }
        return "No content found in the response.";
    }

    public void saveCallHistory(String question, String response) {
        String sql = "INSERT INTO call_history (question, response) VALUES (?, ?)";
        jdbcTemplate.update(sql, question, response);
    }
}
