package demo.com.security.controller;

import demo.com.security.dto.LoginRequest;
import demo.com.security.dto.LoginResponse;
import demo.com.security.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public  TokenController(JwtEncoder jwtEncoder,  UserRepository userRepository,  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.email());
        // Se não achar o usuário pelo email ou se o login/senha estiver incorreto, vai causar esse exception :
        if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) { // Se o email estiver invalido.
            throw new BadCredentialsException("Invalid email");
        }

        // A partir daqui, temos certeza que o Login está correto.

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("myBackEnd") // Quem está criando o Token
                .subject(user.get().getUserId()) // O usuário que solicitou o Token
                .issuedAt(now) // Data de emissão do Token
                .expiresAt(now.plusSeconds(expiresIn)) // O tempo de expiração vai ser agora + 300 segundos
                .build(); // Para fechar, a gente coloca um .build

        // Para obter o valor do Token, devemos utilizar a nossa chave privada para criptografar esse Token criado acima.
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
}
