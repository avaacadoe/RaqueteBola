import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class Game(val ballList : List<Ball>, val racket : Racket, val area : Area) // guarda informação acerca de todos os elementos do jogo

fun Game.addBall(ball: Ball): Game {
    return Game(ballList + ball, racket, area) // retorna uma lista com uma nova bola
}

fun Game.moveRacket(x: Int): Game {
    return Game(ballList, racket.move(x, area), area) // retorna a nova posição da raquete
}

fun Game.draw(canvas: Canvas) {
    canvas.erase() // elimina tudo o que está no canva para o frame seguinte

    ballList.forEach {
        it.draw(canvas)  // desenha todas as bolas da lista no canva
    }
    racket.draw(canvas) // desenha a raquete

    canvas.drawText(width/2,(height*0.98).toInt(),ballList.count().toString(),WHITE,40) // demonstra a contagem das bolas presentes no canva
}