package top.study02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.study02.pojo.Bill;
import top.study02.pojo.PackageBill;
import top.study02.pojo.Provider;
import top.study02.service.BillService;
import top.study02.service.ProviderService;
import top.study02.utils.CheckByRegularExpression;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private ProviderService providerService;

    /**
     * 分页显示所有账单信息 此处无条件查找所有账单显示
     * @param pageNum 第几页
     * @param pageSize 每页个数
     * @param model
     * @return
     */
    @RequestMapping(value = "/billList.html" ,method = RequestMethod.GET)
    public String toBillList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize, Model model){
        PackageBill packageBill = billService.getBillsByPageNumberAndPageSize(pageNum,pageSize);
        //查询出账单中存在的所有供应商providersInBill
        List<Provider> providersInBill = providerService.getProviderInBills();
        model.addAttribute("providersInBill",providersInBill);
        model.addAttribute("bills",packageBill.getBills());
        //此bill对象为查询条件,默认无条件全部查询
        Bill bill = new Bill();
        bill.setProvider(new Provider());
        model.addAttribute("bill",bill);
        model.addAttribute("totalPage",packageBill.getTotalPageNum());
        model.addAttribute("currentPage", pageNum);
        return "billList";
    }

    /**
     *  接收ajax请求，跳转到第几页的账单信息，返回json数据
     * @param pageNum   第几页
     * @param pageSize 每页个数
     * @return
     */
    @RequestMapping(value = "/billList.html" ,method = RequestMethod.POST)
    @ResponseBody
    public List<Bill> toAjaxBillList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize){
        PackageBill packageBill = billService.getBillsByPageNumberAndPageSize(pageNum,pageSize);
        return packageBill.getBills();
    }

    /**
     *  根据ID，查看账单详细信息
     * @param pageNum 从第几页点进来的（方便点击返回的时候，还是返回那一页）
     * @param pageSize
     * @param bill
     * @param model
     * @return
     */
    @RequestMapping("/billView.html")
    public String billView(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize,Bill bill,Model model){
        bill =billService.getBillsByPrimaryKey(bill);
        model.addAttribute("bill",bill);
        model.addAttribute("pageNum",pageNum);
        return "billView";
    }

    /**
     *  根据ID跳转到更新页面
     * @param pageNum
     * @param pageSize
     * @param bill
     * @param model
     * @return
     */
    @RequestMapping(value = "/billUpdate.html",method = RequestMethod.GET)
    public String billUpdate(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize,Bill bill,Model model){
        bill = billService.getBillsByPrimaryKey(bill);
        return getString(pageNum, bill, model);
    }


    @RequestMapping(value = "/billUpdate.html",method = RequestMethod.POST)
    public String doBillUpdate(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize,Bill bill,Model model){
        System.out.println(bill);
        //

        int flag = 0;
        if(!CheckByRegularExpression.isMatchBillId(bill.getGoodsId())){
            model.addAttribute("goodsIdError","请输入以PRO-CODE-开头后面跟着2至4位数字的编号");
            flag++;
        }else {
            Bill hasThisBillId = billService.getBillsByGoodsId(bill);
            if(hasThisBillId!=null&&hasThisBillId.getId()!=bill.getId()){
                model.addAttribute("goodsIdError","该编号已存在");
                flag++;
            }
        }

        flag = getFlag(bill, model, flag);

        if(flag!=0){
            return getString(pageNum, bill, model);
        }

        //
        int result =  billService.updateBill(bill);
        System.out.println(result==1?"修改成功":"修改失败");
        return toBillList(pageNum,pageSize,model);
    }

    private String getString(@RequestParam(defaultValue = "1") int pageNum, Bill bill, Model model) {
        List<Provider> providersInBill = providerService.getAllProviders();
        model.addAttribute("providersInBill", providersInBill);
        model.addAttribute("bill", bill);
        model.addAttribute("pageNum", pageNum);
        return "billUpdate";
    }

    private int getFlag(Bill bill, Model model, int flag) {
        if (bill.getGoodsName().replace(" ", "").equals("") || bill.getGoodsName().indexOf(' ') != -1) {
            model.addAttribute("goodsNameError", "请输入正确的商品名称,不能包含空格");
            flag++;
        }

        if (bill.getUnit().replace(" ", "").equals("") || bill.getUnit().indexOf(' ') != -1) {
            model.addAttribute("unitError", "请输入正确的单位,不能包含空格");
            flag++;
        }
        if (bill.getProvider().getId() == 0) {
            model.addAttribute("providerError", "请选择一个供应商");
            flag++;
        }
        return flag;
    }

    @RequestMapping(value = "/queryBill",method = RequestMethod.GET)
    public String queryBill(Bill bill, HttpServletRequest request,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize){
        if(bill.getPayment()==null&&"".equals(bill.getGoodsName())&&bill.getProvider().getId()==0){
            request.setAttribute("queryError","需查询条件");
            return "forward:/billList.html";
        }
        PackageBill packageBill = billService.getBillsByQueryCondition(bill,pageNum,pageSize);
        request.setAttribute("bills",packageBill.getBills());
        List<Provider> providersInBill = providerService.getProviderInBills();
        request.setAttribute("providersInBill",providersInBill);
        request.setAttribute("totalPage",packageBill.getTotalPageNum());
        request.setAttribute("currentPage", pageNum);
        System.out.println(request.getRequestURI());
        System.out.println(request.getQueryString());
        String queryUrl =request.getRequestURI()+"?"+request.getQueryString();
        System.out.println(queryUrl);
        //此bill对象为查询条件
        request.setAttribute("bill",bill);
        request.setAttribute("queryUrl",queryUrl);
        return "billList";
    }

    @RequestMapping(value = "/queryBill",method = RequestMethod.POST)
    @ResponseBody
    public List<Bill> aJaxQueryBill(Bill bill, HttpServletRequest request, HttpServletResponse response,@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize) throws Exception{
        if(bill.getPayment()==null&&"".equals(bill.getGoodsName())&&bill.getProvider().getId()==0){
            request.setAttribute("queryError","需查询条件");
            request.getRequestDispatcher("/billList.html").forward(request,response);
            return null;
        }

        PackageBill packageBill = billService.getBillsByQueryCondition(bill,pageNum,pageSize);
        List<Bill> bills = packageBill.getBills();
        return  bills;
    }

    @RequestMapping("/deleteBill")
    public String deleteBill(Bill bill,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "8") int pageSize){
        if(bill==null){
            return "forward:/billList.html";
        }
        if(bill.getId()!=0){
            int result = billService.removeBillByPrimaryKey(bill);
            System.out.println(result==1?"删除成功":"删除失败");
        }
        return "forward:/billList.html";
    }

    /**
     * 只接收GET请求
     * 跳转到添加账单页面
     * @return
     */
    @RequestMapping(value = "/billAdd.html",method = RequestMethod.GET)
    public String billAdd(Model model){
        List<Provider> providersInBill = providerService.getAllProviders();
        model.addAttribute("providersInBill",providersInBill);
        return "billAdd";
    }

    /**
     * 处理添加账单的表单请求
     * @param bill
     * @return
     */
    @RequestMapping(value = "/billAdd.html",method = RequestMethod.POST)
    public String doBillAdd(Bill bill,Model model){
        System.out.println(bill);
        int flag = 0;
        if(!CheckByRegularExpression.isMatchBillId(bill.getGoodsId())){
            model.addAttribute("goodsIdError","请输入以PRO-CODE-开头后面跟着2至4位数字的编号");
            flag++;
        }else {
            Bill hasThisBillId = billService.getBillsByGoodsId(bill);
            if(hasThisBillId!=null){
                model.addAttribute("goodsIdError","该编号已存在");
                flag++;
            }
        }

        flag = getFlag(bill, model, flag);

        if(flag!=0){
            List<Provider> providersInBill = providerService.getAllProviders();
            model.addAttribute("providersInBill",providersInBill);
            model.addAttribute("bill",bill);
            return "billAdd" ;
        }

        int result = billService.addBill(bill);
        System.out.println(result==1?"添加成功":"添加失败");
        return "redirect:/billList.html";
    }



}
