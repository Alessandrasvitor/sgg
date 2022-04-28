import {COUNTER_INCREMENT, COUNTER_DECREMENT, COUNTER_INCREMENT_BY_AMOUNT} from '../actions/sistemaActions';

const INITIAL_STATE = {
  value: 0,
};

export function sistemaReducer(state = INITIAL_STATE, action) {
  switch (action.type) {
    case COUNTER_INCREMENT: {
      return {
        value: state.value + 1,
      };
    }

    case COUNTER_INCREMENT_BY_AMOUNT: {
      return {
        value: state.value + action.payload.amount,
      };
    }

    case COUNTER_DECREMENT: {
      return {
        value: state.value - 1,
      };
    }

    default: {
      return state;
    }
  }
}
