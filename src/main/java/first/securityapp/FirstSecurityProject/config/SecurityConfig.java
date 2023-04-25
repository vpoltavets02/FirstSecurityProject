package first.securityapp.FirstSecurityProject.config;

import first.securityapp.FirstSecurityProject.services.PeopleDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PeopleDetailsService peopleDetailsService;

    public SecurityConfig(PeopleDetailsService peopleDetailsService) {
        this.peopleDetailsService = peopleDetailsService;
    }

    //set up an authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(peopleDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    //set up spring security and  authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin")
                .hasRole("ADMIN")
                .antMatchers("/error", "/auth/registration", "/auth/login") // these rows
                .permitAll() //
                .anyRequest()
                .hasAnyRole("USER", "ADMIN")
                // set up authorization
                .and()
                .formLogin() // these rows
                .loginPage("/auth/login")                       //
                .loginProcessingUrl("/process_login")                      //
                .defaultSuccessUrl("/hello", true) //
                .failureUrl("/auth/login?error")                         // set up login page
                .and()
                .logout() // these rows
                .logoutUrl("/logout") //
                .logoutSuccessUrl("/auth/login"); // set up logout
//        .csrf().disable() // отключение csrf токена (защита от межсайтовой подделки запросов)
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }
}
