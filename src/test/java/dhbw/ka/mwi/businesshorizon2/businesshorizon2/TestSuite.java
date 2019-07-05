package dhbw.ka.mwi.businesshorizon2.businesshorizon2;

import dhbw.ka.mwi.businesshorizon2.businesshorizon2.computations.ComputationTestSuite;
import dhbw.ka.mwi.businesshorizon2.businesshorizon2.scenario.ScenarioTestSuite;
import dhbw.ka.mwi.businesshorizon2.businesshorizon2.user.UserTestSuite;
import dhbw.ka.mwi.businesshorizon2.businesshorizon2.scenario.ScenarioPostRequestDtoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ComputationTestSuite.class, ScenarioTestSuite.class, UserTestSuite.class})
public class TestSuite {
}
