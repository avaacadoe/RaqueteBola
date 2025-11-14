import pt.isel.canvas.*

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

class Racket(val x : Int)

data class Area(val width : Int, val height : Int)
data class Game(val ballList : List<Ball>, val racket : Racket, val area : Area)


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

fun Game.drawAndMove(canvas: Canvas): Game {
    racket.draw(canvas)
    var new_new = this
    ballList.forEach {

        if(it.position.y < area.height) {
            val new = it.move(racket.x, area)
            new.draw(canvas)
            new_new = Game(new_new.ballList + new - it, new_new.racket, new_new.area)
        } else {
            if(ballList.count() - 1 == 0) {
                canvas.close()
            }

            new_new = Game(new_new.ballList - it, new_new.racket, new_new.area)
        }
    }

    return new_new
}

fun main() {

    onStart {
        var game = Game(listOf(), Racket(width/2), Area(width, height))
        val canvas = Canvas(game.area.width, game.area.height, BLACK)


        canvas.onTimeProgress(MILLISECONDS_BETWEEN_FRAMES) {
            canvas.erase()
            game = game.drawAndMove(canvas)
            canvas.drawText(width/2,(height*0.98).toInt(),game.ballList.count().toString(),WHITE,40)
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