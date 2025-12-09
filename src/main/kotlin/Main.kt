import pt.isel.canvas.*

const val block_width = 32// largura do tijolo
const val block_height = 15
const val width = block_width*13
const val height = 600
const val radius = 7
const val color = CYAN

const val BALL_SPAWN_TIME =  5000
const val MILLISECONDS_BETWEEN_FRAMES = 10

val DX_RANGE = -6..6
val DY_RANGE = -4..4

const val RACKET_LENGTH = 60
const val RACKET_Y_PERCENTAGE_ON_SCREEN = 0.9

data class Area(val width : Int, val height : Int)

fun main() {

    onStart {
        var game = Game(Racket(width/2), Area(width, height), Level(List(1) {Block(WHITE, 10, GridPosition(4, 10), 2)})) // ponto em que o jogo é atualizado. tenho de criar uma nova bola
        val canvas = Canvas(game.area.width, game.area.height, BLACK) // desenha o canva


        canvas.onTimeProgress(MILLISECONDS_BETWEEN_FRAMES) {
            game = game.updateBall()
            game.draw(canvas) // desenha o canvas atualizado no game
        }

        canvas.onMouseMove { me ->
            game = game.moveRacket(me.x) // atualiza o game com a nova posição da raquete
        }

        canvas.onMouseDown {
            if (!game.hasStarted) {
                game = game.start()
            }
            game = game.checkBall()
        }
    }

    onFinish {}
}