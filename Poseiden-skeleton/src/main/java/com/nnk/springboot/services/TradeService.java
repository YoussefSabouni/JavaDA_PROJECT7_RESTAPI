package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Intended to contain the business code for {@link Trade}.
 */
@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    /**
     * Create a new instance of this {@link TradeService}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param tradeRepository
     *         instance of {@link TradeRepository}.
     */
    public TradeService(TradeRepository tradeRepository) {

        this.tradeRepository = tradeRepository;
    }

    /**
     * Return the list of all {@link Trade} in database
     *
     * @return a {@link List} of {@link Trade}
     */
    public List<Trade> findAll() {

        return this.tradeRepository.findAll();
    }

    /**
     * Search for a {@link Trade} by an id.
     *
     * @param id
     *         internal identifier of a {@link Trade}.
     *
     * @return an {@link Trade} or throw {@link NoSuchElementException}.
     */
    public Trade findById(Integer id) {

        return this.tradeRepository.findById(id)
                                   .orElseThrow(() -> new NoSuchElementException("Invalid trade Id:" + id));
    }

    /**
     * Requests the database registration of a {@link Trade}.
     *
     * @param trade
     *         to be registered.
     *
     * @return {@link Trade}registered.
     */
    public Trade save(Trade trade) {

        return this.tradeRepository.save(trade);
    }

    /**
     * Request the deletion of a {@link Trade}
     *
     * @param id
     *         matches the {@link Trade} to be deleted
     */
    public void deleteById(Integer id) {

        if (!this.tradeRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid trade Id:" + id);
        }

        this.tradeRepository.deleteById(id);
    }
}
