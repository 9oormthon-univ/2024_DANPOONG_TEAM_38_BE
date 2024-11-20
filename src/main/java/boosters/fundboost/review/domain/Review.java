package boosters.fundboost.review.domain;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.global.common.domain.BaseEntity;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.review.domain.enums.ReviewType;
import boosters.fundboost.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    private String image;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boost_id",nullable = true)
    private Boost boost;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    public Review(String title, String description, ReviewType reviewType, Project project, User user, Boost boost) {
        this.title = title;
        this.description = description;
        this.reviewType = reviewType;
        this.project = project;
        this.user = user;
        this.boost = boost;
    }
}
