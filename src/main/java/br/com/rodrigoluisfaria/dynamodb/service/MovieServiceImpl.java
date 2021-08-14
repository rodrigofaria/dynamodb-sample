package br.com.rodrigoluisfaria.dynamodb.service;

import br.com.rodrigoluisfaria.dynamodb.dto.MovieDTO;
import br.com.rodrigoluisfaria.dynamodb.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieDTO create(MovieDTO movieDTO) {
        return movieRepository.create(movieDTO);
    }
}
