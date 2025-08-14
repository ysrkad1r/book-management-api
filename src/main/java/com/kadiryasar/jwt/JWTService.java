package com.kadiryasar.jwt;

import com.kadiryasar.config.JWTProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JWTProperties jwtProperties;
    public String SECRET_KEY;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // "ROLE_ADMIN" seklinde aliyoruz
                .map(r -> r.replace("ROLE_","")) // "ROLE_"kismini silip sadece ADMIN YA DA USER gibi rol kalmasini sagladik
                .collect(Collectors.toList()); // bunlari String listesi haline getiriyor

        claims.put("roles",roles); //tokenin icindeki payloadi tasiyan claims kismina
                                    // roles : Roller seklinde rolleri gomuyor token olusmadan

        return Jwts.builder()
                .setClaims(claims) //claimsleri tokene yerlestirdik
                .setSubject(userDetails.getUsername()) //userin ismini setledi
                .setIssuedAt(new Date())               //tokenin uretilme zamani
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*2)) //tokenin gecerlilik suresi
                .signWith(getKey(), SignatureAlgorithm.HS256) //tokeni cozecek olan keyi ve sifreleme algoritmasini belirledi
                .compact(); //hepsini birlestirdi ve bu parametrelere gore bir token uretti
    }

    //tokenden alinan claims yani tokenin tasidigi bilgi icerisinden istenilen bilgiyi doner
    public <T> T exportToken(String token, Function<Claims, T> claimsFunction){
        Claims claims = getClaims(token);
        return claimsFunction.apply(claims);
    }

    //Token icerisinden claimsleri(payload)[token icerigi] parse eder
    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    //Token icerisinden usernameyi ceker
    public String getUsernameByToken(String token){
        return exportToken(token, Claims::getSubject);
    }


    public List<String> getRoles(String token){
        Claims claims = getClaims(token);
        return claims.get("roles",List.class);
    }

    //token icerisinden expireDate alarak token suresini simdiki sureye gore kiyaslar boolean doner
    public boolean isTokenValid(String token){
        Date expireDate = exportToken(token, Claims::getExpiration);
        return new Date().before(expireDate);
    }

    //Tokenin imzalanmasinda ve cozulmesinde kullanilacak keyi return eder
    //Token sifrelenirken veya sifresi cozulurken kullanilir
    public Key getKey(){
        this.SECRET_KEY = jwtProperties.getSecretKey();
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }















}
