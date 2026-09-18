package artisanat.artisanat.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;

@Configuration
public class PaypalConfig {

    @Value("")
    private String clientId;

    @Value("")
    private String clientSecret;

    @Value("sandbox")
    private String mode;

    @Bean
    public APIContext apiContext(){
        return new APIContext(clientId,clientSecret,mode);
    }
}
