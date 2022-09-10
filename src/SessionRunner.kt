import java.awt.EventQueue
import java.awt.event.ActionListener
import javax.swing.Timer

class SessionRunner(private val model: TerrainModel, var periodMs : Long, private val listener : Listener) {

    interface Listener {
        fun statusChanged(oldStatus : Status, newStatus : Status)
    }

    enum class Status {
        RUNNING, PAUSED, STOPPED
    }
    var status : Status = Status.STOPPED
    var lastIterationTimestamp : Long = 0

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

        moveAnts(model.ants)

        val now = System.currentTimeMillis()
        val delay = 0L.coerceAtLeast(periodMs - (now - lastIterationTimestamp))
        val timer = Timer(delay.toInt()) { nextStep() }
        timer.isRepeats = false
        timer.start()
    }

    private fun moveAnts(ants : List<Ant>) {
        for (ant in ants) {
            ant.move(3, CoinFlip.RandomFlipper, null)
        }
    }
}