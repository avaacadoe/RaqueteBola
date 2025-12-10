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

fun blockGRAY(x: Int, y: Int) = Block(0, GridPosition(x, y), 2)
fun blockNORMAL(points: Int, x: Int, y: Int) = Block(points, GridPosition(x, y), 1) //  pOINTS CAN BE FROM 1 TO 9 EXCLUDING 5
fun blockINDESTRUCTIBLE(x: Int, y: Int) = Block(-1, GridPosition(x, y), 1, true)

fun getLevel(id: Int) : Level  {
    return when (id) {
        1 -> Level(
            listOf(
            blockNORMAL(9,1,2),
            blockNORMAL(8,1,3),
            blockNORMAL(7, 1,4),
            blockNORMAL(6,1, 5),
            blockNORMAL(4,1,6),
            blockNORMAL(3,1, 7),
            blockNORMAL(2,1,8),

                ));

        else -> Level(listOf<Block>())
    }
}