package com.javascape;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.javascape.receivers.Receiver;
import com.javascape.receivers.ReceiverPane;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ReceiverView {
    ObservableList<ReceiverPane> receiverList = FXCollections.observableArrayList();

    public ReceiverView() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(getReceiverUpdateRunnable(), 2, 5, TimeUnit.SECONDS);

    }

    public ListView<ReceiverPane> getReceiverView() {
        ListView<ReceiverPane> view = new ListView<ReceiverPane>(receiverList);

        return view;
    }

    public void update() {
        Logger.print("Updating ReceiverView");
        Platform.runLater(getReceiverUpdateRunnable());

    }

    public Runnable getReceiverUpdateRunnable() {
        return new Runnable() {
            public void run() {

                for (Receiver r : (ObservableList<Receiver>) DataHandler
                        .deserializeObservable(Client.getThread().awaitResponse("getReceiverList"))) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            if (receiverList.contains(r.getReceiverPane())) {
                                receiverList.remove(r.getReceiverPane());
                                receiverList.add(r.getReceiverPane());
                            } else {
                                receiverList.add(r.getReceiverPane());
                            }
                        }
                    });
                    
                    
                }
            }
        };
    }
}
