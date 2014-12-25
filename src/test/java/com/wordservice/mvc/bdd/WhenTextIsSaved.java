package com.wordservice.mvc.bdd;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.controller.NextWordCompletionController;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class WhenTextIsSaved extends IntegrationTestsBase {

    @Autowired
    private NextWordCompletionController nextWordCompletionController;

    @Test
    @Rollback
    public void testGetTop10Words() throws Exception {
        textSaverService.save("This is a map and list of countries containing monthly (annual divided by 12 months) gross and net income (after taxes) average wages in Europe in their local currency, in Euro and in dollars. The chart below reflects the average (mean) wage as reported by various data providers. The salary distribution is right-skewed, therefore more people earn less than the average gross salary. These figures will shrink after income tax is applied. In less developed markets, actual incomes may exceed those listed in the table due to the existence of grey economies. In some countries, social security, contributions for pensions, public schools, and health are included in these taxes.");
        List<WordEntity> byFirstTwo = nextWordCompletionController.getByFirstTwo("and", "list", "of");
        assertEquals(1,byFirstTwo.size());
    }

}