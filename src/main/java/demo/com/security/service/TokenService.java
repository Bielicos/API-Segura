package demo.com.security.service;

import demo.com.security.dto.LoginRequest;
import demo.com.security.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public  TokenService(JwtEncoder jwtEncoder,  UserRepository userRepository,  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String login (LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.email());
        // Se não achar o usuário pelo email ou se o login/senha estiver incorreto, vai causar esse exception :
        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) { // Se o email estiver invalido.
            throw new BadCredentialsException("Invalid email");
        }

        // A partir daqui, temos certeza que o Login está correto.

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(r -> r.getName())
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("myBackEnd") // Quem está criando o Token
                .subject(user.get().getUserId()) // O usuário que solicitou o Token
                .issuedAt(now) // Data de emissão do Token
                .expiresAt(now.plusSeconds(expiresIn)) // O tempo de expiração vai ser agora + 300 segundos
                .claim("scope", scopes)
                .build();

        // Para obter o valor do Token, devemos utilizar a nossa chave privada para criptografar esse Token criado acima.
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
