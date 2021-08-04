package br.com.rodrigoluisfaria.dynamodb.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@Slf4j
public class DataBaseConfig {

    @Value("${dynamodb.endpoint}")
    private String endpoint;

    @PostConstruct
    public void initialize() {
        DynamoDB dynamoDB = new DynamoDB(createAmazonDynamoDBClient());
        String tableName = "movie";

        try {
            log.info("Creating table: {}", tableName);

            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("year", KeyType.HASH),
                            new KeySchemaElement("title", KeyType.RANGE)),
                    Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));

            table.waitForActive();
            log.info("Success. Table status: {}", table.getDescription().getTableStatus());
        } catch (Exception e) {
            log.error("Unable to create table: {}", e.getMessage());
        }
    }

    private AmazonDynamoDB createAmazonDynamoDBClient() {
        log.info("Creating DynamoDB client");
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(
                        endpoint, null);

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
}
