package br.com.rodrigoluisfaria.dynamodb.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

@Component
@Slf4j
public class DataBaseConfig {

    @Value("${dynamodb.endpoint}")
    private String endpoint;

    @PostConstruct
    public void initialize() {
        AmazonDynamoDB dynamoDBClient = createAmazonDynamoDBClient();
        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);

        try {
            Table table = createTable(dynamoDB);
            populateTable(table);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
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

    private Table createTable(DynamoDB dynamoDB) throws InterruptedException {
        String tableName = "movie";
        log.info("Creating table: {}", tableName);

        Table table = dynamoDB.createTable(tableName,
                Arrays.asList(new KeySchemaElement("year", KeyType.HASH),
                        new KeySchemaElement("title", KeyType.RANGE)),
                Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                        new AttributeDefinition("title", ScalarAttributeType.S)),
                new ProvisionedThroughput(10L, 10L));

        table.waitForActive();
        log.info("Success. Table status: {}", table.getDescription().getTableStatus());
        return table;
    }

    private void populateTable(Table table) throws IOException {
        log.info("Populating table...");

        InputStream inputStream = this.getClass().getResourceAsStream("/movies/moviedata.json");
        JsonParser parser = new JsonFactory().createParser(inputStream);

        JsonNode rootNode = new ObjectMapper().readTree(parser);
        Iterator<JsonNode> iterator = rootNode.iterator();

        ObjectNode currentNode;
        while (iterator.hasNext()) {
            currentNode = (ObjectNode) iterator.next();

            int year = currentNode.path("year").asInt();
            String title = currentNode.path("title").asText();

            table.putItem(
                    new Item().withPrimaryKey("year", year, "title", title)
                            .withJSON("info", currentNode.path("info").toString()));
        }

        log.info("Table populated successfully!");
        parser.close();
    }
}
