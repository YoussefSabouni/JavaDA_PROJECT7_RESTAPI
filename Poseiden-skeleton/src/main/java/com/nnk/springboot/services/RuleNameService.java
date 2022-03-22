package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {

        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAll() {

        return this.ruleNameRepository.findAll();
    }

    public RuleName findById(Integer id) {

        return this.ruleNameRepository.findById(id)
                                      .orElseThrow(() -> new NoSuchElementException("Invalid ruleName Id:" + id));
    }

    public RuleName save(RuleName ruleName) {

        return this.ruleNameRepository.save(ruleName);
    }

    public void deleteById(Integer id) {

        if (!this.ruleNameRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid ruleName Id:" + id);
        }

        this.ruleNameRepository.deleteById(id);
    }
}
