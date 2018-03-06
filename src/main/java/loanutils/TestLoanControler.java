package loanutils;

import loangui.LoanControler;
import loanmain.CalcLoanItem;
import loanmain.EventBusLocal;
import loanmain.LoanItem;
import org.junit.*;
import mockit.*;


/**
 * Created by AM91650 on 2018-03-06.
 */

public class TestLoanControler {
    @Mocked
    EventBusLocal mockedEventBus;
    @Mocked
    CalcLoanItem mockedCalcLoanItem;

    LoanItem testLoanItem=new LoanItem();
    LoanControler testLoanControler=new LoanControler();
    Float pAmo=new Float(1), pTau=new Float(2), pMon=new Float(3), pTim=new Float(4);

    @Test
    public void doOperationAbc() {
        testLoanControler.setItem(testLoanItem);
        testLoanControler.setLoanType(testLoanItem.getLoanType());
        testLoanControler.updateEntry(pAmo,pTau,0F,pTim);
        new Expectations() {{
            mockedCalcLoanItem.computeMensAss(testLoanItem); result = anyDouble;
        }};

        new Verifications() {{
            mockedCalcLoanItem.computeMensAss(testLoanItem); times = 1;
            mockedCalcLoanItem.computeDuration(testLoanItem); times = 0;
            mockedCalcLoanItem.computeAmount(testLoanItem); times = 0;
            mockedCalcLoanItem.computeRate(testLoanItem); times = 0;
        }};
    }
}