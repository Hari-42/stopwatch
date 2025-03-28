import javax.swing.JFrame
import javax.swing.JButton
import javax.swing.JOptionPane
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



    val label = JLabel("Stopwatch")
    label.foreground = Color.WHITE
    label.font = Font("Roboto", Font.BOLD, 48)




    val timeLabel = JLabel("00:00.00")
    timeLabel.foreground = Color.WHITE
    timeLabel.font = Font("Roboto", Font.BOLD, 48)






    val panel = JPanel()
    panel.background = Color.BLACK
    panel.preferredSize = Dimension(800, 600)


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

    timer.start()

    val button = JButton("Click Me")
    button.setBounds(100, 80, 100, 30) // x, y, width, height

    button.addActionListener {
        timer.stop()
    }


    panel.add(button)
    panel.isVisible = true


    frame.contentPane.add(panel)

    frame.pack()

    frame.setLocationRelativeTo(null)

    frame.isVisible = true
}