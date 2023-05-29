package nl.inholland.it2bank.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.inholland.it2bank.model.AccountModel;
import nl.inholland.it2bank.model.AccountStatus;
import nl.inholland.it2bank.model.AccountType;
import nl.inholland.it2bank.model.UserModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    default List<AccountModel> findAccountByAttributes(String iban, Integer ownerId, Integer statusId, Double amount, Integer absoluteLimit, Integer typeId) {
        return findAll(new Specification<AccountModel>() {
            @Override
            public Predicate toPredicate(Root<AccountModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<java.util.function.Predicate> predicates = new ArrayList<>();

                if (iban != null) {
                    predicates.add((java.util.function.Predicate) criteriaBuilder.equal(root.get("iban"), iban));
                }
                if (ownerId != null) {
                    predicates.add((java.util.function.Predicate) criteriaBuilder.equal(root.get("ownerId"), ownerId));
                }
                if (statusId != null) {
                    predicates.add((java.util.function.Predicate) criteriaBuilder.equal(root.get("statusId"), statusId));
                }
                if (amount != null) {
                    predicates.add((java.util.function.Predicate) criteriaBuilder.equal(root.get("amount"), amount));
                }
                if (absoluteLimit != null) {
                    predicates.add((java.util.function.Predicate) criteriaBuilder.equal(root.get("absoluteLimit"), absoluteLimit));
                }
                if (typeId != null) {
                    predicates.add((java.util.function.Predicate) criteriaBuilder.equal(root.get("typeId"), typeId));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }
    List<AccountModel> findAll(Specification<AccountModel> accountModelSpecification);

    Optional<AccountModel> findByIban(String finalIban);
}