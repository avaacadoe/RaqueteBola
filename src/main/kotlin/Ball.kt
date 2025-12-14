import pt.isel.canvas.Canvas
import kotlin.math.abs

data class Position (val x : Int, val y : Int)// guarda os valores de posição da bola
data class Velocity (val dx : Int, val dy :Int) // guarda os valores da velocidade da bola
class Ball (val position : Position, val velocity : Velocity) // propriedades da bola

// Desenha a bola numa posição atual
fun Ball.draw(canvas: Canvas) {
    canvas.drawCircle(position.x, position.y, RADIUS, BALL_CALOR)
}

fun Ball.nextX() : Int = position.x + velocity.dx
fun Ball.nextY() : Int = position.y + velocity.dy

// Movimenta a bola e define colisões entre a bola, raquete e paredes
fun Ball.move(game: Game): Game {
    if (nextX() !in 0..game.area.width) {
        val newVelocity = newVelocity(-velocity.dx, velocity.dy)
        return game.changeBall(Ball(Position(position.x + newVelocity.dx, position.y + newVelocity.dy), newVelocity)) // se a bola não estiver entre 0..400 (paredes laterais), apenas inverte a direção  no eixo x
    }

    if (nextY() < 0) {
        val newVelocity = newVelocity(velocity.dx,-velocity.dy)
        return game.changeBall(Ball(Position(position.x + newVelocity.dx, position.y + newVelocity.dy), newVelocity)) // se a bola estiver fora do "teto", apenas inverter a direção no eixo y
    }

    if(collidedWithObj(game.racket.getTopRightPosition(), RACKET_LENGTH, 10)) {
        val newVelocity = newVelocity(velocity.dx + area(game.racket.x),-velocity.dy) // verifica se a bola colide com a raquete e caso seja verdadeiro, inverte e ajusta a direção dependendo do local do impacto

        return game.changeBall(Ball(Position(position.x + newVelocity.dx, position.y + newVelocity.dy), newVelocity)) // atualiza a bola com a nova velocidade
    }

    game.level.blockList.forEach {
        if(collidedWithObj(it.position.toNormalized(), BLOCK_WIDTH, BLOCK_HEIGHT)) {
            val newVelocity = newVelocity(velocity.dx,-velocity.dy)

            val novoBloco = it.collide()

            return Game(game.racket, game.area, game.level.updateBlock(it, novoBloco), Ball(Position(position.x + newVelocity.dx, position.y + newVelocity.dy), newVelocity), game.hasStarted, game.ballsLeft)
        }
    }


    return game.changeBall(Ball(Position(nextX(), nextY()), velocity))
}

fun Ball.area(racketX : Int): Int {

    val distance = position.x - racketX // calcula a àrea em que a bola se encontra
    val newDx = if(abs(distance) < 20) 0 else if (abs(distance) < 35) 1 else 3 // verifica a nova velocidade dependendo do local onde houve o impacto da bola na raquete

    return newDx * if(distance<0) -1 else 1 // retorna uma nova velocidade negativa, caso a distância da colisão seja negativa e retorna uma nova velocidade positiva caso a distância seja positiva

}

fun Ball.collidedWithObj(objPos: Position, objWidth: Int, objHeight: Int): Boolean {
    val closestX = position.x.coerceIn(objPos.x, objPos.x + objWidth)
    val closestY = position.y.coerceIn(objPos.y, objPos.y + objHeight)

    val distanceX = position.x - closestX
    val distanceY = position.y - closestY

    val distanceSquared = distanceX * distanceX + distanceY * distanceY
    return distanceSquared < DIAMETER
}

fun newVelocity (newDx : Int, newDy : Int): Velocity {
    return Velocity(newDx.coerceIn(DX_RANGE), newDy.coerceIn(DY_RANGE)) // cria uma nova velocidade dentro dos limites
}
