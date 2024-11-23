package org.matsim.midnight;


import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.scenario.ScenarioUtils;
//import org.matsim.core.utils.misc.OptionalTime;

public class MidNight {
    public static void main(String[] args) {

        Config config = ConfigUtils.loadConfig("scenarios/equil/config.xml");

        config.controller().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        config.controller().setLastIteration(5);
        config.controller().setRunId("matsimClass2024");

        Scenario scenario = ScenarioUtils.loadScenario(config);

        var handler = new MidNightPeak();
        var manager = EventsUtils.createEventsManager();

        manager.addHandler(handler);

        EventsUtils.readEvents(manager, "C:\\Users\\zelalemb\\Documents\\MOST\\PHD\\Matsim\\matsim tutorial\\matsim-example-project\\output\\matsimClass2024.output_events.xml.gz");

        // get the simulation period from configuration

        //OptionalTime totalSimulationPeriod =scenario.getConfig().qsim().getEndTime();

         double totalSimulationPeriod = scenario.getConfig().qsim().getEndTime().seconds();
         handler.updateOpeningHours(scenario,totalSimulationPeriod);
    }
}
