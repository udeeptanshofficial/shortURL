import { APP_ACTION_TYPES } from "../actionTypes";
import ShortUrlAPI from "./api/shortUrlAPI";

export function setActionInProgress(payload = false) {
    return { type: APP_ACTION_TYPES.SET_ACTION_IN_PROGRESS, payload };
}

export function setSucuessData(payload) {
    return { type: APP_ACTION_TYPES.SET_SUCCESS_DATA, payload };
}

export function setErrorMessage(payload) {
    return { type: APP_ACTION_TYPES.SET_ERROR_MESSAGE, payload };
}

export function handleCreateShortURL(payload) {
    return (dispatch) => {
        dispatch(setSucuessData(null));
        dispatch(setErrorMessage(''));
        dispatch(setActionInProgress(true));
        ShortUrlAPI.create(payload)
            .then((res) => {
                if(res.data.code === 200){
                    dispatch(setSucuessData(res.data.message));
                }
                else{
                    dispatch(setErrorMessage(res.data.message));
                }
                dispatch(setActionInProgress(false));
            })
            .catch(() => {
                dispatch(setErrorMessage('Network error'));
                dispatch(setActionInProgress(false));
            });
    };
}

export function handleGetShortUrlData(shortUrl) {
    return (dispatch) => {
        dispatch(setSucuessData(null));
        dispatch(setErrorMessage(''));
        dispatch(setActionInProgress(true));
        ShortUrlAPI.getById(shortUrl)
            .then((res) => {
                if(res.data.code === 200 || res.data.code === 207){
                    dispatch(setSucuessData(res.data.data));
                }
                else{
                    dispatch(setErrorMessage(res.data.message));
                }
                dispatch(setActionInProgress(false));
            })
            .catch(() => {
                dispatch(setErrorMessage('Network error'));
                dispatch(setActionInProgress(false));
            });
    }
}

export function handleVerifySecurityKey(payload) {
    return (dispatch, getState) => {
        const oldState = getState().app.successData;
        dispatch(setErrorMessage(''));
        dispatch(setActionInProgress(true));
        ShortUrlAPI.verifySecurityKey(payload)
            .then((res) => {
                dispatch(setSucuessData(res.data.data || oldState));
                dispatch(setErrorMessage(res.data.message));
                dispatch(setActionInProgress(false));
            })
            .catch(() => {
                dispatch(setErrorMessage('Network error'));
                dispatch(setActionInProgress(false));
            });
    }
}