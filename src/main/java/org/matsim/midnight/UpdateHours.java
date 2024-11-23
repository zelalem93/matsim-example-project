package org.matsim.midnight;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;

public class UpdateHours {

    public static void main(String[] args) {

        Config config = ConfigUtils.loadConfig("scenarios/equil/config.xml");
        config.controller().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        config.controller().setLastIteration(5);
        config.controller().setRunId("matsimClass2024");

        Scenario scenario = ScenarioUtils.loadScenario(config);

        /// to update the opening hours of facilities
        UpdatingOpeningHours.updateFacilityOpeningHours(scenario);

        Controler controler = new Controler(scenario);

        controler.run();

    }
}
