import java.math.BigInteger

object Main {

  val MESSAGE = "Hello, RSA!"
  val PUBLIC_KEY = "aa"

  def main(args: Array[String]): Unit = {
    val N = 200
    val rsa = new RSA(PUBLIC_KEY, N)

    val messageInt = new BigInteger(MESSAGE.getBytes)
    val encryptedInt = rsa.encrypt(messageInt)
    val encryptedStr = new String(encryptedInt.toByteArray)
    val decryptedInt = rsa.decrypt(encryptedInt)
    val decryptedStr = new String(decryptedInt.toByteArray)
    println("message = " + MESSAGE)
    println("public key = " + PUBLIC_KEY)
    println("messageInt   = " + messageInt)
    println("encryptedInt = " + encryptedInt)
    println("encryptedStr = " + encryptedStr)
    println("decryptedInt = " + decryptedInt)
    println("decryptedStr = " + decryptedStr)
  }
}
