package top.study02.mapper;

import top.study02.pojo.Bill;

import java.util.List;

public interface BillMapper {

    List<Bill> getAllBills();

    Bill getBillsByPrimaryKey(Bill bill);

    int updateBill(Bill bill);

    List<Bill> getBillsByQueryCondition(Bill bill);

    int removeBillByPrimaryKey(Bill bill);

    Bill getBillByGoodsId(Bill bill);

    int addBill(Bill bill);
}
