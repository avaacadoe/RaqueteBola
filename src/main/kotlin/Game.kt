import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class Area(val width : Int, val height : Int)
data class Game(val racket : Racket, val area : Area, val level : Level, val ball : Ball = Ball(Position(208,300), Velocity(0,0)), val hasStarted : Boolean = false, val ballsLeft :Int = 6, val currentLevel: Int = 1) // guarda informação acerca de todos os elementos do jogo

fun Game.moveRacket(x: Int): Game {
    return Game(racket.move(x, area), area, level, ball, hasStarted, ballsLeft, currentLevel) // retorna a nova posição da raquete
}

fun Game.checkBall(): Game {
    if(ball.position.y > area.height && ballsLeft > 0) {
        return Game(racket, area, level, Ball(racket.getPosition(), Velocity(0,0)), false, ballsLeft - 1, currentLevel)
    }

    return this
}

fun Game.draw(canvas: Canvas) {
    canvas.erase() // elimina tudo o que está no canva para o frame seguinte

    ball.draw(canvas)  // desenha todas as bolas da lista no canva
    racket.draw(canvas) // desenha a raquete
    level.draw(canvas)

    canvas.drawText(area.width - 100, area.height-15, "Level: $currentLevel", WHITE, 20)
    canvas.drawText(WIDTH/2, area.height-15, level.points.toString(), WHITE)

    for (i in 1..ballsLeft) {
        canvas.drawCircle(5 + 15*i, area.height - 15, RADIUS, BALL_CALOR)
    }
 //  canvas.drawText(width/2,(height*0.98).toInt(),ball.count().toString(),WHITE,40) // demonstra a contagem das bolas presentes no canva
}

fun Game.start () : Game = Game(racket, area,  level,Ball(ball.position, Velocity(0,-4)), true, ballsLeft, currentLevel)

fun Game.changeBall(ball: Ball) : Game = Game(racket, area, level, ball, hasStarted, ballsLeft, currentLevel)

fun Game.updateBall() : Game {
    return if(!hasStarted) {
        changeBall(Ball(racket.getPosition(), Velocity(0,0)))
    }
    else {
        ball.move(this)
    }
}

fun Game.nextLevel() : Game {
    return  Game(Racket(WIDTH/2), Area(WIDTH, HEIGHT), getLevel(currentLevel+1), currentLevel = currentLevel+1)
}
// pegar no nº restante das bolas- 1 e desenhar a bola, posso usar um for  