// RatingRepository.java
package com.database.mongodb;

//import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
    //List<Rating> findByRatingGreaterThanEqual(int rating);
    // Additional methods if needed
}
