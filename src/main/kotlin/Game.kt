import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class Game(val racket : Racket, val area : Area, val ball : Ball = Ball(Position(208,300), Velocity(0,0)), val hasStarted : Boolean = false, val ballsLeft :Int = 6) // guarda informação acerca de todos os elementos do jogo

fun Game.moveRacket(x: Int): Game {
    return Game(racket.move(x, area), area, ball, hasStarted, ballsLeft) // retorna a nova posição da raquete
}

fun Game.checkBall(): Game {
    if(ball.position.y > area.height && ballsLeft > 0) {
        return Game(racket, area, Ball(racket.getPosition(), Velocity(0,0)), false, ballsLeft - 1)
    }

    return this
}

fun Game.draw(canvas: Canvas) {
    canvas.erase() // elimina tudo o que está no canva para o frame seguinte

    ball.draw(canvas)  // desenha todas as bolas da lista no canva
    racket.draw(canvas) // desenha a raquete

 //  canvas.drawText(width/2,(height*0.98).toInt(),ball.count().toString(),WHITE,40) // demonstra a contagem das bolas presentes no canva
}

fun Game.start () : Game = Game(racket, area, Ball(ball.position, Velocity(DX_RANGE.random(),-4)), true,ballsLeft)


fun Game.updateBall() : Game {
    if(!hasStarted) {
        return Game(racket, area, Ball(racket.getPosition(), Velocity(0,0)), false,ballsLeft)
    }
    else {
        return Game(racket, area, ball.move(racket.x, area), hasStarted, ballsLeft)
    }
}

// pegar no nº restante das bolas- 1 e desenhar a bola, posso usar um for  