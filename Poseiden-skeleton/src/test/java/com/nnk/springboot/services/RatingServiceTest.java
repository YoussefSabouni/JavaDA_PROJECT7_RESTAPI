package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RatingService.class})
@ExtendWith(SpringExtension.class)
public class RatingServiceTest {

    @Autowired
    private RatingService    ratingService;
    @MockBean
    private RatingRepository ratingRepository;
    private Rating           rating;

    @BeforeEach
    private void setUpPerTest() {

        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        Mockito.reset(ratingRepository);
    }

    @org.junit.jupiter.api.Test
    public void saveRating_shouldReturnRatingSaved_forCorrectRating() {

        Rating ratingSaved = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        ratingSaved.setId(1);
        when(ratingRepository.save(eq(rating))).thenReturn(ratingSaved);

        rating = ratingService.save(rating);
        verify(ratingRepository, times(1)).save(any());
        Assertions.assertEquals(ratingSaved, rating);
    }

    @org.junit.jupiter.api.Test
    public void updateRating_shouldReturnRatingUpdated_forCorrectRating() {

        Rating ratingUpdated = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 20);
        ratingUpdated.setId(1);

        rating.setId(1);
        rating.setOrderNumber(20);

        when(ratingRepository.save(eq(rating))).thenReturn(ratingUpdated);

        rating = ratingService.save(rating);

        verify(ratingRepository, times(1)).save(any());
        Assertions.assertEquals(ratingUpdated, rating);
    }

    @org.junit.jupiter.api.Test
    public void findAll_shouldReturnAllRatingInDatabase() {

        rating.setId(1);
        List<Rating> ratings = new ArrayList<Rating>() {{this.add(rating);}};

        for (int i = 0 ; i < 4 ; i++) {

            rating.setId(rating.getId() + 1);
            ratings.add(rating);
        }

        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> listResult = ratingService.findAll();

        Assertions.assertEquals(ratings.size(), listResult.size());
        Assertions.assertTrue(listResult.contains(rating));
        verify(ratingRepository, times(1)).findAll();
    }

    @org.junit.jupiter.api.Test
    public void findById_shouldReturnOneRating_whenRatingIdIsExists() {

        rating.setId(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating ratingFound = ratingService.findById(rating.getId());

        verify(ratingRepository, times(1)).findById(any());
        Assertions.assertEquals(rating, ratingFound);
    }

    @org.junit.jupiter.api.Test
    public void findById_shouldThrowException_whenRatingIdIsNotExists() {

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> ratingService.findById(1));
        verify(ratingRepository, times(1)).findById(any());
    }

    @Test
    public void deleteById_shouldDeleteRating() {


        when(this.ratingRepository.findById(eq(1))).thenReturn(Optional.of(rating));
        Integer id = 1;
        ratingService.deleteById(id);

        when(this.ratingRepository.findById(eq(1))).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> ratingService.deleteById(id));
        verify(this.ratingRepository, times(2)).findById(1);
    }
}
