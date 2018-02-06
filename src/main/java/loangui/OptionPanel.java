/*
 * The panels for options components
 */
package loangui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.common.eventbus.Subscribe;
import loanmain.CalcLoanItem;
import loanmain.ChangeListener;
import loanmain.HelloEvent;
import loanmain.LoanItem;
import loanutils.FloatJTextField;
import loanutils.FormatterFactory;
import loanutils.FrameUtils;
import static loanutils.MyBundle.translate;

/**
 * The panels for options components
 *
 * @author jean-blas imbert
 */
public class OptionPanel extends JPanel implements ChangeListener {

    private static final long serialVersionUID = 1L;
    /**
     * Agency fees text field
     */
    private FloatJTextField afeTF = new FloatJTextField(10);
    /**
     * Insurance fees text field
     */
    private FloatJTextField assTF = new FloatJTextField(10);
    /**
     * Notary fees text field
     */
    private FloatJTextField notTF = new FloatJTextField(10);
    /**
     * Salary text field
     */
    private FloatJTextField salTF = new FloatJTextField(10);
    /**
     * The loan controler
     */
    private LoanControler controler = null;
    /**
     * This field stores the current value when the mouse enter a text field
     */
    private Float curValue = null;

    public LoanItem lItem;


    @Subscribe
    public void someoneSaidHello(HelloEvent event) throws InterruptedException {
        if(event.getMessage()=="optionPanel activé !!"){
        System.out.println("Source of event said \"" + event.getMessage() + "\"");
        itemChanged(  lItem.lMensHorsAssIC,
                lItem.lMensAssIC ,
                lItem.dureeIC ,
                lItem.amountIC ,
                lItem.fraisIC ,
                lItem.lTauxEffIC ,
                lItem.salaryIC,
                lItem.insuranceIC ,
                lItem.lNotFeeIC ,
                lItem.mensualiteIC ,
                lItem.tauxIC ,
                lItem.isMontantIC ,
                lItem.isTauxIC ,
                lItem.isDureeIC,
                lItem.isMensualiteIC);
    }
    }
    public void setLoanItem(LoanItem lItem){
        this.lItem=lItem;
    }
    /**
     * Constructor
     */
    public OptionPanel(final LoanControler pControler) {
        controler = pControler;
        layoutComponents();
        //Add text field focus listener
        FocusListener lFocusListener = new FocusListener() {

            public void focusGained(FocusEvent pEvent) {
                curValue = ((FloatJTextField) pEvent.getSource()).getValue();
            }


            public void focusLost(FocusEvent pEvent) {
                Float lNewValue = ((FloatJTextField) pEvent.getSource()).getValue();
                if ((curValue == null && lNewValue != null) || (curValue != null && !curValue.equals(lNewValue))) {
                    controler.updateOption(afeTF.getValue(), assTF.getValue(), notTF.getValue(), salTF.getValue());
                }
                curValue = null;
            }
        };
        afeTF.addFocusListener(lFocusListener);
        assTF.addFocusListener(lFocusListener);
        notTF.addFocusListener(lFocusListener);
        salTF.addFocusListener(lFocusListener);
    }

    
    /**
     * Fill the components with their respective values
     *
     */

    public void itemChanged(Double lMensHorsAss,
                            Double lMensAss ,
                            Float duree ,
                            Float amount ,
                            Float frais ,
                            Double lTauxEff ,
                            Float salary,
                            Float insurance ,
                            Double lNotFee ,
                            Float mensualite ,
                            Float taux ,
                            boolean isMontant ,
                            boolean isTaux ,
                            boolean isDuree ,
                            boolean isMensualite) {
        afeTF.setText(FormatterFactory.fmtCurrencyNoSymbol(frais));
        assTF.setText(FormatterFactory.fmtCurrencyNoSymbol(insurance));

        notTF.setText(FormatterFactory.fmtCurrencyNoSymbol(lNotFee.floatValue()));
        salTF.setText(FormatterFactory.fmtCurrencyNoSymbol(salary));
        afeTF.setEditable(!controler.isDiffed());
        assTF.setEditable(!controler.isDiffed());
        notTF.setEditable(!controler.isDiffed());
        salTF.setEditable(!controler.isDiffed());
    }

    /**
     * Lay out the components
     */
    private void layoutComponents() {
        JLabel lAfeLabel = new JLabel(translate("fraisdossier"));
        lAfeLabel.setToolTipText(translate("fraisdossiertooltip"));
        afeTF.setToolTipText(translate("fraisdossiertooltip"));
        JLabel lSalLabel = new JLabel(translate("salaire"));
        lSalLabel.setToolTipText(translate("salairetooltip"));
        salTF.setToolTipText(translate("salairetooltip"));
        JLabel lNotLabel = new JLabel(translate("notaire"));
        lNotLabel.setToolTipText(translate("notairetooltip"));
        notTF.setToolTipText(translate("notairetooltip"));
        JLabel lAssLabel = new JLabel(translate("assurance"));
        lAssLabel.setToolTipText(translate("assurancetooltip"));
        assTF.setToolTipText(translate("assurancetooltip"));
        setBorder(BorderFactory.createTitledBorder(translate("options")));
        setToolTipText(translate("optionstooltip"));
        FrameUtils.buildPanelGroup(this, new JComponent[][]{{lAfeLabel, afeTF, new JLabel("\u20ac")},
            {lSalLabel, salTF, new JLabel("\u20ac")}, {lAssLabel, assTF, new JLabel("%")},
            {lNotLabel, notTF, new JLabel("\u20ac")}});
    }
}
