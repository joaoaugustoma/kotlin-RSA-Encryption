import java.math.BigInteger
import java.security.SecureRandom
import javax.swing.*

class HomeSwing : JFrame() {

    private val mensagem = JTextField("", 10)
    private val botao = JButton("Encriptar")

    init {
        val painel = JPanel()
        painel.add(mensagem)
        painel.add(botao)
        add(painel)
        botao.addActionListener {
            val texto = mensagem.text
            val encriptado = criptografiaRSA(texto)
            JOptionPane.showMessageDialog(null, encriptado)
        }
        setSize(300, 100)
        isVisible = true
        defaultCloseOperation = EXIT_ON_CLOSE
    }


    fun criptografiaRSA(msg: String) {
        val msgCifrada: String?
        val msgDecifrada: String?
        val n: BigInteger
        val d: BigInteger
        var e: BigInteger
        val bitlen = 4096

        // Escolha aleatória de dois números primos, P e Q
        val r = SecureRandom()
        val p = BigInteger(bitlen / 2, 100, r)
        val q = BigInteger(bitlen / 2, 100, r)

        // Calcula N pela multiplicação de P e Q
        n = p.multiply(q)

        // Calcula a função totiente pnhi() = (p-1)(q-1)
        //val m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE))
        val m = (p - BigInteger.ONE) * (q - BigInteger.ONE)

        // Escolher um número inteiro "e" : 1 < e < m : "e" e "m" sendo primos entre si
        e = BigInteger("65537")
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

        // Mensagem encriptada (e com n = chave pública)
        msgCifrada = BigInteger(msg.toByteArray()).modPow(e, n).toString()
        println("Mensagem Cifrada: $msgCifrada")

        // Mensagem decriptada (d com n = chave privada)
        msgDecifrada = String(BigInteger(msgCifrada).modPow(d, n).toByteArray())
        println("Mensagem Limpa: $msgDecifrada")
    }
}
