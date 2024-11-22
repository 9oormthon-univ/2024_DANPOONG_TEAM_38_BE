package boosters.fundboost.project.domain;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.global.common.domain.BaseEntity;
import boosters.fundboost.global.exception.ValidationException;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.like.domain.Like;
import boosters.fundboost.project.domain.enums.Progress;
import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectImage> images = new ArrayList<>();

    private String budgetDescription;
    private String scheduleDescription;
    private String teamDescription;
    private String account;
    private long achievedAmount;
    private Long targetAmount;
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Progress progress = Progress.DRAFT;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Boost> boosts;
    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes;
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

    @Builder
    public Project(String mainTitle, String subTitle, List<ProjectImage> images, ProjectCategory category, Region region, User user,
                   String account, String budgetDescription, String scheduleDescription, String teamDescription,
                   Long targetAmount, String introduction, Progress progress, LocalDate startDate, LocalDate endDate, String summary) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.images = images != null ? images : new ArrayList<>();         this.category = category;
        this.region = region;
        this.user = user;
        this.account = account;
        this.budgetDescription = budgetDescription;
        this.scheduleDescription = scheduleDescription;
        this.teamDescription = teamDescription;
        this.targetAmount = targetAmount;
        this.introduction = introduction;
        this.progress = progress != null ? progress : Progress.DRAFT;
        this.startDate = startDate;
        this.endDate = endDate;
        this.summary = summary;
    }

    public void updateBasicInfo(String mainTitle, String subTitle, ProjectCategory category, Region region,
                                String account, String budgetDescription, String scheduleDescription,
                                String teamDescription, Long targetAmount, String introduction,
                                LocalDate startDate, LocalDate endDate, String summary) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.category = category;
        this.region = region;
        this.account = account;
        this.budgetDescription = budgetDescription;
        this.scheduleDescription = scheduleDescription;
        this.teamDescription = teamDescription;
        this.targetAmount = targetAmount;
        this.introduction = introduction;
        this.startDate = startDate;
        this.endDate = endDate;
        this.summary = summary;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}
