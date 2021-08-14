package br.com.rodrigoluisfaria.dynamodb.service;

import br.com.rodrigoluisfaria.dynamodb.dto.MovieDTO;

public interface MovieService {

    MovieDTO create(MovieDTO movieDTO);
}
