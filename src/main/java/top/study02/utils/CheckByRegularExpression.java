package top.study02.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CheckByRegularExpression {

    private static Pattern phonePattern;
    private static Pattern providerIdPattern;
    private static Pattern billIdPattern;

    @Value("${CheckByRegularExpression.phoneTele}")
    public void setPhoneTele(String phoneTele){
        phonePattern=Pattern.compile(phoneTele);
    }

    @Value("${CheckByRegularExpression.providerId}")
    public void setProviderIdPattern(String providerId){
        providerIdPattern=Pattern.compile(providerId);
    }

    @Value("${CheckByRegularExpression.billId}")
    public void setBillIdPattern(String billId){
        billIdPattern = Pattern.compile(billId);
    }
    /**
     * 判断是否是合法的电话号码
     * @param teleNum
     * @return
     */
    public static boolean isTele(String teleNum){
        return phonePattern.matcher(teleNum).matches();
    }

    /**
     * 判断是否复合providerId规则
     * @param providerId
     * @return
     */
    public static boolean isMatchProviderId(String providerId){
        return providerIdPattern.matcher(providerId).matches();
    }

    /**
     * 判断是否复合billId规则
     * @param billID
     * @return
     */
    public static boolean isMatchBillId(String billID){
        return billIdPattern.matcher(billID).matches();
    }
}
