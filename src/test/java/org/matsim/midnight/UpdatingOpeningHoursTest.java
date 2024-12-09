package org.matsim.midnight;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.facilities.ActivityFacility;
import org.matsim.facilities.ActivityOption;
import org.matsim.facilities.OpeningTimeImpl;
import org.matsim.testcases.MatsimTestUtils;

import static org.ejml.EjmlUnitTests.assertEquals;

class UpdatingOpeningHoursTest {

    @RegisterExtension
    public MatsimTestUtils utils = new MatsimTestUtils();

    @Test
    public final void testActivityEndTime(){

            Config config = ConfigUtils.loadConfig("scenarios/equil/config.xml");
            config.controller().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
            config.controller().setLastIteration(5);
            config.controller().setRunId("matsimClass2024");

            Scenario scenario = ScenarioUtils.loadScenario(config);

            var facilities = scenario.getActivityFacilities();
            var facilitiesFactory = facilities.getFactory();
            var facility = facilitiesFactory.createActivityFacility(Id.create("workPlace", ActivityFacility.class), new Coord(-25000,0));
            ActivityOption workOption = facilitiesFactory.createActivityOption("work");
            workOption.addOpeningTime(new OpeningTimeImpl(0,24*3600));
            facility.addActivityOption(workOption);
            facilities.addActivityFacility(facility);

            Person person = PopulationUtils.getFactory().createPerson(Id.createPersonId("1"));
            Plan plan =PopulationUtils.createPlan();

            var homeCoord = new Coord(10000,0);
            var workCoord = new Coord(-25000,0);

            Activity homeAct = PopulationUtils.createActivityFromCoord("home", homeCoord);
            homeAct.setEndTime(10*3600);
            plan.addActivity(homeAct);

            Leg toWork =PopulationUtils.createLeg("bicycle");
            plan.addLeg(toWork);

            // work activity that ends beyond 24 hours
            Activity workAct = PopulationUtils.createActivityFromCoord("work", workCoord);
            homeAct.setEndTime(27*3600);
            plan.addActivity(workAct);

            Leg toHome =PopulationUtils.createLeg("bicycle");
            plan.addLeg(toHome);

            Activity backHome = PopulationUtils.createActivityFromCoord("home", homeCoord);
            plan.addActivity(backHome);

            person.addPlan(plan);
            scenario.getPopulation().addPerson(person);

            //UpdatingOpeningHours.updateFacilityOpeningHours(scenario);

            //var updatedOpeningTime = workOption.getOpeningTimes().iterator().next();
            //assert(config.qsim().getEndTime().seconds(), updatedOpeningTime.getEndTime());

            Controler controler = new Controler(scenario);
            controler.run();







    }

}