package boosters.fundboost.proposal.service;

import boosters.fundboost.global.common.domain.enums.UploadType;
import boosters.fundboost.global.uploader.S3UploaderService;
import boosters.fundboost.project.domain.Project;
import boosters.fundboost.project.service.ProjectService;
import boosters.fundboost.proposal.domain.Proposal;
import boosters.fundboost.proposal.repository.ProposalRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProposalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {
    private final ProposalRepository proposalRepository;
    private final ProjectService projectService;
    private final S3UploaderService s3UploaderService;

    @Override
    @Transactional
    public void writeProposal(User user, ProposalRequest request) {
        Project project = projectService.findById(request.getProjectId());
        String fileUrl = Optional.ofNullable(request.getFile())
                .filter(image -> !image.isEmpty())
                .map(image -> s3UploaderService.uploadFile(image, UploadType.FILE.getDirectory()))
                .orElse(null);

        Proposal proposal = createProposal(project, request, fileUrl);

        proposalRepository.save(proposal);
    }

    private Proposal createProposal(Project project, ProposalRequest request, String file) {
        return Proposal.builder()
                .project(project)
                .title(request.getTitle())
                .content(request.getContent())
                .file(file).build();
    }
}
