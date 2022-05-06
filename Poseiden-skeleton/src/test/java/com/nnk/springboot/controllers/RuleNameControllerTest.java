package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    private static List<RuleName>  ruleNames;
    @MockBean
    private        RuleNameService ruleNameService;
    @Autowired
    private        MockMvc         mockMvc;

    @BeforeAll
    public static void setUp() {

        ruleNames = new ArrayList<>();
        ruleNames.add(new RuleName("name 1", "desc 1", "json", "template", "sql", "part") {{this.setId(1);}});
        ruleNames.add(new RuleName("name 2", "desc 2", "json", "template", "sql", "part") {{this.setId(2);}});
        ruleNames.add(new RuleName("name 3", "desc 3", "json", "template", "sql", "part") {{this.setId(3);}});
    }

    @BeforeEach
    public void setUpPerTest() {

        when(ruleNameService.findAll()).thenReturn(ruleNames);
        when(ruleNameService.findById(1)).thenReturn(ruleNames.get(0));
        when(ruleNameService.save(any())).thenReturn(ruleNames.get(0));
    }

    @Test
    @WithMockUser
    public void home_shouldViewWithReturnAllRuleName_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/ruleName/list"))
               .andExpect(view().name("ruleName/list"))
               .andExpect(model().attribute("ruleNames", ruleNames))
               .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void home_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/ruleName/list")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void addRuleForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/ruleName/add")).andExpect(view().name("ruleName/add")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void addRuleForm_shouldStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/ruleName/add")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void validateRuleForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        RuleName ruleName = ruleNames.get(0);
        ruleName.setId(null);

        mockMvc.perform(post("/ruleName/validate").flashAttr("ruleName", ruleName).with(csrf()))
               .andExpect(view().name("redirect:/ruleName/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser
    public void validateRuleForm_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        RuleName ruleName = new RuleName(null, null, null, null, null, null);

        mockMvc.perform(post("/ruleName/validate").flashAttr("ruleName", ruleName).with(csrf()))
               .andExpect(view().name("ruleName/add"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void validateRuleForm_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/ruleName/validate").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void showUpdateForm_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/ruleName/update/1")).andExpect(status().isOk()).andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithAnonymousUser
    public void showUpdateForm_shouldReturnView_whenUserIsAuthenticated() throws Exception {

        RuleName ruleName = ruleNames.get(0);
        when(ruleNameService.findById(1)).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void updateRule_shouldStatusUnAuthorized_whenUserIsAuthenticated() throws Exception {

        RuleName ruleName = ruleNames.get(0);
        ruleName.setId(null);

        mockMvc.perform(post("/ruleName/update/1").flashAttr("ruleName", ruleName).with(csrf()))
               .andExpect(view().name("redirect:/ruleName/list"))
               .andExpect(status().is3xxRedirection())
               .andExpect(model().attributeExists("ruleName"));
    }

    @Test
    @WithMockUser
    public void updateRule_shouldReturnView_whenUserIsAuthenticatedWithBadRequest() throws Exception {

        RuleName ruleName = new RuleName(null, null, null, null, null, null);

        mockMvc.perform(post("/ruleName/update/1").flashAttr("ruleName", ruleName).with(csrf()))
               .andExpect(view().name("ruleName/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithMockUser
    public void updateRule_shouldReturnView_whenUserIsAuthenticatedWithIdIsNotFound() throws Exception {

        RuleName ruleName = new RuleName(null, null, null, null, null, null);

        mockMvc.perform(post("/ruleName/update/6").flashAttr("ruleName", ruleName).with(csrf()))
               .andExpect(view().name("ruleName/update"))
               .andExpect(status().isOk())
               .andExpect(model().hasErrors());
    }

    @Test
    @WithAnonymousUser
    public void updateRule_shouldStatusUnAuthorized_whenUserIsNotAuthenticated() throws Exception {

        mockMvc.perform(post("/ruleName/update/1").with(csrf())).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void deleteRule_shouldViewWithReturnAllRuleName_whenUserIsAuthenticated() throws Exception {

        mockMvc.perform(get("/ruleName/delete/1"))
               .andExpect(view().name("redirect:/ruleName/list"))
               .andExpect(model().attribute("ruleNames", ruleNames))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void deleteRule_shouldReturnStatusUnAuthorized_whenIsNotAuthenticated() throws Exception {

        mockMvc.perform(get("/ruleName/delete/1")).andExpect(status().isUnauthorized());
    }
}
