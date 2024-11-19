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
    private ProjectCategory category;
    private Region region;
    private Progress progress;
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
}
