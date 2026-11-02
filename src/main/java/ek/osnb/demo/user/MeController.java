package ek.osnb.demo.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class MeController {

    record MeResponse(String username, List<String> roles) {}

    @GetMapping("/api/me")
    MeResponse getCurrentUser(Authentication auth) {

        String name = auth.getName();
        List<String> list = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(Objects::nonNull)
                .filter(role -> role.startsWith("ROLE_"))
                .map(role -> role.replace("ROLE_", ""))
                .toList();

        return new MeResponse(name, list);
    }
}
