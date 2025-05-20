import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

fun main() {
    val frame = JFrame("Stopwatch")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(800, 600)
    frame.layout = BorderLayout()
    frame.background = Color(30, 30, 30)

    // === Title Label ===
    val titleLabel = JLabel("Stopwatch", SwingConstants.CENTER)
    titleLabel.foreground = Color.WHITE
    titleLabel.font = Font("SansSerif", Font.BOLD, 48)
    titleLabel.border = BorderFactory.createEmptyBorder(30, 0, 10, 0)

    // === Time Label ===
    val timeLabel = JLabel("00:00.00", SwingConstants.CENTER)
    timeLabel.foreground = Color.WHITE
    timeLabel.font = Font("Monospaced", Font.BOLD, 60)
    timeLabel.border = BorderFactory.createEmptyBorder(20, 0, 30, 0)

    // === Timer Logic ===
    var elapsed = 0
    val timer = Timer(10, object : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            elapsed += 10
            val minutes = elapsed / 60000
            val seconds = (elapsed / 1000) % 60
            val hundredths = (elapsed % 1000) / 10
            timeLabel.text = String.format("%02d:%02d.%02d", minutes, seconds, hundredths)
        }
    })

    // === Button Style Helper ===
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

    // === Buttons ===
    val startButton = styledButton("Start", Color(102, 255, 102))
    val stopButton = styledButton("Stop", Color(255, 102, 102))
    val resetButton = styledButton("Reset", Color(255, 255, 102))

    startButton.addActionListener { timer.start() }
    stopButton.addActionListener { timer.stop() }
    resetButton.addActionListener {
        timer.stop()
        elapsed = 0
        timeLabel.text = "00:00.00"
    }

    // === Button Panel ===
    val buttonPanel = JPanel().apply {
        background = Color(40, 40, 40)
        layout = FlowLayout(FlowLayout.CENTER, 30, 10)
        add(startButton)
        add(stopButton)
        add(resetButton)
    }

    // === Center Panel ===
    val centerPanel = JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color(30, 30, 30)
        add(titleLabel)
        add(timeLabel)
        add(buttonPanel)
    }

    frame.add(centerPanel, BorderLayout.CENTER)
    frame.isVisible = true
}
