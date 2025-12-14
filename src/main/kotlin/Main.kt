import pt.isel.canvas.*

const val BLOCK_WIDTH = 32// largura do tijolo
const val BLOCK_HEIGHT = 15
const val WIDTH = BLOCK_WIDTH*13
const val HEIGHT = 600
const val RADIUS = 7
const val DIAMETER = RADIUS*RADIUS
const val BALL_CALOR = CYAN

const val MILLISECONDS_BETWEEN_FRAMES = 5

val DX_RANGE = -6..6
val DY_RANGE = -4..4

const val RACKET_LENGTH = 60
const val HALF_RACKET_LENGTH = RACKET_LENGTH/2
const val RACKET_Y_PERCENTAGE_ON_SCREEN = 0.9
const val RACKET_Y_POSITION = HEIGHT*RACKET_Y_PERCENTAGE_ON_SCREEN

fun main() {

    onStart {
        var game = Game(Racket(WIDTH/2), Area(WIDTH, HEIGHT), getLevel(1)) // ponto em que o jogo é atualizado. tenho de criar uma nova bola
        val canvas = Canvas(game.area.width, game.area.height, BLACK) // desenha o canva


        canvas.onTimeProgress(MILLISECONDS_BETWEEN_FRAMES) {
            game.draw(canvas) // desenha o canvas atualizado no game
        }

        canvas.onTimeProgress(MILLISECONDS_BETWEEN_FRAMES) {
            game = game.updateBall()
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