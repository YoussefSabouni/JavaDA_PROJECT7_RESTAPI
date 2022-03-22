package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curveRepository) {

        this.curvePointRepository = curveRepository;
    }

    public List<CurvePoint> findAll() {

        return this.curvePointRepository.findAll();
    }

    public CurvePoint findById(Integer id) {

        return this.curvePointRepository.findById(id)
                                        .orElseThrow(() -> new NoSuchElementException("Invalid curvePoint Id:" + id));
    }

    public CurvePoint save(CurvePoint curvePoint) {

        return this.curvePointRepository.save(curvePoint);
    }

    public void deleteById(Integer id) {

        if (!this.curvePointRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid CurvePoint Id:" + id);
        }

        this.curvePointRepository.deleteById(id);
    }
}
