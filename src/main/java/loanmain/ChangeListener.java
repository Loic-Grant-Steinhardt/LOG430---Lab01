/*
 * Components that are interested in LoanItem changes should implement this interface.
 */
package loanmain;


/**
 * Components that are interested in LoanItem changes should implement this interface.
 *
 * @author jean-blas imbert
 */
public interface ChangeListener {


    /**
     * Function should be fired when the item has changed
     *
     */
    void itemChanged(Double lMensHorsAss,
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
    boolean isMensualite);

}
