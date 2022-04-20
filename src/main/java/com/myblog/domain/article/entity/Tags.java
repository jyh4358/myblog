package com.myblog.domain.article.entity;

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
        name = "TAGS_SEQ_GEN",
        sequenceName = "TAGS_SEQ",
        initialValue = 1, allocationSize = 50)
public class Tags {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAGS_SEQ_GEN")
    @Column(name = "tags_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "tags")
    private List<ArticleTagList> articleTagLists = new ArrayList<>();

    public Tags(String name) {
        this.name = name;
    }
}
