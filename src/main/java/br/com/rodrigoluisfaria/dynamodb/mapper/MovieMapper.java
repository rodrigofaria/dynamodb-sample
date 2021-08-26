package br.com.rodrigoluisfaria.dynamodb.mapper;

import br.com.rodrigoluisfaria.dynamodb.dto.MovieDTO;
import com.amazonaws.services.dynamodbv2.document.Item;

public interface MovieMapper {

    static MovieDTO toMovieDTO(Item item) {
        return MovieDTO.builder()
                .title(item.getJSON("title"))
                .year(item.getInt("year"))
                .build();
    }
}
