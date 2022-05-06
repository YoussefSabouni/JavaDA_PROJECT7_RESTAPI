package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Layer for the {@link Rating}. It's managed here by the JPA interface.
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
