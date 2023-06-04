import fetchWithAuth from "../../utils/fetchWithAuth";
import { notificationActions } from "../notification-slice";
import { userFollowActions } from "../user-follow-slice";
import errorHandler from "./notification-action";

export const searchUser = (email,searchText) => async dispatch => {
    try{
        dispatch(notificationActions.updatePendingStatus())
        const res = await fetchWithAuth(`/api/search-user/${email}?searchText=${searchText}`);
        
        const data = await res.json();

        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode,data.message));
            return;
        }

        dispatch(notificationActions.updateSuccessStatus());
        dispatch(userFollowActions.setSearchResult(data));
    }catch(err){
        console.error(err)
        dispatch(userFollowActions.setSearchResult([]));
        dispatch(errorHandler(0,""));
    }
    
}

export const followUser = (userEmail,userToBeFollowed) =>async dispatch =>{
    try{
        dispatch(notificationActions.updatePendingStatus())
        const res = await fetchWithAuth('/api/user/follow',{
            method:'PUT',
            headers:{
                'Content-Type': 'application/json'
            },
            body:JSON.stringify({
                userEmail,
                userToBeFollowed
            })
        });
        const data = await res.json();

        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode,data.message));
            return;
        }
        if(data){
            dispatch(notificationActions.updateSuccessStatus());
            dispatch(userFollowActions.setIsFollowedByCurrentUser({email:userToBeFollowed}))
        }

    }catch(err){
        console.error(err)
        dispatch(errorHandler(0,""));
        // dispatch(userFollowActions.setSearchResult([]));
    }
}


export const unfollowUser = (userEmail,userToBeUnFollowed) =>async dispatch =>{
    try{
        dispatch(notificationActions.updatePendingStatus())
        const res = await fetchWithAuth('/api/user/unfollow',{
            method:'PUT',
            headers:{
                'Content-Type': 'application/json'
            },
            body:JSON.stringify({
                userEmail,
                userToBeUnFollowed
            })
        });
        const data = await res.json();

        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode,data.message));
            return;
        }
        if(data){
            dispatch(notificationActions.updateSuccessStatus());
            dispatch(userFollowActions.unsetIsFollowedByCurrentUser({email:userToBeUnFollowed}))
        }

    }catch(err){
        console.error(err)
        dispatch(errorHandler(0,""));
        // dispatch(userFollowActions.setSearchResult([]));
    }
}