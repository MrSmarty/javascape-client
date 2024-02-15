package com.javascape.receivers;

import javafx.scene.layout.GridPane;

public class ReceiverPane extends GridPane {

    private String uid;
    private transient Receiver receiver;

    public ReceiverPane(String uid, Receiver receiver) {
        super();
        this.uid = uid;
        this.receiver = receiver;
    }

    public String getUID() {
        return uid;
    }

    public Receiver getReceiver() {
        return receiver;
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
