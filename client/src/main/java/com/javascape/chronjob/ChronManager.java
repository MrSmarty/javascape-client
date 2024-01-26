package com.javascape.chronjob;

import java.util.ArrayList;

import com.javascape.Client;
import com.javascape.DataHandler;

public class ChronManager {

    transient private ArrayList<ChronjobItem> chronjobItems = new ArrayList<ChronjobItem>();

    private Client client;

    public ChronManager() {
        loadData();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void loadData() {
        chronjobItems.add(new ChronjobItem("Set pin", "to on", "setPin %1$s 1"));
        chronjobItems.add(new ChronjobItem("Set pin", "to off", "setPin %1$s 0"));
        chronjobItems.add(new ChronjobItem("Toggle value of pin", "togglePin %1$s"));
        chronjobItems.add(new ChronjobItem("Wait", "seconds", "wait %1$s"));
    }

    public boolean newRepeating(Chronjob job) {
        return Boolean
                .parseBoolean(client.getThread().awaitResponse("newRepeating " + DataHandler.serializeChronjob(job)));
    }

    public void newConditional(ConditionalJob job) {
        client.getThread().awaitResponse("newConditional " + DataHandler.serializeConditional(job));
    }

    public boolean remove(Job job) {
        return Boolean
                .parseBoolean(client.getThread().awaitResponse("deleteChronjob " + DataHandler.serializeJob(job)));
    }

    public ArrayList<ChronjobItem> getAllItems() {
        return chronjobItems;
    }

}
