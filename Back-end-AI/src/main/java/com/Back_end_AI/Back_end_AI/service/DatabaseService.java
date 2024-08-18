package com.Back_end_AI.Back_end_AI.service;

import com.Back_end_AI.Back_end_AI.model.DatabaseParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class DatabaseService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getDatabaseSchema(DatabaseParams params) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> columns = new ArrayList<>();

        try {
            // Recreate the schema_info table
            recreateSchemaInfoTable();

            // Retrieve the schema information
            String query = "SELECT table_schema, table_name, column_name, data_type " +
                    "FROM information_schema.columns " +
                    "WHERE table_schema NOT IN ('pg_catalog', 'information_schema')";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

            logger.info("Retrieving schema information");
            for (Map<String, Object> row : rows) {
                String tableSchema = (String) row.get("table_schema");
                String tableName = (String) row.get("table_name");
                String columnName = (String) row.get("column_name");
                String dataType = (String) row.get("data_type");

                Map<String, String> column = new HashMap<>();
                column.put("table_schema", tableSchema);
                column.put("table_name", tableName);
                column.put("column_name", columnName);
                column.put("data_type", dataType);
                columns.add(column);

                logger.info("Inserting schema info: {}.{}.{} ({})", tableSchema, tableName, columnName, dataType);
                insertSchemaInfo(tableSchema, tableName, columnName, dataType);
            }
            result.put("columns", columns);
            logger.info("Schema retrieval complete. Total columns: {}", columns.size());

        } catch (Exception e) {
            logger.error("Error retrieving or saving database schema", e);
            result.put("error", "Error retrieving or saving database schema: " + e.getMessage());
        }

        return result;
    }

    private void recreateSchemaInfoTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS schema_info (" +
                "id SERIAL PRIMARY KEY, " +
                "table_schema VARCHAR(255), " +
                "table_name VARCHAR(255), " +
                "column_name VARCHAR(255), " +
                "data_type VARCHAR(255), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        jdbcTemplate.execute(createTableSQL);
        logger.info("schema_info table recreated successfully");
    }

    private void insertSchemaInfo(String tableSchema, String tableName, String columnName, String dataType) {
        String insertSQL = "INSERT INTO schema_info (table_schema, table_name, column_name, data_type) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertSQL, tableSchema, tableName, columnName, dataType);
        logger.info("Inserted schema info");
    }
}
