package boosters.fundboost.project.domain;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.global.common.domain.BaseEntity;
import boosters.fundboost.like.domain.Like;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import boosters.fundboost.proposal.domain.Proposal;
import boosters.fundboost.review.domain.Review;
import boosters.fundboost.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;
    private String mainTitle;
    private String subTitle;
    private String introduction;
    private String image;
    private String budgetDescription;
    private String scheduleDescription;
    private String teamDescription;
    private String account;
    private Long targetAmount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Progress progress = Progress.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region region;

    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Boost> boosts;
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes;
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Proposal> proposals;
    @OneToMany(mappedBy = "project", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Review> reviews;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Project(String mainTitle, String subTitle, String image, ProjectCategory category, Region region, User user,
                   String account, String budgetDescription, String scheduleDescription, String teamDescription,
                   Long targetAmount, String introduction, Progress progress) { // introduction 추가
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.image = image;
        this.category = category;
        this.region = region;
        this.user = user;
        this.account = account;
        this.budgetDescription = budgetDescription;
        this.scheduleDescription = scheduleDescription;
        this.teamDescription = teamDescription;
        this.targetAmount = targetAmount;
        this.introduction = introduction;
        this.progress = progress != null ? progress : Progress.PENDING;
    }

}
