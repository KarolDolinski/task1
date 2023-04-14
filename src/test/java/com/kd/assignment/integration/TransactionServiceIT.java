package com.kd.assignment.integration;

import com.kd.assignment.dto.DataRequest;
import com.kd.assignment.dto.Pair;
import com.kd.assignment.exception.TransactionException;
import com.kd.assignment.repository.TransactionServiceImpl;
import com.kd.assignment.repository.DataRepository;
import com.kd.assignment.service.impl.DataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.kd.assignment.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TransactionServiceIT {
    private TransactionServiceImpl transactionService;

    private DataRepository dataRepository;

    private DataServiceImpl dataService;

    @BeforeEach
    public void setUp() {
        dataRepository = new DataRepository();
        transactionService = new TransactionServiceImpl(dataRepository);
        dataService = new DataServiceImpl(transactionService);
    }


    @Test
    public void transactionService_getsFirstTransaction_getsValue() {
        transactionService.begin();
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));

        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
    }

    @Test
    public void transactionService_noTransaction_getsValue() {
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));

        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
    }

    /*
> BEGIN
> SET a 1
> GET a
1
> BEGIN
> SET a 2
> GET a
2
> ROLLBACK
> GET a
1
> ROLLBACK
> GET a
null
 */
    @Test
    public void transactionService_testCase1_getsValue() {
        transactionService.begin();
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));
        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
        transactionService.begin();
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE1));
        assertEquals(TEST_VALUE1, dataService.getValue(TEST_KEY));
        transactionService.rollback();
        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
        transactionService.rollback();
        assertEquals(null, dataService.getValue(TEST_KEY));
    }

/*>
SET a 1
> SET b 1
> COUNT 1
2
> COUNT 2
0
> DELETE a
> COUNT a 1
1
> SET b 3
> COUNT 1
0
*/

    @Test
    public void transactionService_testCase2_getsValue() {
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));
        dataService.setData(getRequest(TEST_KEY1, TEST_VALUE));
        assertEquals(2, dataService.countData(TEST_VALUE));
        assertEquals(0, dataService.countData(TEST_VALUE1));
        dataService.removeValue(TEST_KEY);
        assertEquals(1, dataService.countData(TEST_VALUE));
        DataRequest request2 = getRequest(TEST_KEY1, TEST_VALUE2);
        dataService.setData(request2);
        assertEquals(1, dataService.countData(TEST_VALUE2));
    }

    /*
    > BEGIN
> SET a 1
> GET a
1
> BEGIN
> SET a 2
> GET a
2
> ROLLBACK
> GET a
1
> ROLLBACK
> GET a
null
     */
    @Test
    public void transactionService_testCase3_getsValue() {
        transactionService.begin();
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));
        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
        transactionService.begin();
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE1));
        assertEquals(TEST_VALUE1, dataService.getValue(TEST_KEY));
        transactionService.rollback();
        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
        transactionService.rollback();
        assertEquals(null, dataService.getValue(TEST_KEY));
    }

    /*
    BEGIN
> SET a 1
> BEGIN
> SET a 2
> COMMIT
> GET a
2
> ROLLBACK
exception!
     */
    @Test
    public void transactionService_testCase4_throwsException() {
        transactionService.begin();
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));
        transactionService.begin();
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE1));
        transactionService.commit();
        assertEquals(TEST_VALUE1, dataService.getValue(TEST_KEY));
        assertThrows(TransactionException.class, () -> {
            transactionService.rollback();
        });
    }
    /*
    > SET a 1
> BEGIN
> GET a
1
> SET a 2
> BEGIN
> DELETE a
> GET a
null
> ROLLBACK
> GET a
2
> COMMIT
> GET a
2
     */
    @Test
    public void transactionService_testCase5_throwsException() {
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));
        transactionService.begin();
        assertEquals(TEST_VALUE, dataService.getValue(TEST_KEY));
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE1));
        transactionService.begin();
        dataService.removeValue(TEST_KEY);
        assertEquals(null, dataService.getValue(TEST_KEY));
        transactionService.rollback();
        assertEquals(TEST_VALUE1, dataService.getValue(TEST_KEY));
        transactionService.commit();
        assertEquals(TEST_VALUE1, dataService.getValue(TEST_KEY));
    }

    /*
    > SET a 1
> BEGIN
> COUNT 1
1
> BEGIN
> DELETE a
> COUNT 1
0
> ROLLBACK
> COUNT
     */

    @Test
    public void transactionService_testCase6_throwsException() {
        dataService.setData(getRequest(TEST_KEY, TEST_VALUE));
        transactionService.begin();
        assertEquals(1, dataService.countData(TEST_VALUE));
        transactionService.begin();
        dataService.removeValue(TEST_KEY);
        assertEquals(0, dataService.countData(TEST_VALUE));
        transactionService.rollback();
        assertEquals(1, dataService.countData(TEST_VALUE));
    }
    private static DataRequest getRequest(String testKey, String testValue1) {
        return DataRequest.builder()
                .data(List.of(new Pair(testKey, testValue1)))
                .build();
    }
}