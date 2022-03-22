package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

    @Autowired
    private RuleNameService ruleNameService;

    @Test
    public void ruleTest() {

        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        rule = ruleNameService.save(rule);
        Assert.assertNotNull(rule.getId());
        Assert.assertEquals("Rule Name", rule.getName());

        // Update
        rule.setName("Rule Name Update");
        rule = ruleNameService.save(rule);
        Assert.assertEquals("Rule Name Update", rule.getName());

        // Find
        List<RuleName> listResult = ruleNameService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rule.getId();
        ruleNameService.deleteById(id);
        Assertions.assertThrows(NoSuchElementException.class, () -> ruleNameService.findById(id));
    }
}
