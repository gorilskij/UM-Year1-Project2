package simulation.gui.window;

import body.interfaces.Body;

import javax.swing.*;

public abstract class BodySelector extends JComboBox<Body> {
    public abstract Body[] refreshList();

    void refresh() {
        Object selected = getSelectedItem();
        setModel(new DefaultComboBoxModel<>(refreshList()));
        if (selected != null)
            setSelectedItem(selected);
    }

    BodySelector(Window window) {
        addActionListener(e -> window.setCenterBody((Body) getSelectedItem()));
    }
}
