package boosters.fundboost.user.domain;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.company.domain.Company;
import boosters.fundboost.follow.domain.Follow;
import boosters.fundboost.global.common.domain.BaseEntity;
import boosters.fundboost.like.domain.Like;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.user.domain.enums.Tag;
import boosters.fundboost.user.domain.enums.UserType;
import boosters.fundboost.user.dto.request.ProfileEditRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String image;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;
    private String link;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private Tag tag = Tag.SEASSAK_INVESTOR;
    @OneToOne(mappedBy = "user")
    private Company company;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Follow> follows;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Project> projects;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes;
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Boost> boosts;

    @Builder
    public User(String name, String email, String image, UserType userType, String link, String title, String content, Tag tag) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.userType = userType;
        this.link = link;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public void updateUser(ProfileEditRequest request, String image) {
        if (request.getLink() != null) {
            this.link = request.getLink();
        }
        if (request.getIntroduceTitle() != null) {
            this.title = request.getIntroduceTitle();
        }
        if (request.getIntroduceContent() != null) {
            this.content = request.getIntroduceContent();
        }
        if (image != null) {
            this.image = image;
        }
    }
}
