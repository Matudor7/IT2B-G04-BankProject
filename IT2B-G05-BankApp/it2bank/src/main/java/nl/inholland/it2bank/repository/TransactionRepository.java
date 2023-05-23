package nl.inholland.it2bank.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import nl.inholland.it2bank.model.TransactionModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    default List<TransactionModel> findByAttributes(int userPerforming, String accountFrom, String accountTo, double amount, LocalTime time, String comment) {
        return findAll(new Specification<TransactionModel>() {
            @Override
            public Predicate toPredicate(Root<TransactionModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (userPerforming > 0) {
                    predicates.add(criteriaBuilder.equal(root.get("userPerforming"), userPerforming));
                }

                if (accountFrom != null && !accountFrom.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("accountFrom"), accountFrom));
                }

                if (accountTo != null && !accountTo.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("accountTo"), accountTo));
                }

                if (amount > 0) {
                    predicates.add(criteriaBuilder.equal(root.get("amount"), amount));
                }

                if (time != null) {
                    predicates.add(criteriaBuilder.equal(root.get("time"), time));
                }

                if (comment != null && !comment.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(root.get("comment"), comment));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }

    List<TransactionModel> findAll(Specification<TransactionModel> transactionModelSpecification);
}
