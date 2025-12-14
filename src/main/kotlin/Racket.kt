import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE


class Racket(val x : Int)

// Desenha a raquete com as suas propriedades
fun Racket.draw(canvas : Canvas) {
    canvas.drawRect(x-HALF_RACKET_LENGTH, (RACKET_Y_POSITION).toInt(),RACKET_LENGTH,0,WHITE, 10)
}

// Movimenta e limita a posição da raquete
fun Racket.move(newX : Int, area: Area): Racket {

    if (newX - HALF_RACKET_LENGTH < 0) return this // se a metade esquerda da nova posição da raquete ultrapassar o limite, retornar a própria posição 0
    else if (newX + HALF_RACKET_LENGTH > area.width) return this // se a metade direita da nova posição da raquete ultrapassar os limites, retorna a própria posição 600

    return Racket(newX) // se nenhuma das proposições for verdadeira, apenas retorna a nova posição em que a raquete se encontra
}

fun Racket.getPosition() : Position {
    return Position(x, (RACKET_Y_POSITION).toInt()-10)
}

fun Racket.getTopRightPosition() : Position {
    return Position(x-HALF_RACKET_LENGTH, (RACKET_Y_POSITION).toInt())
}