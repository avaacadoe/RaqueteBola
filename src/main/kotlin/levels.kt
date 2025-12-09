import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class Level (val blockList : List<Block>, val points: Int = 0)
fun Level.draw (canvas: Canvas) {

    canvas.drawText((width*0.5).toInt(), (height*0.95).toInt(), points.toString(), WHITE)

    blockList.forEach{
        it.draw(canvas)
    }
}

fun get_level(id: Int) : Level  {
    return when (id) {
        1 -> Level(List(1) {Block(WHITE, 10, GridPosition(4, 10), 2)})
        else -> Level(listOf<Block>())
    }
}