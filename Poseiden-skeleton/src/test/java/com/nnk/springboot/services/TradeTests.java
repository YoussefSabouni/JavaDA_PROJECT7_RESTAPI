package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeTests {

    @Autowired
    private TradeService tradeService;

    @Test
    public void tradeTest() {

        Trade trade = new Trade("Trade Account", "Type");
        trade.setBuyQuantity(20D);

        // Save
        trade = tradeService.save(trade);
        Assert.assertNotNull(trade.getTradeId());
        Assert.assertEquals("Trade Account", trade.getAccount());

        // Update
        trade.setAccount("Trade Account Update");
        trade = tradeService.save(trade);
        Assert.assertEquals("Trade Account Update", trade.getAccount());

        // Find
        List<Trade> listResult = tradeService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = trade.getTradeId();
        tradeService.deleteById(id);
        Assertions.assertThrows(NoSuchElementException.class, () -> tradeService.findById(id));
    }
}
