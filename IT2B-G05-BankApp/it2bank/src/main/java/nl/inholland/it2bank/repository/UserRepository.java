package nl.inholland.it2bank.repository;

import jakarta.persistence.criteria.*;
import nl.inholland.it2bank.model.UserModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    default  List<UserModel> findUserByAttributes(String firstName, String lastName, Long bsn, String phoneNumber, String email, Integer roleId) {
        return findAll(new Specification<UserModel>() {
            @Override
            public Predicate toPredicate(Root<UserModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (firstName != null) {
                    predicates.add(criteriaBuilder.equal(root.get("firstName"), firstName));
                }
                if (lastName != null) {
                    predicates.add(criteriaBuilder.equal(root.get("lastName"), lastName));
                }
                if (bsn != null) {
                    predicates.add(criteriaBuilder.equal(root.get("bsn"), bsn));
                }
                if (phoneNumber != null) {
                    predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
                }
                if (email != null) {
                    predicates.add(criteriaBuilder.equal(root.get("email"), email));
                }
                if (roleId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("roleId"), roleId));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }

    List<UserModel> findAll(Specification<UserModel> userModelSpecification);
}


