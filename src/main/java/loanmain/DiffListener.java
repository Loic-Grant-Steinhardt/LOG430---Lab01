/*
 * Components that are interested in LoanItem differenciations should implement this interface.
 */
package loanmain;


/**
 * Components that are interested in LoanItem differenciations should implement this interface.
 *
 * @author jean-blas imbert
 */
public interface DiffListener {

    /**
     * Compute the real difference between two loan items
     *
     */

    void itemDiffed(Double lDiffMensHorsAss,
                    Double lDiffMensAss,
                    Double lDiffMens,
                    Double lDiffCoutHorsAss,
                    Double lDiffCoutAss,
                    Double lDiffCout,
                    Double lDiffTauxEff,
                    Double lDiffPctSalary,
                    Double lDiffPerYear);

}
