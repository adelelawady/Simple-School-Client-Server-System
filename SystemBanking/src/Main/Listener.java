package Main;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Listener {

    public Listener() {
    }

    public void AddTextFieldListener(JTextField TXT, JLabel LBL, int Length) {
        TXT.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                warn(e);
            }

            public void removeUpdate(DocumentEvent e) {
                warn(e);
            }

            public void insertUpdate(DocumentEvent e) {
                warn(e);
            }

            public void warn(DocumentEvent documentEvent) {
                DocumentEvent.EventType type = documentEvent.getType();
                String typeString = null;
                if (type.equals(DocumentEvent.EventType.CHANGE)) {
                    typeString = "Change";
                } else if (type.equals(DocumentEvent.EventType.INSERT)) {
                    typeString = "Insert";
                } else if (type.equals(DocumentEvent.EventType.REMOVE)) {
                    typeString = "Remove";
                }
                Color color = new Color(46, 204, 113);
                if (TXT.getText().length() >= Length) {
                    LBL.setForeground(color);
                    if (!LBL.getText().contains(".")) {
                        LBL.setText(LBL.getText() + ".");
                    }
                } else {
                    LBL.setForeground(Color.red);
                    LBL.setText(LBL.getText().replace(".", ""));
                }
            }
        });
    }
}
