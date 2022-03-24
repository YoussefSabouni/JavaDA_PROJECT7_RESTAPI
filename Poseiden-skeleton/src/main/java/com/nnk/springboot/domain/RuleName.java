package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rulename")
public class RuleName {
    // DONE: Map columns in data table RULENAME with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name Type is mandatory")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 3, max = 200, message = "Description must be between 3 and 50 characters long")
    private String description;

    @NotBlank(message = "JSON is mandatory")
    private String json;
    private String template;
    private String sqlStr;
    private String sqlPart;

    public RuleName() {

    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {

        this.name        = name;
        this.description = description;
        this.json        = json;
        this.template    = template;
        this.sqlStr      = sqlStr;
        this.sqlPart     = sqlPart;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getJson() {

        return json;
    }

    public void setJson(String json) {

        this.json = json;
    }

    public String getTemplate() {

        return template;
    }

    public void setTemplate(String template) {

        this.template = template;
    }

    public String getSqlStr() {

        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {

        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {

        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {

        this.sqlPart = sqlPart;
    }
}
