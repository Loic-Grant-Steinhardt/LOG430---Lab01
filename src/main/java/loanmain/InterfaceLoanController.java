package loanmain;

import loangui.LoanControler;

/**
 * Created by ak79010 on 2018-02-27.
 */
public interface InterfaceLoanController {

    public boolean exists(Integer id);
    public LoanControler load(Integer id);
    public LoanControler save(LoanControler p);

}
