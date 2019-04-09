package top.study02;

;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.study02.mapper.BillMapper;
import top.study02.pojo.Bill;
import top.study02.service.UserService;
import top.study02.utils.CheckByRegularExpression;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Study02ApplicationTests {

    @Autowired
    BillMapper billMapper;

    @Test
    public void contextLoads() {
        List<Bill> allBills = billMapper.getAllBills();
        for (Bill bill:allBills
             ) {
            System.out.println(bill);
        }

    }

}
