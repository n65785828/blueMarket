package top.study02.service;


import top.study02.pojo.Bill;
import top.study02.pojo.PackageBill;

import java.util.List;


public interface BillService {
    PackageBill getBillsByPageNumberAndPageSize(int pageNum, int pageSize);

    Bill getBillsByPrimaryKey(Bill bill);

    int updateBill(Bill bill);

    PackageBill getBillsByQueryCondition(Bill bill,int pageNum,int pageSize);

    int removeBillByPrimaryKey(Bill bill);

    Bill getBillsByGoodsId(Bill bill);

    int addBill(Bill bill);
}
