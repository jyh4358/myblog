package com.myblog.domain.member.entity;

import com.myblog.domain.BasicEntity;
import com.myblog.domain.article.entity.Article;
import com.myblog.domain.comment.entity.Comment;
import com.myblog.domain.member.MemberRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
@SequenceGenerator(
        name = "MEMBER_SEQ_GEN",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1, allocationSize = 50)
public class Member extends BasicEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GEN")
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = true, unique = true, length = 50)
    private String email;

    private String priUrl;
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MemberRole role;

    @OneToMany(mappedBy = "member")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(String userId, String username, String email, String priUrl, String provider, String providerId, MemberRole role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.priUrl = priUrl;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    /**
     * 비즈니스 로직
     * 유저명 변경
     * @param username : 유저명
     */

    public void changeUsername(String username) {
        this.username = username;
    }
}
