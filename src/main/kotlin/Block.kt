import pt.isel.canvas.BLACK
import pt.isel.canvas.BLUE
import pt.isel.canvas.CYAN
import pt.isel.canvas.Canvas
import pt.isel.canvas.GREEN
import pt.isel.canvas.MAGENTA
import pt.isel.canvas.RED
import pt.isel.canvas.WHITE
import pt.isel.canvas.YELLOW

data class GridPosition (val x : Int, val y : Int)// guarda os valores de posição da bola
fun GridPosition.toNormalized() : Position = Position(block_width*x, block_height*y)

data class Block(val points : Int, val position : GridPosition, val livesLeft : Int, val indestructable : Boolean = false)

fun Block.draw(canvas: Canvas) {

    val normalPosition = position.toNormalized()

    canvas.drawRect(normalPosition.x, normalPosition.y, block_width, block_height, getColor())
    canvas.drawRect(normalPosition.x, normalPosition.y, block_width, block_height, BLACK, 1)
}

fun Block.getColor() : Int {
    return when (points) {
        0 -> 0x808080;
        1 -> WHITE;
        2 -> 0xFFA500;
        3 -> CYAN;
        4 -> GREEN;
        6 -> RED;
        7 -> BLUE;
        8 -> MAGENTA;
        9 -> YELLOW;
        -1 -> 0xFFD700;
        else -> 0x555;
    }
}

fun Block.hasCollided(ball : Ball) : Boolean {
    val normal = position.toNormalized()
    return ball.nextX() in normal.x ..normal.x+block_width && ball.nextY() in normal.y..normal.y+block_height
}

fun Block.collide(): Block {
    if (!indestructable) {
        return Block(points, position, livesLeft-1, false)
    }
    return this
}