import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.Color
import java.awt.Dimension

fun main() {
    val frame = JFrame("Stopwatch")

    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    val panel = JPanel()
    panel.background = Color.BLACK
    panel.preferredSize = Dimension(800, 600)

    frame.contentPane.add(panel)

    frame.pack()

    frame.setLocationRelativeTo(null)

    frame.isVisible = true
}