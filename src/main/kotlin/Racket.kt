import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE


class Racket(val x : Int)

// Desenha a raquete com as suas propriedades
fun Racket.draw(canvas : Canvas) {
    canvas.drawRect(x-RACKET_LENGTH/2, (height*RACKET_Y_PERCENTAGE_ON_SCREEN).toInt(),RACKET_LENGTH,0,WHITE, 10)

}

// Movimenta e limita a posição da raquete
fun Racket.move(newX : Int, area: Area): Racket {

    if (newX - RACKET_LENGTH/2 < 0) return this // se a metade esquerda da nova posição da raquete ultrapassar o limite, retornar a própria posição 0
    else if (newX + RACKET_LENGTH/2 > area.width) return this // se a metade direita da nova posição da raquete ultrapassar os limites, retorna a própria posição 600

    return Racket(newX) // se nenhuma das proposições for verdadeira, apenas retorna a nova posição em que a raquete se encontra
}

fun Racket.getPosition() : Position {
    return Position(x, (height*RACKET_Y_PERCENTAGE_ON_SCREEN).toInt()-10)
}