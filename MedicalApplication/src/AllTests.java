import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Identifiers.test.IdentifierTest;
import Identifiers.test.PatientIdentifierTest;
import Identifiers.test.Search1IdentifierTest;
import Identifiers.test.Search2IdentifierTest;
import Identifiers.test.Search3IdentifierTest;
import Identifiers.test.TestResultsTest;
import Other.test.AutoCompleteJComboBoxTest;

@RunWith(Suite.class)
@SuiteClasses({ AutoCompleteJComboBoxTest.class, IdentifierTest.class, PatientIdentifierTest.class,
		Search1IdentifierTest.class, Search2IdentifierTest.class, Search3IdentifierTest.class,
		TestResultsTest.class })
public class AllTests {

}
