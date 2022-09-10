import com.google.common.collect.ImmutableList
import java.awt.*
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.ImageIO
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class AntFarm {

    private val defaultPeriod = 1000L

    private val frame = JFrame("Ant Farm")
    private val terrainModel = TerrainModelImpl(Dimension(400, 400))
    private val terrain = Terrain(terrainModel)

    private val startButton = JButton("Start")
    private val pauseButton = JButton("Pause")
    private val stopButton = JButton("Stop")

    private var sessionRunner = SessionRunner(terrainModel, defaultPeriod, object : SessionRunner.Listener {
        override fun statusChanged(oldStatus: SessionRunner.Status, newStatus: SessionRunner.Status) {
            startButton.isEnabled = newStatus == SessionRunner.Status.STOPPED || newStatus == SessionRunner.Status.PAUSED
            stopButton.isEnabled = newStatus == SessionRunner.Status.RUNNING
            pauseButton.isEnabled = newStatus == SessionRunner.Status.RUNNING
        }
    })

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setLocationRelativeTo(null)
         layout()

    }

    fun show() {
        frame.isVisible = true
    }

    private fun layout() {
        frame.contentPane.layout = FlowLayout(FlowLayout.LEFT)

        val controlContainer = JPanel()
        controlContainer.layout = BoxLayout(controlContainer, BoxLayout.Y_AXIS)

        startButton.addActionListener { sessionRunner.start() }
        pauseButton.addActionListener { sessionRunner.pause() }
        stopButton.addActionListener { sessionRunner.stop() }

        controlContainer.add(startButton)
        controlContainer.add(pauseButton)
        controlContainer.add(stopButton)

        frame.contentPane.add(controlContainer)

        frame.contentPane.add(terrain)
        terrain.preferredSize = Dimension(500, 500)
        frame.pack()
    }
}

interface TerrainModel {
    val dimensions: Dimension
    val nestPosition : Point
    val ants : List<Ant>
}

 class Terrain(private val model : TerrainModel) : JPanel() {
     private val backgroundColor = Color.LIGHT_GRAY
     private val gridColor = Color.DARK_GRAY

     private val cellSize = 20
     private val dottedLineDotSize = 3

     private val antImage : Image
     private val antHillImage : Image

     private val ANT_IMAGE_SIZE = 10

     init {
        val antImageFile = File("res/ant.jpg")
        if (!antImageFile.exists()) {
            throw FileNotFoundException()
        }
        val image = ImageIO.read(antImageFile)
        antImage = image.getScaledInstance(ANT_IMAGE_SIZE, ANT_IMAGE_SIZE, Image.SCALE_SMOOTH)
         val antHillImageFile = File("res/ant_hill.png")
         if (!antHillImageFile.exists()) {
             throw FileNotFoundException()
         }
         val image2 = ImageIO.read(antHillImageFile)
         antHillImage = image2.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH)
     }

     override fun paint(g: Graphics?) {
        super.paint(g)
        if (g == null) {
            return
        }
        g.color = backgroundColor
        g.fillRect(g.clipBounds.x, g.clipBounds.y, g.clipBounds.width, g.clipBounds.height)

         drawGrid(g, model.dimensions)

         drawImageInCell(g, antHillImage, model.nestPosition)

        for (ant in model.ants) {
            if (ant.position != model.nestPosition) {
                drawImageInCell(g, antImage, ant.position)
            }
        }
    }

     fun drawGrid(g: Graphics, dimensions : Dimension) {
         // TODO: don't draw out of the clip rect.
         g.color = gridColor
         for (i in 0..dimensions.width step cellSize) {
//            g.drawLine(i, 0, i, dimensions.height)
             drawHorizontalDottedLine(g, 0, dimensions.width, i)
             drawVerticalDotedLine(g, i, 0, dimensions.height)

//             g.drawLine(0, i, dimensions.width, i)

         }
     }

     fun drawVerticalDotedLine(g: Graphics, x : Int, y1 : Int, y2 : Int) {
         for (i in y1..y2 step dottedLineDotSize * 2) {
             g.drawLine(x, i, x, i + dottedLineDotSize)
         }
     }

     fun drawHorizontalDottedLine(g: Graphics, x1 : Int, x2 : Int, y : Int) {
         for (i in x1..x2 step dottedLineDotSize * 2) {
             g.drawLine(i, y, i + dottedLineDotSize, y)
         }
     }

     private fun toSwingCoordinates(x :Int, y: Int, dimension: Dimension) : Point {
         return Point(x,dimension.height - y)
     }

     /**
      * Draws the passed in image at the cell located at the x, y coordinate.
      * Cell coordinate origin (0,0) is at the bottom left. (as a normal person would expect)
      */
     private fun drawImageInCell(g: Graphics, image : Image, cellPosition : Point) {
        // drawImage takes the top-left corner with the coordinate space's origin being at the top-left.
         val cellPositionSwing = toSwingCoordinates(
                 cellPosition.x * cellSize, cellPosition.y * cellSize, model.dimensions)
         g.drawImage(image, cellPositionSwing.x, cellPositionSwing.y - cellSize, null)
     }

}

class TerrainModelImpl(override val dimensions: Dimension) : TerrainModel {
    override val nestPosition = Point(0, 0)

    override val ants: List<Ant>
        get() = ImmutableList.of(Ant(1, this))
}

fun main() {
    EventQueue.invokeLater { AntFarm().show() }
}