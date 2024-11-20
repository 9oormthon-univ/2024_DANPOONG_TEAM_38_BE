package boosters.fundboost.boost.service;

import boosters.fundboost.boost.domain.Boost;
import boosters.fundboost.boost.repository.BoostRepository;
import boosters.fundboost.project.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoostServiceImpl implements BoostService {
    private final BoostRepository boostRepository;

    @Override
    public Page<Project> getBoostedProjects(Long userId, Pageable pageable) {
        Page<Boost> boosts = boostRepository.findAllByUser_Id(userId, pageable);

        return boosts.map(Boost::getProject);
    }
}
