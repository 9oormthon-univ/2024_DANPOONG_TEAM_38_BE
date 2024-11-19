package boosters.fundboost.company.repository;


import boosters.fundboost.company.domain.Company;
import boosters.fundboost.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, CustomCompanyRepository{
    Optional<Company> findByUser(User user);
    Optional<Company> findByBusinessNumber(String businessNumber);
}