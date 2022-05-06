import javax.swing.*;

public class JOptionDimension extends JPanel {
    private final JTextField widthField;
    private final JTextField heightField;
    private boolean confirmed;
    private int width;
    private int height;

    public JOptionDimension() {
        confirmed = false;
        add(new JLabel("Width"));
        widthField = new JTextField("4", 8);
        add(widthField);
        add(new JLabel("Height"));
        heightField = new JTextField("4", 8);
        add(heightField);

        int result = JOptionPane.showConfirmDialog(
                null, this, "New Game", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null);
        if (result == JOptionPane.OK_OPTION) {
            try {
                width = getGameWidth();
                height = getGameHeight();
                confirmed = true;
            } catch (NumberFormatException ignored) {
            }
        }
    }

    private int getGameWidth() throws NumberFormatException {
        return Integer.parseInt(widthField.getText());
    }

    private int getGameHeight() throws NumberFormatException {
        return Integer.parseInt(heightField.getText());
    }

    public int getGWidth() {
        return width;
    }

    public int getGHeight() {
        return height;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
