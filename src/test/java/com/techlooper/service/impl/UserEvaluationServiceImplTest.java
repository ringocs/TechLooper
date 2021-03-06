package com.techlooper.service.impl;

import com.techlooper.config.ConfigurationTest;
import com.techlooper.config.ElasticsearchConfiguration;
import com.techlooper.config.ElasticsearchUserImportConfiguration;
import com.techlooper.entity.SalaryReview;
import com.techlooper.entity.userimport.UserImportEntity;
import com.techlooper.model.SalaryReport;
import com.techlooper.service.UserEvaluationService;
import com.techlooper.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigurationTest.class, ElasticsearchConfiguration.class, ElasticsearchUserImportConfiguration.class})
public class UserEvaluationServiceImplTest {

    @Resource
    private UserEvaluationService userEvaluationService;

    @Resource
    private UserService userService;

    @Test
    public void testScore() throws Exception {
        UserImportEntity userImportEntity = userService.findUserImportByEmail("thach.nguyenngoc@imipgroup.com");
        Map<String, Long> result = userEvaluationService.getTotalNumberOfJobPerSkill();
        long score = userEvaluationService.score(userImportEntity, result);
        assertTrue(score > 0);
    }

    @Test
    public void testRate() throws Exception {
        UserImportEntity userImportEntity = userService.findUserImportByEmail("thach.nguyenngoc@imipgroup.com");
        Map<String, Long> result = userEvaluationService.getTotalNumberOfJobPerSkill();
        double rate = userEvaluationService.rate(userImportEntity, result, 100000L);
        assertTrue(rate > 0 && rate <= 5);
    }

    @Test
    public void testRank() throws Exception {
        UserImportEntity userImportEntity = userService.findUserImportByEmail("thach.nguyenngoc@imipgroup.com");
        Map<String, Integer> result = userEvaluationService.rank(userImportEntity);
        assertTrue(userImportEntity.getScore() > 0 ? result.size() > 0 : result.size() == 0);
    }

    @Test
    public void testGetSkillMap() throws Exception {
        Map<String, Long> result = userEvaluationService.getSkillMap();
        assertTrue(result.size() > 0);
    }

    @Test
    public void testGetTotalNumberOfJobPerSkill() throws Exception {
        Map<String, Long> result = userEvaluationService.getTotalNumberOfJobPerSkill();
        assertTrue(result.size() > 0);
    }

    @Test
    public void testEvaluateJobOffer() throws Exception {
        SalaryReview salaryReview = new SalaryReview();
        salaryReview.setJobTitle("Java Developer");
        salaryReview.setJobLevelIds(Arrays.asList(5, 6));
        salaryReview.setJobCategories(Arrays.asList(35L));
        salaryReview.setNetSalary(2000);
        SalaryReport salaryReport = userEvaluationService.evaluateJobOffer(salaryReview);
        assertTrue(salaryReport.getPercentRank() > 0);
    }

    @Test
    public void testEvaluateJobOfferWithoutJobs() throws Exception {
        SalaryReview salaryReview = new SalaryReview();
        salaryReview.setJobTitle("ABC XYZ");
        salaryReview.setJobLevelIds(Arrays.asList(5, 6));
        salaryReview.setJobCategories(Arrays.asList(35L));
        salaryReview.setNetSalary(2000);
        SalaryReport salaryReport = userEvaluationService.evaluateJobOffer(salaryReview);
        assertTrue(salaryReport.getPercentRank().isNaN());
    }

    @Test
    public void testEvaluateJobOfferTotalJobLessThan10() throws Exception {
        SalaryReview salaryReview = new SalaryReview();
        salaryReview.setJobTitle("Product Manager");
        salaryReview.setJobLevelIds(Arrays.asList(5, 6));
        salaryReview.setJobCategories(Arrays.asList(35L));
        salaryReview.setSkills(Arrays.asList("Agile", "Software Architecture", "Java"));
        salaryReview.setNetSalary(2000);
        SalaryReport salaryReport = userEvaluationService.evaluateJobOffer(salaryReview);
        assertTrue(salaryReport.getPercentRank() > 0);
    }

    @Test
    public void testEvaluateJobOfferTotalJobLessThan10WithEmptySkill() throws Exception {
        SalaryReview salaryReview = new SalaryReview();
        salaryReview.setJobTitle("Software Architect");
        salaryReview.setJobLevelIds(Arrays.asList(5, 6));
        salaryReview.setJobCategories(Arrays.asList(35L));
        salaryReview.setNetSalary(2000);
        SalaryReport salaryReport = userEvaluationService.evaluateJobOffer(salaryReview);
        assertTrue(salaryReport.getPercentRank().isNaN());
    }
}