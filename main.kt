import javax.swing.*
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

fun main() {
    SwingUtilities.invokeLater { createAndShowGUI() }
}

fun createAndShowGUI() {
    val frame = JFrame("Stopwatch & Timer")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.size = Dimension(800, 600)

    val cardLayout = CardLayout()
    val cards = JPanel(cardLayout)


    val stopwatchPanel = createStopwatchPanel()

    val timerPanel = createTimerPanel()

    cards.add(stopwatchPanel, "stopwatch")
    cards.add(timerPanel, "timer")

    val topBar = JPanel().apply {
        layout = FlowLayout(FlowLayout.LEFT)
        background = Color(20, 20, 20)
        val toStopwatch = JButton("Stopwatch").apply {
            addActionListener { cardLayout.show(cards, "stopwatch") }
        }
        val toTimer = JButton("Timer").apply {
            addActionListener { cardLayout.show(cards, "timer") }
        }
        add(toStopwatch)
        add(toTimer)
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

fun createTimerPanel(): JPanel {
    var remainingTime = 0
    var running = false
    var timer: Timer? = null

    val titleLabel = JLabel("Timer", SwingConstants.CENTER).apply {
        foreground = Color.WHITE
        font = Font("SansSerif", Font.BOLD, 48)
        border = BorderFactory.createEmptyBorder(30, 0, 10, 0)
    }

    val timeInput = JFormattedTextField(javax.swing.text.MaskFormatter("##:##:##")).apply {
        text = "00:00:00"
        horizontalAlignment = JTextField.CENTER
        font = Font("Monospaced", Font.BOLD, 60)
        foreground = Color.WHITE
        background = Color(50, 50, 50)
        border = BorderFactory.createEmptyBorder(20, 0, 30, 0)
        caretColor = Color.WHITE
    }

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



    fun showTimerCompleteDialog() {
        JOptionPane.showMessageDialog(
            null,
            "Timer completed!",
            "Timer",
            JOptionPane.INFORMATION_MESSAGE
        )
    }

    fun updateDisplay(timeInMillis: Int) {
        val hours = timeInMillis / 3600000
        val minutes = (timeInMillis % 3600000) / 60000
        val seconds = (timeInMillis % 60000) / 1000
        timeInput.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    startButton.addActionListener {
        if (!running) {
            val timeParts = timeInput.text.split(":")
            if (timeParts.size == 3) {
                try {
                    val hours = timeParts[0].toInt()
                    val minutes = timeParts[1].toInt()
                    val seconds = timeParts[2].toInt()
                    remainingTime = (hours * 3600 + minutes * 60 + seconds) * 1000

                    if (remainingTime > 0) {
                        timer = Timer(1000, object : ActionListener {
                            override fun actionPerformed(e: ActionEvent?) {
                                remainingTime -= 1000
                                if (remainingTime <= 0) {
                                    (e?.source as? Timer)?.stop()
                                    running = false
                                    remainingTime = 0
                                    updateDisplay(remainingTime)
                                    showTimerCompleteDialog()
                                } else {
                                    updateDisplay(remainingTime)
                                }
                            }
                        })
                        timer?.start()
                        running = true
                    }
                } catch (e: NumberFormatException) {
                    JOptionPane.showMessageDialog(null, "Invalid time format", "Error", JOptionPane.ERROR_MESSAGE)
                }
            }
        }
    }

    stopButton.addActionListener {
        timer?.stop()
        running = false
    }

    val buttonPanel = JPanel().apply {
        background = Color(40, 40, 40)
        layout = FlowLayout(FlowLayout.CENTER, 30, 10)
        add(startButton)
        add(stopButton)
    }

    return JPanel().apply {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        background = Color(30, 30, 30)
        add(titleLabel)
        add(timeInput)
        add(buttonPanel)
    }
}