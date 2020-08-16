package com.man.ger.manager.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @Enumerated(EnumType.STRING)
    private CategoryEnumStatus status;

    public Category() {
    }

    public Category(String category) {
        this.category = category;
    }

}

