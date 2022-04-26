package ma.emsi.patient_mvc;

import ma.emsi.patient_mvc.entities.Patient;
import ma.emsi.patient_mvc.repositories.Patientreposetories;
import ma.emsi.patient_mvc.sec.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;


@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }
       //@Bean
        CommandLineRunner commandLineRunner(Patientreposetories patientreposetories){
        return args -> {
            patientreposetories.save(new Patient(1,"hassan",new Date(),false,129));
            patientreposetories.save(new Patient(5,"youkai",new Date(),true,169));
            patientreposetories.save(new Patient(7,"natsu",new Date(),true,122));
            patientreposetories.save(new Patient(120,"moha",new Date(),false,127));

            patientreposetories.findAll().forEach(p->{
                System.out.println(p.getNom());
            });
        };
        }
    //@Bean
    CommandLineRunner saveUser(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("oussama", "1234", "1234");
            securityService.saveNewUser("natsumi", "1234", "1234");
            securityService.saveNewUser("tate", "1234", "1234");
            securityService.saveNewUser("test", "1234", "1234");

            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");


            securityService.addRoleToUser("oussama", "USER");
            securityService.addRoleToUser("oussama", "ADMIN");
            securityService.addRoleToUser("tate", "ADMIN");
            securityService.addRoleToUser("natsumi", "USER");
        };
    }

}

