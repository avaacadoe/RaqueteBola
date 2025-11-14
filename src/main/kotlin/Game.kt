import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class Game(val ballList : List<Ball>, val racket : Racket, val area : Area)

fun Game.addBall(ball: Ball): Game {
    return Game(ballList + ball, racket, area)
}

fun Game.moveRacket(x: Int): Game {
    return Game(ballList, racket.move(x, area), area)
}

fun Game.draw(canvas: Canvas) {
    canvas.erase()

    ballList.forEach {
        it.draw(canvas)
    }
    racket.draw(canvas)

    canvas.drawText(width/2,(height*0.98).toInt(),ballList.count().toString(),WHITE,40)
}