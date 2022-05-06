package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    private static List<BidList>  bidLists;
    @MockBean
    private        BidListService bidListService;
    @Autowired
    private        MockMvc        mockMvc;

    @BeforeAll
    public static void setUp() {

        bidLists = new ArrayList<>();
        bidLists.add(new BidList("Account 1", "Type 1", 1d) {{this.setBidListId(1);}});
        bidLists.add(new BidList("Account 2", "Type 2", 2d) {{this.setBidListId(2);}});
        bidLists.add(new BidList("Account 3", "Type 3", 3d) {{this.setBidListId(3);}});
    }

    @BeforeEach
    public void setUpPerTest() {

        when(bidListService.findAll()).thenReturn(bidLists);
        when(bidListService.findById(1)).thenReturn(bidLists.get(0));
        when(bidListService.save(any())).thenReturn(bidLists.get(0));
    }

    @Test
    @WithMockUser
    public void home_shouldViewWithReturnAllBidList_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/bidList/list"))
               .andExpect(view().name("bidList/list"))
               .andExpect(model().attribute("bidLists", bidLists))
               .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void home_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/bidList/list")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void addBidForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/bidList/add")).andExpect(view().name("bidList/add")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void addBidForm_shouldStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/bidList/add")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void validateBidForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        BidList bidList = bidLists.get(0);
        bidList.setBidListId(null);

        mockMvc.perform(post("/bidList/validate").flashAttr("bidList", bidList).with(csrf()))
               .andExpect(view().name("redirect:/bidList/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser
    public void validateBidForm_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        BidList bidList = new BidList(null, null, null);

        mockMvc.perform(post("/bidList/validate").flashAttr("bidList", bidList).with(csrf()))
               .andExpect(view().name("bidList/add"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void validateBidForm_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/bidList/validate").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void showUpdateForm_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/bidList/update/1")).andExpect(status().isOk()).andExpect(view().name("bidList/update"));
    }

    @Test
    @WithAnonymousUser
    public void showUpdateForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        BidList bidList = bidLists.get(0);
        when(bidListService.findById(1)).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void updateBid_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        BidList bidList = bidLists.get(0);
        bidList.setBidListId(null);

        mockMvc.perform(post("/bidList/update/1").flashAttr("bidList", bidList).with(csrf()))
               .andExpect(view().name("redirect:/bidList/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser
    public void updateBid_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        BidList bidList = new BidList(null, null, null);

        mockMvc.perform(post("/bidList/update/1").flashAttr("bidList", bidList).with(csrf()))
               .andExpect(view().name("bidList/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void updateBid_shouldReturnView_whenUserIsAuthenticatedWithIdIsNotFound() throws Exception {

        BidList bidList = new BidList(null, null, null);

        mockMvc.perform(post("/bidList/update/6").flashAttr("bidList", bidList).with(csrf()))
               .andExpect(view().name("bidList/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void updateBid_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/bidList/update/1").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void deleteBid_shouldViewWithReturnAllBidList_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/bidList/delete/1"))
               .andExpect(view().name("redirect:/bidList/list"))
               .andExpect(model().attribute("bidLists", bidLists))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void deleteBid_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/bidList/delete/1")).andExpect(status().isUnauthorized());
    }
}
