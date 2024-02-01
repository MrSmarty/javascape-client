package com.javascape.receivers;

import javafx.scene.layout.GridPane;

public class ReceiverPane extends GridPane {

    private String uid;

    public ReceiverPane(String uid) {
        super();
        this.uid = uid;
    }

    public String getUID() {
        return uid;
    }

    @Override
    public String toString() {
        return uid;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReceiverPane) {
            return ((ReceiverPane) o).getUID().equals(uid);
        }
        return false;
    }
}
