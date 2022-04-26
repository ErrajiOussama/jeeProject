package ma.emsi.patient_mvc.sec.repositories;

import ma.emsi.patient_mvc.sec.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRole(String roleName);
}
