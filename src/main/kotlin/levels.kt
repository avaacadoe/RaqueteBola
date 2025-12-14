import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

data class Level (val blockList : List<Block>, val points: Int = 0)

fun Level.draw (canvas: Canvas) {

    canvas.drawText(WIDTH/2, (HEIGHT*0.96).toInt(), points.toString(), WHITE)

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


// FUNC <listadePointos: Lista de Int, x, y: Retorna Lista de Blocos, Para cada pontos crie 3 blocos um ao lado do outro, adicione 1 ao x dependendo do id, e cada ponto no y diferente (+1)

fun gerarCluster(listPoints : List<Int>, x : Int, y : Int) : List<Block> {
    var y_c = y
    var list = listOf<Block>()
    listPoints.forEach {
        list = list + blockNORMAL(it,x,y_c)
        list = list + blockNORMAL(it,x + 1, y_c)
        list = list + blockNORMAL(it, x + 2, y_c)
        y_c++
    }

    return list
}

fun getLevel(id: Int) : Level  {
    return when (id) {
        1 -> Level(
            gerarCluster(listOf(9,8,7,6,4,3,2,1), 1, 2) +
                    gerarCluster(listOf(2,3,4,6,7,8), 5, 3) +

                    gerarCluster(listOf(9,8,7,6,4,3,2,1), 9, 2) +
            listOf<Block>(blockNORMAL(1,5,2 ),blockINDESTRUCTIBLE(6,2),blockNORMAL(1,7,2),blockGRAY(5,9),blockGRAY(6,9),blockGRAY(7,9))
        );

        else -> Level(listOf<Block>())
    }
}