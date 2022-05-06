package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Layer for the {@link BidList}. It's managed here by the JPA interface.
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
