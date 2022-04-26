package ma.emsi.patient_mvc.sec.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.patient_mvc.sec.entities.AppRole;
import ma.emsi.patient_mvc.sec.entities.AppUser;
import ma.emsi.patient_mvc.sec.repositories.AppRoleRepository;
import ma.emsi.patient_mvc.sec.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String confirmPassword) {
        if(!password.equals(confirmPassword)) throw new RuntimeException("Password not match");
        String hashedPw = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPw);
        appUser.setActive(true);
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String role, String description) {
        AppRole appRole = appRoleRepository.findByRole(role);
        if(appRole != null) throw new RuntimeException("Role " + role + " already exist!!");
        appRole = new AppRole();
        appRole.setRole(role);
        appRole.setDescription(description);
        AppRole savedAppRole =  appRoleRepository.save(appRole);
        return savedAppRole;
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) throw new RuntimeException("User not found!!");
        AppRole appRole = appRoleRepository.findByRole(role);
        if(appRole == null) throw new RuntimeException("Role not found!!");
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) throw new RuntimeException("User not found!!");
        AppRole appRole = appRoleRepository.findByRole(role);
        if(appRole == null) throw new RuntimeException("Role not found!!");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
