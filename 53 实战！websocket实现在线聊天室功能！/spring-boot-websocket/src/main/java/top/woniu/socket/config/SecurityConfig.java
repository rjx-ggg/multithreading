package top.woniu.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 授权 任何人都需要登录
        http.authorizeRequests(registry -> {
            registry.anyRequest().authenticated();
        });

        // 启用登录表单功能
        http.formLogin();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails woniu1 = User.withUsername("woniu1").password(passwordEncoder.encode("123456")).authorities("1", "2").build();
        UserDetails woniu2 = User.withUsername("woniu2").password(passwordEncoder.encode("123456")).authorities("1", "2").build();
        UserDetails woniu3 = User.withUsername("woniu3").password(passwordEncoder.encode("123456")).authorities("1", "2").build();
        return new InMemoryUserDetailsManager(woniu1, woniu2, woniu3);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
