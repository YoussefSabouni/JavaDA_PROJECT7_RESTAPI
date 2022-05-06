package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Intended to contain the business code for {@link BidList}.
 */
@Service
public class BidListService {

    private final BidListRepository bidListRepository;


    /**
     * Create a new instance of this {@link BidListService}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param bidListRepository
     *         instance of {@link BidListRepository}.
     */
    public BidListService(BidListRepository bidListRepository) {

        this.bidListRepository = bidListRepository;
    }

    /**
     * Return the list of all {@link BidList} in database
     *
     * @return a {@link List} of {@link BidList}
     */
    public List<BidList> findAll() {

        return this.bidListRepository.findAll();
    }

    /**
     * Search for a {@link BidList} by an id.
     *
     * @param id
     *         internal identifier of a {@link BidList}.
     *
     * @return an {@link BidList} or throw {@link NoSuchElementException}.
     */
    public BidList findById(Integer id) {

        return this.bidListRepository.findById(id)
                                     .orElseThrow(() -> new NoSuchElementException("Invalid bidList Id:" + id));
    }

    /**
     * Requests the database registration of a {@link BidList}.
     *
     * @param bidList
     *         to be registered.
     *
     * @return {@link BidList}registered.
     */
    public BidList save(BidList bidList) {

        return this.bidListRepository.save(bidList);
    }

    /**
     * Request the deletion of a {@link BidList}
     *
     * @param id
     *         matches the {@link BidList} to be deleted
     */
    public void deleteById(Integer id) {

        if (!this.bidListRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid bidList Id:" + id);
        }

        this.bidListRepository.deleteById(id);
    }
}
