import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class Level (val blockList : List<Block>, val points: Int = 0)

fun Level.draw (canvas: Canvas) {

    canvas.drawText((width*0.5).toInt(), (height*0.95).toInt(), points.toString(), WHITE)

    blockList.forEach{
        it.draw(canvas)
    }
}

fun Level.updateBlock(antigo: Block, novo: Block) : Level {
    if(novo.livesLeft == 0) {
        return Level(blockList-antigo, points+antigo.points)
    }
    return Level(blockList-antigo+novo, points)
}

fun bGRAY(x: Int, y: Int) = Block(0, GridPosition(x, y), 2)
fun bNORMAL(points: Int, x: Int, y: Int) = Block(points, GridPosition(x, y), 1)
fun bINDESTRUCTIBLE(x: Int, y: Int) = Block(-1, GridPosition(x, y), 1, true)

fun getLevel(id: Int) : Level  {
    return when (id) {
        1 -> Level(listOf(
            bGRAY(4, 5),
            bNORMAL(7, 3, 5),
            bINDESTRUCTIBLE(6, 2)
        ))
        else -> Level(listOf<Block>())
    }
}