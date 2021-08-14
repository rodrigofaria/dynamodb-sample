package br.com.rodrigoluisfaria.dynamodb.controller;

import br.com.rodrigoluisfaria.dynamodb.dto.MovieDTO;
import br.com.rodrigoluisfaria.dynamodb.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public MovieDTO create(@RequestBody MovieDTO movieDTO) {
        return movieService.create(movieDTO);
    }

}
