package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Recuperar o token do cabecalho
        var tokenJWT = recuperarToken(request);
        System.out.println("dofilter" + tokenJWT);
        //Verificar se o token esta válido e obter subject (usuario) gerado na criacao do token
       if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            //se passou ate aqui o token está valido. Precisa avisar o sprint que considere que essa pessoa
            //esta autenticada para essa requisição.
           // Precisa carregar objeto Usuario passando subjet(email)
            System.out.println("subject => " + subject);
            var usuario = repository.findByLogin(subject);

            //Seta para o Spring que o usuario esta logado
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);  //para chamar o proximo filtro, segue fluxo. Caso quiser interromper não chama
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
