package com.javascape.chronjob;

import java.util.ArrayList;

public class ConditionalJob extends Job {

    // Store each condition as:
    // ReceiverUID:SensorID Operator value
    ArrayList<String> conditions;
    ArrayList<String> elseCommands;

    public ConditionalJob(String name, ArrayList<String> commands, ArrayList<String> conditions) {
        super(name, commands);
        this.conditions = conditions;
    }

    public ConditionalJob(String name, ArrayList<String> commands, ArrayList<String> conditions,
            ArrayList<String> elseCommands) {
        super(name, commands);
        this.conditions = conditions;
        this.elseCommands = elseCommands;
    }

}
