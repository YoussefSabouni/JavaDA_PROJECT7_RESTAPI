package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CurvePointService.class})
@ExtendWith(SpringExtension.class)
public class CurvePointServiceTest {

    @Autowired
    private CurvePointService    curvePointService;
    @MockBean
    private CurvePointRepository curvePointRepository;
    private CurvePoint           curvePoint;

    @BeforeEach
    private void setUpPerTest() {

        curvePoint = new CurvePoint(1, 10d, 10d);
        Mockito.reset(curvePointRepository);
    }

    @Test
    public void saveCurvePoint_shouldReturnCurvePointSaved_forCorrectCurvePoint() {

        CurvePoint curvePointSaved = new CurvePoint(1, 10d, 10d);
        curvePointSaved.setCurveId(1);
        when(curvePointRepository.save(eq(curvePoint))).thenReturn(curvePointSaved);

        curvePoint = curvePointService.save(curvePoint);
        verify(curvePointRepository, times(1)).save(any());
        Assertions.assertEquals(curvePointSaved, curvePoint);
    }

    @Test
    public void updateCurvePoint_shouldReturnCurvePointUpdated_forCorrectCurvePoint() {

        CurvePoint curvePointUpdated = new CurvePoint(1, 10d, 10d);
        curvePointUpdated.setCurveId(1);

        curvePoint.setCurveId(1);
        curvePoint.setValue(20d);

        when(curvePointRepository.save(eq(curvePoint))).thenReturn(curvePointUpdated);

        curvePoint = curvePointService.save(curvePoint);

        verify(curvePointRepository, times(1)).save(any());
        Assertions.assertEquals(curvePointUpdated, curvePoint);
    }

    @Test
    public void findAll_shouldReturnAllCurvePointInDatabase() {

        curvePoint.setId(1);
        List<CurvePoint> curvePoints = new ArrayList<CurvePoint>() {{this.add(curvePoint);}};

        for (int i = 0 ; i < 4 ; i++) {

            curvePoint.setId(curvePoint.getId() + 1);
            curvePoint.setCurveId(curvePoint.getCurveId() + 1);
            curvePoint.setValue(curvePoint.getValue() + 10d);
            curvePoints.add(curvePoint);
        }

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> listResult = curvePointService.findAll();

        Assertions.assertEquals(curvePoints.size(), listResult.size());
        Assertions.assertTrue(listResult.contains(curvePoint));
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnOneCurvePoint_whenCurvePointIdIsExists() {

        curvePoint.setCurveId(1);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint curvePointFound = curvePointService.findById(curvePoint.getCurveId());

        verify(curvePointRepository, times(1)).findById(any());
        Assertions.assertEquals(curvePoint, curvePointFound);
    }

    @Test
    public void findById_shouldThrowException_whenCurvePointIdIsNotExists() {

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> curvePointService.findById(1));
        verify(curvePointRepository, times(1)).findById(any());
    }

    @Test
    public void deleteById_shouldDeleteCurvePoint() {


        when(this.curvePointRepository.findById(eq(1))).thenReturn(Optional.of(curvePoint));
        Integer id = 1;
        curvePointService.deleteById(id);

        when(this.curvePointRepository.findById(eq(1))).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> curvePointService.deleteById(id));
        verify(this.curvePointRepository, times(2)).findById(1);
    }
}