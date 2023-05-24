package nl.inholland.it2bank.repository;

import nl.inholland.it2bank.model.AccountModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountModel, Long> {
    Optional<AccountModel> findByIban(String finalIban);
}