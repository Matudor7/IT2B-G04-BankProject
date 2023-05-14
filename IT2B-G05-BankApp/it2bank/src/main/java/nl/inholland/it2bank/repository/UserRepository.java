package nl.inholland.it2bank.repository;

import nl.inholland.it2bank.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long>{
}
