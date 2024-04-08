package com.database.mongodb;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class DataLoaderService {

    private static final int BATCH_SIZE = 1000;  // batch define

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public void loadMovies() throws IOException, CsvValidationException {
        try (CSVReader csvReader = new CSVReader(new FileReader("/root/project/milestone1/data/movies.csv"))) {
            String[] values;
            List<Movie> movies = new ArrayList<>();
            while ((values = csvReader.readNext()) != null) {
                Movie movie = new Movie();
                movie.setMovieId(values[0]);
                movie.setTitle(values[1]);
                movie.setGenres(values[2]);
                movies.add(movie);

                if (movies.size() == BATCH_SIZE) {
                    movieRepository.saveAll(movies);
                    movies.clear();
                }
            }
            if (!movies.isEmpty()) {
                movieRepository.saveAll(movies);
            }
        }
    }

    public void loadRatings() throws IOException, CsvValidationException {
        try (CSVReader csvReader = new CSVReader(new FileReader("/root/project/milestone1/data/ratings.csv"))) {
            String[] values;
            List<Rating> ratings = new ArrayList<>();
            while ((values = csvReader.readNext()) != null) {
                Rating rating = new Rating();
                rating.setUserId(values[0]);
                rating.setMovieId(values[1]);
                rating.setRating(Integer.parseInt(values[2]));
                rating.setTimestamp(Long.parseLong(values[3]));
                ratings.add(rating);

                if (ratings.size() == BATCH_SIZE) {
                    ratingRepository.saveAll(ratings);
                    ratings.clear();
                }
            }
            if (!ratings.isEmpty()) {
                ratingRepository.saveAll(ratings);
            }
        }
    }

    public void loadUsers() throws IOException, CsvValidationException {
        try (CSVReader csvReader = new CSVReader(new FileReader("/root/project/milestone1/data/users.csv"))) {
            String[] values;
            List<User> users = new ArrayList<>();
            while ((values = csvReader.readNext()) != null) {
                User user = new User();
                user.setUserId(values[0]);
                user.setGender(values[1]);
                user.setAge(Integer.parseInt(values[2]));
                user.setOccupation(values[3]);
                user.setZipCode(values[4]);
                users.add(user);

                if (users.size() == BATCH_SIZE) {
                    userRepository.saveAll(users);
                    users.clear();
                }
            }
            if (!users.isEmpty()) {
                userRepository.saveAll(users);
            }
        }
    }
}
