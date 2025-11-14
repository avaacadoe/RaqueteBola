import pt.isel.canvas.*
import kotlin.math.abs

const val width = 400
const val height = 600
const val radius = 7
const val color = CYAN

const val BALL_SPAWN_TIME =  5000
const val MILLISECONDS_BETWEEN_FRAMES = 10

val DX_RANGE = -6..6
val DY_RANGE = -4..4

const val RACKET_LENGTH = 90
const val RACKET_Y_PERCENTAGE_ON_SCREEN = 0.9

data class Position (val x : Int, val y : Int)
data class Velocity (val dx : Int, val dy :Int)
class Ball (val position : Position, val velocity : Velocity)



class Racket(val x : Int)



data class Area(val width : Int, val height : Int)
data class Game(val ballList : List<Ball>, val racket : Racket, val area : Area)

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

fun Racket.draw(canvas : Canvas) {
    canvas.drawRect(x-RACKET_LENGTH/2, (height*RACKET_Y_PERCENTAGE_ON_SCREEN).toInt(),RACKET_LENGTH,0,WHITE, 10)

}

fun Racket.move(newX : Int, area: Area): Racket {

    if (newX - RACKET_LENGTH/2 < 0) return this
    else if (newX + RACKET_LENGTH/2 > area.width) return this

    return Racket(newX)
}

fun Game.addBall(ball: Ball): Game {
    return Game(ballList + ball, racket, area)
}

fun Game.moveRacket(x: Int): Game {
    return Game(ballList, racket.move(x, area), area)
}


fun Game.draw(canvas: Canvas) {
    ballList.forEach {
        it.draw(canvas)
    }
    racket.draw(canvas)
}

fun main() {

    onStart {
        var game = Game(listOf(), Racket(width/2), Area(width, height))
        val canvas = Canvas(game.area.width, game.area.height, BLACK)


        canvas.onTimeProgress(MILLISECONDS_BETWEEN_FRAMES) {
            canvas.erase()
            game.draw(canvas)
            canvas.drawText(width/2,(height*0.98).toInt(),game.ballList.count().toString(),WHITE,40)

            game.ballList.forEach {
                if (it.position.y < 600) {
                    game = Game(game.ballList + it.move(game.racket.x, game.area) - it, game.racket, game.area)
                } else {
                    game = Game(game.ballList - it, game.racket, game.area)

                    if (game.ballList.count() == 0) {
                        canvas.close()
                    }

                }
            }
        }
        canvas.onTimeProgress(BALL_SPAWN_TIME) {
             game = game.addBall(randomBall())
         }
        canvas.onMouseMove { me ->
            game = game.moveRacket(me.x)
        }
    }

    onFinish {}
}