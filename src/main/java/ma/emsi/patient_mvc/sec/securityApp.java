package ma.emsi.patient_mvc.sec;

import ma.emsi.patient_mvc.sec.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class securityApp extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode("5678")).roles("ADMIN", "USER");
//        System.out.println(passwordEncoder.encode("5678"));
        /*
        PasswordEncoder passwordEncoder = passwordEncoder();
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder.encode("1234")).roles("USER");
        //utiliser passwordencoder pour hasher le mot de pass


        //auth.inMemoryAuthentication()
        //        .withUser("user2").password("{noop}1234").roles("USER");

                //not good
                //si on utilise pas {noop} le mot de pass sera hasher, dans meme si on met
                //le mot de pass correct, on aura pas d'accet
                //si on utilise {noop}, le mot de pass sera pass hasher, dans si on met
                //1234 comme mot de pass, utilisateur sera authentifier.
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("5678")).roles("ADMIN", "USER");
        */

        //jdbc auth
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
//                .authoritiesByUsernameQuery("select username as principal, role from users_roles where username=?")
//                .rolePrefix("ROLE_")
//                .passwordEncoder(passwordEncoder);

        //user details services
        auth.userDetailsService(userDetailsService);


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(); //formulaire de login par defaut
        //http.formLogin().loginPage("/login"); ajouter la methode login dans le controlleur
        //pour creer notre propre formulaire de login
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().mvcMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().mvcMatchers("/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
