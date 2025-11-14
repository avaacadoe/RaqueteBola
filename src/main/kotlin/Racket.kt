import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

class Racket(val x : Int)

fun Racket.draw(canvas : Canvas) {
    canvas.drawRect(x-RACKET_LENGTH/2, (height*RACKET_Y_PERCENTAGE_ON_SCREEN).toInt(),RACKET_LENGTH,0,WHITE, 10)

}

fun Racket.move(newX : Int, area: Area): Racket {

    if (newX - RACKET_LENGTH/2 < 0) return this
    else if (newX + RACKET_LENGTH/2 > area.width) return this

    return Racket(newX)
}