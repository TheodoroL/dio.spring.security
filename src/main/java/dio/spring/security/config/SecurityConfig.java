package dio.spring.security.config;

import dio.spring.security.service.SecurityDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private SecurityDatabaseService securityDatabaseService;

    //configuramos nossa authentificação do nosso banco de dados
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityDatabaseService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(List.of(authProvider));
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       //configuração da auth das nossas rotas
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll() // Permite acesso à página de login
                        .requestMatchers("/users").hasAnyRole("USERS", "MANAGERS")
                        .requestMatchers("/managers").hasRole("MANAGERS")
                        .anyRequest().authenticated() // Exige login para qualquer outra rot

                )
                .httpBasic(Customizer.withDefaults()) // falamos que vai usar Http basic authentifcation
//                .formLogin(form -> form
//                        .defaultSuccessUrl("/", true) // Redireciona para a home após login
//                        .permitAll()
//                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL de logout
                        .logoutSuccessUrl("/login") // Redireciona para login após logout
                        .permitAll()
                );

        return http.build();
    }
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//            UserDetails user = User.builder()
//                .username("theodorol")
//                .password(passwordEncoder.encode("1234"))
//                .roles("USERS")
//                .build();
//
//            UserDetails userTeste = User.builder()
//                    .username("teste")
//                    .password(passwordEncoder.encode("4321"))
//                    .roles("USERS")
//                    .build();UserDetails managerTeste = User.builder()
//                    .username("manager")
//                    .password(passwordEncoder.encode("4321"))
//                    .roles("MANAGERS")
//                    .build();
//
//        return new InMemoryUserDetailsManager(user, userTeste, managerTeste);
//    }
    //criamos um bean para criptografia da senha dos users
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
