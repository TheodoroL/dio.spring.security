package dio.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll() // Permite acesso à página de login
                        .requestMatchers("/users").hasAnyRole("USERS", "MANAGERS")
                        .requestMatchers("/managers").hasRole("MANAGERS")
                        .anyRequest().authenticated() // Exige login para qualquer outra rot

                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true) // Redireciona para a home após login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL de logout
                        .logoutSuccessUrl("/login") // Redireciona para login após logout
                        .permitAll()
                );

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
            UserDetails user = User.builder()
                .username("theodorol")
                .password(passwordEncoder.encode("1234"))
                .roles("USERS")
                .build();

            UserDetails userTeste = User.builder()
                    .username("teste")
                    .password(passwordEncoder.encode("4321"))
                    .roles("USERS")
                    .build();UserDetails managerTeste = User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("4321"))
                    .roles("MANAGERS")
                    .build();

        return new InMemoryUserDetailsManager(user, userTeste, managerTeste);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
