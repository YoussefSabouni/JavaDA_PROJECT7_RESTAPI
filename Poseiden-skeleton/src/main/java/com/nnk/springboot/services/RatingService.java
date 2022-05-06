package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Intended to contain the business code for {@link Rating}.
 */
@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    /**
     * Create a new instance of this {@link RatingService}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param ratingRepository
     *         instance of {@link RatingRepository}.
     */
    public RatingService(RatingRepository ratingRepository) {

        this.ratingRepository = ratingRepository;
    }

    /**
     * Return the list of all {@link Rating} in database
     *
     * @return a {@link List} of {@link Rating}
     */
    public List<Rating> findAll() {

        return this.ratingRepository.findAll();
    }

    /**
     * Search for a {@link Rating} by an id.
     *
     * @param id
     *         internal identifier of a {@link Rating}.
     *
     * @return an {@link Rating} or throw {@link NoSuchElementException}.
     */
    public Rating findById(Integer id) {

        return this.ratingRepository.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("Invalid rating Id:" + id));
    }

    /**
     * Requests the database registration of a {@link Rating}.
     *
     * @param rating
     *         to be registered.
     *
     * @return {@link Rating}registered.
     */
    public Rating save(Rating rating) {

        return this.ratingRepository.save(rating);
    }

    /**
     * Request the deletion of a {@link Rating}
     *
     * @param id
     *         matches the {@link Rating} to be deleted
     */
    public void deleteById(Integer id) {

        if (!this.ratingRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid rating Id:" + id);
        }

        this.ratingRepository.deleteById(id);
    }
}
