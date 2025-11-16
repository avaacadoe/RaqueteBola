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

data class Area(val width : Int, val height : Int)

fun main() {

    onStart {
        var game = Game(listOf(), Racket(width/2), Area(width, height)) // ponto em que o jogo é atulizado
        val canvas = Canvas(game.area.width, game.area.height, BLACK) // desenha o canva


        canvas.onTimeProgress(MILLISECONDS_BETWEEN_FRAMES) {
            game.draw(canvas) // desenha o canvas atualizado no game

            game.ballList.forEach {
                if (it.position.y < 600) {
                    game = Game(game.ballList + it.move(game.racket.x, game.area) - it, game.racket, game.area) // atualiza o game quando uma bola entra
                } else {
                    game = Game(game.ballList - it, game.racket, game.area) // atualiza o game quando uma bola sai

                    if (game.ballList.count() == 0) {
                        canvas.close() // fecha o canva se não houver mais bolas no canvas
                    }

                }
            }
        }
        canvas.onTimeProgress(BALL_SPAWN_TIME) {
            game = game.addBall(randomBall()) // atualiza o game com uma nova bola na lista de bolas
        }
        canvas.onMouseMove { me ->
            game = game.moveRacket(me.x) // atualiza o game com a nova posição da raquete
        }
    }

    onFinish {}
}