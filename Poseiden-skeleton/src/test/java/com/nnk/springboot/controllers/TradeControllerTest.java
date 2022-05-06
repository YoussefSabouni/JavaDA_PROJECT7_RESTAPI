package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    private static List<Trade>  trades;
    @MockBean
    private        TradeService tradeService;
    @Autowired
    private        MockMvc      mockMvc;

    @BeforeAll
    public static void setUp() {

        trades = new ArrayList<>();
        trades.add(new Trade("Account 1", "Type 1") {{
            this.setTradeId(1);
            this.setBuyQuantity(1d);
        }});
        trades.add(new Trade("Account 2", "Type 2") {{
            this.setTradeId(2);
            this.setBuyQuantity(2d);
        }});
        trades.add(new Trade("Account 3", "Type 3") {{
            this.setTradeId(3);
            this.setBuyQuantity(3d);
        }});
    }

    @BeforeEach
    public void setUpPerTest() {

        when(tradeService.findAll()).thenReturn(trades);
        when(tradeService.findById(1)).thenReturn(trades.get(0));
        when(tradeService.save(any())).thenReturn(trades.get(0));
    }

    @Test
    @WithMockUser
    public void home_shouldViewWithReturnAllTrade_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/trade/list"))
               .andExpect(view().name("trade/list"))
               .andExpect(model().attribute("trades", trades))
               .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void home_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/trade/list")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void addTradeForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/trade/add")).andExpect(view().name("trade/add")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void addTradeForm_shouldStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/trade/add")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void validateTradeForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        Trade trade = trades.get(0);
        trade.setTradeId(null);

        mockMvc.perform(post("/trade/validate").flashAttr("trade", trade).with(csrf()))
               .andExpect(view().name("redirect:/trade/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser
    public void validateTradeForm_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        Trade trade = new Trade(null, null);

        mockMvc.perform(post("/trade/validate").flashAttr("trade", trade).with(csrf()))
               .andExpect(view().name("trade/add"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void validateTradeForm_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/trade/validate").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void showUpdateForm_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/trade/update/1")).andExpect(status().isOk()).andExpect(view().name("trade/update"));
    }

    @Test
    @WithAnonymousUser
    public void showUpdateForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        Trade trade = trades.get(0);
        when(tradeService.findById(1)).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void updateTrade_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        Trade trade = trades.get(0);
        trade.setTradeId(null);

        mockMvc.perform(post("/trade/update/1").flashAttr("trade", trade).with(csrf()))
               .andExpect(view().name("redirect:/trade/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser
    public void updateTrade_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        Trade trade = new Trade(null, null);

        mockMvc.perform(post("/trade/update/1").flashAttr("trade", trade).with(csrf()))
               .andExpect(view().name("trade/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void updateTrade_shouldReturnView_whenUserIsAuthenticatedWithIdIsNotFound() throws Exception {

        Trade trade = new Trade(null, null);

        mockMvc.perform(post("/trade/update/6").flashAttr("trade", trade).with(csrf()))
               .andExpect(view().name("trade/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void updateTrade_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/trade/update/1").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void deleteTrade_shouldViewWithReturnAllTrade_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/trade/delete/1"))
               .andExpect(view().name("redirect:/trade/list"))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void deleteTrade_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/trade/delete/1")).andExpect(status().isUnauthorized());
    }
}
