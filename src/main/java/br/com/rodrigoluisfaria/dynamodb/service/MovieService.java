package br.com.rodrigoluisfaria.dynamodb.service;

import br.com.rodrigoluisfaria.dynamodb.dto.MovieDTO;

public interface MovieService {

    MovieDTO create(MovieDTO movieDTO);

    MovieDTO findMovie(Integer year, String title);
}
