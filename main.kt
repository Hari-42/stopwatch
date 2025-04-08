import javax.swing.JFrame
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.Timer
import java.awt.event.ActionEvent
import java.awt.event.ActionListener




fun main() {
    val frame = JFrame("Stopwatch")

    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    val panel = JPanel()
    panel.background = Color.BLACK
    panel.preferredSize = Dimension(800, 600)
    panel.layout = null

    val label = JLabel("Stopwatch")
    label.foreground = Color.WHITE
    label.font = Font("Roboto", Font.BOLD, 48)
    label.setBounds(300, 50, 300, 50)



    val timeLabel = JLabel("00:00.00")
    timeLabel.foreground = Color.WHITE
    timeLabel.font = Font("Roboto", Font.BOLD, 48)
    timeLabel.setBounds(300, 250, 300, 50)


    panel.add(label)
    panel.add(timeLabel)



    var elapsed = 0



    val timer = Timer(10, object : ActionListener {
        override fun actionPerformed(e: ActionEvent?) {
            elapsed += 10
            val minutes = (elapsed / 60000)
            val seconds = (elapsed / 1000) % 60
            val hundredths = (elapsed % 1000) / 10
            timeLabel.text = String.format("%02d:%02d.%02d", minutes, seconds, hundredths)
        }
    })

    val buttonstart = JButton("start")
    buttonstart.setBounds(300, 500, 100, 30)
    buttonstart.setBackground(Color.GREEN)
    buttonstart.addActionListener {
        timer.start()
    }


    val buttonstop = JButton("stop")
    buttonstop.setBounds(400, 500, 100, 30)
    buttonstop.setBackground(Color.RED)
    buttonstop.addActionListener {
        timer.stop()
    }


    val buttonreset = JButton("Reset")
    buttonreset.setBounds(350, 550, 100, 30)
    buttonreset.addActionListener {
        timer.stop()
        elapsed = 0
        timeLabel.text = "00:00.00"
    }
    buttonreset.setBackground(Color.YELLOW)


    panel.add(buttonstop)
    panel.add(buttonstart)
    panel.add(buttonreset)
    panel.isVisible = true


    frame.contentPane.add(panel)

    frame.pack()

    frame.setLocationRelativeTo(null)

    frame.isVisible = true
}