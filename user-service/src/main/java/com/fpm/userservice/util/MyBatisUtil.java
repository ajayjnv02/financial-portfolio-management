package com.fpm.userservice.util;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // Load MyBatis global config (mybatis-config.xml)
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);

            // Create a data source from environment variables
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPass = System.getenv("DB_PASS");

            // For MySQL: driver = "com.mysql.cj.jdbc.Driver"
            PooledDataSource dataSource = new PooledDataSource(
                    "com.mysql.cj.jdbc.Driver",
                    dbUrl,
                    dbUser,
                    dbPass
            );

            // Build configuration programmatically
            Environment environment = new Environment(
                    "development",
                    new JdbcTransactionFactory(),
                    dataSource
            );

            org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration(environment);
            // If we want to let MyBatis know about mappers
            // E.g. config.addMapper(UserMapper.class);

            // Optional: If we want to use the XML-based mapper
            // we rely on <mapper resource="..."/> lines in mybatis-config.xml or do it here.

            // Parse the mybatis-config.xml settings
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            sqlSessionFactory = builder.build(config);

            // Optionally, incorporate the global settings from the XML
            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);

            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Error loading MyBatis configuration", e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
