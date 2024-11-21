package boosters.fundboost.proposal.repository;

import boosters.fundboost.proposal.domain.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
}
