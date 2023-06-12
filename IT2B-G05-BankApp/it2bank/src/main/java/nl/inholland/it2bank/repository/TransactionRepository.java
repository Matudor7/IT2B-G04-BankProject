package nl.inholland.it2bank.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import nl.inholland.it2bank.model.TransactionModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    default List<TransactionModel> findTransactionByAttributes(Integer userPerforming, String accountFrom, String accountTo, Double amount, LocalDateTime dateTime, String comment) {
        return findAll((Specification<TransactionModel>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userPerforming != null) {
                predicates.add(criteriaBuilder.equal(root.get("userPerforming"), userPerforming));
            }

            if (accountFrom != null && !accountFrom.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("accountFrom"), accountFrom));
            }

            if (accountTo != null && !accountTo.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("accountTo"), accountTo));
            }

            if (amount != null) {
                predicates.add(criteriaBuilder.equal(root.get("amount"), amount));
            }

            if (dateTime != null) {
                predicates.add(criteriaBuilder.equal(root.get("dateTime"), dateTime));
            }

            if (comment != null && !comment.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("comment"), comment));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    List<TransactionModel> findAll(Specification<TransactionModel> transactionModelSpecification);
}
