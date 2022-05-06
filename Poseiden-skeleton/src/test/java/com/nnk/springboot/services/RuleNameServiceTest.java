package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RuleNameService.class})
@ExtendWith(SpringExtension.class)
public class RuleNameServiceTest {

    @Autowired
    private RuleNameService    ruleService;
    @MockBean
    private RuleNameRepository ruleRepository;
    private RuleName           rule;

    @BeforeEach
    private void setUpPerTest() {

        rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        Mockito.reset(ruleRepository);
    }

    @Test
    public void saveRuleName_shouldReturnRuleNameSaved_forCorrectRuleName() {

        RuleName ruleSaved = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        ruleSaved.setId(1);
        when(ruleRepository.save(eq(rule))).thenReturn(ruleSaved);

        rule = ruleService.save(rule);
        verify(ruleRepository, times(1)).save(any());
        Assertions.assertEquals(ruleSaved, rule);
    }

    @Test
    public void updateRuleName_shouldReturnRuleNameUpdated_forCorrectRuleName() {

        RuleName ruleUpdated = new RuleName("Rule Name", "Description 2", "Json", "Template", "SQL", "SQL Part");
        ruleUpdated.setId(1);

        rule.setId(1);
        rule.setDescription("Description 2");

        when(ruleRepository.save(eq(rule))).thenReturn(ruleUpdated);

        rule = ruleService.save(rule);

        verify(ruleRepository, times(1)).save(any());
        Assertions.assertEquals(ruleUpdated, rule);
    }

    @Test
    public void findAll_shouldReturnAllRuleNameInDatabase() {

        rule.setId(1);
        List<RuleName> rules = new ArrayList<RuleName>() {{this.add(rule);}};

        for (int i = 0 ; i < 4 ; i++) {

            rule.setId(rule.getId() + 1);
            rules.add(rule);
        }

        when(ruleRepository.findAll()).thenReturn(rules);

        List<RuleName> listResult = ruleService.findAll();

        Assertions.assertEquals(rules.size(), listResult.size());
        Assertions.assertTrue(listResult.contains(rule));
        verify(ruleRepository, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnOneRuleName_whenRuleNameIdIsExists() {

        rule.setId(1);

        when(ruleRepository.findById(1)).thenReturn(Optional.of(rule));

        RuleName ruleFound = ruleService.findById(rule.getId());

        verify(ruleRepository, times(1)).findById(any());
        Assertions.assertEquals(rule, ruleFound);
    }

    @Test
    public void findById_shouldThrowException_whenRuleNameIdIsNotExists() {

        when(ruleRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> ruleService.findById(1));
        verify(ruleRepository, times(1)).findById(any());
    }

    @Test
    public void deleteById_shouldDeleteRuleName() {


        when(this.ruleRepository.findById(eq(1))).thenReturn(Optional.of(rule));
        Integer id = 1;
        ruleService.deleteById(id);

        when(this.ruleRepository.findById(eq(1))).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> ruleService.deleteById(id));
        verify(this.ruleRepository, times(2)).findById(1);
    }
}