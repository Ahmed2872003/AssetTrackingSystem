package com.AssetTrackingSys.UserService.User;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetTrackingSys.UserService.User.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id <> :id AND u.role_id <> :excludedRoleId")
    List<User> findAllExcludingRole(@Param("id") Long id, @Param("excludedRoleId") Integer excludedRoleId);

    @Query("SELECT u FROM User u WHERE u.id <> :id AND u.role_id IN :allowedRoles")
    List<User> findAllWithAllowedRoles(@Param("id") Long id, @Param("allowedRoles") List<Integer> allowedRoles);

}
