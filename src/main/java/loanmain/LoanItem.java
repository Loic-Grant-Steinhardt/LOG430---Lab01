/*
 * This class represents the item of a loan, with its entries, options and results.
 */
package loanmain;

import loansolver.OneParamFuncItf;
import loansolver.Solver;
import loansolver.SolverItf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the item of a loan, with its entries, options and results.
 *
 * @author jean-blas imbert
 */
public final class LoanItem implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Suivant le type de loan, le calcul n'est pas le même.
     */
    public static enum LoanType implements Serializable {

        /**
         * Détermine la mensualité connaissant les 3 autres
         */
        MENSUALITE,
        /**
         * Détermine le taux connaissant les 3 autres
         */
        TAUX,
        /**
         * Détermine le montant connaissant les 3 autres
         */
        MONTANT,
        /**
         * Détermine la durée connaissant les 3 autres
         */
        DUREE
    }
    /**
     * The item loan type
     */
    private LoanType type = LoanType.MENSUALITE;
    /**
     * List of components that are interested in item change events
     */
    private transient List<ChangeListener> changeListeners = null;
    /**
     * List of components that are interested in item diff events
     */
    private transient List<DiffListener> diffListeners = null;
    /**
     * Name of this loan item
     */
    private String name = null;
    //Entries
    /**
     * The amount of the loan, in euros
     */
    private Float amount = 0F;
    /**
     * The rate of the loan, in percents
     */
    private Float taux = 0F;
    /**
     * The amount of one monthly payment, in euros
     */
    private Float mensualite = 0F;
    /**
     * The duration of the loan, in months
     */
    private Float duree = 0F;
    //Options
    /**
     * The bank fees, in euros
     */
    private Float frais = 0F;
    /**
     * The user monthly income, in euros
     */
    private Float salary = 0F;
    /**
     * The insurance of the loan, in percents
     */
    private Float insurance = 0F;

    public Double lMensHorsAssIC = computeMensHorsAss(this);
    public Double lMensAssIC = computeMensAss(this);
    public Float dureeIC = this.getDuree();
    public Float amountIC = this.getAmount();
    public Float fraisIC = this.getFrais();
    public Double lF = (this.getMensualite()  + this.getFrais() / 12D / this.getDuree()) / (this.getAmount());
    public Double lTauxEffIC = solveTaux(lF, this);
    public Float salaryIC = this.getSalary();
    public Float insuranceIC = this.getInsurance();
    public Double lNotFeeIC = this.getAmount() * 0.07D;
    public  Float mensualiteIC = this.getMensualite();
    public Float tauxIC = this.getTaux();
    public  boolean isMontantIC = this.getLoanType() != LoanItem.LoanType.MONTANT;
    public boolean isTauxIC = this.getLoanType() != LoanItem.LoanType.TAUX;
    public  boolean isDureeIC = this.getLoanType() != LoanType.DUREE;
    public boolean isMensualiteIC = this.getLoanType() != LoanType.MENSUALITE;


    public Double lMensHorsAss1 ;
    public Double lMensHorsAss2 ;
    public Double lDiffMensHorsAss ;
    public Double lMensAss1 ;
    public Double lMensAss2 ;
    public Double lDiffMensAss ;
    public Double lMens1 ;
    public Double lMens2 ;
    public Double lDiffMens ;
    public Double lCoutHorsAss1 ;
    public Double lCoutHorsAss2 ;
    public Double lDiffCoutHorsAss ;
    public Double lCoutAss1 ;
    public Double lCoutAss2 ;
    public Double lDiffCoutAss ;
    public Double lCout1 ;
    public Double lCout2 ;
    public Double lDiffCout ;
    public Double lTauxEff1 ;
    public Double lTauxEff2 ;
    public Double lDiffTauxEff ;
    public Double lPctSalary1 ;
    public Double lPctSalary2 ;
    public Double lDiffPctSalary ;
    public Double lPerYear1 ;
    public Double lPerYear2 ;
    public Double lDiffPerYear ;
//Methods
    /**
     * Default constructor
     */
    public LoanItem() {
        changeListeners = new ArrayList<ChangeListener>();
    }

    /**
     * Clone this
     *
     * @return the clone of this
     */
    @Override
    public LoanItem clone() {
        LoanItem lClone = new LoanItem();
        lClone.setAmount(getAmount());
        lClone.setDuree(getDuree());
        lClone.setFrais(getFrais());
        lClone.setInsurance(getInsurance());
        lClone.setMensualite(getMensualite());
        lClone.setName(getName());
        lClone.setTaux(getTaux());
        lClone.setSalary(getSalary());
        lClone.setLoanType(getLoanType());
        return lClone;
    }

//Listeners
    /**
     * Add a new listener
     *
     * @param pListener the new listener
     */
    public void addChangeListener(final ChangeListener pListener) {
        if (changeListeners == null) {
            //This happens after a deserialization
            changeListeners = new ArrayList<ChangeListener>();
        }
        if (!changeListeners.contains(pListener)) {
            changeListeners.add(pListener);
        }
    }

    /**
     * Remove a listener
     *
     * @param pListener the listener to remove from the list
     */
    public void removeChangeListener(final ChangeListener pListener) {
        if (changeListeners.contains(pListener)) {
            changeListeners.remove(pListener);
        }
    }

    /**
     * Aware the listeners that this item has changed
     */
    public void fireItemChanged() {
        for (ChangeListener lListener : changeListeners) {
            itemChanged();
            lListener.itemChanged(lMensHorsAssIC,
                    lMensAssIC ,
                    dureeIC ,
                    amountIC ,
                    fraisIC ,
                    lTauxEffIC ,
                    salaryIC,
                    insuranceIC ,
                    lNotFeeIC ,
                    mensualiteIC ,
                    tauxIC ,
                    isMontantIC ,
                    isTauxIC ,
                    isDureeIC ,
                    isMensualiteIC);
        }
    }

    private void itemChanged() {
         lMensHorsAssIC = computeMensHorsAss(this);
         lMensAssIC = computeMensAss(this);
         dureeIC = this.getDuree();
         amountIC = this.getAmount();
         fraisIC = this.getFrais();
         Double lF = (this.getMensualite()  + this.getFrais() / 12D / this.getDuree()) / (this.getAmount());
         lTauxEffIC = solveTaux(lF, this);
         salaryIC = this.getSalary();
         insuranceIC = this.getInsurance();
         lNotFeeIC = this.getAmount() * 0.07D;
         mensualiteIC = this.getMensualite();
         tauxIC = this.getTaux();
         isMontantIC = this.getLoanType() != LoanItem.LoanType.MONTANT;
         isTauxIC = this.getLoanType() != LoanItem.LoanType.TAUX;
         isDureeIC = this.getLoanType() != LoanType.DUREE;
         isMensualiteIC = this.getLoanType() != LoanType.MENSUALITE;

    }

    public static Double computeMensHorsAss(final LoanItem pItem) {
        if (pItem.getAmount().equals(0F) || pItem.getTaux().equals(0F) || pItem.getDuree().equals(0F)) {
            return null;
        }
        return pItem.getAmount() * calcF(pItem);
    }

    public static Double computeMensAss(final LoanItem pItem) {
        if (pItem.getAmount().equals(0F) || pItem.getInsurance().equals(0F)) {
            return null;
        }
        return pItem.getAmount() * pItem.getInsurance() / 1200D;
    }

    private static Double calcF(final LoanItem pItem) {
        double lTaux = pItem.getTaux();
        double lDuration = pItem.getDuree();
        double lX = lTaux / 1200D;
        return lX / (1D - 1D / Math.pow(1D + lX, lDuration * 12D));
    }

    private static Double solveTaux(final Double pC, final LoanItem pItem) {
        final double lD = pItem.getDuree();
        final double lPuis = 1D + 1D / 12D / lD;
        class lFunc implements OneParamFuncItf<Double> {

            @Override
            public Double f(Double pX) {
                return Math.pow(pX, lPuis) - (pC + 1D) * pX + pC;
            }
        }
        double lZ0 = Math.pow((pC + 1D) / lPuis, 12D * lD);
        SolverItf<Double> lSolver = new Solver();
        try {
            Double lRoot = lSolver.solve(new lFunc(), (lZ0 + 1D) / 2D, lZ0 + 1D, 0.00001D);
            return 1200D * (Math.pow(lRoot, 1D / 12D / lD) - 1D);
        } catch (ArithmeticException lEx) {
            return 1000D;
        }
    }

    /**
     * Add a new listener
     *
     * @param pListener the new listener
     */
    public void addDiffListener(final DiffListener pListener) {
        if (diffListeners == null) {
            //This happens after a deserialization
            diffListeners = new ArrayList<DiffListener>();
        }
        if (!diffListeners.contains(pListener)) {
            diffListeners.add(pListener);
        }
    }

    /**
     * Remove a listener
     *
     * @param pListener the listener to remove from the list
     */
    public void removeDiffListener(final DiffListener pListener) {
        if (diffListeners.contains(pListener)) {
            diffListeners.remove(pListener);
        }
    }

    /**
     * Aware the listeners that this item is diffed
     *
     * @param pItem1 the first loan item
     * @param pItem2 the second loan item
     */
    public void fireItemDiffed(final LoanItem pItem1, final LoanItem pItem2) {
        for (DiffListener lListener : diffListeners) {
            itemDiffed(pItem1, pItem2);
            lListener.itemDiffed( lDiffMensHorsAss, lDiffMensAss, lDiffMens, lDiffCoutHorsAss, lDiffCoutAss, lDiffCout, lDiffTauxEff, lDiffPctSalary,lDiffPerYear);
        }
    }

    private void itemDiffed(final LoanItem pItem1, final LoanItem pItem2) {
         lMensHorsAss1 = computeMensHorsAss(pItem1);
        if (lMensHorsAss1 == null) {
            lMensHorsAss1 = 0D;
        }
         lMensHorsAss2 = computeMensHorsAss(pItem2);
        if (lMensHorsAss2 == null) {
            lMensHorsAss2 = 0D;
        }
         lDiffMensHorsAss = lMensHorsAss1 - lMensHorsAss2;
         lMensAss1 = computeMensAss(pItem1);
        if (lMensAss1 == null) {
            lMensAss1 = 0D;
        }
         lMensAss2 = computeMensAss(pItem2);
        if (lMensAss2 == null) {
            lMensAss2 = 0D;
        }
         lDiffMensAss = lMensAss1 - lMensAss2;
         lMens1 = lMensHorsAss1 + lMensAss1;
         lMens2 = lMensHorsAss2 + lMensAss2;
         lDiffMens = lMens1 - lMens2;
         lCoutHorsAss1 = lMensHorsAss1 * pItem1.getDuree() * 12D - pItem1.getAmount();
         lCoutHorsAss2 = lMensHorsAss2 * pItem2.getDuree() * 12D - pItem2.getAmount();
         lDiffCoutHorsAss = lCoutHorsAss1 - lCoutHorsAss2;
         lCoutAss1 = lMensAss1 * pItem1.getDuree() * 12D;
         lCoutAss2 = lMensAss2 * pItem2.getDuree() * 12D;
         lDiffCoutAss = lCoutAss1 - lCoutAss2;
         lCout1 = lCoutHorsAss1 + lCoutAss1;
         lCout2 = lCoutHorsAss2 + lCoutAss2;
         lDiffCout = lCout1 - lCout2 + pItem1.getFrais() - pItem2.getFrais();
         lTauxEff1 = calcTauxEff(pItem1);
         lTauxEff2 = calcTauxEff(pItem2);
         lDiffTauxEff = (lTauxEff1 == null ? 0D : lTauxEff1) - (lTauxEff2 == null ? 0D : lTauxEff2);
         lPctSalary1 = pItem1.getSalary().equals(0F) ? 0F : lMens1 / pItem1.getSalary() * 100D;
         lPctSalary2 = pItem2.getSalary().equals(0F) ? 0F : lMens2 / pItem2.getSalary() * 100D;
         lDiffPctSalary = lPctSalary1 - lPctSalary2;
         lPerYear1 = lMens1 * 12D;
         lPerYear2 = lMens2 * 12D;
         lDiffPerYear = lPerYear1 - lPerYear2;
    }

    public static Double calcTauxEff(final LoanItem pItem) {
        Double lF = (pItem.getMensualite()  + pItem.getFrais() / 12D / pItem.getDuree()) / (pItem.getAmount());
        return solveTaux(lF, pItem);
    }

//getters and setters
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float pAmount) {
        this.amount = pAmount;
    }

    public Float getTaux() {
        return taux;
    }

    public void setTaux(Float pTaux) {
        this.taux = pTaux;
    }

    public Float getMensualite() {
        return mensualite;
    }

    public void setMensualite(Float pMensualite) {
        this.mensualite = pMensualite;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float pDuree) {
        this.duree = pDuree;
    }

    public Float getFrais() {
        return frais;
    }

    public void setFrais(Float pFrais) {
        this.frais = pFrais;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float pSalary) {
        this.salary = pSalary;
    }

    public Float getInsurance() {
        return insurance;
    }

    public void setInsurance(Float pInsurance) {
        this.insurance = pInsurance;
    }

    public void setName(final String pName) {
        name = pName;
    }

    public String getName() {
        return name;
    }

    public LoanType getLoanType() {
        return type;
    }

    public void setLoanType(LoanType pType) {
        this.type = pType;
    }
}
