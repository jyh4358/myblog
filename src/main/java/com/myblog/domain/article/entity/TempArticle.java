package com.myblog.domain.article.entity;

import com.myblog.domain.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TempArticle extends BasicEntity {

    @Id
    @Column(name = "temp_article_id")
    private Long id;

    @Column(nullable = false, length = 10000)
    private String content;

    public TempArticle(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
