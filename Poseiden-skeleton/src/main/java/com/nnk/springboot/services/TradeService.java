package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {

        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() {

        return this.tradeRepository.findAll();
    }

    public Trade findById(Integer id) {

        return this.tradeRepository.findById(id)
                                      .orElseThrow(() -> new NoSuchElementException("Invalid trade Id:" + id));
    }

    public Trade save(Trade trade) {

        return this.tradeRepository.save(trade);
    }

    public void deleteById(Integer id) {

        if (!this.tradeRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid trade Id:" + id);
        }

        this.tradeRepository.deleteById(id);
    }
}
