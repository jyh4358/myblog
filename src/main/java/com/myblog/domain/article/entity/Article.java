package com.myblog.domain.article.entity;


import com.myblog.domain.BasicEntity;
import com.myblog.domain.category.entity.Category;
import com.myblog.domain.comment.entity.Comment;
import com.myblog.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SequenceGenerator(
        name = "ARTICLE_SEQ_GEN",
        sequenceName = "ARTICLE_SEQ",
        initialValue = 1, allocationSize = 50)
/*
    인덱스 테이블 생성
 */
@Table(indexes = {
        @Index(name = "i_title", columnList = "title"),
        @Index(name = "i_content", columnList = "content")})
public class Article extends BasicEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_SEQ_GEN")
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(updatable = false, length = 10000)
    private String content;

    @Column( columnDefinition = "integer default 0", nullable = false)
    private Long hit;

    @Column(nullable = false)
    private String thumbnailUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @OnDelete(action = OnDeleteAction.CASCADE) cascade를 쓰는데 이걸 왜 넣었지?
    private List<ArticleTagList> articleTagLists = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> parentCommentList = new ArrayList<>();

    @Builder
    public Article(String title, String content, Long hit, String thumbnailUrl, Member member, Category category) {
        this.title = title;
        this.content = content;
        this.hit = 0L;
        this.thumbnailUrl = makeDefaultThumbOf(thumbnailUrl);
        this.member = member;
        this.category = category;
    }

    private String makeDefaultThumbOf(String thumbnailUrl) {
        var defaultThumbUrl = "https://cdn.pixabay.com/photo/2020/11/08/13/28/tree-5723734_1280.jpg";
        if (thumbnailUrl == null || thumbnailUrl.equals("")) {
            thumbnailUrl = defaultThumbUrl;
        }
        return thumbnailUrl;
    }

    /**
     *  비즈니스 로직
     *  edit - article 수정
     *  addHit - 조회 증가
     */

    public void edit(String content, String title, String toc, String thumbnailUrl, Category category){
        this.content = content;
        this.title = title;
        this.category = category;
        if(thumbnailUrl != null){
            this.thumbnailUrl = getThumbnailUrl();
        }
    }

    public void addHit(){
        this.hit++;
    }
}
