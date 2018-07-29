import { combineReducers } from 'redux'
import { reducer as formReducer } from 'redux-form';
import app from './appReducer';

export default combineReducers({
  app,
  form: formReducer,
});