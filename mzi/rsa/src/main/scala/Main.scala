import java.math.BigInteger

object Main {

  def main(args: Array[String]): Unit = {
    val N = Integer.parseInt("200")
    val key = new RSA(N)

    val MESSAGE = "Hello, RSA!"

    val intMsg: BigInteger = new BigInteger(MESSAGE.getBytes)

    val encrypted: BigInteger = key.encrypt(intMsg)
    val decrypted: BigInteger = key.decrypt(encrypted)
    println("message = " + MESSAGE)
    println("intMsg    = " + intMsg)
    println("encrypted = " + encrypted)
    println("decrypted = " + decrypted)
    println("decrypted message = " + new String(decrypted.toByteArray))
  }
}
