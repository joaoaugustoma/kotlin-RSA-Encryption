import java.math.BigInteger
import java.security.SecureRandom

private fun criptografiaRSA(msg: String) {
    val msgCifrada: String?
    val msgDecifrada: String?
    val n: BigInteger
    val d: BigInteger
    var e: BigInteger
    val bitlen = 2048

    // Escolha aleatória de dois números primos, P e Q
    val r = SecureRandom()
    val p = BigInteger(bitlen / 2, 100, r)
    val q = BigInteger(bitlen / 2, 100, r)

    // Calcula N pela multiplicação de P e Q
    n = p.multiply(q)

    // Calcula a função totiente phi(n) = (p-1)(q-1)
    val m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE))

    // Escolher um número inteiro "e" : 1 < e < phi(n) : "e" e phi(n) sendo primos entre si
    e = BigInteger("3")
    while (m.gcd(e).toInt() > 1) {
        e = e.add(BigInteger("2"))
    }

    // Calcula "d" sendo o inverso multiplicativo de "e".
    d = e.modInverse(m)
    // println("p : $p")
    // println("q : $q")
    // println("n : $n")
    // println("e : $e")
    // println("d : $d")

    println("Mensagem Original: $msg")

    // Encrypted message - RSA_encrypt
    msgCifrada = BigInteger(msg.toByteArray()).modPow(e, n).toString()
    println("Mensagem Cifrada: $msgCifrada")

    // Decrypted message - RSA_encrypt
    msgDecifrada = String(BigInteger(msgCifrada).modPow(d, n).toByteArray())
    println("Mensagem Limpa: $msgDecifrada")
}

fun main(args: Array<String>) {
    criptografiaRSA("Teste de criptografia RSA")
}