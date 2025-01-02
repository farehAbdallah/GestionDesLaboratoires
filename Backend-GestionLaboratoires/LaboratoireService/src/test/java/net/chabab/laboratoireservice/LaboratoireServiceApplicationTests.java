//package net.chabab.laboratoireservice;
//
//import net.chabab.laboratoireservice.repository.LaboratoireRepository;
//import net.chabab.laboratoireservice.web.LaboratoireController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//@ActiveProfiles("test")
//@SpringBootTest
//class LaboratoireServiceApplicationTests {
//
//	@Autowired
//	private LaboratoireController laboratoireRestController;
//
//	@MockBean
//	private LaboratoireRepository laboratoireRepository;
//	@Test
//	void contextLoads() {
//		// Simple assertion that always passes if the context loads successfully
//		assertTrue(true, "The application context should load without exceptions.");
//	}
//
//	@Test
//	void testLaboratoireRestControllerBeanLoaded() {
//		// Check that the AnalyseRestController bean is loaded
//		assertThat(laboratoireRestController).isNotNull();
//	}
//
//	@Test
//	void testLaboratoireRepositoryBeanLoaded() {
//		// Check that the AnalyseRepository bean is loaded
//		assertThat(laboratoireRepository).isNotNull();
//	}
//
//}