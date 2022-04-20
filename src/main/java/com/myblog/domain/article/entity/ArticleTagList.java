package com.myblog.domain.article.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SequenceGenerator(
        name = "ARTICLE_TAG_LIST_SEQ_GEN",
        sequenceName = "ARTICLE_TAG_LIST_SEQ",
        initialValue = 1, allocationSize = 50)
public class ArticleTagList {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_TAG_LIST_SEQ_GEN")
    @Column(name = "article_tag_list_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "tags_id")
    private Tags tags;


    public ArticleTagList(Article article, Tags tags) {
        this.article = article;
        this.tags = tags;
    }
}
