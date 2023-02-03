package demo.oauth2server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

@Slf4j
public class MockClientDetailService implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info("In Mock Client Detail Service: {}", clientId);

        BaseClientDetails client = new BaseClientDetails();
        client.setClientId(clientId);
        client.setClientSecret(String.format("{noop}%s", clientId));

        return client;
    }
}
