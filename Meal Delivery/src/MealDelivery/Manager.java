package MealDelivery;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Manager<T> {
    protected ArrayList<T> list;
    protected JFrame frame;

    public Manager(ArrayList<T> list, String title) {
        this.list = list;
        this.frame = new JFrame(title);
        this.frame.setSize(500, 400);
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
    }

    protected abstract void initializeUI();

    protected void refreshList(JList<T> jList) {
        DefaultListModel<T> model = new DefaultListModel<>();
        for (T item : list) {
            model.addElement(item);
        }
        jList.setModel(model);
    }
}