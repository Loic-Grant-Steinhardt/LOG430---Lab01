/*
 * Components that are interested in LoanItem changes should implement this interface.
 */
package loanmain;

import loangui.LoanControler;
import loanmain.HelloEvent;
import com.google.common.eventbus.EventBus;

import com.google.inject.AbstractModule;

/**
 * Components that are interested in LoanItem changes should implement this interface.
 *
 * @author jean-blas imbert
 */
public class InterfaceModel extends AbstractModule {

        @Override
        protected void configure(){

            bind(InterfaceLoanController.class).to(LoanControler.class);

            bind(InterfaceEventBus.class).to(EventBusLocal.class);


        }


}
