package br.com.rodrigoluisfaria.dynamodb.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieInformationDTO {

    private String releaseDate;
    private Double rating;
    private String imageUrl;
    private String plot;
    private Integer rank;
    private Integer runningTimeSecs;
    private List<String> genres;
    private List<String> directors;
    private List<String> actors;
}
