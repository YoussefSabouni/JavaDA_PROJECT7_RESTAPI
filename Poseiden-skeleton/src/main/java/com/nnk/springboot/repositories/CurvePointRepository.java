package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data Access Layer for the {@link CurvePoint}. It's managed here by the JPA interface.
 */
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
