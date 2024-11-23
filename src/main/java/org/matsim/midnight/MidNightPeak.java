package org.matsim.midnight;



import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.facilities.ActivityFacility;
import org.matsim.facilities.ActivityOption;
import org.matsim.facilities.OpeningTime;


import java.util.HashSet;

import java.util.Set;

public class MidNightPeak implements ActivityEndEventHandler {
    private final Set<String> midNightClosingFacilities = new HashSet<>();
    @Override
    public void handleEvent(ActivityEndEvent activityEndEvent) {

        if(activityEndEvent.getTime()== 24*3600){
            midNightClosingFacilities.add(activityEndEvent.getFacilityId().toString());
            System.out.println("Activity closes at mid night: Facility ID " + activityEndEvent.getFacilityId() + ", Time: " + activityEndEvent.getTime());
        }

    }

    public void updateOpeningHours(Scenario scenario, double totalSimulationPeriod){
        for (String facilityId : midNightClosingFacilities){
            ActivityFacility facility = scenario.getActivityFacilities().getFacilities().get(Id.create(facilityId, ActivityFacility.class));

            if (facility != null){
                for (ActivityOption activityOption: facility.getActivityOptions().values()){
                    for (OpeningTime openingTime: activityOption.getOpeningTimes()){
                        if (openingTime.getStartTime() == 0.0 && openingTime.getEndTime() == 24*3600){
                            System.out.println("Adjusting opening hours for facility: " + facilityId + ",Activity" + activityOption.getType());
                            openingTime.setEndTime(totalSimulationPeriod);


                    }
                }
            }
        }
    }
}
}
