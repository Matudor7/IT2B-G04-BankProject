package nl.inholland.it2bank.repository;

import jakarta.persistence.criteria.*;
import nl.inholland.it2bank.model.BankAccountModel;
import nl.inholland.it2bank.model.UserModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountModel, Long> {

    @Query("SELECT b.iban FROM BankAccount b JOIN b.userModel u WHERE u.firstName = :firstName")
    Optional<String> findIbanByFirstName(@Param("firstName") String firstName);

    default List<BankAccountModel> findAccountByAttributes(String iban, Integer ownerId, Integer statusId, Double balance, Integer absoluteLimit, Integer typeId, String firstName) {
        return findAll(new Specification<BankAccountModel>() {
            @Override
            public Predicate toPredicate(Root<BankAccountModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (iban != null) {
                    predicates.add(criteriaBuilder.equal(root.get("iban"), iban));
                }
                if (ownerId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("ownerId"), ownerId));

                    if (firstName != null) {
                        Join<BankAccountModel, UserModel> userModelJoin = root.join("user_model");
                        predicates.add(criteriaBuilder.equal(userModelJoin.get("first_name"), firstName));
                    }
                }
                if (statusId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("statusId"), statusId));
                }
                if (balance != null) {
                    predicates.add(criteriaBuilder.equal(root.get("balance"), balance));
                }
                if (absoluteLimit != null) {
                    predicates.add(criteriaBuilder.equal(root.get("absoluteLimit"), absoluteLimit));
                }
                if (typeId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("typeId"), typeId));
                }
                if (firstName != null) {
                    Subquery<Integer> subquery = query.subquery(Integer.class);
                    Root<UserModel> userRoot = subquery.from(UserModel.class);
                    subquery.select(userRoot.get("id"));
                    subquery.where(criteriaBuilder.equal(userRoot.get("firstName"), firstName));

                    predicates.add(criteriaBuilder.in(root.get("ownerId")).value(subquery));
                }


                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }
    List<BankAccountModel> findAll(Specification<BankAccountModel> accountModelSpecification);

    Optional<BankAccountModel> findByIban(String finalIban);
}