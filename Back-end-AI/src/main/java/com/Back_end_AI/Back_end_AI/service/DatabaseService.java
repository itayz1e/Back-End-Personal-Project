package com.Back_end_AI.Back_end_AI.service;

import com.Back_end_AI.Back_end_AI.model.DatabaseParams;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    public Map<String, Object> getDatabaseSchema(DatabaseParams params) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> columns = new ArrayList<>();

        // Extract the database name from the URL
        String databaseName = extractDatabaseName(params.getUrl());

        try (Connection connection = DriverManager.getConnection(params.getUrl(), params.getUsername(), params.getPassword());
             Statement statement = connection.createStatement()) {

            // Modify the query to filter based on the extracted database name
            String query = "SELECT table_schema, table_name, column_name, data_type " +
                    "FROM information_schema.columns " +
                    "WHERE table_schema NOT IN ('pg_catalog', 'information_schema')";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Map<String, String> column = new HashMap<>();
                column.put("table_schema", resultSet.getString("table_schema"));
                column.put("table_name", resultSet.getString("table_name"));
                column.put("column_name", resultSet.getString("column_name"));
                column.put("data_type", resultSet.getString("data_type"));
                columns.add(column);
            }
            result.put("columns", columns);

        } catch (Exception e) {
            result.put("error", "Error retrieving database schema: " + e.getMessage());
        }

        return result;
    }

    private String extractDatabaseName(String url) {

        if (url == null || !url.contains("/")) {
            throw new IllegalArgumentException("Invalid URL format: URL must contain a '/' character.");
        }

        // Extract the database name from the URL
        String[] urlParts = url.split("/");
        // Check if there is at least one part after the last '/'
        if (urlParts.length < 2) {
            throw new IllegalArgumentException("Invalid URL format: URL does not contain a database name.");
        }
        return urlParts[urlParts.length - 1];
    }


}

