import { APP_ACTION_TYPES } from '../actionTypes';

const initialState = {
    actionInProgress: false,
    errorMessage: null,
    successData: null,
};


function appReducer(state = initialState, action = {}) {
    switch(action.type) {
        case APP_ACTION_TYPES.SET_ACTION_IN_PROGRESS:
            return Object.assign({}, state, { actionInProgress: action.payload });
        case APP_ACTION_TYPES.SET_SUCCESS_DATA:
            return Object.assign({}, state, { successData: action.payload });
        case APP_ACTION_TYPES.SET_ERROR_MESSAGE:
            return Object.assign({}, state, { errorMessage: action.payload });
        default:
            return state;
    }
}

export default appReducer;