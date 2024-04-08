package com.database.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

@RestController
//@RequestMapping("/api")
public class DatabaseController {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public DatabaseController(RatingRepository ratingRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
    }

    // GET endpoint to retrieve movies with ratings greater than or equal to the given rating
    @GetMapping("/ratings/{rating}")
    public ResponseEntity<?> getMoviesWithAverageRatingGreaterThanOrEqual(@PathVariable int rating) {
        if (rating < 1 || rating > 5) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "The ratings are only available on a scale of 1 to 5");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        long count = ratingRepository.count();
        System.out.println("Total number of ratings in the database: " + count);

        // Gets all ratings
        List<Rating> allRatings = ratingRepository.findAll();

        System.out.println("Total number of ratings: " + allRatings.size());

        // Group by MovieID and calculate the average rating for each movie
        Map<String, Double> averageRatings = allRatings.stream()
            .collect(Collectors.groupingBy(Rating::getMovieId, Collectors.averagingInt(Rating::getRating)));

        // 체킹을 위해
        System.out.println("Total number of movies with ratings: " + averageRatings.size());

        // Filters the IDs of movies with average ratings or higher
        Set<String> movieIds = averageRatings.entrySet().stream()
            .filter(entry -> entry.getValue() >= rating)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
        
        // 체킹을 위해
        System.out.println("Total number of avg >=4 : " + movieIds.size());

        // Find the movie information
        List<Movie> filteredMovies = movieRepository.findAllById(movieIds);

        System.out.println("Total number of FINAL movie : " + filteredMovies.size());
        System.out.println("==================================================================================");

        // Returns filtered movie information
        List<Map<String, String>> moviesInfo = filteredMovies.stream()
            .map(movie -> {
                Map<String, String> movieInfo = new HashMap<>();
                movieInfo.put("title", movie.getTitle());
                movieInfo.put("genres", movie.getGenres());
                return movieInfo;
            })
            .collect(Collectors.toList());
        
        return new ResponseEntity<>(moviesInfo, HttpStatus.OK);
    }

    
    // POST endpoint to add a new movie to the database
    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        long count = movieRepository.count();
        System.out.println("Total number of movies in the database before POST: " + count);

        Movie savedMovie = movieRepository.save(movie);

        count = movieRepository.count();
        System.out.println("Total number of movies in the database after POST: " + count);
        System.out.println("==================================================================================");
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }
    
    // PUT endpoint to update an existing movie
    @PutMapping("/movies/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieId, @RequestBody Movie updatedMovieDetails) {
        return movieRepository.findById(movieId)
            .map(movie -> {
                //movie.setMovieId(movieId);
                movie.setTitle(updatedMovieDetails.getTitle());
                movie.setGenres(updatedMovieDetails.getGenres());
                Movie updatedMovie = movieRepository.save(movie);
                return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    // More endpoints can be added below for User and other operations as needed

}
