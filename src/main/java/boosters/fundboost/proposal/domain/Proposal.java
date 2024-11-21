package boosters.fundboost.proposal.domain;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.global.common.domain.BaseEntity;
import boosters.fundboost.global.converter.FilesConverter;
import boosters.fundboost.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Proposal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proposal_id")
    private Long id;
    private String title;
    @Convert(converter = FilesConverter.class)
    private List<String> files;
    private String content;
    private LocalDate viewedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Builder
    public Proposal(String title, List<String> files, String content, User user, Company company) {
        this.title = title;
        this.content = content;
        this.viewedAt = LocalDate.now();
        this.files = files;
        this.user = user;
        this.company = company;
    }
}
