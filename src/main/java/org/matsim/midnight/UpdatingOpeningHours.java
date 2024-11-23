package org.matsim.midnight;

import org.matsim.api.core.v01.Scenario;
import org.matsim.facilities.ActivityFacility;
import org.matsim.facilities.ActivityOption;
import org.matsim.facilities.OpeningTime;

public class UpdatingOpeningHours {

    public static void updateFacilityOpeningHours(Scenario scenario){
        double totalSimulationPeriod = scenario.getConfig().qsim().getEndTime().seconds();

        // checking the opening hours of all facilities

        for (ActivityFacility facility : scenario.getActivityFacilities().getFacilities().values()){
            for (ActivityOption activityOption : facility.getActivityOptions().values()){
                for (OpeningTime openingTime : activityOption.getOpeningTimes()){
                    if(openingTime.getStartTime()==0.0 && openingTime.getEndTime()== 24*3600){
                        System.out.println("Adjusting opening hours for facility: " + facility.getId() + ", Activity " + activityOption.getType());
                        openingTime.setEndTime(totalSimulationPeriod);
                    }
                }
            }
        }
    }
}
