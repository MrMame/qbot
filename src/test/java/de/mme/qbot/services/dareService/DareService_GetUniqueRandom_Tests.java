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
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class DareService_GetUniqueRandom_Tests {

    @Autowired
    DareService repo;

    @BeforeEach
    void clearTable(){
        // CleanUp
        repo.removeAll();
    }

    @Test
    void gettingDareIfNoDareWasStored_ReturnsEmptyOptional() {
        // ARRANGE
        // ACT
        Optional<Dare> retDare = repo.getUniqueRandomDare();
        // ASSERT
        Assertions.assertEquals(true, retDare.isEmpty());
    }

    @Test
    void gettingDareIfOneDareWasStored_ReturnsStoredDare() throws MaximumDaresStoredException, TextIsTooLongException {
        // ARRANGE
        Dare newDare = new Dare(0000L,"The new Dare");
        repo.saveDare(newDare);
        // ACT
        Optional<Dare> storedDare = repo.getUniqueRandomDare();

        // ASSERT
        Assertions.assertEquals(newDare.getText(), storedDare.get().getText());
    }

    @Test
    void gettingDares_ReturnsDaresUniqueWithoutDuplicates() throws MaximumDaresStoredException, TextIsTooLongException {

        final int NUMBER_OF_DARES_TO_INSERT = 33;

        // ARRANGE
        List<Dare> dares = new ArrayList<>();
        for(int i = 1;i<=NUMBER_OF_DARES_TO_INSERT;i++){
            Dare newDare = new Dare(0000L,"The new Dare " + i);
            dares.add(newDare);
            repo.saveDare(newDare);
        }

        // ACT
        List<Dare> retDares = new ArrayList<>();
        for(int i = 1;i<=NUMBER_OF_DARES_TO_INSERT;i++){
            retDares.add(repo.getUniqueRandomDare().get());
        }

        // ASSERT
        // check for duplicates
        int cntSamePos = 0;
        int darePos = 0;
        for(Dare d:dares){
            darePos++;
            int cntFound = 0;
            int retDarePos = 0;
            for(Dare retDare:retDares){
                retDarePos++;
                if(d.getText().equals(retDare.getText())){
                    cntFound++;
                    if(darePos==retDarePos){cntSamePos++;}
                }
            }
            // the should be the dare in the return list only one time, not lesse or more.
            Assertions.assertEquals(1,cntFound);
            Assertions.assertNotEquals(NUMBER_OF_DARES_TO_INSERT,cntSamePos);   // if equal, all are at same position
        }
    }


}
