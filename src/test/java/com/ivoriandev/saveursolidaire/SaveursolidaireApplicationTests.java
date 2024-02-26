package com.ivoriandev.saveursolidaire;

import com.ivoriandev.saveursolidaire.utils.constants.AppConstantsTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
@ActiveProfiles(profiles = AppConstantsTest.TEST_PROFILE)
class SaveursolidaireApplicationTests {
}
