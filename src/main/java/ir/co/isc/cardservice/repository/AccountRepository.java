package ir.co.isc.cardservice.repository;


import ir.co.isc.cardservice.dao.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByAccountNumber(String accountNumber);
}
