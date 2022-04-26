package ma.emsi.patient_mvc.sec.service;


import ma.emsi.patient_mvc.sec.entities.AppRole;
import ma.emsi.patient_mvc.sec.entities.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String username, String password, String confirmPassword);
    AppRole saveNewRole(String role, String description);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loadUserByUserName(String username);
}
