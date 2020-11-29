package com.imgpulak.cmp.fsm;

public enum CabState {
    IDLE {
        @Override
        public CabState nextState() {
            return ON_TRIP;
        }
    },
    ON_TRIP {
        @Override
        public CabState nextState() {
            return IDLE;
        }
    };
    public abstract CabState nextState();
}
