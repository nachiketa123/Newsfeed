import fetchWithAuth from "../../utils/fetchWithAuth";
import { newsfeedSliceActions } from "../newsfeed-slice"
import { notificationActions } from "../notification-slice"
import errorHandler from "./notification-action";

/*
    API: TO FETCH THE FEEDITEMs DATA FOR A USER'S EMAIL
*/ 
export const getNewsFeedData = (email) => async (dispatch) => {

    try{
        dispatch(notificationActions.updatePendingStatus());
        const res = await fetchWithAuth(`/api/newsfeed/${email}`,{
            method:'GET',
        })
        const data = await res.json();
        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode, data.message));
            dispatch(newsfeedSliceActions.setFeedItem([]));
            return;
        }
        
        dispatch(notificationActions.updateSuccessStatus());
        dispatch(newsfeedSliceActions.setFeedItem(data));
    }catch(err){
        dispatch(errorHandler(0, ""));
        console.log(err);
    }
    
}

/*
    API: TO ADD A FEEDITEM/POST TO A USER'S NEWSFEED
*/ 
export const addFeedItem = (email,title,content) => async (dispatch) => {

    try{
        dispatch(notificationActions.updatePendingStatus());
        const res = await fetchWithAuth(`/api/newsfeed/${email}`,{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
              },
            body:JSON.stringify({title,content})
        })
        const data = await res.json();
        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode, data.message));
            return;
        }
        dispatch(notificationActions.updateSuccessStatus());
        dispatch(newsfeedSliceActions.addFeedItem(data));
    }catch(err){
        dispatch(errorHandler(0, ""));
        console.log(err);
    }
    
}

/*
    API: TO UPVOTE A USERS FEEDITEM/POST
*/ 
export const upvotePost = (email,index,feedItemId) => async (dispatch) => {

    try{
        dispatch(notificationActions.updatePendingStatus());
        const res = await fetchWithAuth(`/api/newsfeed/feeditem/${feedItemId}/upvote`,{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
              },
            body:email
        })
        const data = await res.json();
        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode, data.message));
            return;
        }
        
        const isLikedByUser = data.upvote.filter(user=> user.email === email).length !== 0
        const isDislikedByUser = data.downvote.filter(user=> user.email === email).length !== 0

        dispatch(notificationActions.updateSuccessStatus());
        dispatch(newsfeedSliceActions.updateUpvoteDownvoteOnFeedItemAt({
            ...data,
            index,
            isLikedByUser,
            isDislikedByUser,
        }));
    }catch(err){
        dispatch(errorHandler(0, ""));
        console.log(err);
    }
    
}

/*
    API: TO DOWNVOTE A USERS FEEDITEM/POST
*/ 
export const downvotePost = (email,index,feedItemId) => async (dispatch) => {

    try{
        dispatch(notificationActions.updatePendingStatus());
        const res = await fetchWithAuth(`/api/newsfeed/feeditem/${feedItemId}/downvote`,{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
              },
            body:email
        })
        const data = await res.json();
        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode, data.message));
            return;
        }
        
        const isLikedByUser = data.upvote.filter(user=> user.email === email).length !== 0
        const isDislikedByUser = data.downvote.filter(user=> user.email === email).length !== 0

        dispatch(notificationActions.updateSuccessStatus());
        dispatch(newsfeedSliceActions.updateUpvoteDownvoteOnFeedItemAt({
            ...data,
            index,
            isLikedByUser,
            isDislikedByUser,
        }));
    }catch(err){
        dispatch(errorHandler(0, ""));
        console.log(err);
    }
}
    
/*
    API: TO ADD COMMENT TO A USERS FEEDITEM/POST
*/ 
export const addComment = (userEmail,index,feedItemId,commentText) => async (dispatch) => {

    try{
        dispatch(notificationActions.updatePendingStatus());
        const res = await fetchWithAuth(`/api/newsfeed/feeditem/${feedItemId}/addComment`,{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
                },
            body:JSON.stringify({userEmail,commentText})
        })
        const data = await res.json();
        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode, data.message));
            return;
        }
        const obj = {
            comment:data,
            index
        }
        dispatch(notificationActions.updateSuccessStatus());
        dispatch(newsfeedSliceActions.addCommentOnFeedAt(obj))
    }catch(err){
        dispatch(errorHandler(0, ""));
        console.log(err);
    }
}

/*
    API: TO UPVOTE A COMMENT ON A USERS FEEDITEM/POST
*/ 
export const upvoteComment = (userEmail,feedItemIndex,commentIndex,feedItemId,comment_id) => async (dispatch) => {

    try{
        dispatch(notificationActions.updatePendingStatus());
        const res = await fetchWithAuth(`/api/newsfeed/feeditem/${feedItemId}/comment/${comment_id}/upvote`,{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
                },
            body:userEmail
        })
        const data = await res.json();
        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode, data.message));
            return;
        }

        const isLikedByTheUser = data.upvote.filter(user=> user.email === userEmail).length !== 0
        const isDislikedByTheUser = data.downvote.filter(user=> user.email === userEmail).length !== 0

        const obj = {
            ...data,
            feedItemIndex,
            commentIndex,
            isLikedByTheUser,
            isDislikedByTheUser
        }

        dispatch(notificationActions.updateSuccessStatus());
        dispatch(newsfeedSliceActions.updateUpvoteDownvoteOnCommentAt(obj))
    }catch(err){
        dispatch(errorHandler(0, ""));
        console.log(err);
    }
}

/*
    API: TO DOWNVOTE A COMMENT ON A USERS FEEDITEM/POST
*/ 
export const downvoteComment = (userEmail,feedItemIndex,commentIndex,feedItemId,comment_id) => async (dispatch) => {

    try{
        dispatch(notificationActions.updatePendingStatus());
        const res = await fetchWithAuth(`/api/newsfeed/feeditem/${feedItemId}/comment/${comment_id}/downvote`,{
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
                },
            body:userEmail
        })
        const data = await res.json();
        if(!res.ok){
            dispatch(errorHandler(data.httpStatusCode, data.message));
            return;
        }

        const isLikedByTheUser = data.upvote.filter(user=> user.email === userEmail).length !== 0
        const isDislikedByTheUser = data.downvote.filter(user=> user.email === userEmail).length !== 0

        const obj = {
            ...data,
            feedItemIndex,
            commentIndex,
            isLikedByTheUser,
            isDislikedByTheUser
        }

        dispatch(notificationActions.updateSuccessStatus());
        dispatch(newsfeedSliceActions.updateUpvoteDownvoteOnCommentAt(obj))
    }catch(err){
        dispatch(errorHandler(0, ""));
        console.log(err);
    }
}

