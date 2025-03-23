import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JLabel
import java.awt.Color
import java.awt.Dimension
import java.awt.Font



fun main() {
    val frame = JFrame("Stopwatch")

    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE



    val label = JLabel("Stopwatch")
    label.foreground = Color.WHITE
    label.font = Font("Roboto", Font.BOLD, 48)



    val panel = JPanel()
    panel.background = Color.BLACK
    panel.preferredSize = Dimension(800, 600)


    panel.add(label)


    frame.contentPane.add(panel)

    frame.pack()

    frame.setLocationRelativeTo(null)

    frame.isVisible = true
}