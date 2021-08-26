package br.com.rodrigoluisfaria.dynamodb.repository;

import br.com.rodrigoluisfaria.dynamodb.dto.MovieDTO;
import br.com.rodrigoluisfaria.dynamodb.mapper.MovieMapper;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class MovieRepository {

    @Value("${dynamodb.endpoint}")
    private String endpoint;

    private DynamoDB dynamoDB;

    @PostConstruct
    public void initialize() {
        AmazonDynamoDB client = createAmazonDynamoDBClient();
        dynamoDB = new DynamoDB(client);
    }

    public MovieDTO create(MovieDTO movieDTO) {
        Table table = dynamoDB.getTable("movie");

        table.putItem(new Item()
                .withPrimaryKey(
                        "year", movieDTO.getYear(),
                        "title", movieDTO.getTitle())
                .withMap("info", movieDTO.getInfo().toMap()));

        return movieDTO;
    }

    public MovieDTO findMovie(Integer year, String title) {
        Table table = dynamoDB.getTable("movie");

        GetItemSpec getItemSpec = new GetItemSpec()
                .withPrimaryKey("year", year, "title", title);
        Item resultItem = table.getItem(getItemSpec);
        return resultItem != null ? MovieMapper.toMovieDTO(resultItem) : null;
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
