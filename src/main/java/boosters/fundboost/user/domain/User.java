package boosters.fundboost.user.domain;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.follow.domain.Follow;
import boosters.fundboost.global.common.domain.BaseEntity;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.user.domain.enums.Tag;
import boosters.fundboost.user.domain.enums.UserType;
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
    private Tag tag;
    @OneToOne(mappedBy = "user")
    private Company company;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Follow> follows;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Project> projects;

    @Builder
    public User(String name, String email, String image, UserType userType, String link, String title, String content) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.userType = userType;
        this.link = link;
        this.title = title;
        this.content = content;
    }
}
