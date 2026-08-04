package com.mobilemoney.account.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobilemoney.account.domain.service.PasswordEncoder;

@Service
public class BCryptPasswordEncoderService
        implements PasswordEncoder {

	//on applique le BCrypt sur le mot de passe 
	
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    @Override
    public String encoder(String motDePasse) {

        return encoder.encode(motDePasse);

    }

    @Override
    public boolean matches(
            String motDePasse,
            String motDePasseHache) {

        return encoder.matches(
                motDePasse,
                motDePasseHache);

    }

}