import com.google.common.collect.ImmutableList
import java.awt.*
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.ImageIO
import javax.swing.*


class AntFarm {
    private val frame = JFrame("Ant Farm")
    private val terrainModel = TerrainModelImpl(Dimension(20, 20))
    private val terrain = Terrain(terrainModel)

    private val startButton = JButton("Start")
    private val pauseButton = JButton("Pause")
    private val stopButton = JButton("Stop")
    private val slowRadioButton = JRadioButton("Slow")
    private val regularRadioButton = JRadioButton("Regular")
    private val fastRadioButton = JRadioButton("Fast")
    private val superFastRadioButton = JRadioButton("Super fast")


    private var sessionRunner = SessionRunner(terrainModel, SessionRunner.Speed.REGULAR, object : SessionRunner.Listener {
        override fun statusChanged(oldStatus: SessionRunner.Status, newStatus: SessionRunner.Status) {
            startButton.isEnabled =
                newStatus == SessionRunner.Status.STOPPED || newStatus == SessionRunner.Status.PAUSED
            stopButton.isEnabled = newStatus == SessionRunner.Status.RUNNING
            pauseButton.isEnabled = newStatus == SessionRunner.Status.RUNNING
            if (newStatus == SessionRunner.Status.STOPPED) {
                terrainModel.reset()
            }
        }

        override fun shouldRepaint() {
            terrain.repaint()
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

        val speedPanel = JPanel()
        speedPanel.layout = BoxLayout(speedPanel, BoxLayout.Y_AXIS)
        speedPanel.border = BorderFactory.createTitledBorder("Speed")
        speedPanel.add(superFastRadioButton)
        speedPanel.add(fastRadioButton)
        speedPanel.add(regularRadioButton)
        speedPanel.add(slowRadioButton)

        val buttonGroup = ButtonGroup()
        buttonGroup.add(slowRadioButton)
        buttonGroup.add(regularRadioButton)
        buttonGroup.add(fastRadioButton)
        buttonGroup.add(superFastRadioButton)
        regularRadioButton.isSelected = true

        slowRadioButton.addActionListener { sessionRunner.speed = SessionRunner.Speed.SLOW }
        regularRadioButton.addActionListener { sessionRunner.speed = SessionRunner.Speed.REGULAR }
        fastRadioButton.addActionListener { sessionRunner.speed = SessionRunner.Speed.FAST }
        superFastRadioButton.addActionListener { sessionRunner.speed = SessionRunner.Speed.SUPER_FAST }

        controlContainer.add(speedPanel)

        frame.contentPane.add(controlContainer)

        frame.contentPane.add(terrain)
        terrain.preferredSize = Dimension(500, 500)
        frame.pack()
    }
}

interface TerrainModel {
    val dimensions: Dimension
    val nestPosition: Point
    val ants: List<Ant>
}

class Terrain(private val model: TerrainModel) : JPanel() {
    private val backgroundColor = Color.LIGHT_GRAY
    private val gridColor = Color.DARK_GRAY

    private val cellSize = 20
    private val dottedLineDotSize = 3

    private val antImage: Image
    private val antHillImage: Image

    private val ANT_IMAGE_SIZE = cellSize

    init {
        val antImageFile = File("res/ant2.png")
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

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        if (g == null) {
            return
        }
        val g2: Graphics2D = g as Graphics2D
        g2.color = backgroundColor
        g2.fillRect(g2.clipBounds.x, g2.clipBounds.y, g2.clipBounds.width, g2.clipBounds.height)

        drawGrid(g2, model.dimensions)

        drawImageInCell(g2, antHillImage, model.nestPosition)

        for (ant in model.ants) {
            if (ant.position != model.nestPosition) {
                drawImageInCell(g2, antImage, ant.position, ant.direction)
            }
        }
    }

    fun drawGrid(g: Graphics2D, dimensions: Dimension) {
        // TODO: don't draw out of the clip rect.
        g.color = gridColor
        for (i in 0..dimensions.width) {
//            g.drawLine(i, 0, i, dimensions.height)
            drawHorizontalDottedLine(g, 0, dimensions.width * cellSize, i * cellSize)
            drawVerticalDotedLine(g, i * cellSize, 0, dimensions.height * cellSize)

//             g.drawLine(0, i, dimensions.width, i)

        }
    }

    fun drawVerticalDotedLine(g: Graphics2D, x: Int, y1: Int, y2: Int) {
        for (i in y1..y2 step dottedLineDotSize * 2) {
            g.drawLine(x, i, x, i + dottedLineDotSize)
        }
    }

    fun drawHorizontalDottedLine(g: Graphics2D, x1: Int, x2: Int, y: Int) {
        for (i in x1..x2 step dottedLineDotSize * 2) {
            g.drawLine(i, y, i + dottedLineDotSize, y)
        }
    }

    private fun toSwingCoordinates(x: Int, y: Int, dimension: Dimension): Point {
        return Point(x * cellSize, (dimension.height - y) * cellSize)
    }

    /**
     * Draws the passed in image at the cell located at the x, y coordinate.
     * Cell coordinate origin (0,0) is at the bottom left. (as a normal person would expect)
     */
    private fun drawImageInCell(
        g: Graphics2D,
        image: Image,
        cellPosition: Point,
        direction: Direction = Direction.NORTH
    ) {
        // drawImage takes the top-left corner with the coordinate space's origin being at the top-left.
        val cellPositionSwing = toSwingCoordinates(cellPosition.x, cellPosition.y, model.dimensions)
        val g2 = g.create() as Graphics2D
        try {
            g2.rotate(
                directionToRotationInRadians(direction), cellPositionSwing.x + image.getWidth(null) / 2.0,
                cellPositionSwing.y - cellSize + image.getHeight(null) / 2.0
            )
            g2.drawImage(image, cellPositionSwing.x, cellPositionSwing.y - cellSize, null)
        } finally {
            g2.dispose()
        }
    }

    private fun directionToRotationInRadians(direction: Direction): Double {
        return when (direction) {
            Direction.NORTH -> Math.toRadians(0.0)
            Direction.NORTH_EAST -> Math.toRadians(45.0)
            Direction.EAST -> Math.toRadians(90.0)
            Direction.SOUTH_EAST -> Math.toRadians(135.0)
            Direction.SOUTH -> Math.toRadians(180.0)
            Direction.SOUTH_WEST -> Math.toRadians(225.0)
            Direction.WEST -> Math.toRadians(270.0)
            Direction.NORTH_WEST -> Math.toRadians(315.0)
        }
    }
}

class TerrainModelImpl(override val dimensions: Dimension) : TerrainModel {
    override val ants: List<Ant> = ImmutableList.of(Ant(1, this))
    override val nestPosition = Point(0, 0)

    fun reset() {
        for (ant in ants) {
            ant.position = Point(0, 0)
        }
    }
}

fun main() {
    EventQueue.invokeLater { AntFarm().show() }
}