import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;

public class MedicalServiceImplTests {
    @Test
    public void testCheckBloodPressure() {
        //arrange  given

        BloodPressure bloodPressure = new BloodPressure(120, 70);
        BigDecimal normalTemperature = new BigDecimal("36.6");
        HealthInfo healthInfo = new HealthInfo(normalTemperature, bloodPressure);
        PatientInfo patientInfo = new PatientInfo("user1", "Ivan", "Smirnov", null, healthInfo);

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("user1"))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        //act      when
        medicalService.checkBloodPressure("user1", new BloodPressure(200, 150));

        //assert   then
        Mockito.verify(sendAlertService, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    public void testCheckTemperature() {
        //arrange  given

        BloodPressure bloodPressure = new BloodPressure(120, 70);
        BigDecimal normalTemperature = new BigDecimal("36.6");
        HealthInfo healthInfo = new HealthInfo(normalTemperature, bloodPressure);
        PatientInfo patientInfo = new PatientInfo("user1", "Ivan", "Smirnov", null, healthInfo);

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("user1"))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        //act      when
        medicalService.checkTemperature("user1", new BigDecimal("34.5"));

        //assert   then
        Mockito.verify(sendAlertService, Mockito.times(1)).send(Mockito.anyString());
    }

    @Test
    public void testCheckNormalBloodPressureTemperature() {
        //arrange  given

        BloodPressure bloodPressure = new BloodPressure(120, 70);
        BigDecimal normalTemperature = new BigDecimal("36.6");
        HealthInfo healthInfo = new HealthInfo(normalTemperature, bloodPressure);
        PatientInfo patientInfo = new PatientInfo("user1", "Ivan", "Smirnov", null, healthInfo);

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("user1"))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        //act      when
        medicalService.checkBloodPressure("user1", new BloodPressure(120, 70));
        medicalService.checkTemperature("user1", new BigDecimal("36.6"));

        //assert   then
        Mockito.verify(sendAlertService, Mockito.times(0)).send(Mockito.anyString());
    }

    @Test
    public void testCheckBloodPressure_Arg() {
        //arrange  given

        BloodPressure bloodPressure = new BloodPressure(120, 70);
        BigDecimal normalTemperature = new BigDecimal("36.6");
        HealthInfo healthInfo = new HealthInfo(normalTemperature, bloodPressure);
        PatientInfo patientInfo = new PatientInfo("user1", "Ivan", "Smirnov", null, healthInfo);

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("user1"))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        //act      when
        medicalService.checkBloodPressure("user1", new BloodPressure(200, 100));

        //assert   then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: user1, need help", argumentCaptor.getValue());
    }

    @Test
    public void testCheckTemperature_Arg() {
        //arrange  given

        BloodPressure bloodPressure = new BloodPressure(120, 70);
        BigDecimal normalTemperature = new BigDecimal("36.6");
        HealthInfo healthInfo = new HealthInfo(normalTemperature, bloodPressure);
        PatientInfo patientInfo = new PatientInfo("user1", "Ivan", "Smirnov", null, healthInfo);

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("user1"))
                .thenReturn(patientInfo);

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        //act      when
        medicalService.checkTemperature("user1", new BigDecimal("35.0"));

        //assert   then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: user1, need help", argumentCaptor.getValue());
    }
}
