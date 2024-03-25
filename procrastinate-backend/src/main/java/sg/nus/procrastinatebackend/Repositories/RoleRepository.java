package sg.nus.procrastinatebackend.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.nus.procrastinatebackend.Models.ERole;
import sg.nus.procrastinatebackend.Models.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(ERole name);
    
}
