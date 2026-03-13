import kotlin.random.Random

/*
 Projeto: Bulls and Cows (Mastermind)
 Objetivo: jogo de adivinhar a palavra oculta gerada pelo computador.
 O jogador tem um número limitado de tentativas para acertar.
*/


// Lê uma linha do console; se for nula, lança erro.
fun lerLinhaSeguro(): String {
    return readlnOrNull() ?: error("Entrada inválida!")
}


// Gera o texto com as regras do jogo.
fun apresentarJogoRegras(tamanhoPalavra: Int, maximoTentativas: Int, exemploSegredo: String): String =
    "Adivinhe a palavra secreta de $tamanhoPalavra letras em até $maximoTentativas tentativas. Exemplo: $exemploSegredo"


// Conta quantos acertos parciais existem
fun contaAcertosParciais(segredo: String, palpite: String): Int {
    var parciais = 0

    for (i in palpite.indices) {
        if (palpite[i] != segredo[i] && segredo.contains(palpite[i])) {
            parciais++
        }
    }

    return parciais
}


// Conta quantos acertos completos existem
fun contaAcertosExatos(segredo: String, palpite: String): Int {
    var exatos = 0

    for (i in segredo.indices) {
        if (segredo[i] == palpite[i]) {
            exatos++
        }
    }

    return exatos
}


// Gera uma palavra secreta
fun gerarSegredo(tamanhoPalavra: Int, alfabeto: String): String {
    val palavra = List(tamanhoPalavra) {
        alfabeto[Random.nextInt(alfabeto.length)]
    }.joinToString("")
    return palavra
}


// Verifica se acertou
fun verificaJogada(segredo: String, palpite: String): Boolean = segredo == palpite


// Verifica se a entrada é válida
fun entradaCorreta(entrada: String, tamanhoPalavra: Int, alfabeto: String): Boolean {

    if (entrada.length != tamanhoPalavra) {
        println("O tamanho do palpite deve ser $tamanhoPalavra caracteres! Tente novamente!")
        return false
    }

    for (letra in entrada) {
        if (!alfabeto.contains(letra)) {
            println("Todos os caracteres devem fazer parte do alfabeto $alfabeto! Tente novamente!")
            return false
        }
    }

    return true
}


// Lê entrada válida
fun entradaSegura(tamanhoPalavra: Int, alfabeto: String): String {

    var entrada: String

    do {
        println("Digite um palpite com $tamanhoPalavra letras:")
        entrada = lerLinhaSeguro()
    } while (!entradaCorreta(entrada, tamanhoPalavra, alfabeto))

    return entrada
}


// Mostra resultados da tentativa
fun mostrarResultados(segredo: String, palpite: String) {

    val acertosExatos = contaAcertosExatos(segredo, palpite)
    val acertosParciais = contaAcertosParciais(segredo, palpite)

    println("Acertos exatos: $acertosExatos | Acertos parciais: $acertosParciais")
}


// Verifica vitória
fun venceu(jogada: Boolean, tentativas: Int, maximoTentativas: Int): Boolean {
    return jogada && tentativas <= maximoTentativas
}


// Verifica derrota
fun perdeu(jogada: Boolean, tentativas: Int, maximoTentativas: Int): Boolean {
    return !jogada && tentativas >= maximoTentativas
}


// Loop principal
fun playGame(secret: String, tamanhoPalavra: Int, maximoTentativas: Int, alfabeto: String) {

    var tentativas = 0
    var jogada: Boolean
    var palpite: String

    do {

        println("Digite um palpite de $tamanhoPalavra letras.")
        palpite = entradaSegura(tamanhoPalavra, alfabeto)

        mostrarResultados(secret, palpite)

        jogada = verificaJogada(secret, palpite)

        tentativas++

        if (perdeu(jogada, tentativas, maximoTentativas)) {
            println("Você perdeu! A palavra era $secret")
            break
        }
        else if (venceu(jogada, tentativas, maximoTentativas)) {
            println("Parabéns! Você venceu!")
        }

    } while (!jogada)
}


// Função principal
fun main() {

    val tamanhoPalavra = 4
    val maximoTentativas = 4
    val exemploSegredo = "ACEB"
    val alfabeto = "ABCDEFGH"

    println(apresentarJogoRegras(tamanhoPalavra, maximoTentativas, exemploSegredo))

    playGame(
        gerarSegredo(tamanhoPalavra, alfabeto),
        tamanhoPalavra,
        maximoTentativas,
        alfabeto
    )
}