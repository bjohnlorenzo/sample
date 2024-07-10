package com.sample.crud;


 import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableCellRenderer;

public class ImageRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel();
        if (value instanceof ImageIcon) {
            label.setIcon((ImageIcon) value);
        } else {
            label.setText(value.toString());
        }
        label.setOpaque(true);
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
        } else {
            label.setBackground(table.getBackground());
        }
        return label;
    }
}