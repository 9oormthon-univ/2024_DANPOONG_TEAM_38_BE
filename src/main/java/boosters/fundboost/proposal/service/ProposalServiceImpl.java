package boosters.fundboost.proposal.service;

import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.service.CompanyService;
import boosters.fundboost.global.common.domain.enums.UploadType;
import boosters.fundboost.global.uploader.S3UploaderService;
import boosters.fundboost.proposal.converter.ProposalConverter;
import boosters.fundboost.proposal.domain.Proposal;
import boosters.fundboost.proposal.dto.response.ProposalPreviewResponse;
import boosters.fundboost.proposal.repository.ProposalRepository;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProposalRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {
    private static final int PAGE_SIZE = 4;
    private final ProposalRepository proposalRepository;
    private final CompanyService companyService;
    private final S3UploaderService s3UploaderService;

    @Override
    @Transactional
    public void writeProposal(User user, ProposalRequest request) {
        Company company = companyService.findById(request.getCompanyId());
        String fileUrl = Optional.ofNullable(request.getFile())
                .filter(image -> !image.isEmpty())
                .map(image -> s3UploaderService.uploadFile(image, UploadType.FILE.getDirectory()))
                .orElse(null);

        Proposal proposal = createProposal(user, request, fileUrl, company);

        proposalRepository.save(proposal);
    }

    @Override
    public Page<ProposalPreviewResponse> getProposals(User user, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Proposal> proposals = proposalRepository.findAllByCompany(user.getCompany(), pageable);
        return ProposalConverter.toProposalPreviewResponse(proposals);
    }

    private Proposal createProposal(User user, ProposalRequest request, String file, Company company) {
        return Proposal.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .company(company)
                .file(file).build();
    }
}
