import pt.isel.canvas.Canvas
import kotlin.math.abs

data class Position (val x : Int, val y : Int)
data class Velocity (val dx : Int, val dy :Int)
class Ball (val position : Position, val velocity : Velocity)

fun Ball.draw(canvas: Canvas) {

    canvas.drawCircle(position.x, position.y, radius, color)

}

fun Ball.move(xRacket : Int, area: Area): Ball {
    if (position.x + velocity.dx !in 0..area.width) {
        val newVelocity = newVelocity(-velocity.dx, velocity.dy)
        return Ball(Position(position.x + newVelocity.dx, position.y + newVelocity.dy), newVelocity)
    }

    if (position.y + velocity.dy < 0) {
        val newVelocity = newVelocity(velocity.dx,-velocity.dy)
        return Ball(Position(position.x + newVelocity.dx, position.y + newVelocity.dy), newVelocity)

    }

    if(position.x + velocity.dx > xRacket - RACKET_LENGTH/2 && position.x + velocity.dx < xRacket + RACKET_LENGTH/2 && position.y + velocity.dy > (height*RACKET_Y_PERCENTAGE_ON_SCREEN).toInt() && position.y + velocity.dy < (height*RACKET_Y_PERCENTAGE_ON_SCREEN).toInt() + 10) {
        val newVelocity = newVelocity(velocity.dx + area(xRacket),-velocity.dy)

        return Ball(Position(position.x + newVelocity.dx, position.y + newVelocity.dy), newVelocity)
    }

    return Ball(Position(position.x + velocity.dx, position.y + velocity.dy), velocity)
}

fun Ball.area(racketX : Int): Int {

    val distance = position.x - racketX // calcula a àrea em que a bola se encontra
    val newDx = if(abs(distance) < 20) 0 else if (abs(distance) < 35) 1 else 3

    return newDx * if(distance<0) -1 else 1

}

fun randomBall (): Ball {
    return Ball(Position(width/2, height/2), Velocity(3, 3))
}

fun newVelocity (newDx : Int, newDy : Int): Velocity {
    return Velocity(newDx.coerceIn(DX_RANGE), newDy.coerceIn(DY_RANGE))
}
