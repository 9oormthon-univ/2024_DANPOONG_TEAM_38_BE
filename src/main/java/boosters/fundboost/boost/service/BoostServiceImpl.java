package boosters.fundboost.boost.service;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.boost.repository.BoostRepository;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.dto.response.BoostedAmountProjection;
import boosters.fundboost.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoostServiceImpl implements BoostService {
    private final BoostRepository boostRepository;

    @Override
    public Page<Project> getBoostedProjects(Long userId, Pageable pageable) {
        Page<Boost> boosts = boostRepository.findAllByUser_Id(userId, pageable);

        return boosts.map(Boost::getProject);
    }

    @Override
    @Transactional
    public void boostCompany(User user, Project project, long amount) {
        boostRepository.save(Boost.of(user, project, amount));
    }

    @Override
    public Map<Long, Long> getBoostedAmounts(List<Long> projectIds) {
        List<BoostedAmountProjection> results = boostRepository.findBoostedAmountsByProjectIds(projectIds);

        return results.stream()
                .collect(Collectors.toMap(
                        BoostedAmountProjection::getProjectId,
                        BoostedAmountProjection::getBoostedAmount
                ));
    }
}
