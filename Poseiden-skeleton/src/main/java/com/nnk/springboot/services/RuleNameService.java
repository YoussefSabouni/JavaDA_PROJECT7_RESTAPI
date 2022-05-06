package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Intended to contain the business code for {@link RuleName}.
 */
@Service
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    /**
     * Create a new instance of this {@link RuleNameService}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param ruleNameRepository
     *         instance of {@link RuleNameRepository}.
     */
    public RuleNameService(RuleNameRepository ruleNameRepository) {

        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Return the list of all {@link RuleName} in database
     *
     * @return a {@link List} of {@link RuleName}
     */
    public List<RuleName> findAll() {

        return this.ruleNameRepository.findAll();
    }

    /**
     * Search for a {@link RuleName} by an id.
     *
     * @param id
     *         internal identifier of a {@link RuleName}.
     *
     * @return an {@link RuleName} or throw {@link NoSuchElementException}.
     */
    public RuleName findById(Integer id) {

        return this.ruleNameRepository.findById(id)
                                      .orElseThrow(() -> new NoSuchElementException("Invalid ruleName Id:" + id));
    }

    /**
     * Requests the database registration of a {@link RuleName}.
     *
     * @param ruleName
     *         to be registered.
     *
     * @return {@link RuleName}registered.
     */
    public RuleName save(RuleName ruleName) {

        return this.ruleNameRepository.save(ruleName);
    }

    /**
     * Request the deletion of a {@link RuleName}
     *
     * @param id
     *         matches the {@link RuleName} to be deleted
     */
    public void deleteById(Integer id) {

        if (!this.ruleNameRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid ruleName Id:" + id);
        }

        this.ruleNameRepository.deleteById(id);
    }
}
