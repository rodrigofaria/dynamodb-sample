package br.com.rodrigoluisfaria.dynamodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> toMap() {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("release_date", this.releaseDate);
        infoMap.put("rating", this.rating);
        infoMap.put("image_url", this.imageUrl);
        infoMap.put("plot", this.plot);
        infoMap.put("rank", this.rank);
        infoMap.put("running_time_secs", this.runningTimeSecs);
        infoMap.put("genres", this.genres);
        infoMap.put("directors", this.directors);
        infoMap.put("actors", this.actors);
        return infoMap;
    }
}
