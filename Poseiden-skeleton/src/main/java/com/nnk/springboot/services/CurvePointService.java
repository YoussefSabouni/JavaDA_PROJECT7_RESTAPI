package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Intended to contain the business code for {@link CurvePoint}.
 */
@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    /**
     * Create a new instance of this {@link CurvePointService}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param curveRepository
     *         instance of {@link CurvePointRepository}.
     */
    public CurvePointService(CurvePointRepository curveRepository) {

        this.curvePointRepository = curveRepository;
    }

    /**
     * Return the list of all {@link CurvePoint} in database
     *
     * @return a {@link List} of {@link CurvePoint}
     */
    public List<CurvePoint> findAll() {

        return this.curvePointRepository.findAll();
    }

    /**
     * Search for a {@link CurvePoint} by an id.
     *
     * @param id
     *         internal identifier of a {@link CurvePoint}.
     *
     * @return an {@link CurvePoint} or throw {@link NoSuchElementException}.
     */
    public CurvePoint findById(Integer id) {

        return this.curvePointRepository.findById(id)
                                        .orElseThrow(() -> new NoSuchElementException("Invalid curvePoint Id:" + id));
    }

    /**
     * Requests the database registration of a {@link CurvePoint}.
     *
     * @param curvePoint
     *         to be registered.
     *
     * @return {@link CurvePoint}registered.
     */
    public CurvePoint save(CurvePoint curvePoint) {

        return this.curvePointRepository.save(curvePoint);
    }

    /**
     * Request the deletion of a {@link CurvePoint}
     *
     * @param id
     *         matches the {@link CurvePoint} to be deleted
     */
    public void deleteById(Integer id) {

        if (!this.curvePointRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid CurvePoint Id:" + id);
        }

        this.curvePointRepository.deleteById(id);
    }
}
