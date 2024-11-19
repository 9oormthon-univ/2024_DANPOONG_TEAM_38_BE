package boosters.fundboost.project.domain;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.global.common.domain.BaseEntity;
import boosters.fundboost.global.exception.ValidationException;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.like.domain.Like;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import boosters.fundboost.proposal.domain.Proposal;
import boosters.fundboost.review.domain.Review;
import boosters.fundboost.user.domain.User;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private long achievedAmount;
    private Long targetAmount;
    @Enumerated(EnumType.STRING)
    private ProjectCategory category;
    @Enumerated(EnumType.STRING)
    private Region region;
    @Enumerated(EnumType.STRING)
    private Progress progress;
    private LocalDate startDate;
    private LocalDate endDate;
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

    @PrePersist
    @PreUpdate
    public void validateDates() {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ValidationException(ErrorStatus.VALIDATION_ERROR);
        }
    }
}
