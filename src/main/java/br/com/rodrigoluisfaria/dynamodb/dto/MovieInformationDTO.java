package br.com.rodrigoluisfaria.dynamodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MovieInformationDTO {

    @JsonProperty("release_date")
    private String releaseDate;
    private Double rating;
    @JsonProperty("image_url")
    private String imageUrl;
    private String plot;
    private Integer rank;
    @JsonProperty("running_time_secs")
    private Integer runningTimeSecs;
    private List<String> genres;
    private List<String> directors;
    private List<String> actors;
}
