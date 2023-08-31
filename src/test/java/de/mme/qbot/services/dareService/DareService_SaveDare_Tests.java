package de.mme.qbot.services.dareService;

import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.services.DareService;
import de.mme.qbot.services.MaximumDaresStoredException;
import de.mme.qbot.services.TextIsTooLongException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@AutoConfigureMockMvc
public class DareService_SaveDare_Tests {

    @Autowired
    DareService repo;

    @BeforeEach
    void clearTable(){
        // CleanUp
        repo.removeAll();
    }

    @Test
    void addingDareToFullDb_ThrowsMaximumDaresStoredException() throws MaximumDaresStoredException, TextIsTooLongException {
        // ARRANGE
        // ACT
        MaximumDaresStoredException thrown = Assertions.assertThrows(MaximumDaresStoredException.class, () -> {
            for(int i = 1; i<= DareService.MAXIMUM_NUMBERS_OF_DARES_ALLOWED+1; i++){
                repo.saveDare(new Dare(0000L, "Dare Text of " + i ));
            }
        });
        // ASSERT
        Assertions.assertEquals(MaximumDaresStoredException.class, thrown.getClass());
    }

    @Test
    void addingDareToEmptyDb_NoErrors() throws MaximumDaresStoredException, TextIsTooLongException {
        // ARRANGE
        Dare newDare = new Dare(000L,"Test Dare");
        // ACT
        Dare savedDare = repo.saveDare(newDare);
        // ASSERT
        assertEquals(savedDare.getText(),newDare.getText());
    }


}
