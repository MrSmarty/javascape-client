package com.javascape;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.javascape.receivers.Receiver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class ReceiverView {
    ObservableList<GridPane> receiverList = FXCollections.observableArrayList();
    Client client;

    public ReceiverView(Client client) {
        this.client = client;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(getReceiverUpdateRunnable(), 3, 20, TimeUnit.SECONDS);

    }

    public ListView<GridPane> getReceiverView() {
        ListView<GridPane> view = new ListView<GridPane>(receiverList);

        return view;
    }

    public void update() {
        Logger.print("Updating ReceiverView");
        Platform.runLater(getReceiverUpdateRunnable());

    }

    public Runnable getReceiverUpdateRunnable() {
        return new Runnable() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        receiverList.clear();
                    }
                });
                for (Receiver r : (ObservableList<Receiver>) DataHandler
                        .deserializeObservable(client.getThread().awaitResponse("getReceiverList"))) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            receiverList.add(r.getReceiverPane());
                        }
                    });
                    
                }
            }
        };
    }
}
