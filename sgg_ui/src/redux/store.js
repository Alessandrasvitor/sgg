import { createStore, combineReducers } from 'redux';

import {sistemaReducer} from './reducers/sistemaReducer';

export default createStore(
  combineReducers({
    counter: sistemaReducer,
  })
);
