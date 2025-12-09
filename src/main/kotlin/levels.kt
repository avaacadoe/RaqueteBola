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
            blockGRAY(4, 5),
            blockNORMAL(7, 3, 5),
            blockINDESTRUCTIBLE(6, 2)
        ));
        2 -> Level(
            listOf(
                // ======== CAMADA DE CIMA (simétrica) ========
                blockGRAY(5, 1),
                blockNORMAL(9, 2, 1),
                blockNORMAL(4, 3, 1),
                blockINDESTRUCTIBLE(4, 1),
                blockNORMAL(4, 9, 1),
                blockNORMAL(9, 10, 1),
                blockGRAY(11, 1),

                // ======== SEGUNDA LINHA ========
                blockNORMAL(3, 2, 2),
                blockGRAY(3, 2),
                blockNORMAL(7, 4, 2),
                blockINDESTRUCTIBLE(5, 2),
                blockINDESTRUCTIBLE(7, 2),
                blockNORMAL(7, 8, 2),
                blockGRAY(10, 2),
                blockNORMAL(3, 9, 2),

                // ======== TERCEIRA LINHA (CENTRAL SUPERIOR) ========
                blockGRAY(3, 3),
                blockNORMAL(8, 3, 3),
                blockNORMAL(2, 4, 3),
                blockNORMAL(1, 5, 3),
                blockNORMAL(1, 7, 3),
                blockNORMAL(2, 8, 3),
                blockNORMAL(8, 9, 3),
                blockGRAY(10, 3),

                // ======== LINHA CENTRAL (BARRA DE INDESTRUTÍVEIS) ========
                blockINDESTRUCTIBLE(3, 4),
                blockINDESTRUCTIBLE(4, 4),
                blockNORMAL(6, 5, 4),
                blockGRAY(6, 4),
                blockNORMAL(6, 7, 4),
                blockINDESTRUCTIBLE(8, 4),
                blockINDESTRUCTIBLE(9, 4),

                // ======== TERCEIRA LINHA (CENTRAL INFERIOR) ========
                blockGRAY(3, 5),
                blockNORMAL(8, 3, 5),
                blockNORMAL(2, 4, 5),
                blockNORMAL(1, 5, 5),
                blockNORMAL(1, 7, 5),
                blockNORMAL(2, 8, 5),
                blockNORMAL(8, 9, 5),
                blockGRAY(10, 5),

                // ======== SEGUNDA LINHA (INFERIOR) ========
                blockNORMAL(3, 2, 6),
                blockGRAY(3, 6),
                blockNORMAL(7, 4, 6),
                blockINDESTRUCTIBLE(5, 6),
                blockINDESTRUCTIBLE(7, 6),
                blockNORMAL(7, 8, 6),
                blockGRAY(10, 6),
                blockNORMAL(3, 9, 6),

                // ======== CAMADA DE BAIXO ========
                blockGRAY(5, 7),
                blockNORMAL(9, 2, 7),
                blockNORMAL(4, 3, 7),
                blockINDESTRUCTIBLE(4, 7),
                blockNORMAL(4, 9, 7),
                blockNORMAL(9, 10, 7),
                blockGRAY(11, 7)
            )
        );
        else -> Level(listOf<Block>())
    }
}