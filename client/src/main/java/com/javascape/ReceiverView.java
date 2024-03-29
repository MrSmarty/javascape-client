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
    ScheduledExecutorService executor;

    public ReceiverView() {
        executor = Executors.newScheduledThreadPool(1);
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

                @SuppressWarnings("unchecked")
                ObservableList<Receiver> temp = (ObservableList<Receiver>) DataHandler.deserializeObservable(Client.getThread().awaitResponse("getReceiverList"));
                for (Receiver r : temp) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            if (receiverList.contains(r.getReceiverPane())) {
                                receiverList.get(receiverList.indexOf(r.getReceiverPane())).getReceiver().updateReceiver(r);
                            } else {
                                receiverList.add(r.getReceiverPane());
                            }
                        }
                    });
                    
                    
                }
            }
        };
    }

    public void quit() {
        executor.shutdown();
    }
}
