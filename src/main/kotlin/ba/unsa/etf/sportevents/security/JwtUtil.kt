package ba.unsa.etf.sportevents.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import java.util.*

object JwtUtil {

    val file = java.io.File("src/main/resources/secretKey.txt")
    private var SECRET: String = file.readText().trim()
    private const val EXPIRATION_TIME = 864000000 // 10 days

    fun generateToken(id: String): String {
        return Jwts.builder()
            .setSubject(id)
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact()
    }

    fun getIdFromToken(token: String): String {
        val claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            // Invalid JWT signature
        } catch (e: MalformedJwtException) {
            // Invalid JWT format
        } catch (e: ExpiredJwtException) {
            // Expired JWT
        } catch (e: UnsupportedJwtException) {
            // Unsupported JWT
        } catch (e: IllegalArgumentException) {
            // JWT claims string is empty
        }
        return false
    }
}
