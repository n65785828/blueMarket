package top.study02.service.serviceImp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.study02.mapper.BillMapper;
import top.study02.mapper.ProviderMapper;
import top.study02.pojo.Bill;
import top.study02.pojo.PackageBill;
import top.study02.pojo.Provider;
import top.study02.service.BillService;

import java.util.Date;
import java.util.List;

@Service
public class BillServiceImp implements BillService {

    @Autowired
    private BillMapper billMapper;


    @Override
    public PackageBill getBillsByPageNumberAndPageSize(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Bill> bills = billMapper.getAllBills();
        PageInfo<Bill> info  = new PageInfo<>(bills);
        PackageBill packageBill = new PackageBill();
        packageBill.setTotalPageNum(info.getPages());
        packageBill.setBills(bills);
        return packageBill;
    }

    @Override
    public Bill getBillsByPrimaryKey(Bill bill) {
        return billMapper.getBillsByPrimaryKey(bill);
    }

    @Override
    public int updateBill(Bill bill) {
        int result =billMapper.updateBill(bill);
        return result;
    }

    @Override
    public PackageBill getBillsByQueryCondition(Bill bill,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Bill> bills = billMapper.getBillsByQueryCondition(bill);
        PageInfo<Bill> info = new PageInfo<>(bills);
        PackageBill packageBill = new PackageBill();
        packageBill.setBills(bills);
        packageBill.setTotalPageNum(info.getPages());
        return packageBill;
    }

    @Override
    public int removeBillByPrimaryKey(Bill bill) {
        return billMapper.removeBillByPrimaryKey(bill);
    }

    @Override
    public Bill getBillsByGoodsId(Bill bill) {
        return billMapper.getBillByGoodsId(bill);
    }

    @Override
    public int addBill(Bill bill) {
        bill.setCreateDate(new Date());

        return billMapper.addBill(bill);
    }
}
