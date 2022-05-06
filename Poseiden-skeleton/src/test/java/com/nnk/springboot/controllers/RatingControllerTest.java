package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    private static List<Rating>  ratings;
    @MockBean
    private        RatingService ratingService;
    @Autowired
    private        MockMvc       mockMvc;

    @BeforeAll
    public static void setUp() {

        ratings = new ArrayList<>();
        ratings.add(new Rating("moodysRating 1", "sandPRating 1", "fitchRating 1", 1) {{this.setId(1);}});
        ratings.add(new Rating("moodysRating 2", "sandPRating 2", "fitchRating 2", 2) {{this.setId(2);}});
        ratings.add(new Rating("moodysRating 3", "sandPRating 3", "fitchRating 3", 3) {{this.setId(3);}});
    }

    @BeforeEach
    public void setUpPerTest() {

        when(ratingService.findAll()).thenReturn(ratings);
        when(ratingService.findById(1)).thenReturn(ratings.get(0));
        when(ratingService.save(any())).thenReturn(ratings.get(0));
    }

    @Test
    @WithMockUser
    public void home_shouldViewWithReturnAllRating_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/rating/list"))
               .andExpect(view().name("rating/list"))
               .andExpect(model().attribute("ratings", ratings))
               .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void home_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/rating/list")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void addRatingForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/rating/add")).andExpect(view().name("rating/add")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void addRatingForm_shouldStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/rating/add")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void validateRatingForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        Rating rating = ratings.get(0);
        rating.setId(null);

        mockMvc.perform(post("/rating/validate").flashAttr("rating", rating).with(csrf()))
               .andExpect(view().name("redirect:/rating/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser
    public void validateRatingForm_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        Rating rating = new Rating(null, null, null, null);

        mockMvc.perform(post("/rating/validate").flashAttr("rating", rating).with(csrf()))
               .andExpect(view().name("rating/add"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void validateRatingForm_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/rating/validate").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void showUpdateForm_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/rating/update/1")).andExpect(status().isOk()).andExpect(view().name("rating/update"));
    }

    @Test
    @WithAnonymousUser
    public void showUpdateForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        Rating rating = ratings.get(0);
        when(ratingService.findById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void updateRating_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        Rating rating = ratings.get(0);
        rating.setId(null);

        mockMvc.perform(post("/rating/update/1").flashAttr("rating", rating).with(csrf()))
               .andExpect(view().name("redirect:/rating/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser
    public void updateRating_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        Rating rating = new Rating(null, null, null, null);

        mockMvc.perform(post("/rating/update/1").flashAttr("rating", rating).with(csrf()))
               .andExpect(view().name("rating/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void updateRating_shouldReturnView_whenUserIsAuthenticatedWithIdIsNotFound() throws Exception {

        Rating rating = new Rating(null, null, null, null);

        mockMvc.perform(post("/rating/update/6").flashAttr("rating", rating).with(csrf()))
               .andExpect(view().name("rating/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void updateRating_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/rating/update/1").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void deleteRating_shouldViewWithReturnAllRating_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/rating/delete/1"))
               .andExpect(view().name("redirect:/rating/list"))
               .andExpect(model().attribute("ratings", ratings))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void deleteRating_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/rating/delete/1")).andExpect(status().isUnauthorized());
    }
}