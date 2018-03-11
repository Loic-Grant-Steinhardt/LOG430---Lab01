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
    LoanItem testLoanItem=new LoanItem();
    LoanControler testLoanControler=new LoanControler();
    Float pAmo=new Float(1200), pTau=new Float(1200), pMon=new Float(1200), pTim=new Float(1200);

    @Injectable
    EventBusLocal mockedEventBus;
    @Injectable
    CalcLoanItem mockedCalcLoanItem;


    @Test
    public void testMensualite() {
        testLoanItem.setInsurance(pTau);
        testLoanItem.setLoanType(LoanItem.LoanType.MENSUALITE);
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

    @Test
    public void testDuree() {
        testLoanItem.setInsurance(pTau);
        testLoanItem.setLoanType(LoanItem.LoanType.DUREE);
        testLoanControler.setItem(testLoanItem);
        testLoanControler.setLoanType(testLoanItem.getLoanType());
        testLoanControler.updateEntry(pAmo,pTau,pMon,0F);
        new Expectations() {{
            mockedCalcLoanItem.computeDuration(testLoanItem); result = anyDouble;
        }};

        new Verifications() {{
            mockedCalcLoanItem.computeMensAss(testLoanItem); times = 0;
            mockedCalcLoanItem.computeDuration(testLoanItem); times = 1;
            mockedCalcLoanItem.computeAmount(testLoanItem); times = 0;
            mockedCalcLoanItem.computeRate(testLoanItem); times = 0;
        }};
    }

    @Test
    public void testMontant() {
        testLoanItem.setInsurance(pTau);
        testLoanItem.setLoanType(LoanItem.LoanType.MONTANT);
        testLoanControler.setItem(testLoanItem);
        testLoanControler.setLoanType(testLoanItem.getLoanType());
        testLoanControler.updateEntry(0F,pTau,pMon,pTim);
        new Expectations() {{
            mockedCalcLoanItem.computeAmount(testLoanItem); result = anyDouble;
        }};

        new Verifications() {{
            mockedCalcLoanItem.computeMensAss(testLoanItem); times = 0;
            mockedCalcLoanItem.computeDuration(testLoanItem); times = 0;
            mockedCalcLoanItem.computeAmount(testLoanItem); times = 1;
            mockedCalcLoanItem.computeRate(testLoanItem); times = 0;
        }};
    }

    @Test
    public void testTaux() {
        testLoanItem.setInsurance(pTau);
        testLoanItem.setLoanType(LoanItem.LoanType.TAUX);
        testLoanControler.setItem(testLoanItem);
        testLoanControler.setLoanType(testLoanItem.getLoanType());
        testLoanControler.updateEntry(pAmo,0F,pMon,pTim);
        new Expectations() {{
            mockedCalcLoanItem.computeRate(testLoanItem); result = anyDouble;
        }};

        new Verifications() {{
            mockedCalcLoanItem.computeMensAss(testLoanItem); times = 0;
            mockedCalcLoanItem.computeDuration(testLoanItem); times = 0;
            mockedCalcLoanItem.computeAmount(testLoanItem); times = 0;
            mockedCalcLoanItem.computeRate(testLoanItem); times = 1;
        }};
    }
}