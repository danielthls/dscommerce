package com.springprofessional.dscommerce.repositories;

import com.springprofessional.dscommerce.entities.User;
import com.springprofessional.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value =
            "SELECT u.email AS username, u.password, r.id AS roleid, r.authority " +
                    "FROM tb_user u " +
                    "INNER JOIN tb_user_role ur on u.id = ur.user_id " +
                    "INNER JOIN tb_role r on r.id = ur.role_id " +
                    "WHERE u.email = :email")
    List<UserDetailsProjection> searchRolesAndUserByRoles(String email);

    Optional<User> findByEmail(String email);
}
