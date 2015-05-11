package com.fredtm.api.integration;

import static org.hamcrest.Matchers.is;

import static com.jayway.restassured.RestAssured.given;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fredtm.api.FredTmApiConfig;
import com.fredtm.api.test.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FredTmApiConfig.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql") })
public class OperationResourcesTest extends TestBase {

	@Test
	public void doTest() {
		given().relaxedHTTPSValidation().auth()
				.basic("leo.zapparoli@gmail.csom", "123")
				.header("Accept", "application/json").log().all().then()
				.get("/operation").then().statusCode(is(200));
	}

}
