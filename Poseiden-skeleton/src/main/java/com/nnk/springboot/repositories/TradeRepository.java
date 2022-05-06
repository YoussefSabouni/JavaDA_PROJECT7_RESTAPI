package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Layer for the {@link Trade}. It's managed here by the JPA interface.
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {}
