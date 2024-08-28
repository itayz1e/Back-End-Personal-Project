package com.Back_end_AI.Back_end_AI.service;

import com.Back_end_AI.Back_end_AI.model.DatabaseParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class DatabaseService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SCHEMA_INFO_KEY = "schema_info";

    public Map<String, Object> getDatabaseSchema(DatabaseParams params) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> columns = new ArrayList<>();

        try {
            // שליפת מבנה הנתונים מה-DB ושמירה ל-Redis
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

                logger.info("Adding schema info: {}.{}.{} ({}) to Redis", tableSchema, tableName, columnName, dataType);
            }

            // שמירת מבנה הנתונים כולו ב-Redis
            redisTemplate.opsForValue().set(SCHEMA_INFO_KEY, columns);
            result.put("columns", columns);
            logger.info("Schema retrieval complete. Total columns: {}", columns.size());

        } catch (Exception e) {
            logger.error("Error retrieving or saving database schema", e);
            result.put("error", "Error retrieving or saving database schema: " + e.getMessage());
        }

        return result;
    }

    public DatabaseParams getDatabaseParamsFromRedis(String key) {
        return (DatabaseParams) redisTemplate.opsForValue().get(key);
    }

    public void saveDatabaseParamsToRedis(DatabaseParams params) {
        try {
            String redisKey = "db_params";
            redisTemplate.opsForValue().set(redisKey, params);
            logger.info("Database parameters saved to Redis");
        } catch (Exception e) {
            logger.error("Error saving database parameters to Redis", e);
            throw e; // throwing exception to be handled by the controller
        }
    }
}
