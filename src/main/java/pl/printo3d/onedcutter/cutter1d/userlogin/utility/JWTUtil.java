package pl.printo3d.onedcutter.cutter1d.userlogin.utility;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil implements Serializable {

  private String secret = "secret";
  private static final long serialVersionUID = -2540185145626017488L;
  public static final long JWT_TOKEN_TIME = 2*60*5; // 2 sec?

  public String generateToken(UserDetails uDetails)
  {
    Map<String, Object> claim = new HashMap<>();

    return makeToken(claim, uDetails.getUsername());
  }

  private String makeToken(Map<String, Object> claim, String username) {

    return Jwts.builder()
    .setClaims(claim)
    .setSubject(username)
    .setIssuedAt(new Date(System.currentTimeMillis()))
    .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_TIME * 1000))
    .signWith(SignatureAlgorithm.HS512, secret)
    .compact();
  }

  public String getUsernameFromToken(String token)
  {
    return getClaimFromToken(token, Claims::getSubject);
  }

  private boolean czyTokenJestStaryJakParowaZZabki(String token)
  {
    final Date expirationDate = getExpirationDateFromToken(token);
    return expirationDate.before(new Date());
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolva) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolva.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  // validate
  public boolean validateToken(String token, UserDetails uDetails)
  {
    return ( uDetails.getUsername().equals(getUsernameFromToken(token)) && !czyTokenJestStaryJakParowaZZabki(token) );
  }


}
