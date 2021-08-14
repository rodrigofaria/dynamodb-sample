package br.com.rodrigoluisfaria.dynamodb.dto;

import lombok.Data;

@Data
public class MovieDTO {

    private String year;
    private String title;
    private MovieInformationDTO info;
}
