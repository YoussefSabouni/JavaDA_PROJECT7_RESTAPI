package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Layer for the {@link RuleName}. It's managed here by the JPA interface.
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {}
