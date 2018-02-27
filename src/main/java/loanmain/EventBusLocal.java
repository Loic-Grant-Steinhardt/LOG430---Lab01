package loanmain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.google.common.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ak79010 on 2018-02-27.
 */
@Singleton
public class EventBusLocal extends EventBus implements InterfaceEventBus{

    private final Map<Integer,EventBusLocal> eventBusMap;
    private Integer id;

    @Inject
    public EventBusLocal(){
        super();
        eventBusMap = new HashMap<Integer, EventBusLocal>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean exists(Integer id) {
        return eventBusMap.containsKey( id );
    }

    public EventBusLocal load(Integer id) {
        EventBusLocal s = eventBusMap.get(id);
        return s;
    }

    public EventBusLocal save(EventBusLocal p) {
        if( p.getId() == null ) {
            p.setId( eventBusMap.size() + 1 );
        }
        eventBusMap.put( p.getId(), p );
        return p;
    }
}
