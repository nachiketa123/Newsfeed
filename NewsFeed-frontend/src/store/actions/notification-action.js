import { notificationActions } from "../notification-slice";

export const HttpStatusCodes = {
    UNAUTHORIZED: 401,
    NOT_FOUND: 404,
    BAD_REQUEST: 400,
    SERVER_ERROR: 500,
    FORBIDDEN: 403,
}

const errorHandler = (httpStatusCode, message) => dispatch => {
    switch(httpStatusCode){
        case HttpStatusCodes.UNAUTHORIZED:
            dispatch(notificationActions.updateFailureStatus(message))
            break;
        case HttpStatusCodes.NOT_FOUND:
            dispatch(notificationActions.updateFailureStatus(message))
            break;
        case HttpStatusCodes.BAD_REQUEST:
            dispatch(notificationActions.updateFailureStatus(message))
            break;
        case HttpStatusCodes.SERVER_ERROR:
            dispatch(notificationActions.updateFailureStatus(message))
            break;
        case HttpStatusCodes.FORBIDDEN:
            dispatch(notificationActions.updateFailureStatus(message))
            break;
        default:
            dispatch(notificationActions.updateFailureStatus("Something went wrong"));
            break;
    }
} 

export default errorHandler;