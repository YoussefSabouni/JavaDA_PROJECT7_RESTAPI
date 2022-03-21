package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {

        this.ratingRepository = ratingRepository;
    }

    public List<Rating> findAll() {

        return this.ratingRepository.findAll();
    }

    public Rating findById(Integer id) {

        return this.ratingRepository.findById(id)
                                    .orElseThrow(() -> new NoSuchElementException("Invalid rating Id:" + id));
    }

    public Rating save(Rating rating) {

        return this.ratingRepository.save(rating);
    }

    public void deleteById(Integer id) {

        if (!this.ratingRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid rating Id:" + id);
        }

        this.ratingRepository.deleteById(id);
    }
}
