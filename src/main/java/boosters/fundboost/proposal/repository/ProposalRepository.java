package boosters.fundboost.proposal.repository;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.proposal.domain.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    Page<Proposal> findAllByCompany(Company company, Pageable pageable);

    Optional<Proposal> findByIdAndCompany(long id, Company company);
}
