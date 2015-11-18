package com.nearsoft;

import com.nearsoft.questions.config.QuestionsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuestionsApplication.class)
@WebAppConfiguration
public class QuestionsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
