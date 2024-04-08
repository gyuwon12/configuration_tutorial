package com.database.mongodb;

//import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Movie findByMovieId(String movieId); // Assuming movieId is unique
}