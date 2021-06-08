package com.arthurbritto.methodik.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TasksAndPanels {
        @Embedded
        public Task task;
        @Relation(
                parentColumn = "panel_id",
                entityColumn = "id"
        )
        public List<Panel> panels;

}
