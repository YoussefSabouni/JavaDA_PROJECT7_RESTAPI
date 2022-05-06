package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BidListService.class})
@ExtendWith(SpringExtension.class)
public class BidListServiceTest {

    @Autowired
    private BidListService    bidListService;
    @MockBean
    private BidListRepository bidListRepository;
    private BidList           bid;

    @BeforeEach
    private void setUpPerTest() {

        bid = new BidList("Account Test", "Type Test", 10d);
        Mockito.reset(bidListRepository);
    }

    @Test
    public void saveBidList_shouldReturnBidListSaved_forCorrectBidList() {

        BidList bidSaved = new BidList("Account Test", "Type Test", 10d);
        bidSaved.setBidListId(1);
        when(bidListRepository.save(eq(bid))).thenReturn(bidSaved);

        bid = bidListService.save(bid);
        verify(bidListRepository, times(1)).save(any());
        Assertions.assertEquals(bidSaved, bid);
    }

    @Test
    public void updateBidList_shouldReturnBidListUpdated_forCorrectBidList() {

        BidList bidUpdated = new BidList("Account Test", "Type Test", 20d);
        bidUpdated.setBidListId(1);

        bid.setBidListId(1);
        bid.setBidQuantity(20d);

        when(bidListRepository.save(eq(bid))).thenReturn(bidUpdated);

        bid = bidListService.save(bid);

        verify(bidListRepository, times(1)).save(any());
        Assertions.assertEquals(bidUpdated, bid);
    }

    @Test
    public void findAll_shouldReturnAllBidListInDatabase() {

        bid.setBidListId(1);
        List<BidList> bidList = new ArrayList<BidList>() {{this.add(bid);}};

        for (int i = 0 ; i < 4 ; i++) {

            bid.setBidListId(bid.getBidListId() + 1);
            bid.setBidQuantity(bid.getBidQuantity() + 10d);
            bidList.add(bid);
        }

        when(bidListRepository.findAll()).thenReturn(bidList);

        List<BidList> listResult = bidListService.findAll();

        Assertions.assertEquals(bidList.size(), listResult.size());
        Assertions.assertTrue(listResult.contains(bid));
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnOneBidList_whenBidListIdIsExists() {

        bid.setBidListId(1);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        BidList bidFound = bidListService.findById(bid.getBidListId());

        verify(bidListRepository, times(1)).findById(any());
        Assertions.assertEquals(bid, bidFound);
    }

    @Test
    public void findById_shouldThrowException_whenBidListIdIsNotExists() {

        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> bidListService.findById(1));
        verify(bidListRepository, times(1)).findById(any());
    }

    @Test
    public void deleteById_shouldDeleteBidList() {


        when(this.bidListRepository.findById(eq(1))).thenReturn(Optional.of(bid));
        Integer id = 1;
        bidListService.deleteById(id);

        when(this.bidListRepository.findById(eq(1))).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> bidListService.deleteById(id));
        verify(this.bidListRepository, times(2)).findById(1);
    }
}
