package nl.inholland.it2bank.repository;

import jakarta.persistence.criteria.*;
import nl.inholland.it2bank.model.UserModel;
import nl.inholland.it2bank.model.UserRoles;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    default  List<UserModel> findUserByAttributes(Integer id, String firstName, String lastName, Long bsn, String phoneNumber, String email, UserRoles role) {
        return findAll(new Specification<UserModel>() {
            @Override
            public Predicate toPredicate(Root<UserModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (id != null) {
                    predicates.add(criteriaBuilder.equal(root.get("id"), id));
                }
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
                if (role != null) {
                    predicates.add(criteriaBuilder.equal(root.get("role"), role));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        });
    }

    List<UserModel> findAll(Specification<UserModel> userModelSpecification);

    //@Query("SELECT u FROM UserModel u WHERE u.email= :email")
    //Optional<UserModel> findUserByEmail(@Param("email") String email);
    Optional<UserModel> findUserByEmail(String email);
}


