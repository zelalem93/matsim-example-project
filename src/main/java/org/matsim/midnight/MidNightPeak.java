package org.matsim.midnight;

import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;


public class MidNightPeak implements ActivityEndEventHandler {
/// checking the facilities which close at exactly midnight
    @Override
    public void handleEvent(ActivityEndEvent activityEndEvent) {
        if(activityEndEvent.getTime()== 24*3600){
            System.out.println("Activity closes at mid night: Facility ID " + activityEndEvent.getFacilityId() + ", Time: " + activityEndEvent.getTime());
        }

    }


}
