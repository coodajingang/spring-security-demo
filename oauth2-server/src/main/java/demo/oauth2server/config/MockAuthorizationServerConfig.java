package demo.oauth2server.config;

import demo.oauth2server.service.MockAuthExceptionEntrypoint;
import demo.oauth2server.service.MockClientDetailService;
import demo.oauth2server.service.MockUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class MockAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Bean
    public UserDetailsService mockUserDetailsService() {
        return new MockUserDetailService();
    }

    @Bean
    public ClientDetailsService mockClientDetailsService() {
        return new MockClientDetailService();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .authenticationEntryPoint(new MockAuthExceptionEntrypoint())
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(mockClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(new InMemoryTokenStore())
                .userDetailsService(mockUserDetailsService())
        ;
    }
}
