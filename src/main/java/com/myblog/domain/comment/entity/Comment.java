package com.myblog.domain.comment.entity;

import com.myblog.domain.article.entity.Article;
import com.myblog.domain.member.entity.Member;
import lombok.Builder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@SequenceGenerator(
        name = "COMMENT_SEQ_GEN",
        sequenceName = "COMMENT_SEQ",
        initialValue = 1, allocationSize = 50)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENT_SEQ_GEN")
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private int tier;

    // 댓글 표시 순서 - 자식댓글은 ID순으로 처리
    private int pOrder;

    //셀프조인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parents_id")
    private Comment parents;

    @OneToMany(mappedBy = "parents", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> child = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @Column(columnDefinition = "boolean default false")
    private boolean secret;

    @Builder
    public Comment(Article article, int tier, int pOrder, Comment parents, Member member, String content, boolean secret) {
        this.article = article;
        this.tier = tier;
        this.pOrder = pOrder;
        this.parents = parents;
        this.member = member;
        this.content = removeDuplicatedEnter(content);
        this.secret = secret;
    }

    private String removeDuplicatedEnter(String content) {
        if(content == null || content.isEmpty()){
            throw new IllegalArgumentException("InvalidContentException");
        }
        char[] contentBox = new char[content.length()];
        int idx = 0;
        String zipWord = "\n\n";

        for(int i = 0; i< content.length(); i++){
            contentBox[idx] = content.charAt(i);
            if(contentBox[idx] == '\n' && idx >= 1){
                int tempIdx = idx;
                int length = 1;
                boolean isRemove = true;

                for(int j = 0; j<2; j++){
                    if(contentBox[tempIdx--] != zipWord.charAt(length--)){
                        isRemove = false;
                        break;
                    }
                }
                if(isRemove) idx -= 1;
            }
            idx++;
        }
        return String.valueOf(contentBox).trim();
    }
}
