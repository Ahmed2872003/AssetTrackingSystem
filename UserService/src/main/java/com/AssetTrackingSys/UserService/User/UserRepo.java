package com.AssetTrackingSys.UserService.User;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AssetTrackingSys.UserService.User.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id <> :id AND u.role_id <> :role_id")
    List<User> findAllExceptAdminAndId(@Param("id") Long excludedId, @Param("role_id") Integer excludedRole_id);

}
