package nl.inholland.it2bank.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nl.inholland.it2bank.model.BankAccountModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountModel, Long> {

    default List<BankAccountModel> findAccountByAttributes(String iban, Integer ownerId, Integer statusId, Double balance, Integer absoluteLimit, Integer typeId) {
        return findAll(new Specification<BankAccountModel>() {
            @Override
            public Predicate toPredicate(Root<BankAccountModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (iban != null) {
                    predicates.add(criteriaBuilder.equal(root.get("iban"), iban));
                }
                if (ownerId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("ownerid"), ownerId));
                }
                if (statusId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("statusid"), statusId));
                }
                if (balance != null) {
                    predicates.add(criteriaBuilder.equal(root.get("balance"), balance));
                }
                if (absoluteLimit != null) {
                    predicates.add(criteriaBuilder.equal(root.get("absolutelimit"), absoluteLimit));
                }
                if (typeId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("typeid"), typeId));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }
    List<BankAccountModel> findAll(Specification<BankAccountModel> accountModelSpecification);

    Optional<BankAccountModel> findByIban(String finalIban);
}