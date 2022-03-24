package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTests {

    @Autowired
    private RatingService ratingService;

    @Test
    public void ratingTest() {

        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Save
        rating = ratingService.save(rating);
        Assert.assertNotNull(rating.getId());
        Assert.assertEquals(10, (int) rating.getOrderNumber());

        // Update
        rating.setOrderNumber(20);
        rating = ratingService.save(rating);
        Assert.assertEquals(20, (int) rating.getOrderNumber());

        // Find
        List<Rating> listResult = ratingService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingService.deleteById(id);
        Assertions.assertThrows(NoSuchElementException.class, () -> ratingService.findById(id));
    }
}
