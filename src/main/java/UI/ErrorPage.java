package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorPage implements ActionListener {

    private JFrame errorPage;
    private JPanel frame;
    private JLabel errorMessage;
    private JTextArea stackTrace;
    private JScrollPane stackTraceFrame;
    private JButton showStackTrace;
    private boolean isStackTraceShown = false;

    public ErrorPage(String error, Exception exception) {
        errorPage = new JFrame();
        errorPage.setLocationRelativeTo(null);

        frame = new JPanel();
        frame.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

        errorMessage = new JLabel("Error: " + error, UIManager.getIcon("OptionPane.errorIcon"), JLabel.LEADING);
        errorMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorMessage.setVisible(true);

        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        stackTrace = new JTextArea(sw.toString());
        stackTrace.setSize(100, 100);
        stackTrace.setEditable(false);
        stackTrace.setAlignmentX(Component.CENTER_ALIGNMENT);
        stackTrace.setVisible(true);

        stackTraceFrame = new JScrollPane(stackTrace);
        stackTraceFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        stackTraceFrame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        stackTraceFrame.setVisible(false);

        showStackTrace = new JButton("Expand");
        showStackTrace.setSize(100, 100);
        showStackTrace.addActionListener(this);
        showStackTrace.setAlignmentX(Component.CENTER_ALIGNMENT);
        showStackTrace.setVisible(true);

        frame.add(errorMessage, BorderLayout.NORTH);
        frame.add(stackTraceFrame, BorderLayout.CENTER);
        frame.add(showStackTrace, BorderLayout.SOUTH);

        frame.setVisible(true);
        errorPage.add(frame);

        errorPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorPage.setTitle("Error");
        errorPage.setMinimumSize(new Dimension(400, 150));
        errorPage.pack();
        errorPage.setVisible(true);
    }

    public ErrorPage(Exception ex) {
        this(ex.getMessage(), ex);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!isStackTraceShown) {
            stackTraceFrame.setVisible(true);
            showStackTrace.setText("Collapse");
            errorPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            stackTraceFrame.setVisible(false);
            showStackTrace.setText("Expand");
            errorPage.pack();
        }
        isStackTraceShown = !isStackTraceShown;
    }

}
