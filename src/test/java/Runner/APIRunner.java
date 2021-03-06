package Runner;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

@KarateOptions(features = "classpath:Sample", tags = "~@ignore")

public class APIRunner {

    @Test
    public void testParallel() {
        /**
         * Karate jUnit4 parallel Runner.
         *
         * Runner.path() method is from karate-core. This will help to point to package you want to execute.
         * All sub-directories and feature files will be picked up if we specify the parent package.
         * And also, Runner.path(), can take multiple string parameters, hence we can provide multiple packages here.
         * There is an optional reportDir() method if you want to customize the directory to which the HTML, XML and JSON files will be output.
         */
        Results results = Runner.parallel(getClass(), 1);
        generateReport(results.getReportDir());
        assertTrue(results.getErrorMessages(), results.getFailCount() == 0);
    }

    public static void generateReport(String karateOutputPath) {
        /**
         * This will generate the json-output file.
         * And through this json file, cucumber report will be generated in the target folder.
         */
        Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "Karate Gradle");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}