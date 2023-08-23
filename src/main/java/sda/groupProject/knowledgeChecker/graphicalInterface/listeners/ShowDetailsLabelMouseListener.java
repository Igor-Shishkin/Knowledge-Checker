package sda.groupProject.knowledgeChecker.graphicalInterface.listeners;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShowDetailsLabelMouseListener implements MouseListener {

    JFrame frame;
    JLabel detailsLabel;
    int quantityCategories;
    JPanel resultPanel;

    public ShowDetailsLabelMouseListener(JFrame frame, JLabel detailsLabel, int quantityCategories,
                                         JPanel resultPanel) {
        this.frame = frame;
        this.detailsLabel = detailsLabel;
        this.quantityCategories = quantityCategories;
        this.resultPanel = resultPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        detailsLabel.setVisible(true);
        frame.pack();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        detailsLabel.setVisible(false);
        frame.pack();
    }
}
