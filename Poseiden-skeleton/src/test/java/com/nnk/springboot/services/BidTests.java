package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidTests {

    @Autowired
    private BidListService bidListService;

    @Test
    public void bidListTest() {

        BidList bid = new BidList("Account Test", "Type Test", 10d);

        // Save
        bid = bidListService.save(bid);
        Assert.assertNotNull(bid.getBidListId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);

        // Update
        bid.setBidQuantity(20d);
        bid = bidListService.save(bid);
        Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);

        // Find
        List<BidList> listResult = bidListService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = bid.getBidListId();
        bidListService.deleteById(id);
        Assertions.assertThrows(NoSuchElementException.class, () -> bidListService.findById(id));
    }
}
