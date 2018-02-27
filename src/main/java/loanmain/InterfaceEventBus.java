package loanmain;

/**
 * Created by ak79010 on 2018-02-27.
 */

import javax.inject.Singleton;

public interface InterfaceEventBus {
    public boolean exists(Integer id);
    public EventBusLocal load(Integer id);
    public EventBusLocal save(EventBusLocal p);
}
