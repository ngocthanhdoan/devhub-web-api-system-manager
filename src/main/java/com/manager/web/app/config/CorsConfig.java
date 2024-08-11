package com.manager.web.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.manager.web.app.api.repository.DevhubApiCrossRepository;
import com.manager.web.app.api.vo.DevhubApiCross;

import java.util.List;
@Configuration
public class CorsConfig {

	@Autowired
	private DevhubApiCrossRepository apiCrossRepository;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		List<DevhubApiCross> corsConfigs = apiCrossRepository.findAll();
		if (corsConfigs != null) {
			for (DevhubApiCross config : corsConfigs) {
				CorsConfiguration corsConfiguration = new CorsConfiguration();
				System.out.println("AllowedOrigins:" + List.of(config.getAllowedOrigins().split(",")));
				corsConfiguration.setAllowedOrigins(List.of(config.getAllowedOrigins().split(",")));
				corsConfiguration.setAllowedMethods(List.of(config.getAllowedMethods().split(",")));
				corsConfiguration.setAllowedHeaders(List.of(config.getAllowedHeaders().split(",")));
				corsConfiguration.setExposedHeaders(List.of(config.getExposedHeaders().split(",")));
				corsConfiguration.setAllowCredentials(config.getAllowCredentials());
				corsConfiguration.setMaxAge(config.getMaxAge() != null ? config.getMaxAge().longValue() : 3600L);

				source.registerCorsConfiguration(config.getUriApi(), corsConfiguration);
			}
		}

		return new CorsFilter(source);
	}
}
