package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BidListService {

    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {

        this.bidListRepository = bidListRepository;
    }

    public List<BidList> findAll() {

        return this.bidListRepository.findAll();
    }

    public BidList findById(Integer id) {

        return this.bidListRepository.findById(id)
                                     .orElseThrow(() -> new NoSuchElementException("Invalid bidList Id:" + id));
    }

    public BidList save(BidList bidList) {

        return this.bidListRepository.save(bidList);
    }

    public void deleteById(Integer id) {

        if (!this.bidListRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid bidList Id:" + id);
        }

        this.bidListRepository.deleteById(id);
    }
}
