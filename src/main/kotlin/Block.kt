import pt.isel.canvas.BLACK
import pt.isel.canvas.Canvas

data class GridPosition (val x : Int, val y : Int)// guarda os valores de posição da bola
fun GridPosition.toNormalized() : Position = Position(block_width*x, block_height*y)

data class Block(val color : Int, val points : Int, val position : GridPosition, val livesLeft : Int, val undestructable : Boolean = false)

fun Block.draw(canvas: Canvas) {

    val normalPosition = position.toNormalized()

    canvas.drawRect(normalPosition.x, normalPosition.y, block_width, block_height,color)
    canvas.drawRect(normalPosition.x, normalPosition.y, block_width, block_height, BLACK, 1)
}

fun Block.collided(ball : Ball) : Boolean {
    val normal = position.toNormalized()
    return ball.nextX() in normal.x..normal.x+block_width && ball.nextY() in normal.y..normal.y+block_height
}

