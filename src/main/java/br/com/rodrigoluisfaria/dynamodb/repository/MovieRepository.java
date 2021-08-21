package br.com.rodrigoluisfaria.dynamodb.repository;

import br.com.rodrigoluisfaria.dynamodb.dto.MovieDTO;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

    @Value("${dynamodb.endpoint}")
    private String endpoint;

    public MovieDTO create(MovieDTO movieDTO) {
        AmazonDynamoDB client = createAmazonDynamoDBClient();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("movie");

        table.putItem(new Item()
                .withPrimaryKey(
                        "year", movieDTO.getYear(),
                        "title", movieDTO.getTitle())
                .withMap("info", movieDTO.getInfo().toMap()));

        return movieDTO;
    }

    private AmazonDynamoDB createAmazonDynamoDBClient() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(
                        endpoint, null);

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }
}
