package com.panambystudio.helpdesk.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfiguration {

	@Bean(name="encryptorBean")
	public StringEncryptor stringEncryptor() {
		
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
	    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
	    
	    config.setPassword("WiJQxRszXu7aCx9ETP7W8vfWr2YzsCu5QQs49/w5zJY=");
	    config.setAlgorithm("PBEWithMD5AndDES");
	    config.setPoolSize("1");
	    config.setProviderName("SunJCE");
	    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
	    config.setStringOutputType("base64");
	    encryptor.setConfig(config);
	    
	    return encryptor;
	}
}
