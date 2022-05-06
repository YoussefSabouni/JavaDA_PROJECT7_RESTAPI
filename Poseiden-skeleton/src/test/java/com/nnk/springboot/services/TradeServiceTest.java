package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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

@ContextConfiguration(classes = {TradeService.class})
@ExtendWith(SpringExtension.class)
public class TradeServiceTest {

    @Autowired
    private TradeService    tradeService;
    @MockBean
    private TradeRepository tradeRepository;
    private Trade           trade;

    @BeforeEach
    private void setUpPerTest() {

        trade = new Trade("Trade Account", "Type");
        Mockito.reset(tradeRepository);
    }

    @Test
    public void saveTrade_shouldReturnTradeSaved_forCorrectTrade() {

        Trade tradeSaved = new Trade("Trade Account", "Type");
        tradeSaved.setTradeId(1);
        when(tradeRepository.save(eq(trade))).thenReturn(tradeSaved);

        trade = tradeService.save(trade);
        verify(tradeRepository, times(1)).save(any());
        Assertions.assertEquals(tradeSaved, trade);
    }

    @Test
    public void updateTrade_shouldReturnTradeUpdated_forCorrectTrade() {

        Trade tradeUpdated = new Trade("Trade Account", "Type 2");
        tradeUpdated.setTradeId(1);

        trade.setTradeId(1);

        when(tradeRepository.save(eq(trade))).thenReturn(tradeUpdated);

        trade = tradeService.save(trade);

        verify(tradeRepository, times(1)).save(any());
        Assertions.assertEquals(tradeUpdated, trade);
    }

    @Test
    public void findAll_shouldReturnAllTradeInDatabase() {

        trade.setTradeId(1);
        List<Trade> trades = new ArrayList<Trade>() {{this.add(trade);}};

        for (int i = 0 ; i < 4 ; i++) {

            trade.setTradeId(trade.getTradeId() + 1);
            trades.add(trade);
        }

        when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> listResult = tradeService.findAll();

        Assertions.assertEquals(trades.size(), listResult.size());
        Assertions.assertTrue(listResult.contains(trade));
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnOneTrade_whenTradeIdIsExists() {

        trade.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade tradeFound = tradeService.findById(trade.getTradeId());

        verify(tradeRepository, times(1)).findById(any());
        Assertions.assertEquals(trade, tradeFound);
    }

    @Test
    public void findById_shouldThrowException_whenTradeIdIsNotExists() {

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> tradeService.findById(1));
        verify(tradeRepository, times(1)).findById(any());
    }

    @Test
    public void deleteById_shouldDeleteTrade() {


        when(this.tradeRepository.findById(eq(1))).thenReturn(Optional.of(trade));
        Integer id = 1;
        tradeService.deleteById(id);

        when(this.tradeRepository.findById(eq(1))).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> tradeService.deleteById(id));
        verify(this.tradeRepository, times(2)).findById(1);
    }
}
