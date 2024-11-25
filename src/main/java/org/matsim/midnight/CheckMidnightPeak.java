package org.matsim.midnight;


import org.matsim.core.events.EventsUtils;


public class CheckMidnightPeak {

  // simple event handler

    public static void main(String[] args) {

        var handler = new MidNightPeak();
        var manager = EventsUtils.createEventsManager();
        manager.addHandler(handler);
        EventsUtils.readEvents(manager, "C:\\Users\\zelalemb\\Documents\\MOST\\PHD\\Matsim\\matsim tutorial\\matsim-example-project\\output\\matsimClass2024.output_events.xml.gz");


    }
}
