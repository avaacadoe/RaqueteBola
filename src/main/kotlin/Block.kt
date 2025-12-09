import pt.isel.canvas.BLACK
import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class GridPosition (val x : Int, val y : Int)// guarda os valores de posição da bola
data class Level (val blockList : List<Block>, val points: Int = 0)
data class Block(val color : Int, val points : Int, val position : GridPosition, val livesLeft : Int, val undestructable : Boolean = false)


fun GridPosition.toNormalized() : Position = Position(block_width*x, block_height*y)


fun Block.draw(canvas: Canvas) {

    val normalPosition = position.toNormalized()

    canvas.drawRect(normalPosition.x, normalPosition.y, block_width, block_height,color)
    canvas.drawRect(normalPosition.x, normalPosition.y, block_width, block_height, BLACK, 1)
}

fun Level.draw (canvas: Canvas) {

    canvas.drawText((width*0.5).toInt(), (height*0.95).toInt(), points.toString(), WHITE)

    blockList.forEach{
        it.draw(canvas)
    }
}

