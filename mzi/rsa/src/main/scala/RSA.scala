import java.math.BigInteger
import java.security.SecureRandom


class RSA(N: Int) {
  val one: BigInteger      = new BigInteger("1")
  val random: SecureRandom = new SecureRandom()

  val p: BigInteger = BigInteger.probablePrime(N/2, random)
  val q: BigInteger = BigInteger.probablePrime(N/2, random)
  val phi: BigInteger = (p.subtract(one)).multiply(q.subtract(one))

  val modulus    = p.multiply(q)
  val publicKey  = new BigInteger("65537")
  val privateKey = publicKey.modInverse(phi)

  def encrypt(message: BigInteger): BigInteger =  message.modPow(publicKey, modulus)

  def decrypt(encrypted: BigInteger): BigInteger = encrypted.modPow(privateKey, modulus)

  override def toString(): String = {
    s"public = ${publicKey}\nprivate = ${privateKey}\nmodulus = ${modulus}"
  }
}