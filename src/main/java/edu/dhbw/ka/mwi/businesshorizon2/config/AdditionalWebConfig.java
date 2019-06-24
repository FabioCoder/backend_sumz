package edu.dhbw.ka.mwi.businesshorizon2.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class AdditionalWebConfig{
	
	//Adresse unter dem das Angular-Frontend erreichbar ist.
	@Value("${sumz.client.host}")
	private String clientHost;
	
	/**
	 * Adresse unter dem das Angular-Frontend erreichbar ist.
	 * @return Adresse des Angular Frontends
	 */
	public String getClientHost() {
		return clientHost;
	}
	
	/**
	 * Setzen der Adresse unter dem das Angular-Frontend erreichbar ist.
	 * @param Adresse des Angular Frontends
	 */
	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}
	
	/**
	 * Diese Methode stellt den Cors Filter ein. Cors (Cross-Origin Resource Sharing) ist ein Mechanismus, der Anfragen an eine von der Hauptdomäne abweichende Domäne ermöglicht.
	 * @return CorsFilter Objekt
	 */
    @Bean
    public CorsFilter corsFilter() {
    	System.out.println("************************* loading cors");
    	
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
