package dhbw.ka.mwi.businesshorizon2.businesshorizon2.scenario;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ScenarioServiceTest.class, ScenarioValidationTest.class, MultiPeriodAccountingFigureTest.class,
        ScenarioPostRequestDtoTest.class, TimeSeriesItemByDateComparatorTest.class, TimeSeriesItemDateTest.class})
public class ScenarioTestSuite {
}
