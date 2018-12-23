package views;

        import javax.swing.*;
        import java.awt.event.ComponentAdapter;
        import java.awt.event.ComponentEvent;


        //TO JEST KLASA TYLKO PO TO, ZEBYM MOGL TESOTWAC NOWE ELEMENTY - DO WYWALENIA
public class QAFactory {
    private JLabel questionText;
    private JPanel MainPanel;
    private JCheckBox checkBox1;
    private JRadioButton radioButton1;

    public QAFactory() {
        questionText.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
    }
}
