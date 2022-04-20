package com.myblog.domain.category.entity;

import com.myblog.domain.article.entity.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SequenceGenerator(
        name = "CATEGORY_SEQ_GEN",
        sequenceName = "CATEGORY_SEQ",
        initialValue = 0, allocationSize = 50)
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_SEQ_GEN")
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = false, length = 50)
    private String title;

    @OneToMany(mappedBy = "category")
    private List<Article> articles = new ArrayList<>();

    @Column(nullable = false)
    private int tier;   // todo 요건 뭐지?

    private int pSortNum;
    private int cSortNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parents_id")
    private Category parents;


    @OneToMany(mappedBy = "parents")
    private List<Category> child = new ArrayList<>();

    public Category(String title, int tier, int pSortNum, int cSortNum, Category parents) {
        this.title = title;
        this.tier = tier;
        this.pSortNum = pSortNum;
        this.cSortNum = cSortNum;
        this.parents = parents;
    }

    /**
     * 비즈니스 로직
     * updateCategory - 카테고리 업데이트
     */

    public void updateCategory(String title, int tier, int pSortNum, int cSortNum, Category parents) {
        this.title = title;
        this.tier = tier;
        this.pSortNum = pSortNum;
        this.cSortNum = cSortNum;
        this.parents = parents;
    }
}
