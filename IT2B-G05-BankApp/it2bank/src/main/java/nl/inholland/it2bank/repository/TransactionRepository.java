package nl.inholland.it2bank.repository;

import nl.inholland.it2bank.model.TransactionModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionModel, Long>{
}
