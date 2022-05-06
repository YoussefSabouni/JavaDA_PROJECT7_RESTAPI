package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurvePointController.class)
public class CurvePointControllerTest {

    private static List<CurvePoint>  curvePoints;
    @MockBean
    private        CurvePointService curvePointService;
    @Autowired
    private        MockMvc           mockMvc;

    @BeforeAll
    public static void setUp() {

        curvePoints = new ArrayList<>();
        curvePoints.add(new CurvePoint(1, 1d, 1d) {{this.setId(1);}});
        curvePoints.add(new CurvePoint(2, 2d, 2d) {{this.setId(2);}});
        curvePoints.add(new CurvePoint(3, 3d, 3d) {{this.setId(3);}});
    }

    @BeforeEach
    public void setUpPerTest() {

        when(curvePointService.findAll()).thenReturn(curvePoints);
        when(curvePointService.findById(1)).thenReturn(curvePoints.get(0));
        when(curvePointService.save(any())).thenReturn(curvePoints.get(0));
    }

    @Test
    @WithMockUser
    public void home_shouldViewWithReturnAllCurvePoint_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
               .andExpect(view().name("curvePoint/list"))
               .andExpect(model().attribute("curvePoints", curvePoints))
               .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void home_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void addCurveForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/curvePoint/add")).andExpect(view().name("curvePoint/add")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void addCurveForm_shouldStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/curvePoint/add")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void validateCurveForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        CurvePoint curvePoint = curvePoints.get(0);
        curvePoint.setId(null);

        mockMvc.perform(post("/curvePoint/validate").flashAttr("curvePoint", curvePoint).with(csrf()))
               .andExpect(view().name("redirect:/curvePoint/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser
    public void validateCurveForm_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        CurvePoint curvePoint = new CurvePoint(null, null, null);

        mockMvc.perform(post("/curvePoint/validate").flashAttr("curvePoint", curvePoint).with(csrf()))
               .andExpect(view().name("curvePoint/add"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void validateCurveForm_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/curvePoint/validate").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void showUpdateForm_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/curvePoint/update/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithAnonymousUser
    public void showUpdateForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        CurvePoint curvePoint = curvePoints.get(0);
        when(curvePointService.findById(1)).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void updateCurve_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        CurvePoint curvePoint = curvePoints.get(0);
        curvePoint.setId(null);

        mockMvc.perform(post("/curvePoint/update/1").flashAttr("curvePoint", curvePoint).with(csrf()))
               .andExpect(view().name("redirect:/curvePoint/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser
    public void updateCurve_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        CurvePoint curvePoint = new CurvePoint(null, null, null);

        mockMvc.perform(post("/curvePoint/update/1").flashAttr("curvePoint", curvePoint).with(csrf()))
               .andExpect(view().name("curvePoint/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void updateCurve_shouldReturnView_whenUserIsAuthenticatedWithIdIsNotFound() throws Exception {

        CurvePoint curvePoint = new CurvePoint(null, null, null);

        mockMvc.perform(post("/curvePoint/update/6").flashAttr("curvePoint", curvePoint).with(csrf()))
               .andExpect(view().name("curvePoint/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void updateCurve_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/curvePoint/update/1").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void deleteCurve_shouldViewWithReturnAllCurvePoint_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/curvePoint/delete/1"))
               .andExpect(view().name("redirect:/curvePoint/list"))
               .andExpect(model().attribute("curvePoints", curvePoints))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void deleteCurve_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/curvePoint/delete/1")).andExpect(status().isUnauthorized());
    }
}