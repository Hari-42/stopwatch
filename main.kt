import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

fun main() {
    SwingUtilities.invokeLater { createAndShowGUI() }
}

fun createAndShowGUI() {
    val frame = JFrame("Stopwatch")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(800, 600)

    val cardLayout = CardLayout()
    val cards = JPanel(cardLayout)

    // ==== Seite 1: Stopwatch ====
    val stopwatchPanel = createStopwatchPanel()

    // ==== Seite 2: Leer ====
    val leerPanel = JPanel().apply {
        background = Color(50, 50, 50)
        layout = BorderLayout()
        val label = JLabel("Timer", SwingConstants.CENTER)
        label.foreground = Color.LIGHT_GRAY
        label.font = Font("SansSerif", Font.PLAIN, 32)
        add(label, BorderLayout.CENTER)
    }

    // ==== Karten hinzufügen ====
    cards.add(stopwatchPanel, "stopwatch")
    cards.add(leerPanel, "timer")

    // ==== Seiten-Wechsler Button Panel ====
    val topBar = JPanel().apply {
        layout = FlowLayout(FlowLayout.LEFT)
        background = Color(20, 20, 20)
        val toStopwatch = JButton("Stopwatch").apply {
            addActionListener { cardLayout.show(cards, "stopwatch") }
        }
        val toLeer = JButton("Timer").apply {
            addActionListener { cardLayout.show(cards, "timer") }
        }
        add(toStopwatch)
        add(toLeer)
    }

    frame.layout = BorderLayout()
    frame.add(topBar, BorderLayout.NORTH)
    frame.add(cards, BorderLayout.CENTER)
    frame.isVisible = true
}

fun createStopwatchPanel(): JPanel {
    var elapsed = 0
    var running = false

    val titleLabel = JLabel("Stopwatch", SwingConstants.CENTER).apply {
        foreground = Color.WHITE
        font = Font("SansSerif", Font.BOLD, 48)
        border = BorderFactory.createEmptyBorder(30, 0, 10, 0)
    }

    val timeLabel = JLabel("00:00.00", SwingConstants.CENTER).apply {
        foreground = Color.WHITE
        font = Font("Monospaced", Font.BOLD, 60)
        border = BorderFactory.createEmptyBorder(20, 0, 30, 0)
    }

    val timer = Timer(10, object : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            elapsed += 10
            val minutes = elapsed / 60000
            val seconds = (elapsed / 1000) % 60
            val hundredths = (elapsed % 1000) / 10
            timeLabel.text = String.format("%02d:%02d.%02d", minutes, seconds, hundredths)
        }
    })

    fun styledButton(text: String, bg: Color): JButton {
        return JButton(text).apply {
            preferredSize = Dimension(120, 40)
            background = bg
            foreground = Color.BLACK
            font = Font("SansSerif", Font.BOLD, 16)
            isFocusPainted = false
            isOpaque = true
            border = BorderFactory.createLineBorder(Color.DARK_GRAY)
        }
    }

    val startButton = styledButton("Start", Color(102, 255, 102))
    val stopButton = styledButton("Stop", Color(255, 102, 102))
    val resetButton = styledButton("Reset", Color(255, 255, 102))

    startButton.addActionListener {
        if (!running) {
            timer.start()
            running = true
        }
    }

    stopButton.addActionListener {
        timer.stop()
        running = false
    }

    resetButton.addActionListener {
        timer.stop()
        elapsed = 0
        timeLabel.text = "00:00.00"
        running = false
    }

    val buttonPanel = JPanel().apply {
        background = Color(40, 40, 40)
        layout = FlowLayout(FlowLayout.CENTER, 30, 10)
        add(startButton)
        add(stopButton)
        add(resetButton)
    }

    return JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color(30, 30, 30)
        add(titleLabel)
        add(timeLabel)
        add(buttonPanel)
    }
}
