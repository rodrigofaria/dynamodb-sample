package br.com.rodrigoluisfaria.dynamodb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDTO {

    private Integer year;
    private String title;
    private MovieInformationDTO info;
}
