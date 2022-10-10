import java.awt.EventQueue
import java.awt.Point
import javax.swing.Timer
import kotlin.random.Random

class SessionRunner(private val model: TerrainModel, var speed : Speed = Speed.REGULAR, private val listener : Listener) {

    enum class Speed(val periodMs : Long) {
        SLOW(3000),
        REGULAR(1000),
        FAST(200),
        SUPER_FAST(50)
    }

    interface Listener {
        fun statusChanged(oldStatus : Status, newStatus : Status)
        fun shouldRepaint()
    }

    enum class Status {
        RUNNING, PAUSED, STOPPED
    }
    var status : Status = Status.STOPPED

    fun start() {
        if (!setStatus(Status.RUNNING)) {
            return
        }
        EventQueue.invokeLater { nextStep() }
    }

    fun pause() {
        setStatus(Status.PAUSED)
    }

    fun stop() {
        setStatus(Status.STOPPED)
    }

    @JvmName("setStatus1")
    private fun setStatus(newStatus : Status) : Boolean {
        if (newStatus == status) {
            return false
        }
        EventQueue.invokeLater { listener.statusChanged(status, newStatus) }
        status = newStatus
        return true
    }

    private fun nextStep() {
        if (status != Status.RUNNING) {
            return
        }

        val before = System.currentTimeMillis()
        moveAnts(model.ants)

        val delay = 0L.coerceAtLeast(speed.periodMs - (System.currentTimeMillis() - before))
        val timer = Timer(delay.toInt()) { nextStep() }
        timer.isRepeats = false
        timer.start()
    }

    private fun moveAnts(ants : List<Ant>) {
        for (ant in ants) {
            println("Move ants was at ${ant.position}")
            ant.move(Random.Default, object: Ant.PositionListener {
                override fun positionChanged(oldPosition: Point, newPosition: Point) {
                    listener.shouldRepaint()
                }

                override fun directionChanged(oldDirection: Direction, newDirection: Direction) {
                    listener.shouldRepaint()
                }
            })
        }
    }
}